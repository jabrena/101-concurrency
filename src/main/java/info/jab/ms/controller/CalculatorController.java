package info.jab.ms.controller;

import info.jab.ms.api.model.SumRequestDto;
import info.jab.ms.model.Calculator5;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Scenario 1: Sharing state in a non-necessary case
 */
@RestController
public class CalculatorController implements info.jab.ms.api.ApiApi {

    private Long counter = Long.valueOf(0);
    private Calculator5 calculator = new Calculator5();

    @Override
    public ResponseEntity<Long> sum2numbers(SumRequestDto sumRequestDto) {
        counter++;
        return ResponseEntity.ok(calculator.calculate(sumRequestDto.getOperator1(), sumRequestDto.getOperator2()));
    }

    @GetMapping("/api/v1/stats/hits")
    public ResponseEntity<Long> getHitCounter() {
        return ResponseEntity.ok(counter);
    }
}
