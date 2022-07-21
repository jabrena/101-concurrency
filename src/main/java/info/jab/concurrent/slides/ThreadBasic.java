package info.jab.concurrent.slides;

class Process implements Runnable {

    public void run() {
        System.out.println("Thread is running...");
    }
}

public class ThreadBasic {

    public static void main(String[] args) throws InterruptedException {
        Process m1 = new Process();
        Thread t1 = new Thread(m1);
        t1.start();
        t1.join();
        System.out.println("Example finished");
    }
}
