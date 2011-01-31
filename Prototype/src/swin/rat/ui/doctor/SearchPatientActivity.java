package swin.rat.ui.doctor;

import java.util.ArrayList;

import swin.rat.model.Patient;
import swin.rat.ui.RatApplication;
import swin.rat.util.Utils;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SearchPatientActivity extends Activity implements OnClickListener
{
	private AutoCompleteTextView mName;
	private ArrayList<String> mStoredNames;
	private Bundle mPass;
	
	static final private int HOME = 1;
	static final private int NEW = 2;
	
	private RatApplication globals;
	
	@Override
	public void onCreate(Bundle b)
	{
		super.onCreate(b);
		setContentView(R.layout.doc_search_patient);
		Utils.receiveClosingBroadcast(this);
		mPass = new Bundle();
		
		globals = (RatApplication)(getApplicationContext());
		
		Button bttn = (Button) findViewById(R.id.newBttn);
		bttn.setOnClickListener(this);
		
		AutoCompleteTextView  email = (AutoCompleteTextView)findViewById(R.id.email);

		ArrayList<String> storeEmails = new ArrayList<String>();
		storeEmails.add("peter@faraway.com");
		storeEmails.add("peter@closeby.com");
		
        mName = (AutoCompleteTextView) findViewById(R.id.auto);
        mStoredNames = new ArrayList<String>();
        mStoredNames.add("Scott1");
        mStoredNames.add("Scott2");
        mStoredNames.add("Scott123");
        mStoredNames.add("Scott3");
        mStoredNames.add("Scott456");
        mStoredNames.add("Scott45");
        mStoredNames.add("Scott3456");
        mStoredNames.add("Scott3462");
        mStoredNames.add("Scott13");
        mStoredNames.add("Scott4526");
        mStoredNames.add("Scott145");
        mStoredNames.add("Scott43456");
        mStoredNames.add("Scott38462");
        mStoredNames.add("Scott0913");
       
        
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.doc_autocomplete_list_item, mStoredNames);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, R.layout.doc_autocomplete_list_item, storeEmails);      
        mName.setAdapter(adapter);
        email.setAdapter(adapter1);
   	}
	
	@Override
	public void onClick(View v) 
	{
		if( !mStoredNames.contains(mName.getText().toString()) )
		{
			Toast.makeText(this, "Name not found!", Toast.LENGTH_SHORT).show();
		}
		else
		{
			globals.patient = new Patient(((TextView)v).getText().toString(),null,null);
			Log.w("tag","here");
			Intent myIntent = new Intent(SearchPatientActivity.this, ConsultationHistoryActivity.class);
			mPass.putString("name", mName.getText().toString());
			myIntent.putExtras(mPass);
			startActivity(myIntent);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu( Menu menu)
	{
		menu.add(1,HOME,0,"Home").setIcon(R.drawable.ic_menu_home);
		menu.add(1,NEW,1,"New").setIcon(android.R.drawable.ic_menu_add);
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
			case NEW:
				myIntent.setClassName("swin.rat","swin.rat.NewActivity");
				startActivity(myIntent);
				break;
			default:
				return false;
		}
		return true;
    }
	
	
}
