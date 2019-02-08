package eu.jvx.js.lib.utils;

import eu.javaexperience.interfaces.simple.publish.SimplePublish1;
import eu.javaexperience.interfaces.simple.publish.SimplePublish2;

public class Task<R, P>
{
	public int index;
	public P param;
	public R result;
	public boolean done = false;
	protected SimplePublish2<SimplePublish1<R>, P> calculator;
	
	public Task(int index, P param, SimplePublish2<SimplePublish1<R>, P> calculator)
	{
		this.index = index;
		this.param = param;
		this.calculator = calculator;
	}

	public boolean isDone()
	{
		return done;
	}
	
	public void execute(final SimplePublish1<Task<R, P>> onDone)
	{
		calculator.publish
		(
			new SimplePublish1<R>()
			{
				@Override
				public void publish(R ret)
				{
					result = ret;
					done = true;
					onDone.publish(Task.this);
				}
			},
			param
		);
	}
}
