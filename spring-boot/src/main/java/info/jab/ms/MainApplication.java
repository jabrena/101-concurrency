package info.jab.ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
@SpringBootApplication
public class MainApplication {

	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);
	}

	record SumRequest(Long operator1, Long operator2) {}

	private Long result = 0L;
	private Calculator calculator;

	@PostMapping("sum2numbers")
	public ResponseEntity<Long> sum2numbers(@RequestBody SumRequest parameters) {
		//Long result = parameters.operator1() + parameters.operator2();
		//this.result = parameters.operator1() + parameters.operator2();
		synchronized (this) {
			this.calculator = new Calculator(parameters.operator1(), parameters.operator2());
			this.result = this.calculator.getResult();
			//return ResponseEntity.ok().body(this.result);
			return ResponseEntity.ok().body(calculator.getResult());
		}
	}

	private class Calculator {
		private final Long operator1;
		private final Long operator2;
		private Long result;

		public Calculator(Long operator1, Long operator2) {
			this.operator1 = operator1;
			this.operator2 = operator2;
			this.calculate();
		}

		public void calculate() {
			this.result = this.operator1 + this.operator2;
		}

		public Long getResult() {
			return this.result;
		}
	}

}
