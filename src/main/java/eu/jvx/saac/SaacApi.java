package eu.jvx.saac;

import eu.javaexperience.datareprez.DataArray;
import eu.javaexperience.datareprez.DataObject;
import eu.javaexperience.interfaces.simple.publish.SimplePublish2;
import eu.jvx.js.lib.JvxClientException;
import eu.teasite.frontend.api.ApiInterface;

public class SaacApi extends ApiInterface
{
	public void listFunctions(SimplePublish2<DataArray, JvxClientException> ret)
	{
		transfer.publish(pack("listFunctions"), (SimplePublish2) ret);
	}
	
	protected DataArray functionCache = null;

	public DataArray listFunctions()
	{
		if(null == functionCache)
		{
			functionCache = transfer.transmitSync(pack("listFunctions"));
		}
		return functionCache;
	}
	
	public void offerForType
	(
		SimplePublish2<DataArray, JvxClientException> ret,
		String id, 
		int nth_arg,
		boolean varadic,
		String content,
		int selectionStart,
		int selectionEnd
	)
	{
		transfer.publish(pack("offerForType", id, nth_arg, varadic, content, selectionStart, selectionEnd), (SimplePublish2)ret);
	}

	public void execute
	(
		SimplePublish2<DataObject, JvxClientException> ret,
		DataObject saac_serialize_container
	)
	{
		transfer.publish(pack("execute", saac_serialize_container), (SimplePublish2)ret);
	}
}
