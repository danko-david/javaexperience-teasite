package eu.teasite.frontend.api.transfers;

import eu.javaexperience.datareprez.DataObject;
import eu.javaexperience.interfaces.simple.publish.SimplePublish1;

public class NamespaceTransfer extends ApiPacketTransfer
{
	protected String ns;
	protected ApiPacketTransfer transf;
	
	public NamespaceTransfer(String ns, ApiPacketTransfer trf)
	{
		this.ns = ns;
		this.transf = trf;
	}

	@Override
	public void transmitAsync(DataObject packet, SimplePublish1<DataObject> callback)
	{
		packet.putString("N", this.ns);
		transf.transmitAsync(packet, callback);
	}
}
