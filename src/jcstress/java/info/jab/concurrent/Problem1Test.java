package info.jab.concurrent;


import org.openjdk.jcstress.annotations.Actor;
import org.openjdk.jcstress.annotations.Arbiter;
import org.openjdk.jcstress.annotations.Expect;
import org.openjdk.jcstress.annotations.JCStressTest;
import org.openjdk.jcstress.annotations.Outcome;
import org.openjdk.jcstress.annotations.State;
import org.openjdk.jcstress.infra.results.I_Result;
import org.openjdk.jcstress.infra.results.JJ_Result;

public class Problem1Test {

    //@JCStressTest
    @Outcome(id = "1", expect = Expect.FORBIDDEN, desc = "The object has an issue")
    @Outcome(id = "2", expect = Expect.ACCEPTABLE, desc = "Both actors increment the value")
    @State
    public static class Scenario1 {

        IProblem1 problem1 = new Problem1();

        @Actor
        public void thread1() {
            problem1.increment();
        }

        @Actor
        public void thread2() {
            problem1.increment();
        }

        @Arbiter
        public void observer(I_Result res) {
            res.r1 = Math.toIntExact(problem1.getCounter());
        }

    }

    //@JCStressTest
    @Outcome(id = "1", expect = Expect.FORBIDDEN, desc = "The object has an issue")
    @Outcome(id = "2", expect = Expect.ACCEPTABLE, desc = "Both actors increment the value")
    @State
    public static class Scenario2 {

        IProblem1 problem1 = new Problem1_V2();

        @Actor
        public void thread1() {
            problem1.increment();
        }

        @Actor
        public void thread2() {
            problem1.increment();
        }

        @Arbiter
        public void observer(I_Result res) {
            res.r1 = Math.toIntExact(problem1.getCounter());
        }

    }

    // d @JCStressTest
    @Outcome(id = "1", expect = Expect.FORBIDDEN, desc = "The object has an issue")
    @Outcome(id = "2", expect = Expect.ACCEPTABLE, desc = "Both actors increment the value")
    @State
    public static class Scenario3 {

        IProblem1 problem1 = new Problem1_V3();

        @Actor
        public void thread1() {
            problem1.increment();
        }

        @Actor
        public void thread2() {
            problem1.increment();
        }

        @Arbiter
        public void observer(I_Result res) {
            res.r1 = Math.toIntExact(problem1.getCounter());
        }

    }

    // d @JCStressTest
    @Outcome(id = "1", expect = Expect.FORBIDDEN, desc = "The object has an issue")
    @Outcome(id = "2", expect = Expect.ACCEPTABLE, desc = "Both actors increment the value")
    @State
    public static class Scenario4 {

        IProblem1 problem1 = new Problem1_V4();

        @Actor
        public void thread1() {
            problem1.increment();
        }

        @Actor
        public void thread2() {
            problem1.increment();
        }

        @Arbiter
        public void observer(I_Result res) {
            res.r1 = Math.toIntExact(problem1.getCounter());
        }

    }

    // d @JCStressTest
    @Outcome(id = "1", expect = Expect.FORBIDDEN, desc = "The object has an issue")
    @Outcome(id = "2", expect = Expect.ACCEPTABLE, desc = "Both actors increment the value")
    @State
    public static class Scenario5 {

        IProblem1 problem1 = new Problem1_V5();

        @Actor
        public void thread1() {
            problem1.increment();
        }

        @Actor
        public void thread2() {
            problem1.increment();
        }

        @Arbiter
        public void observer(I_Result res) {
            res.r1 = Math.toIntExact(problem1.getCounter());
        }

    }

    @JCStressTest
    @Outcome(id = "1, 0", expect = Expect.ACCEPTABLE, desc = "Both actors increment the value")
    @Outcome(id = "1, 1", expect = Expect.ACCEPTABLE, desc = "The object has an issue")
    @State
    public static class Scenario6 {

        IProblem1 problem1 = new Problem1_V5();

        @Actor
        public void thread1(JJ_Result r) {
            r.r1 = problem1.increment();
        }

        @Actor
        public void thread2(JJ_Result r) {
            r.r2 = problem1.getCounter();
        }

    }
}
