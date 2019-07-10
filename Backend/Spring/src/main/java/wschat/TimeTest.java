package wschat;

import java.sql.Date;
import java.util.Timer; 
import java.util.TimerTask;

//this has a fixed rate of schedule method od the timmer class

class Helper extends TimerTask
{
	public static int i = 0;
	public void run() {
		System.out.println("Timer is running " + i++);
		
		if(i == 5) {
			synchronized(TimeTest.scheduling) {
				TimeTest.scheduling.notify();
			}
		}
	}
}
public class TimeTest {
	
	protected static TimeTest scheduling;
	
	public static void main(String[] args) throws InterruptedException
	{ 
	      scheduling = new TimeTest();
	    Timer timer = new Timer(); 
	    TimerTask tasking = new Helper(); 
	    
	   Date d = new Date(0);
	    
	    //timer.scheduleAtFixedRate(tasking, d, 6000);
	  //  Date date = new Date(0, 0, 0); 
	    
        timer.scheduleAtFixedRate(tasking, d, 6000); 
        timer.schedule(tasking, 2000, 6000); 
        
        System.out.println("The timer ran");
        sychronized(scheduling);{
        	scheduling.wait();
        	timer.cancel();
        	System.out.println(timer.purge());
        }
	      
	   
	      
	}

	private static void sychronized(TimeTest scheduling2) {
		// TODO Auto-generated method stub
		System.out.print("This is running the schronized");
	}

	//private static void sychronized(TimeTest scheduling2) {
		
		
	//} 
}
