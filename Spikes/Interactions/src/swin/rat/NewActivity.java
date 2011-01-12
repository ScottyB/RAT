package swin.rat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewActivity extends Activity implements OnClickListener
{
	private FormEditText mName;
	private FormEditText mEmail;
	private Pattern pattern;
	private Matcher matcher;
	private static final String EMAIL_PATTERN ="^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	@Override
	public void onCreate( Bundle b)
	{
		super.onCreate(b);
		setContentView(R.layout.newlayout);
		
		Utils.receiveClosingBroadcast(this);
		
		Button bttn = (Button) findViewById(R.id.newBttn);
		mName = (FormEditText) findViewById(R.id.name);
		mEmail = (FormEditText) findViewById(R.id.email);
		bttn.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View arg0) 
	{
		// TODO Auto-generated method stub
		if(mEmail.getText().length() == 0)
		{
			Toast.makeText(this, "Please enter an Email", Toast.LENGTH_SHORT).show();
			
		}	
		else
		{
			if( !validateEmail(mEmail.getText().toString()))
			{
				mEmail.setState(FormEditText.INVALID);
			}
			else
			{
				mEmail.setState(FormEditText.VALID);
				Intent myIntent = new Intent();
				myIntent.setClassName("swin.rat", "swin.rat.SessionActivity");
				startActivity(myIntent);
			}
			
		}
	}
	
	@Override
	public void onBackPressed()
	{
		Utils.returnHome(this);
	}
	
	// To check if any data will be lost if user exists screen
	private boolean isGotData()
	{
		return false;
	}
	
	public boolean validateEmail(final String text)
    {
    	pattern = Pattern.compile(EMAIL_PATTERN);
    	matcher = pattern.matcher(text);
    	return matcher.matches();
    }
}
