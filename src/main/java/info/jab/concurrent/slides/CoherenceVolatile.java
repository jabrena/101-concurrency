package info.jab.concurrent.slides;
/**
 * This code checks to see if a Java Virtual Machine
 * respects important properties of volatile variables.
 *
 * The Java Memory Model prohibits certain compiler optimizations.
 * In particular, all volatile reads and writes should have sequentially
 * consistent semantics as viewed by any two threads (there are minor
 * exceptions involving more than two threads).  One effect of this is that
 * once a thread sees a volatile write to a variable by another thread, it
 * cannot forget that it has seen the write.
 *
 * In particular, for the code sequence (with volatile x):
 *
 *    i = p.x;
 *    j = q.x;
 *    k = p.x;
 *
 * If p and q point to the same object and the first read of p.x might return
 * a different value from the read of q.x, the compiler may not reuse the
 * value of i for the write to k.  It can only reuse the value if
 * it can prove that q and p do not point to the same object, or that no
 * other thread can update p.x.
 *
 * It should be noted that this is NOT true if x is not volatile.
 *
 * This is spelled out in much more detail in
 *
 * "The Java Memory Model", http://www.cs.umd.edu/~pugh/java/memoryModel
 *
 */


import java.awt.Point;
public class CoherenceVolatile {
    static boolean sawNonvolatileViolation = false;
    static class VPoint {
	volatile int x;
	volatile int y;
    }

    static VPoint vp = new VPoint();
    static VPoint vq = vp;

    public static void checkVolatile() {
        boolean optimizationDone = true;
        boolean interleavingSeen = false;
        boolean loopHoistingDone = true;
        VPoint pp = vp;
        VPoint qq = vq;
        int i,i0,j,k,m;
        i = 0;
        for(int l = 0; l < 10000000; l++) {
            i0 = i;
            i = pp.x;
            j = qq.x;
            k = pp.x;
            m = pp.x;

            if (l > 0 && i0 != i) loopHoistingDone = false;
            if (k != m) optimizationDone = false;
            if (i != j) interleavingSeen = true;
            if (j > k) {
		System.out.println("i = " + i
				   + ", j = " + j
				   + ", k = " + k
				   + ", j > k -- in violation of JMM");
                System.out.println("Compiler is reordering reads of " +
				   "volatile variables, in violation of " +
				   "consistency requirements\nfor volatiles.");

                System.exit(0);
            }
        }
        if (!optimizationDone)  {
	    // System.out.println("optimization not done (yet)");
            interleavingSeen = false;
        }
        else if (loopHoistingDone)
            System.out.println("Extremely poor interleaving or Loop hoisting done");
        else if (!interleavingSeen)
            System.out.println("no intra-loop interleaving seen");
        else System.out.println("Saw intra-loop interleaving and only legal optimizations");
        Thread.yield();

    }


    public static void main(String args[]) throws InterruptedException {

        Thread t1 = new Thread() {
            public void run() {
                while (true) {
                    for(int l = 0;l < 10000000; l++) {
                        p.x++;
                    }
                    Thread.yield();
                }
            }
        };
        Thread t2 = new Thread() {
            public void run() {
                for(int l = 0;l < 10; l++)  check();
            }
        };
        t1.start();
        t2.start();
	t2.join();

	if (!sawNonvolatileViolation) {
		System.out.println(
		"Didn't see violation of Coherence for non-volatile fields");
		System.out.println(
		"JVM might not be performing optimizations at all");
		System.out.println(
		"Or only performing thread context switches at back edges");
		System.out.println(
		"Unable to check for violations coherence for volatile variables");
		System.exit(0);
		}
	System.out.println(
		"Saw optimization for non-volatile fields, checking volatile");

        t1 = new Thread() {
            public void run() {
                while (true) {
                    for(int l = 0;l < 10000000; l++) {
                        vp.x++;
                    }
                    Thread.yield();
                }
            }
        };
        t2 = new Thread() {
            public void run() {
                for(int l = 0;l < 10; l++)  checkVolatile();
                System.out.println("No violation of the JMM detected");
                System.exit(0);
            }
        };
        t1.start();
        t2.start();
    }
    static Point p = new Point();
    static Point q = p;

    public static void check() {
        Point pp = p;
        Point qq = q;
        int i,i0,j,k,m;
        i = 0;
        for(int l = 0;l < 10000000; l++) {
            i0 = i;
            i = pp.x;
            j = qq.x;
            k = pp.x;
            m = pp.x;

            if (j > k) {
			sawNonvolatileViolation = true;
			return;
			}
        }

    }


}
