package info.jab.ms.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Scenario 2: Shared state with a type
 */
@RestController
@RequestMapping(value = "api/v2", produces = MediaType.APPLICATION_JSON_VALUE)
public class MyController2 {
    private Long result = 0L;

    @PostMapping("sum2numbers")
    public ResponseEntity<Long> sum2numbers(@RequestBody SumRequest parameters) {
        synchronized (this) {
            this.result = parameters.operator1() + parameters.operator2();
            return ResponseEntity.ok().body(this.result);
        }
    }
}
