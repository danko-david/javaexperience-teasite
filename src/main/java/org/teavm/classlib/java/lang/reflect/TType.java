package org.teavm.classlib.java.lang.reflect;

public interface TType
{
    default String getTypeName()
    {
        return toString();
    }
}
