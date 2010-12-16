package swin.rat;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class KeyEvent extends Activity implements TextWatcher
{
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout2);
        
        final EditText temp = (EditText) findViewById(R.id.edittext);
        
        // Setup a key input event listener 
        // NOTE: Only works for touch screen based keyboards
        temp.addTextChangedListener(this);
        
        Button bttn = (Button) findViewById(R.id.destroy);
        bttn.setOnClickListener( new OnClickListener()
        {
			@Override
			public void onClick(View arg0) {
				finish();	// destroys the current activity i.e returns to the previous activity
				
		}});
    }

	@Override
	public void afterTextChanged(Editable arg0)
	{
		((TextView) findViewById(R.id.num)).setText(new Integer(arg0.length()).toString());	
		findViewById(R.id.destroy).setVisibility(View.VISIBLE);
	}

	// Unused methods that are required to use the TextWatcher interface
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,int arg3) {}
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}
		
    
    
}

