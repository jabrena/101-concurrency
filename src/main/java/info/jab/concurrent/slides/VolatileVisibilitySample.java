package info.jab.concurrent.slides;

public class VolatileVisibilitySample {

    volatile boolean initFlag = false;
    public void save(){

        this.initFlag = true;
        String threadname = Thread.currentThread().getName();
        System.out.println(" Threads ："+threadname+": Modify shared variables initFlag");
    }
    public void load(){

        String threadname = Thread.currentThread().getName();
        while (!initFlag){

            // Thread is idling here , wait for initFlag State change
        }
        System.out.println(" Threads ："+threadname+" The current thread sniffs initFlag A change of state ");
    }
    public static void main(String[] args){

        VolatileVisibilitySample sample = new VolatileVisibilitySample();
        Thread threadA = new Thread(()->{

            sample.save();
        },"threadA");
        Thread threadB = new Thread(()->{

            sample.load();
        },"threadB");
        threadB.start();
        try {

            Thread.sleep(1000);
        } catch (InterruptedException e) {

            e.printStackTrace();
        }
        threadA.start();
    }
}
