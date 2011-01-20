package swin.rat;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
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

public class DisplayExerciseActivity extends Activity implements OnClickListener
{
	private Exercise exercise;
	private AnimationDrawable anima;
	private ImageView img;
	private BitmapDrawable still;
	private AcessObject globals;
	private TextView txt; 
	private TextView des;
	private TextView data;
	private Button forward;
	private Button back;
	
	private int reference; // Use to track exercise in the global arrayList 
	
	@Override
	public void onCreate(Bundle b)
	{
		super.onCreate(b);
		setContentView(R.layout.display);
		
		txt = (TextView) findViewById(R.id.name);
		des = (TextView) findViewById(R.id.des);
		data = (TextView) findViewById(R.id.data);
		Button add = (Button) findViewById(R.id.add);
		forward = (Button) findViewById(R.id.next);
		back = (Button) findViewById(R.id.back);
		
		img = (ImageView) findViewById(R.id.img);
		still = new BitmapDrawable();
		exercise = new Exercise();
		globals = ((AcessObject)getApplicationContext());
		exercise = globals.getExercise();
		
		loadExercise();
		
		Bundle bundle = getIntent().getExtras();
		reference = bundle.getInt("ref");
		
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
		
		add.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				globals.setToAdd(true);
				finish();
			}
		});
		
		forward.setOnClickListener(this);
		back.setOnClickListener(this);
	
	}
	
	private void loadExercise()
	{
		String dataString = "";
		if( exercise.getRepetition() != null)
			dataString += "Repetitions: " + exercise.getRepetition() +'\n';
		if( exercise.getHoldTime() != null)
			dataString +=  "Hold Time: " + exercise.getHoldTime() + '\n';
		
		data.setText(dataString);
		
		txt.setText(exercise.getLongName());
		des.setText(exercise.getText());
		
		anima = new AnimationDrawable();
		
		try {
			still = new BitmapDrawable( MediaStore.Images.Media.getBitmap(getContentResolver(), exercise.getAnimation().get(0)));
		} catch (FileNotFoundException e1) {
		} catch (IOException e1) {
		}
		
		int w = still.getIntrinsicWidth();
		int h = still.getIntrinsicHeight();
		img.setMinimumWidth( w * 2 );
		img.setMinimumHeight( h * 2);
		
		for( int i=0; i<exercise.getAnimation().size(); i++)
		{
			
			Bitmap temp1;
			try {
				temp1 = MediaStore.Images.Media.getBitmap(getContentResolver(), exercise.getAnimation().get(i));
				BitmapDrawable drw = new BitmapDrawable(temp1);
				img.setBackgroundDrawable(drw);
				anima.addFrame(drw, exercise.getFrameTime(i)*1000);
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
		ArrayList<Exercise> temp = new ArrayList<Exercise>();
		temp = globals.getBodyExercises();
		if( view.getId() == forward.getId() )
		{
			if( reference+1 < temp.size() )
			{
				++reference;
				exercise = temp.get(reference);
				loadExercise();
				
		
			}
			else
			{
				
				
				
			}
		}
		if( view.getId() == back.getId() )
		{
			if( reference-1 >= 0 )
			{
				--reference;
				exercise = temp.get(reference);
				loadExercise();
				if( reference == temp.size()-1)
				{
					
					
				}
			}
			else
			{
				
				
				
			}
		}
		
	}

}
