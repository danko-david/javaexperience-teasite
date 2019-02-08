package eu.jvx.js.lib.activity;

import eu.javaexperience.teasite.frontend.tools.TeasiteFrontendTools;

public abstract class AbstractDefaultActivity extends AbstractActivity
{
	public AbstractDefaultActivity()
	{
		super(TeasiteFrontendTools.getInfrastructurePageStater());
	}
}
