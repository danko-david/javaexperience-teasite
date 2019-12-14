package eu.jvx.js.tbs.ui;

import java.util.Collection;
import java.util.Date;

import org.teavm.jso.JSObject;
import org.teavm.jso.browser.Window;
import org.teavm.jso.dom.events.Event;
import org.teavm.jso.dom.events.EventListener;
import org.teavm.jso.dom.html.HTMLElement;

import eu.javaexperience.datareprez.DataObject;
import eu.javaexperience.datareprez.DataReprezTools;
import eu.javaexperience.datareprez.convertFrom.PublicFieldObjectLike;
import eu.javaexperience.interfaces.simple.SimpleCall;
import eu.javaexperience.interfaces.simple.publish.SimplePublish1;
import eu.javaexperience.interfaces.simple.publish.SimplePublish2;
import eu.javaexperience.teavm.datareprez.DataObjectTeaVMImpl;
import eu.jvx.js.lib.NativeJsSupport;
import eu.jvx.js.lib.bindings.H;
import eu.jvx.js.lib.bindings.VanillaTools;
import eu.jvx.js.lib.bindings.VanillaTools.ClassList;
import eu.jvx.js.lib.style.TbsStyle;
import eu.jvx.js.lib.style.StyleTools.StyleAlaCarte;
import eu.jvx.js.lib.ui.component.func.HtmlDataContainerTools;
import eu.jvx.js.lib.ui.component.func.ViewOrEdit;
import static eu.jvx.js.lib.bindings.H.H;

public class TbsTools
{
	public static void modal(HTMLElement header, HTMLElement body, HTMLElement footer)
	{
		HTMLElement modal = H("div").attrs("class", "modal fade", "role", "dialog", "style", "display: none").addChilds
		(
			H("div").attrs("class", "modal-dialog").addChilds
			(
				H("div").attrs("class", "modal-content").addChilds
				(
					null == header?null:H("div").attrs("class", "modal-header").addChilds(header),
					null == body?null:H("div").attrs("class", "modal-body").addChilds(body),
					null == footer?null:H("div").attrs("class", "modal-footer").addChilds(footer)
				)
			)
		).getHtml();
		
		Window.current().getDocument().getBody().appendChild(modal);
		NativeJsSupport.getSupport().eval(modal, "$(param).modal({})");
	}
	
	public static HTMLElement createModalCloseButton(String text)
	{
		return VanillaTools.inlineCreateElement("button", "type", "button", "class", "btn btn-default", "data-dismiss", "modal", "#text", text);
	}

	public static void closeModal(HTMLElement html)
	{
		HTMLElement modal = VanillaTools.whereParent(html, ".modal");
		if(null != modal)
		{
			NativeJsSupport.getSupport().eval(modal, "$(param).modal('hide')");
		}
	}
	
	protected static final String CH_VIEW = "glyphicon-pencil";
	protected static final String CH_EDIT = "glyphicon-lock";
	
	public static ViewOrEdit<String> createTbsEditToggle
	(
		SimpleCall afterEditEnter,
		SimplePublish1<SimplePublish1<Boolean>> onEditCommit,
		SimpleCall onRollback
	)
	{
		HTMLElement button = H("span", "class", "btn btn-group btn-success glyphicon glyphicon-pencil view_or_edit_event enter_edit_state").getHtml();
		final ViewOrEdit<String> ret = new ViewOrEdit<String>
		(
			"",
			HtmlDataContainerTools.wrapUnfunctional
			(
				button//,
			),
			HtmlDataContainerTools.wrapUnfunctional
			(
				H("div").addChilds
				(
					H("span", "class", "btn btn-group btn-success glyphicon glyphicon-floppy-disk view_or_edit_event save_modifications").style(StyleAlaCarte.MARGIN_1).getHtml(),//,
					H("span", "class", "btn btn-group btn-success glyphicon glyphicon-floppy-remove view_or_edit_event discard_modifications").style(StyleAlaCarte.MARGIN_1).getHtml()//,
				).getHtml()
			)
		);
		
		HTMLElement container = ((HTMLElement) ret.getHtml()); 
		
		container.addEventListener("click", new EventListener<Event>()
		{
			@Override
			public void handleEvent(Event a)
			{
				ClassList cl = VanillaTools.getClassList(a.getTarget().cast());
				
				if(cl.contains("enter_edit_state"))
				{
					ret.enterMode(false, true);
					if(null != afterEditEnter)
					{
						afterEditEnter.call();
					}
				}
				else if(cl.contains("save_modifications"))
				{
					onEditCommit.publish(new SimplePublish1<Boolean>()
					{
						@Override
						public void publish(Boolean a)
						{
							ret.enterMode(true, Boolean.TRUE == a);
						}
					});
				}
				else if(cl.contains("discard_modifications"))
				{
					ret.enterMode(true, false);
					if(null != onRollback)
					{
						onRollback.call();
					}
				}
			}
		});
		
		StyleAlaCarte.MARGIN_2.getDecorator().setStyle((HTMLElement) ret.getHtml(), true);
		return ret;
	}
	
	public static <T> ViewOrEdit<String> createTbsEditToggle
	(
		final Collection<ViewOrEdit<T>> editors,
		final SimplePublish2<Collection<ViewOrEdit<T>>, SimplePublish1<Boolean>> onEditCommit,
		final SimpleCall onRollback
	)
	{
		return createTbsEditToggle
		(
			new SimpleCall()
			{
				@Override
				public void call()
				{
					for(ViewOrEdit<T> editor:editors)
					{
						editor.enterMode(false, true);
					}
				}
			},
			null == onEditCommit?null:new SimplePublish1<SimplePublish1<Boolean>>()
			{
				@Override
				public void publish(SimplePublish1<Boolean> a)
				{
					onEditCommit.publish(editors, a);
				}
			},
			new SimpleCall()
			{
				@Override
				public void call()
				{
					for(ViewOrEdit<T> editor:editors)
					{
						editor.enterMode(true, false);
					}
					if(null != onRollback)
					{
						onRollback.call();
					}
				}
			}
		);
	}
	
	/**
	 * https://eonasdan.github.io/bootstrap-datetimepicker/Options/
	 * */
	public static class DateTimePickerOptions extends PublicFieldObjectLike
	{
		
		/**
		 * See momentjs' docs for valid formats. Format also dictates what components are shown, e.g. MM/dd/YYYY will not display the time picker.
		 * YYYY-MM-DD HH:ss
		 * */
		public String format;
		
		/**
		 * Changes the heading of the datepicker when in "days" view.
		 * */
		public String dayViewHeaderFormat;
		
		/**
		 * Allows for several input formats to be valid. See this PR. (https://github.com/Eonasdan/bootstrap-datetimepicker/pull/666)
		 * */
		public String extraFormats;
		
		/**
		 * Number of minutes the up/down arrow's will move the minutes value in the time picker
		 * */
		public Integer stepping;
		
		/**
		 * Prevents date/time selections before this date.
		 * minDate will override defaultDate and useCurrent if either of these settings are the same day since both options are invalid according to the rules you've selected.
		 * */
		public Date minDate;
		
		/**
		 * Prevents date/time selections after this date.
		 * maxDate will override defaultDate and useCurrent if either of these settings are the same day since both options are invalid according to the rules you've selected.
		 * */
		public Date maxDate;
		
		/**
		 * On show, will set the picker to the current date/time
		 * */
		public Boolean useCurrent;
		
		/**
		 * Using a Bootstraps collapse to switch between date/time pickers.
		 * */
		public Boolean collapse;
		
		/**
		 * You must include moment-with-locales.js or a local js file.
		 * */
		public String locale;
		
		/**
		 * Sets the picker default date/time. Overrides useCurrent
		 * */
		public Date defaultDate;
		
		
	}
	
	public static void datetimepicker(HTMLElement htmlElement, DateTimePickerOptions opts)
	{
		JSObject o = null;
		if(null != opts)
		{
			DataObject obj = new DataObjectTeaVMImpl();
			DataReprezTools.copyInto(obj, opts);
			o = (JSObject) obj.getImpl();
		}
		NativeJsSupport.getSupport().eval((JSObject) (Object) new JSObject[]{htmlElement, o}, "$(param[0]).datetimepicker(param[1])");
	}

	public static void modalMessage(String title, String message)
	{
		modalMessage(title, message, null);
	}
	
	public static void modalMessage(String title, String message, SimpleCall ok)
	{
		H content = H("div"); 
		content.addChilds(new H("div").attrs("#text", message));
		
		H header = H("h4");
		
		header.attrs("#text", title);
		
		H footer = H("div");
		
		HTMLElement okBtn = createModalCloseButton("Ok");
		if(null != ok)
		{
			new H(okBtn).onClick((e)->ok.call());
		}
		footer.addChilds(okBtn);
		
		TbsTools.modal
		(
			header.getHtml(),
			content.getHtml(),
			footer.getHtml()
		);
	}
	
	protected static H buttonify(H elem)
	{
		ClassList cl = VanillaTools.getClassList(elem.getHtml());
		if(!cl.contains("btn"))
		{
			elem.style(TbsStyle.BTN_DEFAULT);
		}
		
		return elem;
	}

	public static void confirmModal
	(
		Object title,
		Object body,
		Object cancelButton,
		SimpleCall onCancel,
		Object acceptButton,
		SimpleCall onAccept
	)
	{
		modal
		(
			VanillaTools.toHtmlElement(title),
			VanillaTools.toHtmlElement(body),
			new H("div").addChilds
			(
				buttonify(new H(VanillaTools.toHtmlElement(cancelButton)).attrs("data-dismiss", "modal")),
				buttonify(new H(VanillaTools.toHtmlElement(acceptButton)).onClick((e)->{onAccept.call();closeModal((HTMLElement) e.getTarget());}))
			).getHtml()
		);
	}
}
