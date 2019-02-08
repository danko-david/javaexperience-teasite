package eu.jvx.js.lib.utils;

import java.util.ArrayList;
import java.util.List;

import eu.javaexperience.interfaces.simple.SimpleCall;
import eu.javaexperience.interfaces.simple.publish.SimplePublish1;
import eu.javaexperience.interfaces.simple.publish.SimplePublish2;
import eu.jvx.js.lib.ProgressObserver;

public class BatchUnit<R, P>
{
	public String unitName;
	protected BatchTask<R, P> exec;
	protected boolean parallel;
	
	protected SimplePublish2<SimplePublish1<R>, P> calculator;

	protected ProgressObserver observer;
	
	public BatchUnit(String name, boolean parallel, SimplePublish2<SimplePublish1<R>, P> calculator)
	{
		this.unitName = name;
		this.parallel = parallel;
		this.calculator = calculator;
	}
	
	public void execute(SimplePublish1<BatchTask<R, P>> onAllDone, List<P> params)
	{
		this.exec = new BatchTask<R, P>(onAllDone);
		this.exec.name = unitName;
		
		for(int i = 0;i<params.size();++i)
		{
			this.exec.addTask(this.calculator, params.get(i));
		}
		
		if(parallel)
		{
			this.exec.doParallel(observer);
		}
		else
		{
			this.exec.doSerial(observer);
		}
	}
	
	public static <R,P> SimpleCall chainUnits
	(
		final SimplePublish1<List<R>> ret,
		final List<BatchUnit<R,P>> units,
		final List<P> params,
		final ProgressObserver observer
	)
	{
		return new SimpleCall()
		{
			@Override
			public void call()
			{
				if(0 == units.size())
				{
					ret.publish((List<R>)params);
					return;
				}
				
				BatchUnit<R, P> unit = units.get(0);
				unit.setObserver(observer);
				unit.execute
				(
					new SimplePublish1<BatchTask<R, P>>()
					{
						@Override
						public void publish(BatchTask<R, P> p1)
						{
							//go deeper in execution or return
							if(2 > units.size())
							{
								//no more chain, returning
								ret.publish(p1.getResults());
							}
							else
							{
								//enqueue the next step
								SimpleCall next = chainUnits
								(
									ret,
									slice1(units),
									(List<P>) p1.getResults(),
									observer
								);
								next.call();
							}
						}
					},
					params
				);
			}
		};
	}
	
	protected static <R, P> List<BatchUnit<R,P>> slice1(List<BatchUnit<R,P>> lst)
	{
		List<BatchUnit<R,P>> ret = new ArrayList<>();
		
		for(int i=1;i<lst.size();++i)
		{
			ret.add(lst.get(i));
		}
		
		return ret;
	}
	
	protected void setObserver(ProgressObserver observer)
	{
		this.observer = observer;
	}
}
