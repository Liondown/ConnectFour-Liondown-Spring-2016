import java.util.TimerTask;

public class MyTimerTask extends TimerTask {
	private Clock clock;
	public MyTimerTask(Clock c){
		clock = c;
	}
	public void run(){
		clock.incrementSeconds();
	}

}
