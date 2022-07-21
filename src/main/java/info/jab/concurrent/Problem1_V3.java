package info.jab.concurrent;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.NotThreadSafe;
import net.jcip.annotations.ThreadSafe;

/**
 * Feature: Develop a Class which manage a Simple Counter
 *
 * Background: The class need to be Thread Safe
 *
 * Scenario: Provide information about a Counter
 *     Given a class which manage a Counter
 *     When  call the method getCounter()
 *     Then  returns the counter value
 *
 * Scenario: Increment the Counter
 *     Given a class which manage a Counter
 *     When  call the method increment()
 *     Then  increment the counter value
 */
@ThreadSafe
public class Problem1_V3 implements IProblem1 {

    @GuardedBy("Synchronized")
    private Long counter = 0L;

    public Long getCounter() {
        return counter;
    }

    public synchronized Long increment() {
        return ++this.counter;
    }
}
