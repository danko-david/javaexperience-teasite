package eu.jvx.js.lib.utils;

import java.util.ArrayList;
import java.util.List;

public class BatchTaskChainBuilder<R, P>
{
	protected List<BatchUnit<R, P>> units;
	
	public BatchTaskChainBuilder()
	{
		units = new ArrayList<>();
	}
	
	public <TmpRet> BatchTaskChainBuilder<R, TmpRet> add(BatchUnit<TmpRet, P> unit)
	{
		this.units.add((BatchUnit)unit);
		return (BatchTaskChainBuilder<R, TmpRet>)(BatchTaskChainBuilder) this;
	}
	
	public List<BatchUnit<R, P>> getUnits()
	{
		return units;
	}
}
