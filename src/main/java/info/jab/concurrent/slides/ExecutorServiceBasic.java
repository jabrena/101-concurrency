package info.jab.concurrent.slides;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceBasic {

    private static CountDownLatch latch = new CountDownLatch(1);

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.submit(new Process2());
        latch.await();
        executor.shutdown();
        executor.awaitTermination(60, TimeUnit.SECONDS);
        System.out.println("Example finished");
    }

    static class Process2 implements Runnable {

        public void run() {
            System.out.println("Thread is running...");
            latch.countDown();
        }
    }
}
