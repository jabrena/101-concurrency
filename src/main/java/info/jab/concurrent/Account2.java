package info.jab.concurrent;

import java.util.concurrent.locks.ReentrantLock;
import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class Account2 {

    private int balance;
    private ReentrantLock bankLock = new ReentrantLock();

    public Account2(int balance) {
        this.balance = balance;
    }

    public synchronized int getBalance() {
        return this.balance;
    }

    public int withdraw(int amount) {
        bankLock.lock();
        try {
            this.balance = this.balance - amount;
            return this.balance;
        } finally {
            bankLock.unlock();
        }
    }
}
