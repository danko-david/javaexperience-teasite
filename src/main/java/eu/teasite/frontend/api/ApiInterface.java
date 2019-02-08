package eu.teasite.frontend.api;

import eu.javaexperience.datareprez.DataArray;
import eu.javaexperience.datareprez.DataObject;
import eu.javaexperience.datareprez.DataReprezTools;
import eu.javaexperience.datareprez.convertFrom.DataWrapper;
import eu.javaexperience.teavm.datareprez.DataObjectTeaVMImpl;
import eu.teasite.frontend.api.transfers.ApiPacketTransfer;

public abstract class ApiInterface
{
	protected ApiPacketTransfer transfer;
	
	protected static final DataWrapper WRAPPER = DataReprezTools.combineWrappers
	(
		DataReprezTools.WRAP_DATA_LIKE,
		DataReprezTools.WRAP_ARRAY_COLLECTION_MAP
	);
	
	protected DataObject pack(String method, Object... params)
	{
		DataObject ret = new DataObjectTeaVMImpl();
		
		ret.putString("f", method);
		
		DataArray p = ret.newArrayInstance();
		
		int i=0;
		for(Object o:params)
		{
			DataReprezTools.put
			(
				WRAPPER,
				p,
				i++,
				o
			);
		}
		
		ret.putArray("p", p);
		
		return ret;
	}
}
