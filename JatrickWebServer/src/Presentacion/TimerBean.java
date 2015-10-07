package Presentacion;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.annotation.Resource;
import javax.ejb.*;
import javax.faces.bean.ManagedProperty;
import javax.naming.Context;
import javax.naming.InitialContext;


 
@Stateless
 
public class TimerBean implements ITimerBean
{
   private @Resource SessionContext ctx;
 

   
   private Timer time;
   
   @Resource
   TimerService service;
   
   
   public void scheduleTimer(long milliseconds)
   {
     time= ctx.getTimerService().createTimer(new Date(new Date().getTime() + milliseconds), "Hello World");
    

   }
   public int getCurrentTime(){
	   
	   Timer timer = null;
	Collection<Timer> timers = service.getTimers();
		Iterator<Timer> iterator = timers.iterator();
		while (iterator.hasNext()) {
			timer = iterator.next();
			return 	(int) timer.getTimeRemaining();
		}
		return 0;
   }

   
   @Timeout
   public void timeoutHandler(Timer timer)
   {
      System.out.println("---------------------");
      System.out.println("* Received Timer event: " + timer.getInfo());
      System.out.println("---------------------");
      
      timer.cancel();
   }
}
