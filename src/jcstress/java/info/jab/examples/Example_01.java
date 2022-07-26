package info.jab.examples;

import org.openjdk.jcstress.annotations.Actor;
import org.openjdk.jcstress.annotations.Expect;
import org.openjdk.jcstress.annotations.JCStressTest;
import org.openjdk.jcstress.annotations.Outcome;
import org.openjdk.jcstress.annotations.State;
import org.openjdk.jcstress.infra.results.II_Result;

public class Example_01 {

    @JCStressTest
    @Outcome(id = "1, 1", expect = Expect.ACCEPTABLE_INTERESTING, desc = "Both actors came up with the same value: atomicity failure.")
    @Outcome(id = "1, 2", expect = Expect.ACCEPTABLE, desc = "actor1 incremented, then actor2.")
    @Outcome(id = "2, 1", expect = Expect.ACCEPTABLE, desc = "actor2 incremented, then actor1.")
    @State
    public static class Case1 {

        int v;

        @Actor
        public void actor1(II_Result r) {
            r.r1 = ++v; // record result from actor1 to field r1
        }

        @Actor
        public void actor2(II_Result r) {
            r.r2 = ++v; // record result from actor2 to field r2
        }

    }

    @JCStressTest
    @Outcome(id = "1, 1", expect = Expect.ACCEPTABLE_INTERESTING, desc = "Both actors came up with the same value: atomicity failure.")
    @Outcome(id = "1, 2", expect = Expect.ACCEPTABLE, desc = "actor1 incremented, then actor2.")
    @Outcome(id = "2, 1", expect = Expect.ACCEPTABLE, desc = "actor2 incremented, then actor1.")
    @State
    public static class Case2 {

        AuxCase2 v = new AuxCase2();

        @Actor
        public void actor1(II_Result r) {
            r.r1 = v.add(); // record result from actor1 to field r1
        }

        @Actor
        public void actor2(II_Result r) {
            r.r2 = v.add(); // record result from actor2 to field r2
        }

    }

    public static class AuxCase2 {

        private int v;

        public int add() {
            return ++v;
        }
    }

}
