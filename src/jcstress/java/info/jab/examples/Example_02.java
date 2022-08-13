package info.jab.examples;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.II_Result;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class Example_02 {

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
            r.r1 = v.add1(); // record result from actor1 to field r1
        }

        @Actor
        public void actor2(II_Result r) {
            r.r2 = v.add2(); // record result from actor2 to field r2
        }

    }

    public static class AuxCase2 {

        private int v;

        public int add1() {
            return ++v;
        }

        public int add2() {
            return ++v;
        }
    }

    @JCStressTest
    @Outcome(id = "1, 1", expect = Expect.ACCEPTABLE_INTERESTING, desc = "Both actors came up with the same value: atomicity failure.")
    @Outcome(id = "1, 2", expect = Expect.ACCEPTABLE, desc = "actor1 incremented, then actor2.")
    @Outcome(id = "2, 1", expect = Expect.ACCEPTABLE, desc = "actor2 incremented, then actor1.")
    @State
    public static class Case3 {

        AuxCase3 v = new AuxCase3();

        @Actor
        public void actor1(II_Result r) {
            r.r1 = v.add1(); // record result from actor1 to field r1
        }

        @Actor
        public void actor2(II_Result r) {
            r.r2 = v.add2(); // record result from actor2 to field r2
        }

    }

    public static class AuxCase3 {

        private volatile int v;

        public int add1() {
            return ++v;
        }

        public int add2() {
            return ++v;
        }
    }

    @JCStressTest
    @Outcome(id = "1, 1", expect = Expect.ACCEPTABLE_INTERESTING, desc = "Both actors came up with the same value: atomicity failure.")
    @Outcome(id = "1, 2", expect = Expect.ACCEPTABLE, desc = "actor1 incremented, then actor2.")
    @Outcome(id = "2, 1", expect = Expect.ACCEPTABLE, desc = "actor2 incremented, then actor1.")
    @State
    public static class Case4 {

        AuxCase4 v = new AuxCase4();

        @Actor
        public void actor1(II_Result r) {
            r.r1 = v.add1(); // record result from actor1 to field r1
        }

        @Actor
        public void actor2(II_Result r) {
            r.r2 = v.add2(); // record result from actor2 to field r2
        }
    }

    public static class AuxCase4 {

        private int v;

        public synchronized int add1() {
            return ++v;
        }

        public synchronized int add2() {
            return ++v;
        }
    }

    @JCStressTest
    @Outcome(id = "1, 1", expect = Expect.ACCEPTABLE_INTERESTING, desc = "Both actors came up with the same value: atomicity failure.")
    @Outcome(id = "1, 2", expect = Expect.ACCEPTABLE, desc = "actor1 incremented, then actor2.")
    @Outcome(id = "2, 1", expect = Expect.ACCEPTABLE, desc = "actor2 incremented, then actor1.")
    @State
    public static class Case5 {

        AuxCase5 v = new AuxCase5();

        @Actor
        public void actor1(II_Result r) {
            r.r1 = v.add1(); // record result from actor1 to field r1
        }

        @Actor
        public void actor2(II_Result r) {
            r.r2 = v.add2(); // record result from actor2 to field r2
        }
    }

    public static class AuxCase5 {

        private ReentrantLock lock = new ReentrantLock();

        private int v;

        public int add1() {
            lock.lock();
            try {
                return ++v;
            } finally {
                lock.unlock();
            }
        }

        public int add2() {
            lock.lock();
            try {
                return ++v;
            } finally {
                lock.unlock();
            }
        }
    }

    @JCStressTest
    @Outcome(id = "1, 1", expect = Expect.ACCEPTABLE_INTERESTING, desc = "Both actors came up with the same value: atomicity failure.")
    @Outcome(id = "1, 2", expect = Expect.ACCEPTABLE, desc = "actor1 incremented, then actor2.")
    @Outcome(id = "2, 1", expect = Expect.ACCEPTABLE, desc = "actor2 incremented, then actor1.")
    @State
    public static class Case6 {

        AuxCase6 v = new AuxCase6();

        @Actor
        public void actor1(II_Result r) {
            r.r1 = v.add1(); // record result from actor1 to field r1
        }

        @Actor
        public void actor2(II_Result r) {
            r.r2 = v.add2(); // record result from actor2 to field r2
        }
    }

    public static class AuxCase6 {

        private AtomicInteger v = new AtomicInteger(0);

        public int add1() {
            return v.incrementAndGet();
        }

        public int add2() {
            return v.incrementAndGet();
        }
    }
}
