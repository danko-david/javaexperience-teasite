package eu.teasite.frontend.api.transfers;

import eu.javaexperience.datareprez.DataObject;
import eu.javaexperience.interfaces.simple.SimpleCall;
import eu.javaexperience.interfaces.simple.publish.SimplePublish2;
import eu.jvx.js.lib.JvxClientException;
import eu.jvx.js.lib.ui.FrontendTools;

public class PacketTransferTools
{
	public static void publishResponse(SimplePublish2<Object, JvxClientException> callback, DataObject data)
	{
		FrontendTools.runOnThread
		(
			new SimpleCall()
			{
				@Override
				public void call()
				{
					if(null != data)
					{
						Object ret = data.opt("r");
						DataObject exc = data.optObject("e");
						
						if(null != callback)
						{
							JvxClientException ex = null;
							if(null != exc)
							{
								ex = new JvxClientException(exc.getString("message"), exc.getString("detail"));
							}
							
							callback.publish(ret, ex);
						}
					}
				}
			}
		);
	}
	
	public static <T> T unpack(DataObject data) throws JvxClientException
	{
		if(null != data)
		{
			Object ret = data.opt("r");
			DataObject exc = data.optObject("e");
			
			JvxClientException ex = null;
			if(null != exc)
			{
				System.out.println(exc.getString("message"));
				throw new JvxClientException(exc.getString("message"), exc.getString("detail"));
			}
			
			return (T) ret;
		}
		return null;
	}
	
	/*public static void publishResponse(JSFunction callback, JSObject data)
	{
		final NativeJs js = NativeJsSupport.getSupport();
		if(null != data)
		{
			Object ret = js.getProp(data, "r");
			Object exc = js.getProp(data, "e");
			
			if(null != callback)
			{
				SimplePublish2<Object, JvxClientException> cb = (SimplePublish2<Object, JvxClientException>) callback;
				JvxClientException ex = null;
				if(null != exc)
				{
					ex = new JvxClientException((String)js.getProp(exc, "message"), (String) js.getProp(exc, "detail"));
				}
				cb.publish(ret, ex);
			}
		}
	}
	
	public static JSObject wrapToNative(Object o)
	{
		final NativeJs js = NativeJsSupport.getSupport();
		return JSString.valueOf(o.toString());
	}
	
	public static JSObject prepreaRequestPacket(JSObject packet)
	{
		final NativeJs js = NativeJsSupport.getSupport();
		JSArray<JSObject> arr = (JSArray<JSObject>) js.getProp(packet, "p");
		
		for(int i=0;i<arr.getLength();++i)
		{
			Object o = arr.get(i);
			if(null != o)
			{
				if(null != js.getProp(o, "$toString"))
				{
					//rpc server can cast this
					arr.set(i, wrapToNative(o));
				}
			}
		}
		
		return packet;
	}*/
}
