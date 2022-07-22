package info.jab.ms.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Scenario 1: Immutable case
 */
@RestController
@RequestMapping(value = "api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class MyController1 {

    @PostMapping("sum2numbers")
    public ResponseEntity<Long> sum2numbers(@RequestBody SumRequest parameters) {
        Long result = parameters.operator1() + parameters.operator2();
        return ResponseEntity.ok().body(result);
    }
}
