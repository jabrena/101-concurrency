package info.jab.concurrent;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.NotThreadSafe;

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
@NotThreadSafe
public class Problem1_V2 implements IProblem1 {

    @GuardedBy("Volatile??")
    private volatile Long counter = 0L;

    public Long getCounter() {
        return counter;
    }

    public Long increment() {
        return ++this.counter;
    }
}
