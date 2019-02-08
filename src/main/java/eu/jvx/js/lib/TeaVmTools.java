package eu.jvx.js.lib;

import org.teavm.jso.JSBody;
import org.teavm.jso.JSObject;
import org.teavm.jso.browser.Window;
import org.teavm.jso.dom.html.HTMLElement;
import org.teavm.jso.dom.xml.NodeList;

import eu.javaexperience.teavm.templatesite.common.PageId;
import eu.javaexperience.teavm.templatesite.common.PageStorage;

public class TeaVmTools
{
	public static final boolean IS_FRONTEND;
	
	static
	{
		boolean teavm = true;
		try
		{
			NativeJsSupport.getSupport().newObject();
		}
		catch(Throwable e)
		{
			teavm = false;
		}
		IS_FRONTEND = teavm;
	}
	
	public static boolean isUrchin()
	{
		return !IS_FRONTEND;
	}
	
	public static boolean isOnFrontend()
	{
		return IS_FRONTEND;
	}
	
	public static <T extends PageId> T identifyPage(PageStorage<T> ps)
	{
		NodeList<? extends HTMLElement> ids = Window.current().getDocument().querySelectorAll(".page_identifier_data");
		for(int i=0;i<ids.getLength();++i)
		{
			String id = ids.item(i).getAttribute("data-page_id");
			for(T p: ps.getPages())
			{
				if(p.getId().equals(id))
				{
					return p;
				}
			}
		}
		
		return null;
	}
	
	public static boolean tryManageApiError(Object a, JvxClientException b)
	{
		if(null != b)
		{
			Window.alert(b.message);
			NativeJsSupport.getSupport().log("tryManageApiError", a, b);
			return true;
		}
		
		return false;
	}

	@JSBody(params = "obj", script = "return null != obj && undefined != obj")
	public static native boolean isValuable(JSObject eval);
}
