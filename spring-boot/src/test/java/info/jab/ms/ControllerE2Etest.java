package info.jab.ms;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;
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
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.BDDAssertions.then;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ControllerE2Etest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    int randomServerPort;

    @Test
    public void given_endpoint_when_sendParameters_then_Ok() {

        //Given
        final String baseUrl = "http://localhost:" + randomServerPort + "/api/v1/sum2numbers";

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

    public class Task implements Callable<Long> {

        private final String endpoint = "/api/v1/sum2numbers";
        private final Long operator1;
        private final Long operator2;

        private final TestRestTemplate restTemplate;

        Task(TestRestTemplate restTemplate, Long parameter) {
            this.restTemplate = restTemplate;
            this.operator1 = parameter;
            this.operator2 = parameter;
        }

        private Long process() {
            final String baseUrl = "http://localhost:" + randomServerPort + "/api/v1/sum2numbers";

            //When
            Map sumParameters = new HashMap<String, String>();
            sumParameters.put("operator1", String.valueOf(this.operator1));
            sumParameters.put("operator2", String.valueOf(this.operator2));
            ResponseEntity<Long> result = this.restTemplate.postForEntity(baseUrl, sumParameters, Long.class);
            return result.getBody();
        }

        @Override
        public Long call() {
            return this.process();
        }
    }

    @Test
    public void concurrencyTest() throws InterruptedException, ExecutionException {
        ExecutorService service = Executors.newFixedThreadPool(2);

        //When
        //Then
        AtomicLong counter = new AtomicLong(0);
        for (int i = 1; i < 5; i++) {
            List<Task> taskList = new ArrayList<>();
            Long experimentParameter1 = counter.incrementAndGet();
            Long experimentParameter2 = counter.incrementAndGet();
            taskList.add(new Task(restTemplate, experimentParameter1));
            taskList.add(new Task(restTemplate, experimentParameter2));

            List<Future<Long>> futures = service.invokeAll(taskList);
            Long result1 = futures.get(0).get().longValue();
            Long result2 = futures.get(1).get().longValue();

            then(experimentParameter1 * 2).isEqualTo(result1);
            then(experimentParameter2 * 2).isEqualTo(result2);
            then(result1).isNotEqualTo(result2);
        }

        service.shutdown();
        service.awaitTermination(1, TimeUnit.MINUTES);
    }
}
