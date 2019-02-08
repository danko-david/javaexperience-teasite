package eu.jvx.js.lib;

import org.teavm.jso.JSObject;

import eu.javaexperience.datareprez.convertFrom.DataPrimitiveSourceObject;
import eu.javaexperience.datareprez.convertFrom.ModifiableObject;

public interface NativeObject extends DataPrimitiveSourceObject, ModifiableObject, JSObject
{
	public Object toNative();
}
