package eu.javaexperience.teavm.datareprez;

import java.util.Iterator;

import org.teavm.jso.JSObject;
import org.teavm.jso.core.JSArray;
import org.teavm.jso.json.JSON;

import eu.javaexperience.datareprez.DataArray;
import eu.javaexperience.datareprez.abstractImpl.DataArrayAbstractImpl;
import eu.javaexperience.datareprez.abstractImpl.DataProtocol;

public class DataArrayTeaVMImpl extends DataArrayAbstractImpl implements DataArray
{
	public static final DataArray INSTANCE = new DataArrayTeaVMImpl();
	
	protected JSArray<JSObject> array;
	
	public DataArrayTeaVMImpl()
	{
		array = JSArray.create();
	}
	
	public DataArrayTeaVMImpl(JSArray<JSObject> array)
	{
		this.array = array;
	}

	@Override
	public Object getImpl()
	{
		return array;
	}

	@Override
	public Iterator<Object> iterator()
	{
		return null;
	}

	@Override
	public int size()
	{
		return array.getLength();
	}

	@Override
	public byte[] toBlob()
	{
		return JSON.stringify(array).getBytes();
	}

	@Override
	protected <T> void setSubjectValue(int index, Class<T> cls, T value)
	{
		array.set(index, DataProtocolTeaVMImpl.castToNative(cls, value));
	}

	@Override
	protected <T> T getValueAs(int index, Class<T> cls, boolean mayNull)
	{
		return (T) DataProtocolTeaVMImpl.castFromNative(cls, array.get(index));
	}

	@Override
	protected DataProtocol getProtocolHandler()
	{
		return DataProtocolTeaVMImpl.intance;
	}
	
	@Override
	public String toString()
	{
		return "DataArrayTeaVM: "+JSON.stringify(array);
	}

	@Override
	public Class getCommonsClass()
	{
		return JSObject.class;
	}
}
