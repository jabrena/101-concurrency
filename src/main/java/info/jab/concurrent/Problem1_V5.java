package info.jab.concurrent;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import net.jcip.annotations.GuardedBy;
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
public class Problem1_V5 implements IProblem1 {

    ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    @GuardedBy("ReentrantReadWriteLock")
    private Long counter = 0L;

    public Long getCounter() {
        try {
            readWriteLock.readLock().lock();
            return counter;
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    public Long increment() {
        try {
            readWriteLock.writeLock().lock();
            return ++this.counter;
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }
}
