package info.jab.ms.controller;

import info.jab.ms.model.Calculator4;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Scenario 4: Problems with Atomicity
 */
@RestController
@RequestMapping(value = "api/v4", produces = MediaType.APPLICATION_JSON_VALUE)
public class MyController4 {

    private Calculator4 calculator;

    @PostMapping("sum2numbers")
    public ResponseEntity<Long> sum2numbers(@RequestBody SumRequest parameters) {
        synchronized (this) {
            this.calculator = new Calculator4(parameters.operator1(), parameters.operator2());
            return ResponseEntity.ok().body(calculator.getResult());
        }
    }
}
