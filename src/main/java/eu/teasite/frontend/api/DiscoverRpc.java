package eu.teasite.frontend.api;

import java.util.Map;

import eu.javaexperience.rpc.discover.DiscoverRpcInterface;

public class DiscoverRpc extends ApiInterface implements DiscoverRpcInterface
{
	@Override
	public String[] getNamespaces()
	{
		return transfer.transmitSync(pack("getNamespace"));
	}

	@Override
	public String help()
	{
		return transfer.transmitSync(pack("help"));
	}

	@Override
	public String source(String language, String namespace, Map<String, String> params)
	{
		return transfer.transmitSync(pack("source", language, namespace, params));
	}

	@Override
	public boolean ping()
	{
		return transfer.transmitSync(pack("ping"));
	}
}
