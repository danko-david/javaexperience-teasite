package eu.javaexperience.teavm.datareprez;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.teavm.jso.JSBody;
import org.teavm.jso.JSObject;
import org.teavm.jso.core.JSArray;
import org.teavm.jso.core.JSBoolean;
import org.teavm.jso.core.JSNumber;
import org.teavm.jso.core.JSString;
import org.teavm.jso.json.JSON;

import eu.javaexperience.datareprez.DataArray;
import eu.javaexperience.datareprez.DataObject;
import eu.javaexperience.datareprez.abstractImpl.DataProtocol;
import eu.javaexperience.datareprez.convertFrom.DataReprezComponentTypes;
import eu.javaexperience.io.primitive.LineReader;
import eu.javaexperience.io.primitive.LineReader.LineMode;
import eu.javaexperience.reflect.CastTo;
import eu.jvx.js.lib.teavm.NativeJsSupportTeaVM.Direct;

public class DataProtocolTeaVMImpl implements DataProtocol
{
	public static final DataProtocolTeaVMImpl intance = new DataProtocolTeaVMImpl();

	@Override
	public byte[] acquirePacket(InputStream is) throws IOException
	{
		return LineReader.readByteLine(is, LineMode.Unix);
	}

	@Override
	public void sendPacket(byte[] data, OutputStream os) throws IOException
	{
		os.write(data);
		os.write(10);
	}

	@Override
	public DataObject newObjectInstance()
	{
		return new DataObjectTeaVMImpl();
	}

	@Override
	public DataArray newArrayInstance()
	{
		return new DataArrayTeaVMImpl();
	}

	@Override
	public DataObject objectFromBlob(byte[] data)
	{
		return new DataObjectTeaVMImpl(JSON.parse(new String(data)));
	}

	@Override
	public DataArray arrayFromBlob(byte[] data)
	{
		return new DataArrayTeaVMImpl((JSArray<JSObject>)JSON.parse(new String(data)));
	}
	
	public static JSObject castToNative(Class cls, Object o)
	{
		if(null != o)
		{
			DataReprezComponentTypes type = DataReprezComponentTypes.recognise(cls);
			if(null != type)
			{
				switch(type)
		        {
					case Boolean: 	return JSBoolean.valueOf((Boolean) CastTo.Boolean.cast(o));
					case DataArray:	return ((DataArrayTeaVMImpl)o).array;
					case DataObject:return ((DataObjectTeaVMImpl)o).obj;
					case Double:	return JSNumber.valueOf((Double) CastTo.Double.cast(o));
					case Integer:	return JSNumber.valueOf((Integer) CastTo.Int.cast(o));
					case Long:		return JSNumber.valueOf((Long) CastTo.Long.cast(o));
					case NULL:		return null;
					case String: 	return JSString.valueOf((String) CastTo.String.cast(o));
				}
			}
			throw new RuntimeException("Unrecognised class type: "+cls);
		}
		return null;
	}
	
	public static Object castFromNative(Class cls, JSObject o)
	{
		Object rec = receiveObject(o);
		if(null == cls)
		{
			return rec;
		}
		
		return CastTo.getCasterForTargetClass(cls).cast(rec);
	}
	
	//source JSNumber because inheritance results strange error: Implicit super constructor JSNumber() is not visible for default constructor. Must define an explicit constructor
	protected static abstract class JSLongNumber implements JSObject
	{
		@JSBody(params = {"number"}, script = "return number;")
		private static native long longValue(JSLongNumber var0);

		public final long longValue()
		{
			return longValue(this);
		}
		
		@JSBody(params = {"number"}, script = "return number;")
		private static native double doubleValue(JSLongNumber var0);
		
		public final double doubleValue()
		{
			return doubleValue(this);
		}
	}
	
	protected static Object receiveObject(JSObject o)
	{
		if(null != o)
		{
			String jt = ((Direct)o).getClassType();
			
			switch(jt)
			{
				case "Number":	
					JSLongNumber num = ((JSLongNumber)o);
					double d = num.doubleValue();
					if(Math.floor(d) == d)
					{
						return num.longValue();
					}
					return d;
				
				case "Array":	return new DataArrayTeaVMImpl((JSArray<JSObject>)o);
				case "Boolean":	return ((JSBoolean)o).booleanValue();
				case "String":	return ((JSString)o).stringValue();
				
				case "jl_String": return (String) (Object)o;
				case "jl_Integer": return (Integer) (Object)o;
				case "jl_Boolean": return (Boolean) (Object)o;
				case "jl_Long": return (Long) (Object)o;
				case "jl_Double": return (Double) (Object)o;
			}
			return new DataObjectTeaVMImpl(o);
		}
		return null;
	}
	
	@Override
	public Class getCommonsClass()
	{
		return JSObject.class;
	}
}
