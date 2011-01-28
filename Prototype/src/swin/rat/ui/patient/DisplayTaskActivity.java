package swin.rat.ui.patient;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import swin.rat.model.PrescribedTask;
import swin.rat.model.Task;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayTaskActivity extends Activity implements OnClickListener
{
	private Task task;
	private AnimationDrawable anima;
	private ImageView img;
	private BitmapDrawable still;
	private RatApplication globals;
	private TextView txt; 
	private TextView des;
	private TextView data;
	private Button forward;
	private Button back;
	private ArrayList<Task> selected;
	
	
	
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
		
		forward = (Button) findViewById(R.id.next);
		back = (Button) findViewById(R.id.back);
		
		img = (ImageView) findViewById(R.id.img);
		still = new BitmapDrawable();
		task = new Task();
		globals = ((RatApplication)getApplicationContext());
		task = globals.task;
		selected = new ArrayList<Task>();
		selected = globals.allTasks;
		
		
		loadTask();
		
		
		Button review = (Button) findViewById(R.id.review);
		Bundle bundle = getIntent().getExtras();
		if(bundle != null)
		{
			if(bundle.containsKey("ref"))
			{
				reference = bundle.getInt("ref");
			}
		}
		else
		{
			review.setVisibility(View.INVISIBLE);
			forward.setVisibility(View.INVISIBLE);
			back.setVisibility(View.INVISIBLE);
		}
		
		review.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0) 
			{
				globals.task = task;
				Intent myIntent = new Intent(DisplayTaskActivity.this, FeedbackActivity.class);
				startActivity(myIntent);
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
		String dataString = "";
		if( task.repetition != null)
			dataString += "Repetitions: " + task.repetition +'\n';
		if( task.holdTime != null)
			dataString +=  "Hold Time: " + task.holdTime + '\n';
		
		data.setText(dataString);
		
		txt.setText(task.longName);
		des.setText(task.text);
		
		anima = new AnimationDrawable();
		Log.e("tag", "IdddddO Exception");
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
		
		int w = still.getIntrinsicWidth();
		int h = still.getIntrinsicHeight();
		img.setMinimumWidth( w * 2 );
		img.setMinimumHeight( h * 2);
		Log.e("tag","Safe ");
		for( int i=0; i<task.getFrames().size(); i++)
		{
			Bitmap temp1;
			try {
				temp1 = MediaStore.Images.Media.getBitmap(getContentResolver(), task.getFrames().get(i));
				Log.i("tag", "hello1");
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
		ArrayList<Task> temp = new ArrayList<Task>();
		temp = globals.allTasks;
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
