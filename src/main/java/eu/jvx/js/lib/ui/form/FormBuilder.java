package eu.jvx.js.lib.ui.form;

import org.teavm.jso.dom.events.Event;
import org.teavm.jso.dom.events.EventListener;
import org.teavm.jso.dom.html.HTMLElement;

import eu.javaexperience.datareprez.DataObject;
import eu.javaexperience.interfaces.simple.publish.SimplePublish1;
import eu.jvx.js.lib.ImpersonalisedHtml;
import eu.jvx.js.lib.bindings.H;
import eu.jvx.js.lib.ui.FrontendTools;
import eu.jvx.js.tbs.ui.TbsLayoutTools;
import static eu.jvx.js.lib.bindings.H.H;
import static eu.jvx.js.lib.bindings.VanillaTools.*;

public class FormBuilder implements Cloneable
{
	protected H root = new H("form");
	
	public FormBuilder onSubmit(final SimplePublish1<DataObject> onSubmit)
	{
		root.on("submit", new EventListener<Event>()
		{
			@Override
			public void handleEvent(Event arg0)
			{
				arg0.preventDefault();
				if(null != onSubmit)
				{
					onSubmit.publish(FrontendTools.serializeInputsInArea(root.getHtml()));
				}
			}
		});
		return this;
	}
	
	public FormBuilder addSm(int l, int d, String label, HTMLElement data)
	{
		root.addChilds
		(
			H("div").attrs("class", "form-group").addChilds
			(
				TbsLayoutTools.sm(l, new H("span").attrs ("#text", label).getHtml(), d, data),
				H("div").attrs("class", "clearfix")
			).getHtml()
		);
		
		return this;
	}
	
	public FormBuilder addSm(int l, int d, HTMLElement label, HTMLElement data)
	{
		root.addChilds
		(
			H("div").attrs("class", "form-group").addChilds
			(
				TbsLayoutTools.sm(l, label, d, data),
				H("div").attrs("class", "clearfix")
			).getHtml()
		);
		
		return this;
	}
	
	public FormBuilder addSm(int l, int d, ImpersonalisedHtml t, String id, ImpersonalisedHtml elem)
	{
		root.addChilds
		(
			H("div").attrs("class", "form-group").addChilds
			(
				TbsLayoutTools.sm(l, t.getHtml(), d, elem.getHtml()),
				H("div").attrs("class", "clearfix")
			).getHtml()
		);
		
		return this;
	}
	
	public FormBuilder addRaw(HTMLElement elem)
	{
		root.addChilds(elem);
		return this;
	}
	
	public HTMLElement getHtml()
	{
		return root.getHtml();
	}
	
	public H getRoot()
	{
		return root;
	}

	public FormBuilder nerf()
	{
		onSubmit((e)->{});
		return this;
	}
}
