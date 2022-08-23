package info.jab.ms.controller;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import static org.assertj.core.api.BDDAssertions.then;

@Disabled
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CalculatorControllersConcurrentE2Etest {

    @Autowired
    private TestRestTemplate restTemplate;
    @LocalServerPort
    int randomServerPort;

    private final String path = "/api/v1/";
    private final String sumEndpoint = "sum2numbers";
    private final String hitsEndpoint = "stats/hits";

    @Test
    public void given_sumEndpoint_when_sendConcurrentRequest_then_Ok() throws InterruptedException, ExecutionException {
        ExecutorService service = Executors.newFixedThreadPool(2);

        int numberOfIterations = 1_000;

        //Test one endpoint
        AtomicLong counter = new AtomicLong(0);
        for (int j = 1; j < numberOfIterations; j++) {
            //Given
            List<ConcurrentTask> taskList = new ArrayList<>();
            Long experimentParameter1 = counter.incrementAndGet();
            Long experimentParameter2 = counter.incrementAndGet();
            taskList.add(new ConcurrentTask(restTemplate, randomServerPort, path + sumEndpoint, experimentParameter1));
            taskList.add(new ConcurrentTask(restTemplate, randomServerPort, path + sumEndpoint, experimentParameter2));

            //When
            List<Future<Long>> futures = service.invokeAll(taskList);

            //Then
            Long result1 = futures.get(0).get().longValue();
            Long result2 = futures.get(1).get().longValue();
            then(experimentParameter1 * 2).isEqualTo(result1);
            then(experimentParameter2 * 2).isEqualTo(result2);
            then(result1).isNotEqualTo(result2);
        }

        service.shutdown();
        service.awaitTermination(10, TimeUnit.SECONDS);
    }

    @Test
    public void given_hitEndpoint_when_simulateConcurrentScenario_then_Ok() throws InterruptedException, ExecutionException {
        ExecutorService service = Executors.newFixedThreadPool(2);

        int numberOfIterations = 1_000;

        //Test one endpoint
        AtomicLong counter = new AtomicLong(0);
        for (int j = 1; j < numberOfIterations; j++) {
            //Given
            List<ConcurrentTask> taskList = new ArrayList<>();
            Long experimentParameter1 = counter.incrementAndGet();
            Long experimentParameter2 = counter.incrementAndGet();
            taskList.add(new ConcurrentTask(restTemplate, randomServerPort, path + sumEndpoint, experimentParameter1));
            taskList.add(new ConcurrentTask(restTemplate, randomServerPort, path + sumEndpoint, experimentParameter2));

            //When
            List<Future<Long>> futures = service.invokeAll(taskList);
        }

        service.shutdown();
        service.awaitTermination(10, TimeUnit.SECONDS);

        //Given
        final String baseUrl = "http://localhost:" + randomServerPort + path + hitsEndpoint;

        //When
        ResponseEntity<Long> result = this.restTemplate.getForEntity(baseUrl, Long.class);

        //Then
        then(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(result.getBody()).isEqualTo(1_000 * 2);
    }

}
