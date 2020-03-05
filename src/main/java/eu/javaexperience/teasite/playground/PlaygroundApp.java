package eu.javaexperience.teasite.playground;

import eu.jvx.js.lib.bindings.H;
import eu.jvx.js.lib.bindings.VanillaTools;
import eu.jvx.js.lib.history.HistoryTools;
import eu.jvx.js.lib.layout.BaseLayout;
import eu.jvx.js.lib.style.TbsStyle;
import eu.jvx.js.lib.teavm.NativeJsSupportTeaVM;

public class PlaygroundApp
{
	protected static PlaygroundActivity[] ACTIVITIES =
	{
		new InputContentDemoActivity(),
		new LoggingDemoActivity()
	};
	
	public static void main(String[] args)
	{
		NativeJsSupportTeaVM.init();
		
		H root = BaseLayout.getDesignedRoot();
		
		H header = new H(VanillaTools.getDom().querySelector(".site-header"));
		H links = new H("div").attrs("style", "padding-bottom:20px");
		
		header.addChilds
		(
			new H("h1").attrs("style", "padding: 20px", "#text", "Teasite Playground"),
			links
		);
		
		for(PlaygroundActivity pa:ACTIVITIES)
		{
			links.addChilds
			(
				createActivityLink(pa.getName(), pa.getUrlActivity()),
				new H("span").attrs("#html", "&nbsp")
			);
		}
		
		String act = HistoryTools.$_GET("activity");
		if(null == act)
		{
			act = "";
		}
		
		for(PlaygroundActivity pa:ACTIVITIES)
		{
			if(pa.getUrlActivity().equals(act))
			{
				H cnt = new H("div");
				
				root.addChilds
				(
					new H("div").style(TbsStyle.LABEL_INFO).attrs("#text", pa.getName()),
					new H("span").attrs("#text", " Demo source class: "+pa.getClass().getName()),
					new H("br"),
					new H("br"),
					cnt
				);
				pa.start(cnt);
				return;
			}
		}
		
		root.addChilds
		(
			new H("div").attrs("#text", "Choose a demo activity")
		);
	}
	
	public static H createActivityLink(String name, String activity)
	{
		return new H("a").attrs("#text", name, "href", "?activity="+activity).style(TbsStyle.BTN_DEFAULT);
	}
}