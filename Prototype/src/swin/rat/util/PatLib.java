package swin.rat.util;

import swin.rat.ui.patient.PatientHomeActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.Toast;

/**
 * Class might be better suited in a library or template
 * @author scott
 *
 */
public class PatLib 
{	
	static public void sendClosingBroadcast( Context context)
	{
		Intent broadcastIntent = new Intent();
		broadcastIntent.setAction("swin.rat.patient.CLOSE");
		context.sendBroadcast(broadcastIntent);
	}
	
	static public void receiveClosingBroadcast( Context context)
	{
		IntentFilter intentFilter = new IntentFilter();
	    intentFilter.addAction("swin.rat.patient.CLOSE");
	    context.registerReceiver(new Reciever((Activity)(context)), intentFilter);
	}

	static private class Reciever extends BroadcastReceiver
	{
		private Activity mToClose;
		
		Reciever(Activity temp)
		{
			mToClose = temp;
		}
		
		@Override
		public void onReceive(Context context, Intent intent) 
		{
			mToClose.finish();
		}
		
	}
	
	static public void returnHome( final Context context)
	{
		AlertDialog.Builder build = new AlertDialog.Builder(context);
		build.setMessage("Save and finish?")
			 .setCancelable(true)
			 .setPositiveButton("Yes", new DialogInterface.OnClickListener() 
			 {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Intent myIntent = new Intent(context, PatientHomeActivity.class);
					sendClosingBroadcast(context);
					context.startActivity(myIntent); 
					
				}
			})
			 .setNegativeButton("No",null);
		AlertDialog dialog = build.create();
		dialog.show();
	}
	
		
	
	
}
