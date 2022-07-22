package info.jab.ms.controller;

import info.jab.ms.model.Calculator6;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Scenario 6: No Shared state
 */
@RestController
@RequestMapping(value = "api/v6", produces = MediaType.APPLICATION_JSON_VALUE)
public class MyController6 {
    private Calculator6 calculator = new Calculator6();

    @PostMapping("sum2numbers")
    public ResponseEntity<Long> sum2numbers(@RequestBody SumRequest parameters) {
            return ResponseEntity.ok().body(calculator.calculate(parameters.operator1(), parameters.operator2()));
    }
}
