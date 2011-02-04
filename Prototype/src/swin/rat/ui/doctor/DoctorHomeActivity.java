package swin.rat.ui.doctor;

import swin.rat.ui.RatApplication;
import swin.rat.util.Utils;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class DoctorHomeActivity extends Activity 
{
	private Context context;
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doc_home);
        context = this;
        Utils.receiveClosingBroadcast(this);
        
        Button newBttn = (Button) findViewById(R.id.newBttn);
        Button existingBttn = (Button) findViewById(R.id.existingBttn);
        
        Button patient = (Button) findViewById(R.id.patient);
        patient.setOnClickListener( new OnClickListener()
        {

			@Override
			public void onClick(View arg0) 
			{
				RatApplication globals = (RatApplication) getApplicationContext();
				if( globals.patient == null )
				{
					Toast.makeText(context, "No patient in system", Toast.LENGTH_SHORT).show();
				}
				else
				{
					Intent myIntent = new Intent();
					myIntent.setClassName("swin.rat.ui.doctor", "swin.rat.ui.patient.PatientHomeActivity");
					startActivity(myIntent);
				}
			}
        	
        });
        
        
        
        // Loads New Patient Activity
        newBttn.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View view)
			{
				Intent myIntent = new Intent();
				myIntent.setClassName("swin.rat.ui.doctor", "swin.rat.ui.doctor.NewPatientActivity");
				startActivity(myIntent);
			}
        });
        
        // Search for an existing patient
        existingBttn.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View arg0) 
			{
				RatApplication globals = (RatApplication) getApplicationContext();
				if(globals.allPatients.size() != 0 )
				{
					Intent myIntent = new Intent();
					myIntent.setClassName("swin.rat.ui.doctor","swin.rat.ui.doctor.SearchPatientActivity");
					startActivity(myIntent);
				}
				else
				{
					Toast.makeText(context, "No patients in system", Toast.LENGTH_SHORT).show();
				}
			}
        });
    }
}