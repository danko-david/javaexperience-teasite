package org.teavm.classlib.java.util.concurrent;

import org.teavm.classlib.java.util.TIterator;
import org.teavm.classlib.java.util.TMap;
import org.teavm.classlib.java.util.function.TBiConsumer;
import org.teavm.classlib.java.util.function.TBiFunction;
import org.teavm.classlib.java.util.function.TFunction;

public interface TConcurrentMap<K, V> extends TMap<K, V>
{
	default V getOrDefault(Object key, V defaultValue)
	{
		V v;
		return ((v = get(key)) != null) ? v : defaultValue;
	}

    default void forEach(TBiConsumer<? super K, ? super V> action) {
        TIterator<org.teavm.classlib.java.util.TMap.Entry<K, V>> it = this.entrySet().iterator();
        while(it.hasNext())
        {
        	org.teavm.classlib.java.util.TMap.Entry<K, V> entry = it.next();
            K k;
            V v;
            try {
                k = entry.getKey();
                v = entry.getValue();
            } catch(IllegalStateException ise) {
                // this usually means the entry is no longer in the map.
                continue;
            }
            action.accept(k, v);
        }
    }

     V putIfAbsent(K key, V value);

     boolean remove(Object key, Object value);

    boolean replace(K key, V oldValue, V newValue);

    V replace(K key, V value);

    default void replaceAll(TBiFunction<? super K, ? super V, ? extends V> function) {
        forEach((k,v) -> {
            while(!replace(k, v, function.apply(k, v))) {
                // v changed or k is gone
                if ( (v = get(k)) == null) {
                    // k is no longer in the map.
                    break;
                }
            }
        });
    }

    default V computeIfAbsent(K key,
            TFunction<? super K, ? extends V> mappingFunction) {
        V v, newValue;
        return ((v = get(key)) == null &&
                (newValue = mappingFunction.apply(key)) != null &&
                (v = putIfAbsent(key, newValue)) == null) ? newValue : v;
    }


    default V computeIfPresent(K key,
           TBiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        V oldValue;
        while((oldValue = get(key)) != null) {
            V newValue = remappingFunction.apply(key, oldValue);
            if (newValue != null) {
                if (replace(key, oldValue, newValue))
                    return newValue;
            } else if (remove(key, oldValue))
               return null;
        }
        return oldValue;
    }

  
    default V compute(K key,
            TBiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        V oldValue = get(key);
        for(;;) {
            V newValue = remappingFunction.apply(key, oldValue);
            if (newValue == null) {
                // delete mapping
                if (oldValue != null || containsKey(key)) {
                    // something to remove
                    if (remove(key, oldValue)) {
                        // removed the old value as expected
                        return null;
                    }

                    // some other value replaced old value. try again.
                    oldValue = get(key);
                } else {
                    // nothing to do. Leave things as they were.
                    return null;
                }
            } else {
                // add or replace old mapping
                if (oldValue != null) {
                    // replace
                    if (replace(key, oldValue, newValue)) {
                        // replaced as expected.
                        return newValue;
                    }

                    // some other value replaced old value. try again.
                    oldValue = get(key);
                } else {
                    // add (replace if oldValue was null)
                    if ((oldValue = putIfAbsent(key, newValue)) == null) {
                        // replaced
                        return newValue;
                    }

                    // some other value replaced old value. try again.
                }
            }
        }
    }


    default V merge(K key, V value,
            TBiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        V oldValue = get(key);
        for (;;) {
            if (oldValue != null) {
                V newValue = remappingFunction.apply(oldValue, value);
                if (newValue != null) {
                    if (replace(key, oldValue, newValue))
                        return newValue;
                } else if (remove(key, oldValue)) {
                    return null;
                }
                oldValue = get(key);
            } else {
                if ((oldValue = putIfAbsent(key, value)) == null) {
                    return value;
                }
            }
        }
    }
}
