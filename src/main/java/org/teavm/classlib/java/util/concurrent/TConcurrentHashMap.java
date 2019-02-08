package org.teavm.classlib.java.util.concurrent;

import org.teavm.classlib.java.util.THashMap;

public class TConcurrentHashMap<K, V> extends THashMap<K, V> implements TConcurrentMap<K, V>
{
	@Override
	public V putIfAbsent(K key, V value)
	{
		if(!containsKey(key))
		{
			return put(key, value);
		}
		else
		{
			return null;
		}
	}
	@Override
	public boolean remove(Object key, Object value)
	{
		Object in = get(key);
		if(null != value && value.equals(in))
		{
			remove(key);
			return true;
		}
		else
		{
			return false;
		}
	}

	@Override
	public boolean replace(K key, V oldValue, V newValue)
	{
		Object in = get(key);
		if(null != in && in.equals(oldValue))
		{
			put(key, newValue);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public V replace(K key, V value)
	{
		if(containsKey(key))
		{
			return put(key, value);
		}
		else
		{
			return null;
		}
	}
}
