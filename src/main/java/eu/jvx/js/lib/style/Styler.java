package eu.jvx.js.lib.style;

import eu.javaexperience.arrays.ArrayTools;

public class Styler implements StyleDecoratorSource
{
	protected String cssClass;
	protected String rules;
	
	protected String[] requiredClasses;
	
	protected String[] otherRules;
	
	protected StyleAlaCarteMenu decorator;
	
	public Styler(String cssClass, String rules, String... req)
	{
		this.cssClass = cssClass;
		this.rules = rules;
		this.requiredClasses = req;
		decorator = new StyleAlaCarteMenu(ArrayTools.arrayAppend(cssClass, req));
	}
	
	public Styler addOtherRules(String... rules)
	{
		this.otherRules = rules;
		return this;
	}

	@Override
	public StyleDecorator getDecorator()
	{
		return decorator;
	}

	public String[] getOtherRules()
	{
		return otherRules;
	}
}
