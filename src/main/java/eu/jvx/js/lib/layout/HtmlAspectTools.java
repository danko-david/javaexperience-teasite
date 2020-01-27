package eu.jvx.js.lib.layout;

import eu.javaexperience.text.Format;
import eu.jvx.js.lib.bindings.H;

public class HtmlAspectTools
{
	public static String percentage(double req, double base)
	{
		return Format.formatDouble((req/base)*100.0)+"%";
	}
	
	//https://stackoverflow.com/questions/1495407/maintain-the-aspect-ratio-of-a-div-with-css
	public static HWrapper createAspectBox(int width, int height)
	{
		H content = null;
		H root = new H("div").attrs("style", "width:100%; position: relative; padding-bottom:"+percentage(height, width)).addChilds
		(
			content = new H("div").attrs("style", "position:absolute; top: 0; bottom: 0; left: 0; right: 0;")
		);
		
		return new HWrapper(root, content);
	}
	
	public static void addKeepDistanceWithSize(H container, H element, int cwidth, int cheight, int ew, int eh, int w, int h)
	{
		String style = element.getHtml().getAttribute("style");
		if(null == style)
		{
			style = "";
		}
		else
		{
			style += ";";
		}
		
		style += "position: absolute; top: "+percentage(eh, cheight)+"; left: "+percentage(ew, cwidth)+"; ";
		style += "width: "+percentage(w, cwidth)+"; height: "+percentage(h, cheight)+"; ";
		
		container.addChilds(element.attrs("style", style));
	}
}
