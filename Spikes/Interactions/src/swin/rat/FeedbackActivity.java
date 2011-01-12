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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

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
		
		
		
		TextView txt = (TextView) findViewById(R.id.title);
		txt.setText(getIntent().getExtras().getString("name"));
		
		
		
		bttn.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0) 
			{
				Intent myIntent1 = new Intent(FeedbackActivity.this, SelectionActivity.class);
				Bundle b = new Bundle();
				b.putString("0", "Ankle");
				b.putString("1", "Neck");
				myIntent1.putExtras(b);
				startActivity(myIntent1);
				
			}
			
		});
		
		ListView list = (ListView) findViewById(R.id.list);
		ArrayList<String> values = new ArrayList<String>();
		values.add("Exercise 1 details");
		values.add("Exercise 2 details");
		values.add("Exercise 3 details");
		values.add("Exercise 4 details");
		values.add("Exercise 5 details");
		values.add("Exercise 6 details");
		Log.i("tag","NO");
		String [] temp = new String[values.size()];
		list.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,values.toArray(temp)));
		
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
		
		switch(item.getItemId())
		{
			case HOME: 
				Utils.returnHomeNoMessage(this);
				break;
			case SESSION:
				Intent myIntent = new Intent();
				myIntent.setClassName("swin.rat","swin.rat.SessionActivity");
				startActivity(myIntent);
				break;
			case SELECTION:
				Intent myIntent1 = new Intent(FeedbackActivity.this, SelectionActivity.class);
				Bundle b = new Bundle();
				b.putString("0", "Ankle");
				b.putString("1", "Neck");
				myIntent1.putExtras(b);
				startActivity(myIntent1);
				break;
			default:
				return false;
		}
		
		return true;
    }
}
