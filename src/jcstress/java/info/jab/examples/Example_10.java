package info.jab.examples;

import org.openjdk.jcstress.annotations.Actor;
import org.openjdk.jcstress.annotations.Expect;
import org.openjdk.jcstress.annotations.JCStressTest;
import org.openjdk.jcstress.annotations.Outcome;
import org.openjdk.jcstress.annotations.State;
import org.openjdk.jcstress.infra.results.I_Result;

//Happens Before
@JCStressTest
@Outcome(id = "1", expect = Expect.ACCEPTABLE, desc = "Actor2 is executed before Actor1")
@Outcome(id = "2", expect = Expect.ACCEPTABLE, desc = "Actor2 is executed after y = 2 and before x = 3 in Actor1")
@Outcome(id = "3", expect = Expect.ACCEPTABLE_INTERESTING, desc = "y = 2 can not be reordered with x = 3 as we have volatile on x") //Forbidden
@Outcome(id = "6", expect = Expect.ACCEPTABLE, desc = "Actor2 executed after Actor1")
@State
public class Example_10 {
    int y = 1;
    volatile int x = 1;

    @Actor
    void actor1() {
        y = 2;
        x = 3;
    }

    @Actor
    void actor2(I_Result r) {
        r.r1 = y * x;
    }
}
