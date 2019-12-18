package org.teavm.classlib.java.lang;

import java.util.Enumeration;
import java.util.Properties;
import org.teavm.backend.javascript.spi.GeneratedBy;
import org.teavm.classlib.PlatformDetector;
import org.teavm.classlib.java.io.TConsole;
import org.teavm.classlib.java.io.TInputStream;
import org.teavm.classlib.java.io.TPrintStream;
import org.teavm.classlib.java.lang.reflect.TArray;
import org.teavm.dependency.PluggableDependency;
import org.teavm.interop.Address;
import org.teavm.interop.DelegateTo;
import org.teavm.interop.Import;
import org.teavm.interop.Unmanaged;
import org.teavm.runtime.Allocator;
import org.teavm.runtime.GC;
import org.teavm.runtime.RuntimeArray;
import org.teavm.runtime.RuntimeClass;

public final class TSystem extends TObject {
	public static final TPrintStream out = new TPrintStream(new TConsoleOutputStreamStdout(), false);
	public static final TPrintStream err = new TPrintStream(new TConsoleOutputStreamStderr(), false);
	public static final TInputStream in = new TConsoleInputStream();
	private static Properties properties;

	public static TConsole console() {
		return null;
	}

	public static void arraycopy(TObject src, int srcPos, TObject dest, int destPos, int length) {
		if (src != null && dest != null) {
			if (srcPos >= 0 && destPos >= 0 && length >= 0 && srcPos + length <= TArray.getLength(src)
					&& destPos + length <= TArray.getLength(dest)) {
				if (src != dest) {
					Class<?> srcType = src.getClass().getComponentType();
					Class<?> targetType = dest.getClass().getComponentType();
					if (srcType == null || targetType == null) {
						throw new TArrayStoreException();
					}

					if (srcType != targetType) {
						if (!srcType.isPrimitive() && !targetType.isPrimitive()) {
							Object[] srcArray = (Object[]) (Object) src;
							int pos = srcPos;

							for (int i = 0; i < length; ++i) {
								Object elem = srcArray[pos++];
								if (!targetType.isInstance(elem)) {
									doArrayCopy(src, srcPos, dest, destPos, i);
									throw new TArrayStoreException();
								}
							}

							doArrayCopy(src, srcPos, dest, destPos, length);
							return;
						}

						if (!srcType.isPrimitive() || !targetType.isPrimitive()) {
							throw new TArrayStoreException();
						}
					}
				}

				doArrayCopy(src, srcPos, dest, destPos, length);
			} else {
				throw new TIndexOutOfBoundsException();
			}
		} else {
			throw new TNullPointerException(TString.wrap("Either src or dest is null"));
		}
	}

	@GeneratedBy(SystemNativeGenerator.class)
	@DelegateTo("doArrayCopyLowLevel")
	private static native void doArrayCopy(Object var0, int var1, Object var2, int var3, int var4);

	@Unmanaged
	static void doArrayCopyLowLevel(RuntimeArray src, int srcPos, RuntimeArray dest, int destPos, int length) {
		RuntimeClass type = RuntimeClass.getClass(src);
		int itemSize = type.itemType.size;
		if ((type.itemType.flags & 2) == 0) {
			itemSize = Address.sizeOf();
		}

		Address srcAddress = Address.align(src.toAddress().add(RuntimeArray.class, 1), itemSize);
		srcAddress = srcAddress.add(itemSize * srcPos);
		Address destAddress = Address.align(dest.toAddress().add(RuntimeArray.class, 1), itemSize);
		destAddress = destAddress.add(itemSize * destPos);
		Allocator.moveMemoryBlock(srcAddress, destAddress, length * itemSize);
	}

	@DelegateTo("currentTimeMillisLowLevel")
	@GeneratedBy(SystemNativeGenerator.class)
	public static native long currentTimeMillis();

	private static long currentTimeMillisLowLevel() {
		return PlatformDetector.isWebAssembly() ? (long) currentTimeMillisWasm() : currentTimeMillisC();
	}

	@Import(name = "currentTimeMillis", module = "teavm")
	private static native double currentTimeMillisWasm();

	@Import(name = "currentTimeMillis")
	private static native long currentTimeMillisC();

	private static void initPropertiesIfNeeded() {
		if (properties == null) {
			Properties defaults = new Properties();
			defaults.put("java.version", "1.8");
			defaults.put("os.name", "TeaVM");
			defaults.put("file.separator", "/");
			defaults.put("path.separator", ":");
			defaults.put("line.separator", lineSeparator());
			defaults.put("java.io.tmpdir", "/tmp");
			defaults.put("java.vm.version", "1.8");
			defaults.put("user.home", "/");
			properties = new Properties(defaults);
		}

	}

	public static String getProperty(String key) {
		initPropertiesIfNeeded();
		return properties.getProperty(key);
	}

	public static String getProperty(String key, String def) {
		String value = getProperty(key);
		return value != null ? value : def;
	}

	public static Properties getProperties() {
		initPropertiesIfNeeded();
		Properties result = new Properties();
		copyProperties(properties, result);
		return result;
	}

	public static void setProperties(Properties props) {
		initPropertiesIfNeeded();
		copyProperties(props, properties);
	}

	private static void copyProperties(Properties from, Properties to) {
		to.clear();
		if (from != null) {
			Enumeration e = from.propertyNames();

			while (e.hasMoreElements()) {
				String key = (String) e.nextElement();
				to.setProperty(key, from.getProperty(key));
			}
		}

	}

	public static String setProperty(String key, String value) {
		initPropertiesIfNeeded();
		return (String) properties.put(key, value);
	}

	public static String clearProperty(String key) {
		return (String) properties.remove(key);
	}

	@GeneratedBy(SystemNativeGenerator.class)
	@PluggableDependency(SystemNativeGenerator.class)
	public static native void setErr(TPrintStream var0);

	@GeneratedBy(SystemNativeGenerator.class)
	@PluggableDependency(SystemNativeGenerator.class)
	public static native void setOut(TPrintStream var0);

	@DelegateTo("gcLowLevel")
	public static void gc() {
	}

	private static void gcLowLevel() {
		GC.collectGarbage(0);
	}
	
	public String getenv(String key)
	{
		return null;//TOOD actually we can inject something
	}

	public static void runFinalization() {
	}

	public static long nanoTime() {
		return currentTimeMillis() * 1000000L;
	}

	public static int identityHashCode(Object x) {
		return ((TObject) x).identity();
	}

	public static String lineSeparator() {
		return "\n";
	}
}