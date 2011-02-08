package swin.rat.ui.doctor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;

import swin.rat.model.BodyPoint;
import swin.rat.model.Consultation;
import swin.rat.ui.RatApplication;
import swin.rat.util.Utils;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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

public class ConsultationHistoryActivity extends Activity implements OnClickListener, OnItemClickListener
{
	static final private int HOME =1;	// Menu
	static final private String NAME = "people";	// Name of list
	private ListView mList;
	private RatApplication globals;
	private TextView lName;
	private ArrayList<HashMap<String,String>> mHeader;
	private ArrayList<ArrayList<HashMap<String,Object>>> mChild;
	
	@Override
	public void onCreate(Bundle b)
	{
		super.onCreate(b);
		setContentView(R.layout.doc_consultation_history);
		
		Utils.receiveClosingBroadcast(this);
		
		globals = (RatApplication) getApplicationContext();
		Button lNew = (Button) findViewById(R.id.newBttn);
	
		lName = (TextView) findViewById(R.id.name);
		String name = globals.patient.name;
		
		lName.setText(name);
		
		lNew.setOnClickListener(this);
		
		
		mList = (ListView) findViewById(R.id.list);
		
		mHeader = new ArrayList<HashMap<String,String>>();
		mChild = new ArrayList<ArrayList<HashMap<String,Object>>>();
	
		mList.setOnItemClickListener( this );
		
		ArrayList<String> temp = new ArrayList<String>();
		
		for(int i=0; i< globals.patient.consultations.size(); i++)
		{
			temp.add(globals.patient.consultations.get(i).toString());
		}
		Log.e("tag", "4Note for loop");
		

		
		mList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, temp));
		Log.e("tag","safe");
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
					Intent myIntent = new Intent(ConsultationHistoryActivity.this, SelectionActivity.class);
					
					
					RatApplication global = (RatApplication)getApplicationContext();
					
					Calendar now = Calendar.getInstance();
					
					Consultation con = new Consultation(now,globals.patient.newestConsultation().bodyPoints,"");
					global.patient.consultations.add( con );
												
					
					startActivity(myIntent);      		  
		        	  
			       }
			})
			.setNegativeButton("No",  new DialogInterface.OnClickListener()
			{
				@Override
				 public void onClick(DialogInterface dialog, int id) 
		         {
					Intent myIntent = new Intent(ConsultationHistoryActivity.this, BodyPointsActivity.class);
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
		b.putInt("pos", position);
		Intent myIntent = new Intent(ConsultationHistoryActivity.this, ConsultationFeedbackActivity.class);
		myIntent.putExtras(b);
		startActivity(myIntent);
		
	}
}
