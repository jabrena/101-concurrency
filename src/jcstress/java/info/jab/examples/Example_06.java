package info.jab.examples;

import org.openjdk.jcstress.annotations.Actor;
import org.openjdk.jcstress.annotations.Expect;
import org.openjdk.jcstress.annotations.JCStressTest;
import org.openjdk.jcstress.annotations.Outcome;
import org.openjdk.jcstress.annotations.State;
import org.openjdk.jcstress.infra.results.II_Result;

@JCStressTest
@Outcome(id = "0, 0", expect = Expect.ACCEPTABLE_INTERESTING)
@Outcome(id = "0, 1", expect = Expect.ACCEPTABLE)
@Outcome(id = "1, 1", expect = Expect.ACCEPTABLE)
@Outcome(id = "1, 0", expect = Expect.ACCEPTABLE)
@State
public class Example_06 {
    int x;
    int y;

    @Actor
    public void actor1(II_Result r) {
        x = 1;
        r.r1 = y;
    }

    @Actor
    public void actor2(II_Result r) {
        y = 1;
        r.r2 = x;
    }

}
