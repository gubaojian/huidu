package net.java.efurture.huidu.timer;

import android.os.Handler;

/**
 * Handler Timer
 * */
public class HandlerTimer implements  Runnable{

	private Handler handler;
	private long interval;
	private TimerStatus status;
	private Runnable task;
	
	
	public HandlerTimer(){
	   this(null);
	}
     public HandlerTimer(long interval){
		this(interval, null);
	}
	
	public HandlerTimer(Runnable task){
		this(1000, task);
	}
	
	public HandlerTimer(long interval, Runnable task){
		this(interval, task, new Handler());
	}
	
	public HandlerTimer(long interval, Runnable task, Handler handler){
		this.handler = handler;
		this.interval = interval;
		this.status = TimerStatus.Waiting;
	}
	
	@Override
	public final  void run() {
		if (status == TimerStatus.Waiting 
			|| status == TimerStatus.Paused
			|| status == TimerStatus.Stopped) {
			return;
		}
		if (this.task != null) {
			this.task.run();
		}
		if (handler != null) {
			handler.postDelayed(this, interval);
		}
	}

	/**
	 * 启动定时器
	 * */
	public void start(){
		this.status = TimerStatus.Running;
		handler.postDelayed(this, interval);
	}
	
	/**
	 * 暂停定时器
	 * */
	public void pause(){
		this.status = TimerStatus.Paused;
		handler.removeCallbacks(this);
	}
	
	/**
	 * 启动定时器
	 * */
	public void restart(){
		this.status = TimerStatus.Running;
		handler.postDelayed(this, interval);
	}
	
	/**
	 * 停止定时器
	 * */
	public void stop(){
		status = TimerStatus.Stopped;
		handler.removeCallbacks(this);
	}
	
	/**
	 * 取消定时器
	 * */
	public void cancel(){
		status = TimerStatus.Stopped;
		handler.removeCallbacks(this);
	}

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		if (this.handler != null) {
			this.handler.removeCallbacks(this);
		}
		this.handler = handler;
	}

	public long getInterval() {
		return interval;
	}

	public void setInterval(long interval) {
		this.interval = interval;
	}

	public Runnable getTask() {
		return task;
	}

	public void setTask(Runnable task) {
		this.task = task;
	}
	
    /**
     * Timer的几种状态
     * */
	static enum TimerStatus{
		
		Waiting(0, "待启动"),
		Running(1, "运行中"),
		Paused(-1, "暂停"),
		Stopped(-2, "停止");
		
		private int code;
		private String desc;
		private TimerStatus(int code, String desc) {
			this.code = code;
			this.desc = desc;
		}
		
		public int getCode() {
			return code;
		}
		
		public void setCode(int code) {
			this.code = code;
		}
		
		public String getDesc() {
			return desc;
		}
		
		public void setDesc(String desc) {
			this.desc = desc;
		}
	}
}
