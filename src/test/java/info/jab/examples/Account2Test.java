package info.jab.examples;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.*;

class Account2Test {

    @Test
    public void test() {

        Account2 account2 = new Account2(0);
        then(account2.getBalance()).isEqualTo(0);

    }

    @Test
    public void test2() {

        Account2 account2 = new Account2(10);
        account2.withdraw(1);
        then(account2.getBalance()).isEqualTo(9);

    }

}
