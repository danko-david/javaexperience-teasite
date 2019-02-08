package eu.jvx.js.lib;

public interface ProgressObserver
{
	public void start(ProgressObservable obsa);
	public void progress(ProgressObservable obsa, String currentStatusText, int current, int max);
	public void end(ProgressObservable obsa);
}
