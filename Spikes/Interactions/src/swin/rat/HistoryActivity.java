package swin.rat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemLongClickListener;

public class HistoryActivity extends Activity implements OnClickListener, OnItemLongClickListener
{
	static final private int HOME =1;	// Menu
	static final private String NAME = "people";	// Name of list
	private ExpandableListView mList;
	
	private ArrayList<HashMap<String,String>> mHeader;
	private ArrayList<ArrayList<HashMap<String,Object>>> mChild;
	
	@Override
	public void onCreate(Bundle b)
	{
		super.onCreate(b);
		setContentView(R.layout.history);
		
		Utils.receiveClosingBroadcast(this);
		
		
		Button lNew = (Button) findViewById(R.id.newBttn);
		Button lDisplay = (Button ) findViewById(R.id.displayBttn);
		TextView lName = (TextView) findViewById(R.id.name);
		String name = this.getIntent().getExtras().getString("name");
		lName.setText(name);
		lNew.setOnClickListener(this);
		
		mList = (ExpandableListView) findViewById(R.id.list);
		
		mHeader = new ArrayList<HashMap<String,String>>();
		mChild = new ArrayList<ArrayList<HashMap<String,Object>>>();

		 mList.setOnItemLongClickListener( this );
		
		
		populateHeaders();
		populateNotes();
		
		lDisplay.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v) 
			{
				Intent myIntent = new Intent();
				myIntent.setClassName("swin.rat", "swin.rat.FeedbackActivity");
				startActivity(myIntent);
				
			}
			
		});
		final LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mList.setAdapter( new SimpleExpandableListAdapter(
	            this,
	            mHeader,
	            android.R.layout.simple_expandable_list_item_1,
	            new String[] { NAME },            // the name of the field data
	            new int[] { android.R.id.text1 }, // the text field to populate with the field data
	            mChild,
	            0,
	            null,
	            new int[] {}
	        	) {
	            @Override
	            public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) 
	            {
                	final View v = super.getChildView(groupPosition, childPosition, isLastChild, convertView, parent);
                	String text = (String) ((Map<String,Object>)getChild(groupPosition, childPosition)).get(NAME);
                	((TextView)v).setText(text);
                	return v;
	           }

	            @Override
	            public View newChildView(boolean isLastChild, ViewGroup parent) 
	            {
	            	
	            	return layoutInflater.inflate(android.R.layout.simple_expandable_list_item_1, null, false);
	            }
	        }
	    );
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
					b.putString("0", "Ankle");
					b.putString("1", "Neck");
					myIntent.putExtras(b);
					startActivity(myIntent);      		  
		        	  
			       }
			})
			.setNegativeButton("No",  new DialogInterface.OnClickListener()
			{
				@Override
				 public void onClick(DialogInterface dialog, int id) 
		         {
					Intent myIntent = new Intent();
					myIntent.setClassName("swin.rat", "swin.rat.SessionActivity");
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
	
	private void populateHeaders()
	{
		HashMap<String,String> meeting1 = new HashMap<String,String>();
		meeting1.put(NAME, "14-Aug-34, 3 Exercises");
		
		HashMap<String,String> meeting2 = new HashMap<String,String>();
		meeting2.put(NAME, "16-Aug-34, 1 Exercise");
		
		HashMap<String,String> meeting3 = new HashMap<String,String>();
		meeting3.put(NAME, "21-Aug-34, 4 Exercises");
		
		HashMap<String,String> meeting4 = new HashMap<String,String>();
		meeting4.put(NAME, "3-Sep-34, 3 Exercises");
		
		
		
		mHeader.add(meeting1);
		mHeader.add(meeting2);
		mHeader.add(meeting3);
		mHeader.add(meeting4);
	
	}
	
	private void populateNotes()
	{
		ArrayList<HashMap<String, Object>> group1data = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map = new HashMap<String,Object>();
		map.put(NAME, "Very sore after leg raises");
		group1data.add(map);
		
		ArrayList<HashMap<String, Object>> group2data = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map2 = new HashMap<String,Object>();
		map2.put(NAME, "Has made great improvement");
		group2data.add(map2);
		
		ArrayList<HashMap<String, Object>> group3data = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map3 = new HashMap<String,Object>();
		map3.put(NAME, "");
		group3data.add(map3);
		
		ArrayList<HashMap<String, Object>> group4data = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map4 = new HashMap<String,Object>();
		map4.put(NAME, "Will try knee exercises to fix hips. If that doesn't work might have to amputate at the neck.");
		group4data.add(map4);
		
		
		mChild.add(group1data);
		mChild.add(group2data);
		mChild.add(group3data);
		mChild.add(group4data);
		
	   
	    
	    
	}
	
	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View v,	int position, long arg3) 
	{
		Bundle b = new Bundle();
		b.putString("name", ((TextView)v).getText().toString());
		
		Intent myIntent = new Intent(HistoryActivity.this, FeedbackActivity.class);
		myIntent.putExtras(b);
		startActivity(myIntent);
		return false;
	}
}
