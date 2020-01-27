package eu.jvx.js.lib.layout;

import eu.jvx.js.lib.bindings.H;

public class HWrapper
{
	protected H root;
	protected H content;
	public HWrapper(H root, H content)
	{
		this.root = root;
		this.content = content;
	}
	
	public H getRoot()
	{
		return root;
	}

	public H getContent()
	{
		return content;
	}
}