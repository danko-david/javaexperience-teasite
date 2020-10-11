package eu.teasite.frontend.api.transfers;

import java.util.HashMap;
import java.util.Map;

import org.teavm.jso.JSObject;
import org.teavm.jso.browser.Window;
import org.teavm.jso.dom.events.MessageEvent;
import org.teavm.jso.json.JSON;
import org.teavm.jso.websocket.WebSocket;

import eu.javaexperience.datareprez.DataObject;
import eu.javaexperience.interfaces.simple.SimpleGet;
import eu.javaexperience.interfaces.simple.publish.SimplePublish1;
import eu.javaexperience.patterns.behavioral.mediator.EventMediator;
import eu.javaexperience.retry.RetryTools;
import eu.javaexperience.teavm.datareprez.DataObjectTeaVMImpl;

public class WebSocketTransfer extends ApiPacketTransfer
{
	protected ManagedWebSocket websocket;
	protected String url;
	protected Boolean connected;
	
	protected void acceptSocket(ManagedWebSocket ws)
	{
		connected = null;
		websocket = ws;
		websocket.getWebSocket().onMessage(this::onMessage);
		websocket.getConnectionStateListener().addEventListener(connectionStateChange::dispatchEvent);
		connected = true;
		connectionStateChange.dispatchEvent(true);
	}
	
	protected void resendRequests()
	{
		WebSocket ws = websocket.getWebSocket();
		for(WebSocketPendingRequest pr:pendingResponses.values())
		{
			pr.send(ws);
		}
	}
	
	public static WebSocketTransfer connectPath(String path)
	{
		return new WebSocketTransfer("ws://"+Window.current().getLocation().getHost()+path);
	}
	
	protected SimplePublish1<DataObject> serverEvents;
	
	public WebSocketTransfer(String url)
	{
		this.url = url;
		reconnect();
	}
	
	protected ManagedWebSocket reconnectSocket()
	{
		ManagedWebSocket ret = ManagedWebSocket.openConnection(url);
		if(null == ret)
		{
			throw new RuntimeException("Can't connect");
		}
		return ret;
	}
	
	public void reconnect()
	{
		SimpleGet<ManagedWebSocket> reconn = RetryTools.waitReconnect(()->reconnectSocket(), "WebSocket");
		acceptSocket(reconn.get());
		resendRequests();
	}
	
	protected synchronized void onConnectionStateChange(boolean conn)
	{
		if(this.connected != Boolean.valueOf(conn))
		{
			this.connected = conn;
			connectionStateChange.dispatchEvent(conn);
		}
	}
	
	protected EventMediator<Boolean> connectionStateChange = new EventMediator<>();
	
	protected void onMessage(MessageEvent msg)
	{
		DataObjectTeaVMImpl obj = new DataObjectTeaVMImpl(JSON.parse(msg.getDataAsString()));
		String trace = obj.optString("t");
		if(null != trace)
		{
			WebSocketPendingRequest pending = pendingResponses.remove(trace);
			if(null != pending && null != pending.handler)
			{
				pending.handler.publish(obj);
				return;
			}
		}
		
		if(null != serverEvents)
		{
			serverEvents.publish(obj);
		}
	}
	
	protected class WebSocketPendingRequest
	{
		public DataObject request;
		public String traceId;
		public SimplePublish1<DataObject> handler;
		
		public WebSocketPendingRequest(DataObject req, String trace, SimplePublish1<DataObject> resp)
		{
			this.request = req;
			this.traceId = trace;
			this.handler = resp;
		}
		
		public void send(WebSocket ws)
		{
			ws.send(JSON.stringify((JSObject)request.getImpl()));
		}
	}
	
	protected long id = 0;
	protected Map<String, WebSocketPendingRequest> pendingResponses = new HashMap<>();
	
	@Override
	protected void transmitAsync(DataObject req, SimplePublish1<DataObject> resp)
	{
		String trace = String.valueOf(++id);
		req.putString("t", trace);
		WebSocketPendingRequest wspr = new WebSocketPendingRequest(req, trace, resp);
		pendingResponses.put(trace, wspr);
		wspr.send(websocket.getWebSocket());
	}

	public void setServerEventListener(SimplePublish1<DataObject> serverEvents)
	{
		this.serverEvents = serverEvents;
	}
	
	public EventMediator<Boolean> getConnectionStateListener()
	{
		return connectionStateChange;
	}

	public void setAutoReconnect()
	{
		connectionStateChange.addEventListener((e)->
		{
			if(Boolean.FALSE == e)
			{
				System.out.println("disconnected: reconnect");
				//must run in new thread to `sleep` have a thread context when
				//invoking `waitConnect()`;
				new Thread()
				{
					public void run()
					{
						reconnect();
					}
				}.start();
			}
			else
			{
				resendRequests();
			}
		});
	}
}
