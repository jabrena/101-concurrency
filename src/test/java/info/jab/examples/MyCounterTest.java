package info.jab.examples;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

class MyCounterTest {

    @Test
    public void test() {

        MyCounter counter = new MyCounter();
        counter.increment();

        //Then
        then(counter.getCount()).isEqualTo(1);
    }

    @Test
    public void test2() throws Exception {

        MyCounter counter = new MyCounter();
        counter.incrementWithWait();

        //Then
        then(counter.getCount()).isEqualTo(1);
    }

}
