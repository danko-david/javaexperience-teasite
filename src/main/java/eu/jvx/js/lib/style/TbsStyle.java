package eu.jvx.js.lib.style;

import eu.javaexperience.arrays.ArrayTools;

public enum TbsStyle implements StyleDecoratorSource
{
	//btn alert
	BTN_DEFAULT("btn-default", "btn"),
	BTN_PRIMARY("btn-primary", "btn"),
	BTN_SUCCESS("btn-success", "btn"),
	BTN_INFO("btn-info", "btn"),
	BTN_WARNING("btn-warning", "btn"),
	BTN_DANGER("btn-danger", "btn"),
	
	ALERT_SUCCESS("alert-success", "alert"),
	ALERT_INFO("alert-info", "alert"),
	ALERT_WARNING("alert-warning", "alert"),
	ALERT_DANGER("alert-danger", "alert"),
	
	LABEL_SUCCESS("label-success", "label"),
	LABEL_INFO("label-info", "label"),
	LABEL_WARNING("label-warning", "label"),
	LABEL_DANGER("label-danger", "label"),
	
	FORM_GROUP("form-group"),
	FORM_CONTROL("form-control"),
	
	
	INPUT_GROUP("input-group"),
	INPUT_GROUP_ADDON("input-group-addon"),
	BTN_GROUP("btn-group"),
	WELL("well"),
	
	
	
	;
	
	protected String mainClass;
	protected StyleAlaCarteMenu decorator;
	
	
	private TbsStyle(String cls, String... base)
	{
		this.mainClass = cls;
		decorator = new StyleAlaCarteMenu(ArrayTools.arrayAppend(cls, base));
	}
	
	@Override
	public StyleDecorator getDecorator()
	{
		return decorator;
	}
}
