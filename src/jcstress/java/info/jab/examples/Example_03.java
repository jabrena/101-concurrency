package info.jab.examples;

import org.openjdk.jcstress.annotations.Actor;
import org.openjdk.jcstress.annotations.Expect;
import org.openjdk.jcstress.annotations.JCStressTest;
import org.openjdk.jcstress.annotations.Mode;
import org.openjdk.jcstress.annotations.Outcome;
import org.openjdk.jcstress.annotations.Signal;
import org.openjdk.jcstress.annotations.State;
import org.openjdk.jcstress.infra.results.ZZ_Result;

public class Example_03 {

    @JCStressTest
    @Outcome(id = "false, true", expect = Expect.ACCEPTABLE_INTERESTING, desc = "Actor1, doesn´t see changes from Actor2")
    @Outcome(id = "true, true", expect = Expect.ACCEPTABLE, desc = "Actor1, see changes from Actor2")
    @State
    public static class Case1 {

        boolean flag;

        //Read
        @Actor
        public void actor1(ZZ_Result r) {
            /*
            //Business Logic in this Thread
            while (flag) {
                // spin
            }
            if(flag) {
                System.out.println("Ok");
            }
            */
            r.r1 = flag;
        }

        //Write
        @Actor
        public void actor2(ZZ_Result r) {
            flag = true;
            r.r2 = flag;
        }
    }

    @JCStressTest
    @Outcome(id = "false, true", expect = Expect.ACCEPTABLE_INTERESTING, desc = "Actor1, doesn´t see changes from Actor2")
    @Outcome(id = "true, true", expect = Expect.ACCEPTABLE, desc = "Actor1, see changes from Actor2")
    @State
    public static class Case2 {

        volatile boolean flag = false;

        //Read
        @Actor
        public void actor1(ZZ_Result r) {
            /*
            //Business Logic in this Thread
            while (flag) {
                // spin
            }
            if(flag) {
                System.out.println("Ok");
            }
            */
            r.r1 = flag;
        }

        //Write
        @Actor
        public void actor2(ZZ_Result r) {
            flag = true;
            r.r2 = flag;
        }
    }

    @JCStressTest(Mode.Termination)
    @Outcome(id = "TERMINATED", expect = Expect.ACCEPTABLE, desc = "Gracefully finished.")
    @Outcome(id = "STALE", expect = Expect.ACCEPTABLE_INTERESTING, desc = "Test hung up.")
    @State
    public static class Case3 {
        int v;

        @Actor
        public void actor1() {
            while (v == 0) {
                // spin
            }
        }

        @Signal
        public void signal() {
            v = 1;
        }
    }

    @JCStressTest(Mode.Termination)
    @Outcome(id = "TERMINATED", expect = Expect.ACCEPTABLE, desc = "Gracefully finished.")
    @Outcome(id = "STALE", expect = Expect.ACCEPTABLE_INTERESTING, desc = "Test hung up.")
    @State
    public static class Case4 {

        volatile int v;

        @Actor
        public void actor1() {
            while (v == 0) {
                // spin
            }
        }

        @Signal
        public void signal() {
            v = 1;
        }
    }

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
