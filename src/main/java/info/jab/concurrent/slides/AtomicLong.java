package info.jab.concurrent.slides;

/**
 * This code checks to see if a Java Virtual Machine respects important
 * properties of volatile variables.
 *
 * This particular test ensures that reads and writes of volatile longs
 * are performed atomically.
 *
 * The bad compiler optimization mentioned below indicates a bug in the
 * handling of operations on long integers.  This should not be a problem
 * on most platforms.
 *
 * The other test is a little more peculiar.  Writes to 64-bit (long)
 * volatiles must be performed atomically.  Each thread has a "key",
 * a 64 bit number whose rightmost 32 bits are identical to its leftmost
 * 32 bits.  Each thread repeatedly writes its key to a shared volatile long.
 * This write should be performed atomically: all 64 bits should be from the
 * same write.  This means that, when read, the volatile long's rightmost
 * 32 bits should always be identical to its leftmost 32 bits.
 *
 * Each thread repeatedly reads this value.   If the 32 rightmost bits are
 * not the same as the 32 leftmost bits at some point, then either the read
 * that obtained that value was not atomic, or the write that wrote that
 * value was not atomic.
 *
 * Because two threads are involved in this mistake, the error message may
 * get printed twice before the system has a chance to exit.
 *
 * There are two command line arguments allowed.  The first is the number
 * of threads, and the second is the number of iterations each thread will
 * go through to find non-atomic longs.
 */

public class AtomicLong extends Thread {
    static volatile long v;
    static int w;
    static volatile long v2;
    static int count = 10000000;

    long key;

    AtomicLong(int k) {
	long temp = k;
	key = (temp<< 32) | temp;
    }

    public void check(long temp) {
	    long temp1 = temp>>>32;
	    long temp2 = (temp <<32) >>>32;
	    long temp3 = 0xffffffffL & temp;

	    if (temp2 != temp3) {
		System.out.println("Bad compiler optimization.\n\n" +
				   "There is a bug in the handling of longs " +
				   " in this VM.\n" +
				   Long.toHexString(temp)
				   +" botton 32 bits are either " + Long.toHexString(temp2)
				   + " or " + Long.toHexString(temp3));
		System.exit(1);
	    }
	    if (temp1 != temp3) {
		System.out.println("Nonatomic write to or read from a volatile long.\n\n" +
				   "This means that one of two things happened.\n" +
				   "1) Two threads may have written to a volatile " +
				   "variable in such a way that\none wrote the first 32 bits, " +
				   "and the other wrote the second 32 bits.\n" +
				   "2) A thread reading the volatile might have read " +
				   "the first 32 bits from one\nwrite and the second 32 bits " +
				   "from another.\n\nEither way, it is a violation of the " +
				   "semantics of volatile variables.\n" +
				   "Saw " + Long.toHexString(temp)
				   +" which is " + Long.toHexString(temp1)
				   + "." + Long.toHexString(temp2));
		System.exit(1);
	    }
    }

    public void run() {
	for(int i = 0; i < count; i++) {
	    check(v);
	    v = key;
	    check(v2);
	    v = key;
	    }
    }





    public static void main(String args[]) {
	int threads = 10;
	if (args.length > 0) {
	    threads = Integer.parseInt(args[0]);
	    if (args.length > 1)
		count = Integer.parseInt(args[1]);
	}
	if (threads > (1L << 32)) {
	    System.err.println("Too many threads.");
	    System.exit(1);
	}
	for(int t = 1; t < threads; t++)
	    new AtomicLong(t).start();
    }
}

