package swin.rat.ui.doctor;

import swin.rat.util.Utils;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class DoctorHomeActivity extends Activity 
{
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doc_home);
        
        Utils.receiveClosingBroadcast(this);
        
        Button newBttn = (Button) findViewById(R.id.newBttn);
        Button existingBttn = (Button) findViewById(R.id.existingBttn);
        
        Button patient = (Button) findViewById(R.id.patient);
        patient.setOnClickListener( new OnClickListener()
        {

			@Override
			public void onClick(View arg0) 
			{
				Intent myIntent = new Intent();
				myIntent.setClassName("swin.rat.ui.doctor", "swin.rat.ui.patient.PatientHomeActivity");
				startActivity(myIntent);
				
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
				Intent myIntent = new Intent();
				myIntent.setClassName("swin.rat.ui.doctor","swin.rat.ui.doctor.SearchPatientActivity");
				startActivity(myIntent);
			}
        });
    }
}