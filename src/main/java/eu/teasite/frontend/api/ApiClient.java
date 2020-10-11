package eu.teasite.frontend.api;

import eu.javaexperience.datareprez.DataObject;
import eu.javaexperience.interfaces.simple.SimpleCall;
import eu.javaexperience.patterns.behavioral.mediator.EventMediator;
import eu.javaexperience.semantic.references.MayNull;
import eu.teasite.frontend.api.transfers.AjaxTransfer;
import eu.teasite.frontend.api.transfers.ApiPacketTransfer;
import eu.teasite.frontend.api.transfers.WebSocketTransfer;

/**
 * Creating javascript side RPC solution just wont work:
 * 	If i make RPC interface (so an RPC interface class which can get
 * 	from ExportApi class) native (this interface should extend JSObject and all
 * 	parameter should be teavm conformable) will not satisfy both side:
 * 	parameters are teavm conformable so they will be wrapped into the native
 * 	javacript environment, but the callback's "publish" method will be
 * 	"optimised" out even if callback marked with @Functor 	 
 * 
 * 2nd try: make RPC java conformable.
 * 
 * 
 * */
public class ApiClient extends ApiTransaction
{
	public ApiClient(String startUrl, boolean websocket)
	{
		super(websocket?WebSocketTransfer.connectPath(startUrl+"/websocket"):new AjaxTransfer(startUrl+"/ajax"));
		if(websocket)
		{
			WebSocketTransfer wst = (WebSocketTransfer) transfer;
			wst.setServerEventListener(serverEvents::dispatchEvent);
		}
	}
	
	public ApiClient(ApiPacketTransfer transfer)
	{
		super(transfer);
	}
	
	protected EventMediator<DataObject> serverEvents = new EventMediator<DataObject>();
	
	public EventMediator<DataObject> getServerEventManager()
	{
		return serverEvents;
	}
	
	public ApiTransaction startTransaction()
	{
		return new ApiTransactionCollector(this);
	}

	@Override
	protected ApiPacketTransfer wrapTransfer(Class cls, ApiPacketTransfer transfer)
	{
		return transfer;
	}

	@Override public void commit(@MayNull SimpleCall onDone){}//has no effect

	public static ApiClient connectTeasiteDefault(boolean websocket)
	{
		return new ApiClient("/site_api", websocket);
	}
}
