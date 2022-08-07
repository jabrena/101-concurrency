package info.jab.examples;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.II_Result;

import java.util.concurrent.locks.ReentrantLock;

@JCStressTest
@Outcome(id = "1, 1", expect = Expect.ACCEPTABLE_INTERESTING, desc = "Both actors came up with the same value: atomicity failure.")
@Outcome(id = "1, 2", expect = Expect.ACCEPTABLE, desc = "actor1 incremented, then actor2.")
@Outcome(id = "2, 1", expect = Expect.ACCEPTABLE, desc = "actor2 incremented, then actor1.")
@State
public class Example_04 {

    AuxExample_04 v = new AuxExample_04();

    @Actor
    public void actor1(II_Result r) {
        r.r1 = v.add1(); // record result from actor1 to field r1
    }

    @Actor
    public void actor2(II_Result r) {
        r.r2 = v.add2(); // record result from actor2 to field r2
    }

    public class AuxExample_04 {

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
}


