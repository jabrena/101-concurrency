package info.jab.ms;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import static org.assertj.core.api.BDDAssertions.then;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MyControllersE2Etest {

    @Autowired
    private TestRestTemplate restTemplate;
    @LocalServerPort
    int randomServerPort;

    private final String endpoint = "/sum2numbers";

    @Test
    public void given_endpoint_when_sendParameters_then_Ok() {

        //Given
        final String baseUrl = "http://localhost:" + randomServerPort + "/api/v4" + endpoint;

        //When
        Map sumParameters = new HashMap<String, String>();
        sumParameters.put("operator1","2");
        sumParameters.put("operator2","2");
        ResponseEntity<Long> result = this.restTemplate.postForEntity(baseUrl, sumParameters, Long.class);

        //Then
        var expectedSum = sumParameters
                .values()
                .stream()
                .mapToLong(x -> Long.parseLong((String) x))
                .reduce(0, Long::sum);
        then(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(result.getBody()).isEqualTo(expectedSum);
    }

    @Test
    public void concurrencyOneTest() throws InterruptedException, ExecutionException {
        ExecutorService service = Executors.newFixedThreadPool(2);

        String version = "v5";
        int numberOfIterations = 50;

        //Test one endpoint
        AtomicLong counter = new AtomicLong(0);
        for (int j = 1; j < numberOfIterations; j++) {
            List<ConcurrentTask> taskList = new ArrayList<>();
            Long experimentParameter1 = counter.incrementAndGet();
            Long experimentParameter2 = counter.incrementAndGet();
            taskList.add(new ConcurrentTask(restTemplate, randomServerPort, "/api/" + version + endpoint, experimentParameter1));
            taskList.add(new ConcurrentTask(restTemplate, randomServerPort, "/api/" + version + endpoint, experimentParameter2));

            List<Future<Long>> futures = service.invokeAll(taskList);
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
    public void concurrencyTest() throws InterruptedException, ExecutionException {
        ExecutorService service = Executors.newFixedThreadPool(2);

        int numberOfVersions = 6;
        int numberOfIterations = 50;

        //Test all endpoints
        for (int i = 1; i <= numberOfVersions; i++) {

            AtomicLong counter = new AtomicLong(0);
            for (int j = 1; j < numberOfIterations; j++) {
                List<ConcurrentTask> taskList = new ArrayList<>();
                Long experimentParameter1 = counter.incrementAndGet();
                Long experimentParameter2 = counter.incrementAndGet();
                taskList.add(new ConcurrentTask(restTemplate, randomServerPort, "/api/v" + i + endpoint, experimentParameter1));
                taskList.add(new ConcurrentTask(restTemplate, randomServerPort, "/api/v" + i + endpoint, experimentParameter2));

                List<Future<Long>> futures = service.invokeAll(taskList);
                Long result1 = futures.get(0).get().longValue();
                Long result2 = futures.get(1).get().longValue();

                then(experimentParameter1 * 2).isEqualTo(result1);
                then(experimentParameter2 * 2).isEqualTo(result2);
                then(result1).isNotEqualTo(result2);
            }
        }

        service.shutdown();
        service.awaitTermination(10, TimeUnit.SECONDS);
    }
}
