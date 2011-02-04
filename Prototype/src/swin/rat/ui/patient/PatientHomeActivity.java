package swin.rat.ui.patient;


import swin.rat.ui.RatApplication;
import swin.rat.ui.doctor.R;
import swin.rat.util.PatLib;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


public class PatientHomeActivity extends Activity
{
	private Context context;
	
	@Override
	public void onCreate( Bundle b )
	{
		super.onCreate(b);
		setContentView(R.layout.pat_home);
		context = this;
		PatLib.receiveClosingBroadcast(this);
		Button bttn = (Button)findViewById(R.id.list);
		TextView numOfTasks = (TextView) findViewById(R.id.txt);
		RatApplication globals = (RatApplication) getApplicationContext();
		
		globals.patient = globals.allPatients.get(globals.allPatients.size()-1);
		
		int temp = globals.patient.newestConsultation().tasks.size();
		Log.w("tag", "TEMP"+ temp);
		String temp1 = "Number Of Activities: " +temp;
		numOfTasks.setText(temp1);
		bttn.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0) 
			{
				Intent myIntent = new Intent(PatientHomeActivity.this, TaskListActivity.class);
				//myIntent.setClassName("swin.rat.ui.patient", "swin.rat.ui.patient.TaskListActivity");
				startActivity(myIntent);
				
			}
			
			
		});
	}
}
