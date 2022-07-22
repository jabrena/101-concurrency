package info.jab.ms;

import info.jab.ms.model.Calculator5;
import info.jab.ms.model.Calculator6;
import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.JJ_Result;

@JCStressTest
@Outcome(id = "2, 4", expect = Expect.ACCEPTABLE, desc = "actor1 incremented, then actor2.")
@Outcome(id = "4, 2", expect = Expect.ACCEPTABLE, desc = "actor1 incremented, then actor2.")
@Outcome(id = "2, 2", expect = Expect.FORBIDDEN, desc = "actor1 incremented, then actor2.")
@Outcome(id = "4, 4", expect = Expect.FORBIDDEN, desc = "actor1 incremented, then actor2.")
@State
public class Calculator6Test {

    Calculator6 calculator = new Calculator6();;

    @Actor
    void actor1(JJ_Result res) {
        res.r1 = calculator.calculate(1L, 1L);
    }

    @Actor
    void actor2(JJ_Result res) {
        res.r2 = calculator.calculate(2L, 2L);
    }
}
