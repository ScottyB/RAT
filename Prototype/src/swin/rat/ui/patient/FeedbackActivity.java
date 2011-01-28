package swin.rat.ui.patient;

import swin.rat.ui.RatApplication;
import swin.rat.ui.doctor.R;
import swin.rat.util.PatLib;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class FeedbackActivity extends Activity
{
	private Context context;
	private TextView reps;
	
	
	@Override
	public void onCreate(Bundle b)
	{
		super.onCreate(b);
		setContentView(R.layout.pat_feedback);
		
		context = this;
		PatLib.receiveClosingBroadcast(context);
		Button bttn = (Button) findViewById(R.id.newBttn);
		reps = (TextView) findViewById(R.id.reps);
		
		bttn.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0) 
			{
				PatLib.returnHome(context);
				
			}
			
		});
		
		TextView txt = (TextView) findViewById(R.id.txt);
		RatApplication rat = (RatApplication) getApplicationContext();
		txt.setText("Feedback: " + rat.task.shortName);
	}
	
	@Override
	public void onBackPressed()
	{	
		if( reps.getText().length() > 0)
		{
			AlertDialog.Builder build = new AlertDialog.Builder(context);
			build.setMessage("Save Data?")
				 .setCancelable(false)
				 .setPositiveButton("Yes", new DialogInterface.OnClickListener() 
				 {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						finish();
						
					}
				})
				 .setNegativeButton("No",null);
			AlertDialog dialog = build.create();
			dialog.show();
		}
		else
		{
			super.onBackPressed();
		}
		
		//
	}
}
