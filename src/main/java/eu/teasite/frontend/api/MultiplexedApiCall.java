package eu.teasite.frontend.api;

import eu.jvx.js.lib.JvxClientException;
import eu.javaexperience.datareprez.DataObject;
import eu.javaexperience.interfaces.simple.publish.SimplePublish2;

public class MultiplexedApiCall extends ApiInterface
{
	public void doMulticall(SimplePublish2<DataObject, JvxClientException> callback, java.lang.Object a)
	{
		transfer.publish(pack("doMulticall", a), (SimplePublish2) callback);
	}
}