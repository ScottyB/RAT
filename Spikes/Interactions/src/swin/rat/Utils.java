package swin.rat;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;


public class Utils 
{	
	static public void sendClosingBroadcast( Context context)
	{
		Intent broadcastIntent = new Intent();
		broadcastIntent.setAction("swin.rat.CLOSE");
		context.sendBroadcast(broadcastIntent);
	}
	
	static public void receiveClosingBroadcast( Context context)
	{
		IntentFilter intentFilter = new IntentFilter();
	    intentFilter.addAction("swin.rat.CLOSE");
	    
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
	
	static public void returnHome(Context context)
	{
		AlertDialog.Builder build = new AlertDialog.Builder(context);
		build.setMessage("Are you sure you want to return home? Changes will be lost.")
			 .setCancelable(true)
			 .setPositiveButton("Yes", new DialogPositiveClick(context))
			 .setNegativeButton("No",null);
		AlertDialog dialog = build.create();
		dialog.show();
	}
	
	static private class DialogPositiveClick implements Dialog.OnClickListener
	{
		private Context context;
		
		DialogPositiveClick(Context context)
		{
			this.context = context;
		}
		
		@Override
		public void onClick(DialogInterface arg0, int arg1) 
		{
			Intent myIntent = new Intent();
			sendClosingBroadcast(context);
			myIntent.setClassName("swin.rat", "swin.rat.HomeActivity");
			context.startActivity(myIntent); 
			
		}
		
	}
	
	static public void returnHomeNoMessage(Context context)
	{
		Intent myIntent = new Intent();
		sendClosingBroadcast(context);
		myIntent.setClassName("swin.rat", "swin.rat.HomeActivity");
		context.startActivity(myIntent); 
	}
}
