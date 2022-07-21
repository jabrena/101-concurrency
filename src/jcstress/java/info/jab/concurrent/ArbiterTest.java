package info.jab.concurrent;

import org.openjdk.jcstress.annotations.Actor;
import org.openjdk.jcstress.annotations.Arbiter;
import org.openjdk.jcstress.annotations.Description;
import org.openjdk.jcstress.annotations.Expect;
import org.openjdk.jcstress.annotations.JCStressTest;
import org.openjdk.jcstress.annotations.Outcome;
import org.openjdk.jcstress.annotations.State;
import org.openjdk.jcstress.infra.results.I_Result;

@JCStressTest
@Description("Tests concurrent increments.")
@Outcome(id = {"1", "2"}, expect = Expect.ACCEPTABLE_INTERESTING, desc = "Legal interleavings")
@State
// Shows that the @Arbiter Method is scheduled after threads
public class ArbiterTest {
    private volatile int cnt = 0;

    @Actor
    public void thread1() {
        cnt = 1;
    }

    @Actor
    public void thread2() {
        cnt = 2;
    }

    @Arbiter
    public void observe(I_Result res) {
        res.r1 = cnt;
    }
}
