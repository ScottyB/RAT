package swin.rat;

import android.app.Activity;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;

public class TimerEvent extends Activity implements OnTouchListener
{
	private TextView timerField; 
	private MyCount countDown;
	private long currentValue;
	private View lay;
	
	@Override
	public void onCreate( Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout3);
		timerField = (TextView) findViewById(R.id.timer); 
		lay = findViewById(R.id.layout);
		lay.setOnTouchListener(this);
			
		if( savedInstanceState != null)
		{
			currentValue = savedInstanceState.getLong("countValue");
			countDown = new MyCount(currentValue-1000,1000);
			countDown.start();
			savedInstanceState = null;
		}
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState)
	{
		outState.putLong("countValue", currentValue);
		super.onSaveInstanceState(outState);
	}

	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) 
	{
		lay.setOnTouchListener(null);
		countDown = new MyCount(10000,1000);
		countDown.start();
		return false;
	}
	
	public class MyCount extends CountDownTimer
	{
		public MyCount(long millisInFuture, long countDownInterval) 
		{
			super(millisInFuture, countDownInterval);
		}
		
		@Override
		public void onFinish() 
		{
			lay.setOnTouchListener(TimerEvent.this);
			timerField.setText("10");
		}

		@Override
		public void onTick(long millisUntilFinished) 
		{
			currentValue = millisUntilFinished;
			timerField.setText(""+millisUntilFinished / 1000);
		}
		
	}
		
	
	// Plays the default notification 
	public void soundPlay()
	{
		 Ringtone ring;
		 ring = RingtoneManager.getRingtone(this, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
		 ring.play();
	}
}
