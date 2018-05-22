import java.util.Timer;
import java.util.TimerTask;

public class Clock {

	private Timer time;
	private int hr;
	private int min;
	private int sec;
	private Timer timer;
	
	public Clock(){
		resetClock();
		timer = new Timer();
	}
	
	public void setTime(int hours, int minutes, int seconds){
		if(0 <= hours && hours < 24)
			hr = hours;
		else
			hr = 0;
		
		if(0 <= minutes && minutes < 60)
			min = minutes;
		else
			min = 0;
		
		if (0 <= seconds && seconds < 60)
		       sec = seconds;
		  else
		       sec = 0;
	}
	
	public int getHours(){
		return hr;
	}
	
	public int getMinutes(){
		return min;
	}
	
	public int getSeconds(){
		return sec;
	}
	
	public String getTime(){
		String time = "";
		
		if(hr < 10)
			time = time.concat("0");
		
		time = time.concat(hr + ":");
		
		if(min < 10)
			time = time.concat("0");
		
		time = time.concat(min + ":");
		
		if(sec < 10)
			time = time.concat("0");
		
		time = time.concat(sec + "");
		
		return time;
		
	}
	
	public void incrementSeconds(){
		sec++;
		if(sec > 59){
			sec = 0;
			incrementMinutes();
		}
	}
	
	public void incrementMinutes(){
		min++;
		if(min > 59)
		{
			min = 0;
			incrementHours();
		}
	}
	
	public void incrementHours(){
		hr++;
		if(hr > 23)
			hr = 9;
	}
	
	public void startClock(){
		timer.schedule(new MyTimerTask(this), 0, 1000);
	}
	public void resetClock(){
		setTime(0,0,0);
	}
}
