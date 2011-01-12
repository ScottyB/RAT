package swin.rat;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class CustomWidgetActivity extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        final FormEditText test = (FormEditText) findViewById(R.id.test);
        Button bttn = (Button) findViewById(R.id.change);
        bttn.setOnClickListener(new OnClickListener()
        {

			@Override
			public void onClick(View arg0) 
			{
				
				test.toggleState();
				
			}
        	
        });
    }
}
