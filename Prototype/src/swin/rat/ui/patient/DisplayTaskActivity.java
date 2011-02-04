package swin.rat.ui.patient;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import swin.rat.model.Feedback;
import swin.rat.model.PrescribedTask;
import swin.rat.model.TaskTemplate;
import swin.rat.ui.RatApplication;
import swin.rat.ui.doctor.R;
import swin.rat.util.PatLib;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayTaskActivity extends Activity implements OnClickListener
{
	private PrescribedTask task;
	private AnimationDrawable anima;
	private ImageView img;
	private BitmapDrawable still;
	private RatApplication globals;
	private TextView txt; 
	private TextView des;
	private TextView data;
	private Button forward;
	private Button back;
	private EditText repsText;
	private EditText freqText;
	private ArrayList<TaskTemplate> selected;
	
	
	
	private int reference; // Use to track Task in the global arrayList 
	
	@Override
	public void onCreate(Bundle b)
	{
		super.onCreate(b);
		setContentView(R.layout.pat_display_task);
		PatLib.receiveClosingBroadcast(this);
		txt = (TextView) findViewById(R.id.name);
		des = (TextView) findViewById(R.id.des);
		data = (TextView) findViewById(R.id.data);
		repsText = (EditText) findViewById(R.id.reps);
		freqText = (EditText) findViewById(R.id.freq);
		forward = (Button) findViewById(R.id.next);
		back = (Button) findViewById(R.id.back);
		
		img = (ImageView) findViewById(R.id.img);
		still = new BitmapDrawable();
		task = new PrescribedTask();
		globals = ((RatApplication)getApplicationContext());
		task = globals.prescribedTask;
		selected = new ArrayList<TaskTemplate>();
		selected = (ArrayList<TaskTemplate>) globals.taskFactory.tasks;
		
		
	   // des.setMovementMethod(new ScrollingMovementMethod());
		loadTask();
		
		
		Button saveBttn = (Button) findViewById(R.id.review);
		Bundle bundle = getIntent().getExtras();
		
		reference = bundle.getInt("ref");
	
		saveBttn.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0) 
			{
				Integer reps = Integer.parseInt(repsText.getText().toString());
				Integer freq = Integer.parseInt(freqText.getText().toString());
				boolean isFun;
				RadioButton funBttn = (RadioButton) findViewById(R.id.fun);
				isFun = funBttn.isChecked(); 
				Log.i("tag","IS FUN: " + isFun);
				Feedback feedback = new Feedback(isFun, reps, freq);
				task.feedback = feedback;
				
				int location = globals.patient.newestConsultation().getTaskLocation(task);
				globals.patient.newestConsultation().tasks.set(location, task); 
				
				
				Intent resultIntent = new Intent();
				resultIntent.putExtra("isSaved",true);
				resultIntent.putExtra("ref", reference);
				setResult(Activity.RESULT_OK, resultIntent);
				finish();
				
				
				
				
			}
			
		});
		
		img.setOnClickListener( new OnClickListener()
		{
			@Override
			public void onClick(View arg0) 
			{
				anima.setOneShot(false);
				if(anima.isRunning() )
				{
					anima.stop();
					img.setBackgroundDrawable(still);
				}
				else
				{
					img.setBackgroundDrawable(anima);
					anima.start();
				}
			}
		});
		
		
		forward.setOnClickListener(this);
		back.setOnClickListener(this);
	
	}
	
	
	
	
		
	private void loadTask()
	{
		txt.setText(task.longName);
		des.setText(task.text);
		repsText.setText(task.repetition.toString());
		freqText.setText(task.freq.toString());
		anima = new AnimationDrawable();
		
		try {
			Log.e("tag",""+task.getFrames().get(0));
			still = new BitmapDrawable( MediaStore.Images.Media.getBitmap(getContentResolver(), task.getFrames().get(0)));
		} 
		catch (FileNotFoundException e1) 
		{
			Log.e("tag","File Not Found");
		} 
		catch (IOException e1) 
		{
			Log.e("tag", "IO Exception");
		}
		
		for( int i=0; i<task.getFrames().size(); i++)
		{
			Bitmap temp1;
			try {
				temp1 = MediaStore.Images.Media.getBitmap(getContentResolver(), task.getFrames().get(i));
				BitmapDrawable drw = new BitmapDrawable(temp1);
				img.setBackgroundDrawable(drw);
				anima.addFrame(drw, task.frameTimes.get(i)*1000);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		
		img.setBackgroundDrawable(still);
	}
	
	@Override
	public void onClick(View view) 
	{
		ArrayList<PrescribedTask> temp = new ArrayList<PrescribedTask>();
		temp = (ArrayList<PrescribedTask>) globals.patient.newestConsultation().tasks;
		if( view.getId() == forward.getId() )
		{
			if( reference+1 < temp.size() )
			{
				++reference;
				task = temp.get(reference);
				loadTask();
			}
		}
		if( view.getId() == back.getId() )
		{
			if( reference-1 >= 0 )
			{
				--reference;
				task = temp.get(reference);
				loadTask();
				
			}
		}
		
		
	}

}
