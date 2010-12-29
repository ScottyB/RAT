package swin.rat;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class EventsActivity extends Activity
{
	 Boolean toggle = false;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
       
        
        final Button bttn = (Button) findViewById(R.id.button1);
        final Button bttn2 = (Button) findViewById(R.id.button2);
        bttn2.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View arg0) {
				
			// How to call and load another activity
				Intent myIntent = new Intent();
				myIntent.setClassName("swin.rat", "swin.rat.KeyEvent");
				//myIntent.setClassName("swin.rat","swin.rat.TimerEvent");
				startActivity(myIntent);
			}
		});
        
        
        bttn.setOnClickListener(new OnClickListener()
        {

			@Override
			public void onClick(View arg0) 
			{
				View temp = findViewById(R.id.layout);
				if( toggle == false)
				{
					temp.setBackgroundColor(Color.WHITE);
					findViewById(R.id.button2).setVisibility(View.VISIBLE);
					toggle = true;
				}
				else
				{
					temp.setBackgroundColor(Color.BLACK);
					toggle = false;
				}
			}
		});
    
    }
    
    
}
