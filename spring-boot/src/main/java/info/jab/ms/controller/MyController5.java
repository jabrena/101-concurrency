package info.jab.ms.controller;

import info.jab.ms.model.Calculator5;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Scenario 5: Shared state fixed in the Guilty object
 */
@RestController
@RequestMapping(value = "api/v5", produces = MediaType.APPLICATION_JSON_VALUE)
public class MyController5 {
    private Calculator5 calculator = new Calculator5();

    @PostMapping("sum2numbers")
    public ResponseEntity<Long> sum2numbers(@RequestBody SumRequest parameters) {
            return ResponseEntity.ok().body(calculator.calculate(parameters.operator1(), parameters.operator2()));
    }
}
