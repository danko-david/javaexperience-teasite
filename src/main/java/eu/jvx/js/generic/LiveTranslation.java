package eu.jvx.js.generic;

import java.util.LinkedList;
import java.util.List;

import org.teavm.jso.dom.html.HTMLElement;
import org.teavm.jso.dom.xml.Element;

import eu.jvx.js.lib.ImpersonalisedHtml;
import eu.jvx.js.lib.bindings.VanillaTools;

public class LiveTranslation implements ImpersonalisedHtml
{
	protected static List<LiveTranslation> liveTranslations = new LinkedList<>();
	
	protected Translation trans;
	protected HTMLElement container;
	
	private LiveTranslation(){}
	
	public static LiveTranslation register(Translation t, String tag, String... attrs)
	{
		LiveTranslation lt = new LiveTranslation();
		lt.trans = t;
		lt.container = VanillaTools.inlineCreateElement(tag, attrs);
		liveTranslations.add(lt);
		return lt;
	}
	
	public static LiveTranslation register(final String id, String tag, String... attrs)
	{
		LiveTranslation lt = new LiveTranslation();
		lt.trans = new Translation()
		{
			@Override
			public String getLabel()
			{
				return id;
			}
			
			@Override
			public String getContent(Language lang)
			{
				// TODO Auto-generated method stub
				return null;
			}
		};
		lt.container = VanillaTools.inlineCreateElement(tag, attrs);
		liveTranslations.add(lt);
		return lt;
	}
	
	
	protected void updateLanguage(Language lang)
	{
		//TODO check detached
		if(VanillaTools.isDetached(container))
		{
			liveTranslations.remove(this);
			return;
		}
		
		if(null == lang)
		{
			container.setInnerHTML(trans.getLabel());
		}
		else
		{
			//TODO skip if already the right language
			container.setInnerHTML(trans.getContent(lang));
		}
	}
	
	public static void updateAllTranslationLanguage(Language lang)
	{
		for(LiveTranslation lt:liveTranslations)
		{
			lt.updateLanguage(lang);
		}
	}
	
	@Override
    public Object getImpersonator()
    {
	    return this;
    }

	@Override
    public Element getHtml()
    {
	    return container;
    }
}