package swin.rat;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class FeedbackActivity extends Activity
{
	static final private int HOME = 1;
	static final private int SESSION = 2;
	static final private int SELECTION = 3;
	
	@Override
	public void onCreate(Bundle b)
	{
		super.onCreate(b);
		setContentView(R.layout.feedback);
		Utils.receiveClosingBroadcast(this);
		Button bttn = (Button) findViewById(R.id.newBttn);
		
		Bundle incoming = new Bundle();
		incoming = getIntent().getExtras();
		String numb = incoming.getString("number");
		
		// 10 as date format is dd-Jan-yy,
		String noDate = numb.substring(10);
		if( noDate.contains("Exercises"))
			noDate = noDate.replace("Exercises", "");
		if( noDate.contains("Exercise"))
			noDate = noDate.replace("Exercise", "");
		noDate = noDate.replace("0", "");
		noDate = noDate.trim();
		
		int number = Integer.parseInt(noDate);
		Log.i("tag", noDate);
		
		TextView txt = (TextView) findViewById(R.id.title);
		txt.setText(getIntent().getExtras().getString("number"));
		
		
		
		bttn.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0) 
			{
				Intent myIntent1 = new Intent(FeedbackActivity.this, SelectionActivity.class);
				
				AcessObject temp = ((AcessObject)getApplicationContext());
				
				Bundle b = new Bundle();
				b.putBoolean("state", SelectionActivity.STATE_SELECTION);
				ArrayList<String> body = new ArrayList<String>();
				body.add("Upper Leg");
				body.add("Stomach");
				b.putStringArrayList("bodyPoints",body);
				myIntent1.putExtras(b);			
				
				startActivity(myIntent1);
				
			}
			
		});
		
		ListView list = (ListView) findViewById(R.id.list);
		ArrayList<String> values = new ArrayList<String>();
		
		for(int i=1; i<number+1; i++)
		{
			values.add("Exercise "+ i + " details");
		}
		
		String [] temp = new String[values.size()];
		list.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,values.toArray(temp)));
		list.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) 
			{
				Intent myIntent1 = new Intent(FeedbackActivity.this, DisplayExerciseActivity.class);
				
				AcessObject temp = ((AcessObject)getApplicationContext());
				temp.setExercise(temp.getExercises().get(1));
				startActivity(myIntent1);
				
			}
			
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu( Menu menu)
	{
		menu.add(1,HOME,0,"Home").setIcon(R.drawable.ic_menu_home);
		menu.add(1,SESSION,1,"Session").setIcon(R.drawable.ic_menu_compose);
		menu.add(1,SELECTION,2,"Selection").setIcon(R.drawable.ic_menu_account_list);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
    {
		Intent myIntent = new Intent();
		switch(item.getItemId())
		{
			case HOME: 
				Utils.returnHomeNoMessage(this);
				break;
			case SESSION:
				myIntent.setClassName("swin.rat","swin.rat.SessionActivity");
				startActivity(myIntent);
				break;
			case SELECTION:
				myIntent = new Intent(FeedbackActivity.this, SelectionActivity.class);
				Bundle b = new Bundle();
				ArrayList<String> temp = new ArrayList<String>();
				temp.add("one");
				b.putBoolean("state", SelectionActivity.STATE_DISPLAY);
				b.putStringArrayList("activities", temp);
				myIntent.putExtras(b);
				startActivity(myIntent);
				break;
			default:
				return false;
		}
		
		return true;
    }
}
