package eu.jvx.js.lib.utils;

import java.util.ArrayList;
import java.util.List;

import org.teavm.jso.browser.TimerHandler;
import org.teavm.jso.browser.Window;

import eu.javaexperience.interfaces.simple.publish.SimplePublish1;
import eu.javaexperience.interfaces.simple.publish.SimplePublish2;
import eu.javaexperience.log.JavaExperienceLoggingFacility;
import eu.javaexperience.log.LogLevel;
import eu.javaexperience.log.Loggable;
import eu.javaexperience.log.Logger;
import eu.javaexperience.log.LoggingTools;
import eu.jvx.js.lib.ProgressObservable;
import eu.jvx.js.lib.ProgressObserver;

public class BatchTask<R,P> implements ProgressObservable
{
	public static final Logger LOG = JavaExperienceLoggingFacility.getLogger(new Loggable("BatchTask"));
	
	public SimplePublish1<BatchTask<R,P>> onAllReady;
	public List<Task<R, P>> tasks;
	
	protected SimplePublish1<Task<R, P>> onSingleTaskDone;
	
	protected ProgressObserver observer;
	public String name;
	
	public BatchTask(SimplePublish1<BatchTask<R, P>> onReady)
	{
		this.onAllReady = onReady;
		this.tasks = new ArrayList<>();
		this.onSingleTaskDone = new SimplePublish1<Task<R,P>>()
		{
			@Override
			public void publish(Task<R, P> p1)
			{
				notifyTaskDone(p1);
			}
		};
	}
	
	public Task<R, P> getUnfinisedTask()
	{
		for(int i=0;i<tasks.size();++i)
		{
			Task<R, P> t = tasks.get(i);
			if(!t.isDone())
			{
				return t;
			}
		}
		
		return null;
	}
	
	/**
	 * after task executed, it must call {@link #notifyTaskDone(Callback0)};
	 * */
	public void addTask(SimplePublish2<SimplePublish1<R>, P> cb, P param)
	{
		if(null != cb)
		{
			int index = tasks.size();
			Task<R, P> t = new Task<>(index, param, cb);
			tasks.add(t);
		}
	}
	
	protected Boolean parallelMode;
	
	public void notifyTaskDone(Task<R, P> task)
	{
		//check all done
		if(null == parallelMode)
		{
			LoggingTools.tryLogFormat(LOG, LogLevel.ERROR, "Not yet started (with doParallel or doSerial) but task notified that task is ready");
			return;
		}
		
		if(null != observer)
		{
			observer.progress(this, "", task.index, tasks.size());
		}
		
		executeNext();
	}
	
	protected void executeNext()
	{
		Task<R,P> undone = getUnfinisedTask();
		
		if(!parallelMode)
		{
			if(null != undone)
			{
				undone.execute(this.onSingleTaskDone);
			}
		}
		
		if(null == undone)
		{
			if(null != observer)
			{
				observer.end(this);
			}
			this.onAllReady.publish(this);
		}
	}
	
	protected void startTaskAsync(final Task<R, P> t)
	{
		Window.setTimeout(new TimerHandler()
		{
			@Override
			public void onTimer()
			{
				t.execute(onSingleTaskDone);
			}
		}, 0);
	}
	
	protected void executeAll()
	{
		for(int i=0;i<tasks.size();++i)
		{
			startTaskAsync(tasks.get(i));
		}
	}

	//functions used to start batch callback execution
	public void doParallel(ProgressObserver observer)
	{
		if(tasks.isEmpty())
		{
			onAllReady.publish(this);
			return;
		}
		
		this.observer = observer;
		parallelMode = true;
		
		if(null != observer)
		{
			observer.start(this);
		}
		
		for(Task<R, P> t:tasks)
		{
			startTaskAsync(t);
		}
	}
	
	public void doSerial(ProgressObserver observer)
	{
		this.observer = observer;
		parallelMode = false;
		if(null != observer)
		{
			observer.start(this);
		}
		executeNext();
	}
	
	public List<R> getResults()
	{
		List<R> ret = new ArrayList<>();
		for(int i=0;i<tasks.size();++i)
		{
			ret.add(tasks.get(i).result);
		}
		return ret;
	}

	@Override
	public String getName()
	{
		return name;
	}
}
