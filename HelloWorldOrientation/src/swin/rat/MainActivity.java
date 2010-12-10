package swin.rat;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {
	private TextView myTextView;
	   /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        myTextView = (TextView) findViewById(R.id.helloworld);
        
        int width = getWindowManager().getDefaultDisplay().getWidth();
        int height = getWindowManager().getDefaultDisplay().getHeight();

        if ( width > height )
        	myTextView.setTextColor(getResources().getColor(R.color.Red));
        else        	
        	myTextView.setTextColor(getResources().getColor(R.color.Blue));
    }    
        	   
    @Override
    protected void onDestroy() {
    	super.onDestroy();
    }
}