package info.jab.concurrent;

import org.openjdk.jcstress.annotations.Actor;
import org.openjdk.jcstress.annotations.Arbiter;
import org.openjdk.jcstress.annotations.Expect;
import org.openjdk.jcstress.annotations.JCStressTest;
import org.openjdk.jcstress.annotations.Outcome;
import org.openjdk.jcstress.annotations.State;
import org.openjdk.jcstress.infra.results.I_Result;

import java.util.concurrent.atomic.AtomicInteger;

@JCStressTest
@State
@Outcome(id="2",expect = Expect.ACCEPTABLE,desc = "both update")
@Outcome(id="1",expect = Expect.FORBIDDEN,desc = "lost one update")
public class AtomicIntegerTest {

    private final AtomicInteger sequencer = new AtomicInteger();

    @Actor
    public void actor1(){
        sequencer.incrementAndGet();
    }

    @Actor
    public void actor2(){
        sequencer.incrementAndGet();
    }

    @Arbiter
    public void arbiter(I_Result r){
        r.r1= sequencer.get();
    }
}
