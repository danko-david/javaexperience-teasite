package eu.jvx.js.tbs;

public class TbsWeight
{
	public static final TbsWeight Default = new TbsWeight("default");
	public static final TbsWeight Primary = new TbsWeight("primary");
	public static final TbsWeight Success = new TbsWeight("success");
	public static final TbsWeight Info = new TbsWeight("info");
	public static final TbsWeight Warning = new TbsWeight("warning");
	public static final TbsWeight Danger = new TbsWeight("danger");
	
	protected String cssName;
	
	public TbsWeight(String name)
	{
		this.cssName = name;
	}
	
	public String getCssName()
	{
		return this.cssName;
	}
}
