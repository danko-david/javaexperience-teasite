package eu.teasite.frontend.api;

import eu.javaexperience.datareprez.DataObject;
import eu.javaexperience.interfaces.simple.publish.SimplePublish2;
import eu.javaexperience.rpc.client.JvxClientException;

public class MultiplexedApiCall extends ApiInterface
{
	public void doMulticall(SimplePublish2<DataObject, JvxClientException> callback, java.lang.Object a)
	{
		transfer.publish(pack("doMulticall", a), (SimplePublish2) callback);
	}
}