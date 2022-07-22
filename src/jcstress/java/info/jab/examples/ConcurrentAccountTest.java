package info.jab.examples;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.I_Result;

//@JCStressTest
@Outcome(id = "10", expect = Expect.ACCEPTABLE, desc = "actor1 incremented, then actor2.")
@Outcome(id = "20", expect = Expect.FORBIDDEN, desc = "actor1 incremented, then actor2.")
@Outcome(id = "40", expect = Expect.FORBIDDEN, desc = "actor1 incremented, then actor2.")
@State
public class ConcurrentAccountTest {

    Account account = new Account(50);

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
