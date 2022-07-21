package info.jab.concurrent;

import org.openjdk.jcstress.annotations.Actor;
import org.openjdk.jcstress.annotations.Arbiter;
import org.openjdk.jcstress.annotations.Expect;
import org.openjdk.jcstress.annotations.JCStressTest;
import org.openjdk.jcstress.annotations.Outcome;
import org.openjdk.jcstress.annotations.State;
import org.openjdk.jcstress.infra.results.I_Result;

@JCStressTest
@Outcome(id = "10", expect = Expect.ACCEPTABLE, desc = "actor1 incremented, then actor2.")
@Outcome(id = "20", expect = Expect.FORBIDDEN, desc = "actor1 incremented, then actor2.")
@Outcome(id = "40", expect = Expect.FORBIDDEN, desc = "actor1 incremented, then actor2.")
@State
public class ConcurrentAccountTest2 {

    Account2 account = new Account2(50);

    @Actor
    void actor1() {
        account.withdraw(10); // record result from actor1 to field r1
    }

    @Actor
    void actor2() {
        account.withdraw(30); // record result from actor2 to field r2
    }

    @Arbiter
    public void observe(I_Result res) {
        res.r1 = account.getBalance();
    }
}
