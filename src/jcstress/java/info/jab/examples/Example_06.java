package info.jab.examples;

import org.openjdk.jcstress.annotations.Actor;
import org.openjdk.jcstress.annotations.JCStressTest;
import org.openjdk.jcstress.annotations.Outcome;
import org.openjdk.jcstress.annotations.State;
import org.openjdk.jcstress.infra.results.II_Result;

import static org.openjdk.jcstress.annotations.Expect.ACCEPTABLE;
import static org.openjdk.jcstress.annotations.Expect.ACCEPTABLE_INTERESTING;

@JCStressTest
@Outcome(id = {"0, 1", "1, 0", "1, 1"}, expect = ACCEPTABLE, desc = "ok")
@Outcome(id = "0, 0", expect = ACCEPTABLE_INTERESTING, desc = "danger")
@State
public class Example_06 {
    volatile int x;
    volatile int y;

    @Actor
    public void actor1(II_Result r) {
        x = 1;
        r.r2 = y;
    }

    @Actor
    public void actor2(II_Result r) {
        y = 1;
        r.r1 = x;
    }
}
