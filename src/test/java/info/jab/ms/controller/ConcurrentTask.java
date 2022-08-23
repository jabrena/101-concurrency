package info.jab.ms.controller;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

class ConcurrentTask implements Callable<Long> {
    private final String endpoint;
    private final Long operator1;
    private final Long operator2;
    private final int randomServerPort;
    private final TestRestTemplate restTemplate;

    ConcurrentTask(TestRestTemplate restTemplate, int randomServerPort, String endpoint, Long parameter) {
        this.restTemplate = restTemplate;
        this.randomServerPort = randomServerPort;
        this.endpoint = endpoint;
        this.operator1 = parameter;
        this.operator2 = parameter;
    }

    @Override
    public Long call() {
        String baseUrl = "http://localhost:" + randomServerPort + endpoint;
        ResponseEntity<Long> result = this.restTemplate.postForEntity(baseUrl, getBodyPayload(), Long.class);
        return result.getBody();
    }

    private Map<String, String> getBodyPayload() {
        Map sumParameters = new HashMap<String, String>();
        sumParameters.put("operator1", String.valueOf(this.operator1));
        sumParameters.put("operator2", String.valueOf(this.operator2));
        return sumParameters;
    }
}
