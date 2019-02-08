package eu.jvx.js.lib.style;

import org.teavm.jso.dom.html.HTMLElement;

import eu.jvx.js.lib.bindings.VanillaTools;
import eu.jvx.js.lib.ui.style.CssStyleSheet;
import eu.jvx.js.lib.ui.style.StyleContainer;

public class StyleTools
{
	/**
	 * Like bootstrap 4.0
	 * */
	public static enum StyleAlaCarte implements StyleDecoratorSource, StyleClassSource
	{
		DISPLAY_INLINE_BLOCK("d-inline", "display: inline-block"),
		DISPLAY_BLOCK("d-block", "display: block"),
		
		/************************* Paddings ***********************************/
		
		PADDING_0("p-0", "padding: 0"),
		PADDING_1("p-1", "padding: .25rem"),
		PADDING_2("p-2", "padding: .5rem"),
		PADDING_3("p-3", "padding: 1rem"),
		PADDING_4("p-4", "padding: 1.5rem"),
		PADDING_5("p-5", "padding: 3rem"),
		PADDING_A("p-a", "padding: auto"),
		
		PADDING_LEFT_0("pl-0", "padding-left: 0"),
		PADDING_LEFT_1("pl-1", "padding-left: .25rem"),
		PADDING_LEFT_2("pl-2", "padding-left: .5rem"),
		PADDING_LEFT_3("pl-3", "padding-left: 1rem"),
		PADDING_LEFT_4("pl-4", "padding-left: 1.5rem"),
		PADDING_LEFT_5("pl-5", "padding-left: 3rem"),
		PADDING_LEFT_A("pl-a", "padding-left: auto"),
		
		PADDING_RIGHT_0("pr-0", "padding-right: 0"),
		PADDING_RIGHT_1("pr-1", "padding-right: .25rem"),
		PADDING_RIGHT_2("pr-2", "padding-right: .5rem"),
		PADDING_RIGHT_3("pr-3", "padding-right: 1rem"),
		PADDING_RIGHT_4("pr-4", "padding-right: 1.5rem"),
		PADDING_RIGHT_5("pr-5", "padding-right: 3rem"),
		PADDING_RIGHT_A("pr-a", "padding-right: auto"),
		
		PADDING_TOP_0("pt-0", "padding-top: 0"),
		PADDING_TOP_1("pt-1", "padding-top: .25rem"),
		PADDING_TOP_2("pt-2", "padding-top: .5rem"),
		PADDING_TOP_3("pt-3", "padding-top: 1rem"),
		PADDING_TOP_4("pt-4", "padding-top: 1.5rem"),
		PADDING_TOP_5("pt-5", "padding-top: 3rem"),
		PADDING_TOP_A("pt-a", "padding-top: auto"),
		
		PADDING_BOTTOM_0("pt-0", "padding-bottom: 0"),
		PADDING_BOTTOM_1("pt-1", "padding-bottom: .25rem"),
		PADDING_BOTTOM_2("pt-2", "padding-bottom: .5rem"),
		PADDING_BOTTOM_3("pt-3", "padding-bottom: 1rem"),
		PADDING_BOTTOM_4("pt-4", "padding-bottom: 1.5rem"),
		PADDING_BOTTOM_5("pt-5", "padding-bottom: 3rem"),
		PADDING_BOTTOM_A("pt-a", "padding-bottom: auto"),
		
		PADDING_X_0("px-0", "padding-left: 0; padding-right: 0"),
		PADDING_X_1("px-1", "padding-left: .25rem; padding-right: .25rem"),
		PADDING_X_2("px-2", "padding-left: .5rem; padding-right: .5rem"),
		PADDING_X_3("px-3", "padding-left: 1rem; padding-right: 1rem"),
		PADDING_X_4("px-4", "padding-left: 1.5rem; padding-right: 1.5rem"),
		PADDING_X_5("px-5", "padding-left: 3rem; padding-right: 3rem"),
		PADDING_X_A("px-a", "padding-left: auto; padding-right: auto"),
		
		PADDING_Y_0("py-0", "padding-top: 0; padding-bottom: 0"),
		PADDING_Y_1("py-1", "padding-top: .25rem; padding-bottom: .25rem"),
		PADDING_Y_2("py-2", "padding-top: .5rem; padding-bottom: .5rem"),
		PADDING_Y_3("py-3", "padding-top: 1rem; padding-bottom: 1rem"),
		PADDING_Y_4("py-4", "padding-top: 1.5rem; padding-bottom: 1.5rem"),
		PADDING_Y_5("py-5", "padding-top: 3rem; padding-bottom: 3rem"),
		PADDING_Y_A("py-a", "padding-top: auto; padding-bottom: auto"),
		
		/**************************** Margins *********************************/
		
		MARGIN_0("m-0", "margin: 0"),
		MARGIN_1("m-1", "margin: .25rem"),
		MARGIN_2("m-2", "margin: .5rem"),
		MARGIN_3("m-3", "margin: 1rem"),
		MARGIN_4("m-4", "margin: 1.5rem"),
		MARGIN_5("m-5", "margin: 3rem"),
		MARGIN_A("m-a", "margin: auto"),
		
		MARGIN_LEFT_0("ml-0", "margin-left: 0"),
		MARGIN_LEFT_1("ml-1", "margin-left: .25rem"),
		MARGIN_LEFT_2("ml-2", "margin-left: .5rem"),
		MARGIN_LEFT_3("ml-3", "margin-left: 1rem"),
		MARGIN_LEFT_4("ml-4", "margin-left: 1.5rem"),
		MARGIN_LEFT_5("ml-5", "margin-left: 3rem"),
		MARGIN_LEFT_A("ml-a", "margin-left: auto"),
		
		MARGIN_RIGHT_0("mr-0", "margin-right: 0"),
		MARGIN_RIGHT_1("mr-1", "margin-right: .25rem"),
		MARGIN_RIGHT_2("mr-2", "margin-right: .5rem"),
		MARGIN_RIGHT_3("mr-3", "margin-right: 1rem"),
		MARGIN_RIGHT_4("mr-4", "margin-right: 1.5rem"),
		MARGIN_RIGHT_5("mr-5", "margin-right: 3rem"),
		MARGIN_RIGHT_A("mr-a", "margin-right: auto"),
		
		MARGIN_TOP_0("mt-0", "margin-top: 0"),
		MARGIN_TOP_1("mt-1", "margin-top: .25rem"),
		MARGIN_TOP_2("mt-2", "margin-top: .5rem"),
		MARGIN_TOP_3("mt-3", "margin-top: 1rem"),
		MARGIN_TOP_4("mt-4", "margin-top: 1.5rem"),
		MARGIN_TOP_5("mt-5", "margin-top: 3rem"),
		MARGIN_TOP_A("mt-a", "margin-top: auto"),
		
		MARGIN_BOTTOM_0("mt-0", "margin-bottom: 0"),
		MARGIN_BOTTOM_1("mt-1", "margin-bottom: .25rem"),
		MARGIN_BOTTOM_2("mt-2", "margin-bottom: .5rem"),
		MARGIN_BOTTOM_3("mt-3", "margin-bottom: 1rem"),
		MARGIN_BOTTOM_4("mt-4", "margin-bottom: 1.5rem"),
		MARGIN_BOTTOM_5("mt-5", "margin-bottom: 3rem"),
		MARGIN_BOTTOM_A("mt-a", "margin-bottom: auto"),
		
		MARGIN_X_0("mx-0", "margin-left: 0; margin-right: 0"),
		MARGIN_X_1("mx-1", "margin-left: .25rem; margin-right: .25rem"),
		MARGIN_X_2("mx-2", "margin-left: .5rem; margin-right: .5rem"),
		MARGIN_X_3("mx-3", "margin-left: 1rem; margin-right: 1rem"),
		MARGIN_X_4("mx-4", "margin-left: 1.5rem; margin-right: 1.5rem"),
		MARGIN_X_5("mx-5", "margin-left: 3rem; margin-right: 3rem"),
		MARGIN_X_A("mx-a", "margin-left: auto; margin-right: auto"),
		
		MARGIN_Y_0("my-0", "margin-top: 0; margin-bottom: 0"),
		MARGIN_Y_1("my-1", "margin-top: .25rem; margin-bottom: .25rem"),
		MARGIN_Y_2("my-2", "margin-top: .5rem; margin-bottom: .5rem"),
		MARGIN_Y_3("my-3", "margin-top: 1rem; margin-bottom: 1rem"),
		MARGIN_Y_4("my-4", "margin-top: 1.5rem; margin-bottom: 1.5rem"),
		MARGIN_Y_5("my-5", "margin-top: 3rem; margin-bottom: 3rem"),
		MARGIN_Y_A("my-a", "margin-top: auto; margin-bottom: auto"),
		
		
		
		
		TEXT_COLOR_RED("c-r", "color: red"),
		
		FS_1("fs-1", "font-size: 1em;"),
		FS_2("fs-2", "font-size: 2rem;"),
		FS_3("fs-3", "font-size: 3rem;"),
		FS_4("fs-4", "font-size: 4rem;"),
		FS_5("fs-5", "font-size: 5rem;"),
		FS_6("fs-6", "font-size: 6rem;"),
		FS_7("fs-7", "font-size: 7rem;"),
		FS_8("fs-8", "font-size: 8rem;"),
		FS_9("fs-9", "font-size: 9rem;"),
		
		HIDDEN_BEHIND("hi-be", "position: absolute; top: 0; right: 0; margin: 0; padding: 0; cursor: pointer; opacity: 0; width: 100%; height: 100%;"),
		
		TEXT_CENTER("txt-center", "text-align: center;"),
		
		FLOAT_RIGHT("fl-right", "float: right;"),
		
		CLEAR_BOTH("clb", "clear:both"),
		
		HIDDEN("hidden", "display:none"),
		
		WELL("well-block",  "min-height: 20px;padding: 20px;margin-bottom: 20px;background-color: #f5f5f5;border: 1px solid #e3e3e3;border-radius: 4px;"),
		
		;
		
		//.
		
		protected final String cssClass;
		protected final String rules;
		protected final StyleAlaCarteMenu decorator;
		
	
		
		private StyleAlaCarte(String cls, String rule)
		{
			this.cssClass = cls;
			this.rules = rule;
			this.decorator = new StyleAlaCarteMenu(cls);
		}

		@Override
		public String getCssClassName()
		{
			return cssClass;
		}
		
		protected static StyleContainer style = new StyleContainer();
		
		static
		{
			VanillaTools.addToHeader((HTMLElement) style.getHtml());
			style.refresh();
			
			CssStyleSheet s = style.getStyleSheet();
			
			for(StyleAlaCarte sac:StyleAlaCarte.values())
			{
				s.insertRule("."+sac.cssClass+"{"+sac.rules+"}", s.getLength());
			}
		}

		@Override
		public StyleDecorator getDecorator()
		{
			return decorator;
		}
	}
}
