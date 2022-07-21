package info.jab.concurrent.slides;
/*
 * Check to see if JVM enforces order of Reads after Writes of
 * volatile variables.  All memory operations on volatile variables
 * should be sequentially consistent (according to existing
 * semantics).
 *
 * On many processors, a write and a following read can effectively be
 * reordered due to the write buffer.  Even the strong memory
 * semantics of SPARC TSO can exhibit this behavior.  It can also be
 * observed under Intel IA-64, Intel IA-32, Alpha and PowerPC.
 *
 * Thus, between a write to a volatile variable and a read of a
 * volatile variable, a memory barrier instruction is required.
 *
 * This is an important observation.  Doug Lea's FJTaskRunner.java
 * relies on the JVM's properly dealing with read-after-write of two
 * volatiles.
 *
 * The specific test made by this program is simple.  Both threads
 * iterate over arrays.  At each index, it assigns the value of the
 * index to one of two volatile variables.  The element of the array
 * at that index is given the value of the other volatile variable.
 * The other thread reverses the usage of the two variables.
 *
 * The upshot of this is that if one thread writes a value to a
 * volatile variable, the value read out of the other variable will be
 * stored in the array.  Consider the following code:
 *
 * Thread 1
 *   a = i;
 *   AA[i] = b;
 *
 * Thread 2
 *   b = j;
 *   BB[j] = a;
 *
 * If the instructions in Thread 2 are reordered, you may end up with
 * BB[AA[i]] == a, but a != i.  This is impossible without reordering;
 * a program which does this does not respect sequentially consistent
 * ordering for its volatiles.
 *
 * Many older JVMs do not support this behavior correctly.   Old versions
 * of HotSpot and ExactVM on SPARC Solaris, IBM's JVM and Microsoft's JVM
 * on Windows NT fail the test.  More recent JVMs have corrected this.
 */

public class ReadAfterWrite {

    static volatile int a;

    static final int n = 1000000;
    static final int[] AA = new int[n];
    static final int[] BB = new int[n];

    // Force a and b to be on different cache lines
    // don't know if this matters
    static int tmp1,tmp2,tmp3,tmp4,tmp5,tmp6,tmp7,tmp8;

    static volatile int b;

    static class WriteA extends Thread {
	public void run() {
	    this.yield();
	    final int [] mem = BB;
	    for(int i = 0; i < n; i++) {
		a = i;
		mem[i] = b;
	    }
	}
    }
    static class WriteB extends Thread {
	public void run() {
	    final int [] mem = AA;
	    for(int i = 0; i < n; i++) {
		b = i;
		mem[i] = a;
	    }
	}
    }

    public static void dump(String name, String name2, int [] mem, int i) {
	int j = i;
	int count = 0;
	while (j > 1 && count < 4) {
	    if (mem[j-1] != mem[j]) count++;
	    j--;
	}
	count = 0;
	while (j < n && count < 8) {
	    if (j == i) System.out.print("* ");
	    j++;
	    if (mem[j] != mem[j-1]) {
		System.out.println("After writing " +
				   (j-1)
				   + " to " + name
				   + ", read "
				   + mem[j-1]
				   + " from " + name2);
		count++;
	    }
	}
    }

    public static void main(String args[]) throws Exception {
	test();
	for(int i = 0; i < n; i++) {
	    AA[i] = 0;
	    BB[i] = 0;
	}
	a = 0;
	b = 0;
	System.out.println();
	test();
    }

    public static void test() throws Exception {
	Thread t1 = new WriteA();
	Thread t2 = new WriteB();
	t1.start();
	t2.start();
	t1.join();
	t2.join();
	for(int i = 100; i < n; i++) {
	    int j = AA[i]+1;
	    if ( 100 < j && j < n && BB[j] < i) {
		System.out.println("Thread 1");
		dump("a", "b", BB, j);
		System.out.println("Thread 2");
		dump("b", "a", AA, i);
		System.out.println("\n\nOn the starred lines above, you " +
				   "will note that the value read from a\n"+
				   "is less than the value written to "+
				   "a, and that the value read from b is\n"+
				   "less than this value written to "+
				   "b.  This is a violation of\n"+
				   "read-after-write ordering of "+
				   "volatiles.  See comments in the source\n"+
				   "file of this code for more details.");

		return;
	    }
	}
    }
}
