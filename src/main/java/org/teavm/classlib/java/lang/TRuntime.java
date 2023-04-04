package org.teavm.classlib.java.lang;

import org.teavm.interop.DelegateTo;
import org.teavm.runtime.GC;

public class TRuntime {
	private static final TRuntime instance = new TRuntime();

	public void exit(int status) {
	}

	@DelegateTo("freeMemoryLowLevel")
	public long freeMemory() {
		return 2147483647L;
	}

	private long freeMemoryLowLevel() {
		return (long) GC.getFreeMemory();
	}

	public void gc() {
		System.gc();
	}

	public static TRuntime getRuntime() {
		return instance;
	}
	
	public static void addShutdownHook(Thread thread)
	{}

	@DelegateTo("totalMemoryLowLevel")
	public long totalMemory() {
		return 2147483647L;
	}

	private long totalMemoryLowLevel() {
		return GC.availableBytes();
	}
}
