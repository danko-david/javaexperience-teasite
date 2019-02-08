package org.teavm.classlib.java.util.concurrent.atomic;

public class TAtomicReference<V>
{
    private volatile V value;

    public TAtomicReference(V initialValue)
    {
        value = initialValue;
    }
    
    public TAtomicReference() {}

    public final V get()
    {
        return value;
    }

    public final void set(V newValue)
    {
        value = newValue;
    }

    public final void lazySet(V newValue)
    {
    	value = newValue;
    }

    public final boolean compareAndSet(V expect, V update)
    {
    	if (this.value == expect)
    	{
    		this.value = update;
    		return true;
		}
    	else
    	{
			return false;
		}
    }

    public final boolean weakCompareAndSet(V expect, V update)
    {
    	return this.compareAndSet(expect, update);
    }

    public final V getAndSet(V newValue)
    {
    	V result = this.value;
		this.value = newValue;
		return result;
    }

    public String toString()
    {
        return String.valueOf(get());
    }
}
