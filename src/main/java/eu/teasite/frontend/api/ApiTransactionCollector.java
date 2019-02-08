package eu.teasite.frontend.api;

import java.util.ArrayList;

import eu.javaexperience.datareprez.DataArray;
import eu.javaexperience.datareprez.DataObject;
import eu.javaexperience.interfaces.simple.SimpleCall;
import eu.javaexperience.interfaces.simple.publish.SimplePublish1;
import eu.javaexperience.interfaces.simple.publish.SimplePublish2;
import eu.javaexperience.reflect.CastTo;
import eu.javaexperience.semantic.references.MayNull;
import eu.javaexperience.teavm.datareprez.DataObjectTeaVMImpl;
import eu.jvx.js.lib.JvxClientException;
import eu.teasite.frontend.api.transfers.ApiPacketTransfer;

public class ApiTransactionCollector extends ApiTransaction
{
	protected ApiClient client;
	
	public ApiTransactionCollector(ApiClient client)
	{
		super(client.transfer);
		this.client = client;
	}
	
	protected static class PendingRequest
	{
		DataObject req;
		SimplePublish1<DataObject> callback;
		
		public PendingRequest(DataObject req, SimplePublish1<DataObject> callback)
		{
			this.req = req;
			this.callback = callback;
		}
	}
	
	protected ArrayList<PendingRequest> pending = new ArrayList<>();
	
	public ApiTransactionCollector(ApiPacketTransfer transfer)
	{
		super(transfer);
	}

	@Override
	protected ApiPacketTransfer wrapTransfer(final Class cls, ApiPacketTransfer transfer)
	{
		return new ApiPacketTransfer()
		{
			@Override
			public void transmitAsync(DataObject a, SimplePublish1<DataObject> b)
			{
				a.putString("N", cls.getSimpleName());
				long t = pending.size();
				a.putLong("t", t);
				pending.add(new PendingRequest(a, b));
			}
		};
	}
	
	protected long tid = 0;
	
	@Override
	public void commit(@MayNull SimpleCall onDone)
	{
		DataObject o = new DataObjectTeaVMImpl();
		o.putLong("t", ++tid);
		
		final ArrayList<PendingRequest> packets = (ArrayList<PendingRequest>) pending.clone();
		pending.clear();
		
		DataArray ps = o.newArrayInstance();
		for(int i=0;i<packets.size();++i)
		{
			ps.putObject(packets.get(i).req);
		}
		o.putArray("p", ps);
		
		MultiplexedApiCall multi = client.getApiClass(MultiplexedApiCall.class);
		multi.doMulticall
		(
			new SimplePublish2<DataObject, JvxClientException>()
			{
				@Override
				public void publish
				(
					DataObject ret,
					JvxClientException b
				)
				{
					DataArray arr = ret.getArray("r");
					for(int i=0;i<arr.size();++i)
					{
						DataObject resp = arr.getObject(i);
						try
						{
						
							Object o = resp.get("t");
							int id = (int) CastTo.Int.cast(o);
							SimplePublish1<DataObject> cb = packets.get(id).callback;
							if(null != cb)
							{
								cb.publish(resp);
							}
						}
						catch(Exception e)
						{
							e.printStackTrace();
						}
					}
					
					if(null != onDone)
					{
						onDone.call();
					}
				}
			},
			o
		);
	}
}
