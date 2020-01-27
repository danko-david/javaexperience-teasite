package eu.jvx.js.lib.layout;

import eu.javaexperience.interfaces.simple.SimpleGet;
import eu.jvx.js.lib.bindings.H;
import eu.jvx.js.lib.bindings.VanillaTools;
import eu.jvx.js.lib.style.StyleTools.StyleAlaCarte;

import static eu.jvx.js.lib.bindings.H.*;

import org.teavm.jso.browser.Window;

public class BaseLayout
{
	private BaseLayout(){}
	
	protected static SimpleGet<H> DEFAULT_CREATOR = ()->
	{
		VanillaTools.addToHeader(H("meta", "content", "width=device-width, initial-scale=1.0", "name", "viewport").getHtml());
		
		H ret = H("div").attrs("id", "site_mainframe", "class", "content");
		H(Window.current().getDocument().getBody()).
		addChilds
		(
			H("div").attrs("class", "site-header").style(StyleAlaCarte.TEXT_CENTER),
			H("div").attrs("class", "container").addChilds
			(
				H("div").attrs("class", "row").addChilds(ret)
			)
		);
		
		return ret;
	};
	
	protected static SimpleGet<H> CREATOR = DEFAULT_CREATOR;
	protected static H MAIN_WRAPPER;
	
	public static void setMainStructure(SimpleGet<H> creator)
	{
		if(null != MAIN_WRAPPER)
		{
			throw new RuntimeException("Basic site structure already initialized");
		}
		CREATOR = creator;
	}
	
	public static H getDesignedRoot()
	{
		if(null == MAIN_WRAPPER)
		{
			MAIN_WRAPPER = CREATOR.get();
		}
		return MAIN_WRAPPER;
	}
}
