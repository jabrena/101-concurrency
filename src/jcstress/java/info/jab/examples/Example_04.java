package info.jab.examples;

import org.openjdk.jcstress.annotations.Actor;
import org.openjdk.jcstress.annotations.Expect;
import org.openjdk.jcstress.annotations.JCStressTest;
import org.openjdk.jcstress.annotations.Mode;
import org.openjdk.jcstress.annotations.Outcome;
import org.openjdk.jcstress.annotations.Signal;
import org.openjdk.jcstress.annotations.State;

public class Example_04 {

    @JCStressTest(Mode.Termination)
    @Outcome(id = "TERMINATED", expect = Expect.ACCEPTABLE, desc = "Gracefully finished.")
    @Outcome(id = "STALE", expect = Expect.ACCEPTABLE_INTERESTING, desc = "Test hung up.")
    @State
    public static class Case5 {

        AuxCase5 v = new AuxCase5();

        @Actor
        public void actor1() {
            v.poll();
        }

        @Signal
        public void actor2() {
            v.enable();
        }
    }

    public static class AuxCase5 {

        boolean flag = false;

        public void enable() {
            flag = true;
        }

        public void poll() {
            while(flag == false) {
                //Spin
            }
        }
    }

    @JCStressTest(Mode.Termination)
    @Outcome(id = "TERMINATED", expect = Expect.ACCEPTABLE, desc = "Gracefully finished.")
    @Outcome(id = "STALE", expect = Expect.ACCEPTABLE_INTERESTING, desc = "Test hung up.")
    @State
    public static class Case6 {

        AuxCase6 v = new AuxCase6();

        @Actor
        public void actor1() {
            v.poll();
        }

        @Signal
        public void actor2() {
            v.enable();
        }
    }

    public static class AuxCase6 {

        volatile boolean flag = false;

        public void enable() {
            flag = true;
        }

        public void poll() {
            while(flag == false) {
                //Process
            }
        }
    }

}
