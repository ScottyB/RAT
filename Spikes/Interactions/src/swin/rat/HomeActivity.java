package swin.rat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class HomeActivity extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Utils.receiveClosingBroadcast(this);
        
        Button newBttn = (Button) findViewById(R.id.newBttn);
        Button existingBttn = (Button) findViewById(R.id.existingBttn);
        
        newBttn.setOnClickListener(new OnClickListener()
        {

			@Override
			public void onClick(View view)
			{
				// TODO Auto-generated method stub
				Intent myIntent = new Intent();
				myIntent.setClassName("swin.rat", "swin.rat.NewActivity");
				startActivity(myIntent);
			}
        	
        });
        
        existingBttn.setOnClickListener(new OnClickListener()
        {

			@Override
			public void onClick(View arg0) 
			{
				// TODO Auto-generated method stub
				Intent myIntent = new Intent();
				myIntent.setClassName("swin.rat","swin.rat.SearchActivity");
				startActivity(myIntent);
			}
        	
        });
    }
}
