package info.jab.ms.model;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.locks.ReentrantLock;

@ThreadSafe
public class Calculator5 {
    ReentrantLock mutex = new ReentrantLock();

    private Long result;

    public Long calculate(Long operator1, Long operator2) {
        mutex.lock();
        try {
            this.result = operator1 + operator2;
            return result;
        } finally {
            mutex.unlock();
        }
    }
}
