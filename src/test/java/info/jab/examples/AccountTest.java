package info.jab.examples;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

class AccountTest {

    @Test
    public void test() {

        Account account = new Account(0);
        then(account.getBalance()).isEqualTo(0);

    }

    @Test
    public void test2() {

        Account account = new Account(10);
        account.withdraw(1);
        then(account.getBalance()).isEqualTo(9);

    }

}
