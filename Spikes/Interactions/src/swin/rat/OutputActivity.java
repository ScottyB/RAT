package swin.rat;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class OutputActivity extends Activity implements OnClickListener
{
	static final private int HOME =1;
	static final private int DELETE = 2;
	static final private int EDIT = 3;
	
	private Context mContext;
	
	@Override
	public void onCreate(Bundle b)
	{
		super.onCreate(b);
		setContentView(R.layout.output);
		 
		mContext = this.getBaseContext();
		Utils.receiveClosingBroadcast(this);
		Button bttn = (Button) findViewById(R.id.newBttn);
		bttn.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) 
	{
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setMessage("Are you sure that you want to send?")
			  .setCancelable(true)
			  .setPositiveButton("Yes", new Dialog.OnClickListener()
			  {
				@Override
				public void onClick(DialogInterface dialog, int which) 
				{
					Utils.sendClosingBroadcast(mContext);
					Intent myIntent = new Intent();
					myIntent.setClassName("swin.rat", "swin.rat.HomeActivity");
					startActivity(myIntent);
					
				}
			  })
			  .setNegativeButton("No", null);
		AlertDialog alert = dialog.create();
		alert.show();
	}
	
	@Override
	public boolean onCreateOptionsMenu( Menu menu)
	{
		menu.add(1,HOME,0,"Home").setIcon(R.drawable.ic_menu_home);
		menu.add(1,DELETE,1,"Delete").setIcon(android.R.drawable.ic_menu_delete);
		menu.add(1,EDIT,2,"Edit").setIcon(android.R.drawable.ic_menu_edit);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
    {
		
		switch(item.getItemId())
		{
			case HOME: 
				Utils.returnHome(this);
				break;
			case DELETE:
				
				break;
			case EDIT:
				
			default:
				return false;
		}
		
		return true;
    }
}
