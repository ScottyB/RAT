package swin.rat.ui.patient;


import swin.rat.ui.doctor.R;
import swin.rat.util.PatLib;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


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
