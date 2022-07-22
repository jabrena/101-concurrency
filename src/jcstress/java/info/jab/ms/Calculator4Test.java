package info.jab.ms;

import info.jab.ms.model.Calculator4;
import org.openjdk.jcstress.annotations.Actor;
import org.openjdk.jcstress.annotations.Expect;
import org.openjdk.jcstress.annotations.JCStressTest;
import org.openjdk.jcstress.annotations.Outcome;
import org.openjdk.jcstress.annotations.State;
import org.openjdk.jcstress.infra.results.JJ_Result;

//@JCStressTest
@Outcome(id = "2, 4", expect = Expect.ACCEPTABLE, desc = "actor1 incremented, then actor2.")
@Outcome(id = "4, 2", expect = Expect.ACCEPTABLE, desc = "actor1 incremented, then actor2.")
@Outcome(id = "2, 2", expect = Expect.FORBIDDEN, desc = "actor1 incremented, then actor2.")
@Outcome(id = "4, 4", expect = Expect.FORBIDDEN, desc = "actor1 incremented, then actor2.")
@State
public class Calculator4Test {

    Calculator4 calculator;

    @Actor
    void actor1(JJ_Result res) {
        calculator = new Calculator4(1L, 1L);
        res.r1 = calculator.getResult();
    }

    @Actor
    void actor2(JJ_Result res) {
        calculator = new Calculator4(2L, 2L);
        res.r2 = calculator.getResult();
    }
}
