package info.jab.ms.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.BDDAssertions.then;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CalculatorControllersE2Etest {

    @Autowired
    private TestRestTemplate restTemplate;
    @LocalServerPort
    int randomServerPort;

    private final String path = "/api/v1/";
    private final String sumEndpoint = "sum2numbers";
    private final String hitsEndpoint = "stats/hits";

    @Test
    public void given_sumEndpoint_when_sendValidRequest_then_Ok() {

        //Given
        final String baseUrl = "http://localhost:" + randomServerPort + path + sumEndpoint;

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
    public void given_hitsEndpoint_when_reach_then_Ok() {

        //Given
        final String baseUrl = "http://localhost:" + randomServerPort + path + hitsEndpoint;

        //When
        ResponseEntity<Long> result = this.restTemplate.getForEntity(baseUrl, Long.class);

        //Then
        then(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(result.getBody()).isEqualTo(0);
    }
}
