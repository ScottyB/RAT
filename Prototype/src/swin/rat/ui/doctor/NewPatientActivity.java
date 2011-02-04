package swin.rat.ui.doctor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import swin.rat.model.Patient;
import swin.rat.ui.RatApplication;
import swin.rat.util.FormEditText;
import swin.rat.util.Utils;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class NewPatientActivity extends Activity implements OnClickListener
{
	private FormEditText mName;
	private FormEditText mEmail;
	private Pattern pattern;
	private Matcher matcher;
	private static final String EMAIL_PATTERN ="^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private RatApplication global;
	
	@Override
	public void onCreate( Bundle b)
	{
		super.onCreate(b);
		setContentView(R.layout.doc_new_patient);
		
		Utils.receiveClosingBroadcast(this);
		global = (RatApplication)getApplicationContext();
		Button bttn = (Button) findViewById(R.id.newBttn);
		mName = (FormEditText) findViewById(R.id.name);
		mEmail = (FormEditText) findViewById(R.id.email);
		bttn.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) 
	{
		if ( mName.getText().length() == 0 )
		{
			Toast.makeText(this, "Please enter a Name", Toast.LENGTH_SHORT).show();
		}
		else if(mEmail.getText().length() == 0)
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
				global.patient = new Patient(mName.getText().toString(),null, mEmail.getText().toString());
				mEmail.setState(FormEditText.VALID);
				Intent myIntent = new Intent();
				myIntent.setClassName("swin.rat.ui.doctor", "swin.rat.ui.doctor.BodyPointsActivity");
				startActivity(myIntent);
			}
		}
	}
	
	@Override
	public void onBackPressed()
	{
		if( !hasGotData() )
		{
			global.clearPatient();
			Utils.returnHome(this);
		}
		else
		{
			Utils.returnHomeNoMessage(this);
		}
	}
	
	// To check if any data will be lost if user exists screen
	private boolean hasGotData()
	{
		return mName.isEmpty();
	}
	
	public boolean validateEmail(final String text)
    {
    	pattern = Pattern.compile(EMAIL_PATTERN);
    	matcher = pattern.matcher(text);
    	return matcher.matches();
    }
}
