package eu.jvx.js.lib.teavm;

import org.teavm.jso.JSBody;
import org.teavm.jso.JSObject;
import org.teavm.jso.browser.TimerHandler;
import org.teavm.jso.core.JSArray;
import org.teavm.jso.core.JSString;

import eu.jvx.js.lib.NativeJs;
import eu.jvx.js.lib.NativeJsSupport;
import eu.jvx.js.lib.NativeObject;

public class NativeJsSupportTeaVM
{
	protected static final Globals GLOBALS = null;
	
	public static abstract class Direct implements JSObject
	{
		@JSBody(params = "key",script = "var ret = this[key]; if(typeof ret === 'string') ret = $rt_str(ret); if(undefined == ret) return null; return ret;")
	 	public native JSObject get(String key);
		
		@JSBody(params = {"key", "o"}, script = "if(null != o && o.$getClass1){ var cls = o.$getClass1(); if('java.lang.String' === $rt_ustr(cls.$getName())) o = $rt_ustr(o); }; this[key] = o")
		public abstract void put(String key, JSObject o);

		@JSBody(script = "return Object.keys(this);")
		public abstract JSArray<JSString> keys();

		@JSBody(script = "return typeof this;")
		public abstract String typeOf();
		
		@JSBody(script = "return this.constructor.name;")
		public abstract String getClassType();
		
		
		@JSBody(params = {"key", "o"}, script = "this[key] = o")
		public abstract void rawPut(String key, JSObject o);
		
		@JSBody(params = {"key"}, script = "return this[key];")
		public abstract JSObject rawGet(String key);

		public abstract String getString(String key);
		public abstract void putString(String key, String value);
		
		@JSBody(params = {"key", "o"}, script = "this[key] = o")
		public abstract void setFunc(String key, TimerHandler o);
	}
	
	private static abstract class Globals implements JSObject
	{
		@JSBody(params = "script",script = "var ret = eval(script); if(typeof ret === 'string') ret = $rt_str(ret); return ret;")
		public abstract JSObject eval(String srcipt);
		
		@JSBody(params = {"script"}, script = "Function('\"use strict\";return (' + script + ')')();")
		public abstract JSObject createFunction(String script);
		
		@JSBody(params = {"function", "param"}, script = "return func(param)")
		public abstract JSObject callFunction(JSObject func, JSObject param);
		
		
		@JSBody(params = {"p","script"}, script = "$rt_globals.param = p;var ret = eval(script); if(typeof ret === 'string') ret = $rt_str(ret); return ret;")
		public abstract JSObject eval(JSObject param, String script);
		
		
		@JSBody(params = "key", script = "for(var i=0;i<key.length;++i){ if(null != key[i]){ if(key[i].$getClass1 && 'java.lang.String' === $rt_ustr(key[i].$getClass1().$getName())) key[i] = $rt_ustr(key[i]); }; }; console.log(key);")
		public abstract JSObject log(JSObject... srcipt);

		@JSBody(params = {"origin", "key"}, script = "for(var i=0;i<key.length;++i){ if(null != key[i] && key.$getClass1){ var cls = o.$getClass1(); if('java.lang.String' === $rt_ustr(o.$getName())) key[i] = $rt_ustr(key[i]); }; }; return new (Function.prototype.bind.apply(origin, [origin].concat(key)));")
		public abstract JSObject newInstanceOf(JSObject origin, JSObject[] args);
		
		@JSBody(params = {}, script = "return {};")
		public abstract JSObject newObject();
		
		@JSBody(params = "obj",script = "return Object.keys(obj)")
		public abstract JSArray<JSString> keys(JSObject obj);
		
	}
	
	public static void init()
	{
		new NativeJsSupport().getClass().toString();
		NativeJsSupport.SUPPORT = new NativeJs()
		{
			
			@Override
			public Object eval(String src)
			{
				return GLOBALS.eval(src);
			}

			@Override
			public Object newInstanceOf(Object origin, Object... args)
			{
				return GLOBALS.newInstanceOf((JSObject)origin, (JSObject[])args);
			}
			
			@Override
			public Object getProp(Object obj, String prop)
			{
				return ((Direct)obj).get(prop);
			}

			@Override
			public void setProp(Object obj, String prop, Object value)
			{
				((Direct)obj).put(prop, (JSObject) value);
			}

			@Override
			public Object log(Object... src)
			{
				return GLOBALS.log((JSObject[]) src);
			}

			@Override
			public NativeObject newObject()
			{
				return (NativeObject)GLOBALS.newObject();
			}

			@Override
			public Object eval(JSObject param, String src)
			{
				return GLOBALS.eval(param, src);
			}

			@Override
			public JSArray<JSString> keys(JSObject pass)
			{
				return GLOBALS.keys(pass);
			}
		};
	}
}
