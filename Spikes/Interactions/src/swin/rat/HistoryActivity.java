package swin.rat;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

public class HistoryActivity extends Activity implements OnClickListener, OnItemClickListener
{
	static final private int HOME =1;	// Menu
	static final private String NAME = "people";	// Name of list
	private ListView mList;
	
	private TextView lName;
	private ArrayList<HashMap<String,String>> mHeader;
	private ArrayList<ArrayList<HashMap<String,Object>>> mChild;
	
	@Override
	public void onCreate(Bundle b)
	{
		super.onCreate(b);
		setContentView(R.layout.history);
		
		Utils.receiveClosingBroadcast(this);
		
		
		Button lNew = (Button) findViewById(R.id.newBttn);
		
		lName = (TextView) findViewById(R.id.name);
		String name = this.getIntent().getExtras().getString("name");
		lName.setText(name);
		lNew.setOnClickListener(this);
		
		mList = (ListView) findViewById(R.id.list);
		
		mHeader = new ArrayList<HashMap<String,String>>();
		mChild = new ArrayList<ArrayList<HashMap<String,Object>>>();

		 mList.setOnItemClickListener( this );
		
		
		
		
		String [] temp ={"16-Aug-34, 1 Exercise","21-Aug-34, 4 Exercises", "03-Sep-34, 3 Exercises",
				"01-Jan-99, 1 Exercise","03-Feb-99, 4 Exercises", "09-Mar-99, 5 Exercises"};
		
		mList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, temp));
//		
	}

	
	@Override
	public void onClick(View v) 
	{
		// Lets the user start a new session or continue from last session
		AlertDialog.Builder temp = new AlertDialog.Builder(this);
		temp.setMessage("Load data from last session? Press No for a new session.")
			.setCancelable(false)
			.setNeutralButton("Cancel", null)
			.setPositiveButton("Yes", new DialogInterface.OnClickListener()
			{
				@Override
				 public void onClick(DialogInterface dialog, int id) 
		           {
					Intent myIntent = new Intent(HistoryActivity.this, SelectionActivity.class);
					Bundle b = new Bundle();
					ArrayList<String> temp = new ArrayList<String>();
					temp.add("one");
					temp.add("two");
					temp.add("one");
					ArrayList<String> temp2 = new ArrayList<String>();
					temp2.add("Ankle");
					temp2.add("Upper Leg");
					b.putBoolean("gallery", true);
					b.putStringArrayList("bodyPoints", temp2);
					b.putBoolean("state", SelectionActivity.STATE_SELECTION);
					b.putStringArrayList("activities", temp);
					myIntent.putExtras(b);
					startActivity(myIntent);      		  
		        	  
			       }
			})
			.setNegativeButton("No",  new DialogInterface.OnClickListener()
			{
				@Override
				 public void onClick(DialogInterface dialog, int id) 
		         {
					Intent myIntent = new Intent(HistoryActivity.this, SessionActivity.class);
					Bundle b = new Bundle();
					ArrayList<String> temp = new ArrayList<String>();
					temp.add("Upper Arm");
					temp.add("Upper Leg");
					temp.add("Stomach");
					b.putStringArrayList("points", temp);
					myIntent.putExtras(b);
					startActivity(myIntent);   		  
		        	  
			    }
			});
		AlertDialog alert = temp.create();
		alert.show();
	}
	
	@Override
	public boolean onCreateOptionsMenu( Menu menu)
	{
		menu.add(1,HOME,0,"Home").setIcon(R.drawable.ic_menu_home);
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
			default:
				return false;
		}
		
		return true;
    }
	
	
	
	
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) 
	{
		Bundle b = new Bundle();
		b.putString("number", ((TextView)v).getText().toString());
		
		Intent myIntent = new Intent(HistoryActivity.this, FeedbackActivity.class);
		myIntent.putExtras(b);
		startActivity(myIntent);
		
	}
}
