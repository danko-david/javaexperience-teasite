package eu.jvx.js.lib.ui.component.func;

import java.util.Map;
import java.util.Map.Entry;

import org.teavm.jso.dom.events.EventListener;
import org.teavm.jso.dom.events.MouseEvent;
import org.teavm.jso.dom.html.HTMLElement;

import eu.javaexperience.interfaces.simple.publish.SimplePublish1;
import eu.javaexperience.semantic.references.MayNull;
import eu.jvx.js.lib.bindings.VanillaTools;
import eu.jvx.js.lib.bindings.VanillaTools.ClassList;

public class ToggleStateTools
{
	/**
	 * inStateToState: null specifies the starting state
	 * */
	public static ToggleState<String> toggleClassOnClickWithMap
	(
		final HTMLElement elem,
		final Map<String, String> inStateToState,
		final @MayNull SimplePublish1<ToggleState<String>> onChange
	)
	{
		final ToggleState<String> ret = new ToggleState<String>(elem, inStateToState.get(null))
		{
			@Override
			public String toggleState()
			{
				ClassList cl = VanillaTools.getClassList(root);
				for(Entry<String, String> kv:inStateToState.entrySet())
				{
					String k = kv.getKey();
					if(cl.contains(k))
					{
						return kv.getValue();
					}
				}
				
				return null;
			}

			@Override
			public void updateState()
			{
				String cls = getState();
				ClassList cl = VanillaTools.getClassList(root);
				for(Entry<String, String> kv:inStateToState.entrySet())
				{
					String k = kv.getKey();
					cl.remove(k);
				}
				cl.add(cls);
				
				if(null != onChange)
				{
					onChange.publish(this);
				}
			}
		};
		
		elem.listenClick(new EventListener<MouseEvent>()
		{
			@Override
			public void handleEvent(MouseEvent arg0)
			{
				ret.toggle();
			}
		});
		
		return ret;
	}
}
