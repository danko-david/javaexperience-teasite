package eu.jvx.js.lib.ui.component.func;

import java.util.ArrayList;
import java.util.List;

import org.teavm.tooling.TeaVMTool;

import eu.jvx.js.lib.TeaVmTools;

public abstract class HtmlListContainer<D, E> extends HtmlDataContainer<D>
{
	@Override
	public D getData()
	{
		int l = getNoOfContainers();
		ArrayList<E> lst = new ArrayList<E>();
		for(int i=0;i<l;++i)
		{
			lst.add(getNthContainer(i).getData());
		}
		
		return pack(lst);
	}
	
	@Override
	public void setData(D data)
	{
		List<E> ds = extract(data);
		
		//ensure container exact count
		
		int crnt = getNoOfContainers();
		int req = ds.size();
		
		if(crnt > req)
		{
			for(int i=req;i < crnt;++i)
			{
				removeContainer(0);
			}
		}
		else if(req > crnt)
		{
			for(int i=crnt;i<req;++i)
			{
				createNewContainerLastPoz();
			}
		}
		
		for(int i=0;i<req;++i)
		{
			getNthContainer(i).setData(ds.get(i));
		}
	}
	
	public abstract List<E> extract(D data);
	public abstract D pack(List<E> elemsData);
	
	public abstract void removeContainer(int i);
	public abstract int getNoOfContainers();
	
	
	public abstract HtmlDataContainer<E> getNthContainer(int i);
	
	public abstract HtmlDataContainer<E> createNewContainerLastPoz();
	
	static
	{
		if(TeaVmTools.isUrchin())
		{
			HtmlListContainerUserEditable ed = new HtmlListContainerUserEditable()
			{
				@Override
				protected HtmlDataContainer<String> create()
				{
					return null;
				}
			};
			
			ed.setData("[\"something\"]");
			System.out.println(ed.getData());
		}
		
	}
}
