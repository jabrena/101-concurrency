package info.jab.ms.model;

/**
 * TODO Review the design.
 */
public class Calculator4 {
        private final Long operator1;
        private final Long operator2;
        private Long result;

        public Calculator4(Long operator1, Long operator2) {
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
