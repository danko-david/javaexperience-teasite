
package eu.teasite.api;

import eu.javaexperience.datareprez.DataObject;
import eu.javaexperience.rpc.RpcRequest;
import eu.javaexperience.rpc.RpcSession;
import eu.javaexperience.rpc.bulk.BulkApiRequestApi;
import eu.javaexperience.rpc.web.RpcUrlNode;
import eu.javaexperience.web.Context;
import eu.javaexperience.web.facility.SiteFacilityTools;

public class MultiplexedApiCall extends BulkApiRequestApi
{
	protected final RpcUrlNode<? extends RpcRequest, ? extends RpcSession> api;

	public MultiplexedApiCall(RpcUrlNode<? extends RpcRequest, ? extends RpcSession> node)
	{
		this.api = node;
	}

	@Override
	public DataObject handleSingleRequest(DataObject obj)
	{
		Context ctx = SiteFacilityTools.getCurrentContext();
		return api.serveRequest(ctx, obj);
	}
}