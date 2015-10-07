package Presentacion;

import javax.ejb.Remote;


@Remote
public interface ITimerBean {
 
	public void scheduleTimer(long milliseconds) ;
	public int getCurrentTime();
}