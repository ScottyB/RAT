package swin.rat;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;	

public class LogCommandsActivity extends Activity
{
	public static final String TAG = "LogCommandsActivity";
   
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.main);
        Log.d(TAG,"I am onCreate");
    }
    
    @Override
    protected void onStart()
    {
    	super.onStart();
    	Log.d(TAG,"I am onStart");	// Debug message
    	
    }
    
    @Override
    protected void onResume()
    {
    	super.onResume();
    	Log.v(TAG, "I am onResume");	// API states verbose messages should never be compiled 
    									// into a completed program
    }
    
    @Override
    protected void onPause()
    {
    	super.onPause();
    	Log.e(TAG, "I am onPause");	// Error message
    }
    
    @Override
    protected void onStop()
    {
    	super.onStop();
    	Log.w(TAG, "I am onStop");	// Warning message
    }
    
    @Override
    protected void onRestart()
    {
    	super.onRestart();
    	Log.i(TAG, "I am onRestart");	// Info message
    }
    
    @Override
    protected void onDestroy()
    {
    	super.onDestroy();
    	Log.e(TAG, "I am onDestroy");	// Error message
    }
}
