package eu.javaexperience.teavm.datareprez;

import org.teavm.jso.JSObject;
import org.teavm.jso.core.JSArray;
import org.teavm.jso.core.JSString;
import org.teavm.jso.json.JSON;

import eu.javaexperience.datareprez.abstractImpl.DataObjectAbstractImpl;
import eu.javaexperience.datareprez.abstractImpl.DataProtocol;
import eu.jvx.js.lib.NativeJs;
import eu.jvx.js.lib.NativeJsSupport;
import eu.jvx.js.lib.teavm.NativeJsSupportTeaVM.Direct;

public class DataObjectTeaVMImpl extends DataObjectAbstractImpl
{
	protected static NativeJs JS = NativeJsSupport.getSupport();

	public static final DataObjectTeaVMImpl INSTANCE = new DataObjectTeaVMImpl();
	
	protected JSObject obj;
	
	public DataObjectTeaVMImpl(JSObject obj)
	{
		this.obj = obj;
	}

	public DataObjectTeaVMImpl()
	{
		this.obj = (JSObject) JS.newObject();
	}

	
	@Override
	public Object getImpl()
	{
		return obj;
	}

	@Override
	public byte[] toBlob()
	{
		return JSON.stringify(obj).getBytes();
	}
	
	@Override
	public String toString()
	{
		return "DataObjectTeaVM: "+JSON.stringify(obj);
	}

	@Override
	public String[] keys()
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

	@Override
	protected void setSubjectValue(String key, Class<?> valueType, Object val)
	{
		JS.setProp(obj, key, DataProtocolTeaVMImpl.castToNative(valueType, val));
	}

	@Override
	protected <T> T getValueAs(String key, Class<T> retType, boolean mayNull)
	{
		return (T) DataProtocolTeaVMImpl.castFromNative(retType, (JSObject) JS.getProp(obj, key));
	}

	@Override
	protected DataProtocol getProtocolHandler()
	{
		return DataProtocolTeaVMImpl.intance;
	}
	
	@Override
	public Class getCommonsClass()
	{
		return JSObject.class;
	}
}
