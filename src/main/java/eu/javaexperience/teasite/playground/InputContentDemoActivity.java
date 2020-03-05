package eu.javaexperience.teasite.playground;

import eu.jvx.js.lib.bindings.H;
import eu.jvx.js.lib.bindings.VanillaTools;
import eu.jvx.js.tbs.ui.TbsLayoutTools;

public class InputContentDemoActivity implements PlaygroundActivity
{
	public static H createContentCopyBlock(H from, H... targets)
	{
		H ts = new H("div");
		for(H h:targets)
		{
			ts.addChilds(new H("div").addChilds(h));
		}
		
		from.on
		(
			"change",
			e->
			{
				String content = VanillaTools.getContent(from.getHtml());
				for(H t:targets)
				{
					VanillaTools.setContent(t.getHtml(), content);
				}
			}
		);
		
		return new H
		(
			TbsLayoutTools.sm
			(
				4,
				from.getHtml(),
				8,
				ts.getHtml()
			)
		);
	}
	
	public static H[] createContentAvailableBlocks(String... selectOptions)
	{
		H sel = new H("select");
		for(String s:selectOptions)
		{
			sel.addChilds(new H("option").attrs("#text", s, "value", s));
		}
		
		return new H[]
		{
			new H("input").attrs("type", "text"),
			new H("input").attrs("type", "number"),
			new H("input").attrs("type", "checkbox", "value", "true"),
			new H("input").attrs("type", "radio"),
			sel,
			new H("textarea"),
			new H("span"),
		};
	}
	
	public static void handle(H root)
	{
		for(H h:createContentAvailableBlocks("false", "true"))
		{
			root.addChilds
			(
				createContentCopyBlock(h, createContentAvailableBlocks("false", "true"))
				.attrs("style", "margin-top:20px;border-bottom: 2px solid black")
			);
		}
	}

	@Override
	public String getName()
	{
		return "Input Content Demo";
	}

	@Override
	public String getUrlActivity()
	{
		return "input_content_demo";
	}

	@Override
	public void start(H container)
	{
		handle(container);
	}
}
