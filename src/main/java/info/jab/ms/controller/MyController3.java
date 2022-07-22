package info.jab.ms.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Scenario 2: Shared state with an Atomic variable
 */
@RestController
@RequestMapping(value = "api/v3", produces = MediaType.APPLICATION_JSON_VALUE)
public class MyController3 {
    private AtomicLong result = new AtomicLong(0);

    @PostMapping("sum2numbers")
    public synchronized ResponseEntity<Long> sum2numbers(@RequestBody SumRequest parameters) {
        this.result.addAndGet(parameters.operator1() + parameters.operator2());
        var result = ResponseEntity.ok().body(this.result.get());
        this.result.set(0);
        return result;
    }
}
