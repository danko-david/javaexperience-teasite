package eu.teasite.frontend.api.transfers;

import org.teavm.interop.Async;
import org.teavm.platform.async.AsyncCallback;

import eu.javaexperience.datareprez.DataObject;
import eu.javaexperience.interfaces.simple.publish.SimplePublish1;
import eu.javaexperience.interfaces.simple.publish.SimplePublish2;
import eu.javaexperience.rpc.client.JvxClientException;

public abstract class ApiPacketTransfer
{
	@Async
	public native DataObject syncTransmit(DataObject request);
	
	protected void syncTransmit(DataObject arg, AsyncCallback<DataObject> callback)
	{
		transmitAsync(arg, (res)-> callback.complete(res));
	}
	
	public final void publish(DataObject request, SimplePublish2<Object, JvxClientException> callback)
	{
		transmitAsync
		(
			request,
			new SimplePublish1<DataObject>()
			{
				@Override
				public void publish(DataObject data)
				{
					PacketTransferTools.publishResponse(callback, data);
				}
			}
		);
	}
	
	protected abstract void transmitAsync(DataObject req, SimplePublish1<DataObject> resp);

	public <T> T transmitSync(DataObject pack) throws JvxClientException
	{
		return PacketTransferTools.unpack(syncTransmit(pack));
	}
}
