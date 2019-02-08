package eu.jvx.js.lib.reflect;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.teavm.jso.JSObject;
import org.teavm.jso.core.JSArray;
import org.teavm.jso.core.JSString;

import eu.javaexperience.collection.map.NullMap;
import eu.javaexperience.parse.ParsePrimitive;
import eu.javaexperience.reflect.Mirror;
import eu.javaexperience.text.StringTools;
import eu.jvx.js.lib.teavm.NativeJsSupportTeaVM.Direct;

public class TeaVmMockedReflection
{
	//Teavm's name Mangling 
	protected static final ConcurrentMap<Class, Map<String, String>> DIALECT = new ConcurrentHashMap<>();
	
	public static Map<String, String> getDialect(Object o)
	{
		if(null == o)
		{
			return null;
		}
		
		Class<? extends Object> cls = o.getClass();
		Map<String, String> ret = DIALECT.get(cls);
		
		if(null != ret && 0 == ret.size())
		{
			return ret;
		}
		
		Map<String, String> dialects = new HashMap<String, String>();
		
		String[] keys = keys(o);
		outside:for(Field f:Mirror.collectClassFields(cls, false))
		{
			String name = f.getName();
			String sname = "$"+name;
			int slen = sname.length();
			for(String key:keys)
			{
				if(slen <= key.length() && key.indexOf(sname) > -1)
				{
					if(key.equals(sname) || null != ParsePrimitive.tryParseInt(StringTools.getSubstringAfterFirstString(key, sname, null)))
					{
						dialects.put(name, key);
						continue outside;
					}
				}
			}
		}
		
		if(0 == dialects.size())
		{
			dialects = NullMap.instance;
		}
		
		DIALECT.put(cls, dialects);
		return dialects;
	}
	
	public static String[] keys(Object obj)
	{
		JSArray<JSString> arr = ((Direct)obj).keys();
		String[] ret = new String[arr.getLength()];
		for(int i=0;i<ret.length;++i)
		{
			JSString s = arr.get(i);
			if(null != s)
			{
				ret[i] = s.stringValue();
			}
		}
		return ret;
	}
	
	public static Object getObjectField(Object o, String name)
	{
		Map<String, String> dial = getDialect(o);
		if(null != dial)
		{
			String n = dial.get(name);
			if(null != n)
			{
				return ((Direct)o).rawGet(n);
			}
		}
		return null;
	}
	
	public static boolean setObjectField(Object o, String name, Object value)
	{
		Map<String, String> dial = getDialect(o);
		if(null != dial)
		{
			String n = dial.get(name);
			if(null != n)
			{
				((Direct)o).rawPut(n, (JSObject)value);
				return true;
			}
		}
		return false;
	}
}