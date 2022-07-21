package info.jab.concurrent;

import net.jcip.annotations.NotThreadSafe;

@NotThreadSafe
public class Account {

    private int balance;

    public Account(int balance) {
        this.balance = balance;
    }

    public int getBalance() {
        return this.balance;
    }

    public int withdraw(int amount) {
        this.balance = this.balance - amount;
        return this.balance;
    }
}
