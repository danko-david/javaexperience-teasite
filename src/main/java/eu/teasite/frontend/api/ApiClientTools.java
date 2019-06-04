package eu.teasite.frontend.api;

import eu.javaexperience.interfaces.simple.getBy.GetBy1;
import eu.javaexperience.interfaces.simple.publish.SimplePublish2;
import eu.javaexperience.rpc.client.JvxClientException;
import eu.jvx.js.lib.ui.FrontendTools;

public class ApiClientTools
{
	public static <R, P> SimplePublish2<P, JvxClientException> createWrapperReceiver(final SimplePublish2<R, JvxClientException> callback, final GetBy1<R, P> wrap)
	{
		return FrontendTools.wrapDispatchWithThread
		(
			new SimplePublish2<P, JvxClientException>()
			{
				@Override
				public void publish(final P a, final JvxClientException b)
				{
					if(null != b)
					{
						callback.publish(null, b);
					}
					else if(null != a)
					{
						callback.publish(wrap.getBy(a), b);
					}
					else
					{
						callback.publish(null, null);
					}
				}
			}
		);
	}
}
