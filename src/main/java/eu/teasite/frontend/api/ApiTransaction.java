package eu.teasite.frontend.api;

import java.util.HashMap;

import eu.javaexperience.collection.map.KeyVal;
import eu.javaexperience.interfaces.simple.SimpleCall;
import eu.javaexperience.semantic.references.MayNull;
import eu.teasite.frontend.api.transfers.ApiPacketTransfer;
import eu.teasite.frontend.api.transfers.NamespaceTransfer;

public abstract class ApiTransaction
{
	protected ApiPacketTransfer transfer;
	
	public ApiTransaction(ApiPacketTransfer transfer)
	{
		this.transfer = transfer;
	}
	
	protected boolean autocommit = true;
	
	protected HashMap<KeyVal<Class, String>, Object> apiInstances = new HashMap<>();
	
	protected static <API> API newApiInstance(Class<API> cls, ApiPacketTransfer transfer)
	{
		try
		{
			API ret = cls.newInstance();
			((ApiInterface)ret).transfer = transfer;
			return ret;
		}
		catch(Exception e)
		{
			throw new RuntimeException(e);
		}
	}
	
	protected abstract ApiPacketTransfer wrapTransfer(Class cls, ApiPacketTransfer transfer);
	
	public <API extends ApiInterface> API getApiClass(Class<API> cls)
	{
		return getApiClass(cls, cls.getSimpleName());
	}
	
	public <API extends ApiInterface> API getApiClass(Class<API> cls, String namespace)
	{
		KeyVal key = new KeyVal<>(cls, namespace);
		Object ret = apiInstances.get(key);
		if(null == ret)
		{
			NamespaceTransfer trans = new NamespaceTransfer(namespace, transfer);
			
			ret = newApiInstance(cls, wrapTransfer(cls, trans));
			
			if(null != ret)
			{
				apiInstances.put(key, ret);
			}
		}
		
		return (API) ret;
	}
	
	public ApiPacketTransfer getBackendTransfer()
	{
		return transfer;
	}
	
	public abstract void commit(@MayNull SimpleCall onDone);

	public void commit()
	{
		commit(null);
	}
}
