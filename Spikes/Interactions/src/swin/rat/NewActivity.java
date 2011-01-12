package swin.rat;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewActivity extends Activity implements OnClickListener
{
	private EditText mName;
	
	@Override
	public void onCreate( Bundle b)
	{
		super.onCreate(b);
		setContentView(R.layout.newlayout);
		
		Utils.receiveClosingBroadcast(this);
		
		Button bttn = (Button) findViewById(R.id.newBttn);
		mName = (EditText) findViewById(R.id.name);
		
		bttn.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View arg0) 
	{
		// TODO Auto-generated method stub
		if(mName.getText().length() == 0)
		{
			Toast.makeText(this, "Please enter a Name", Toast.LENGTH_SHORT).show();
			
		}	
		else
		{
			Intent myIntent = new Intent();
			myIntent.setClassName("swin.rat", "swin.rat.SessionActivity");
			startActivity(myIntent);
			
		}
	}
	
	@Override
	public void onBackPressed()
	{
		Utils.returnHome(this);
	}
	
	// To check if any data will be lost if user exists screen
	private boolean isGotData()
	{
		return false;
	}
}
