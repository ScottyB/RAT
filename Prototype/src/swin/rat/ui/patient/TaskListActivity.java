package swin.rat.ui.patient;


import java.util.ArrayList;

import swin.rat.model.PrescribedTask;
import swin.rat.ui.RatApplication;
import swin.rat.ui.doctor.R;
import swin.rat.util.PatLib;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class TaskListActivity extends ListActivity
{
	private static ArrayList<PrescribedTask> list;
	public static final int IS_SAVED = 3;
	private MyAdapter dapt;
	private ListView lv;
	
	@Override
	public void onCreate( Bundle b )
	{
		super.onCreate(b);
		list = new ArrayList<PrescribedTask>();
		list = (ArrayList<PrescribedTask>) ((RatApplication)getApplicationContext()).patient.newestConsultation().tasks;
		
		dapt = new MyAdapter(this);
		setListAdapter(dapt);
		PatLib.receiveClosingBroadcast(this);
		lv = getListView();
	
		
		lv.setOnItemClickListener( new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) 
			{
				RatApplication global = (RatApplication)getApplicationContext();
				global.prescribedTask = global.patient.newestConsultation().tasks.get(position);
				Bundle b = new Bundle();
				b.putInt("ref", position);
				Intent myIntent = new Intent(TaskListActivity.this, DisplayTaskActivity.class);
				myIntent.putExtras(b);
				startActivityForResult(myIntent,IS_SAVED);
				
			}
			
		});
	}
	
	private static class MyAdapter extends BaseAdapter
    {
    	private LayoutInflater mInflater;
    	
    	public MyAdapter(Context context) 
    	{
    		mInflater = LayoutInflater.from(context);
    		 
    	}
    	
    	@Override
		public int getCount() 
		{
			return list.size();
		}
    	
    	@Override
		public Object getItem(int position) 
		{
			return position;
		}

		@Override
		public long getItemId(int position) 
		{
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) 
		{
			ViewHolder holder = new ViewHolder();
			if( convertView == null)
			{
				convertView = mInflater.inflate(R.layout.pat_list_item, null);
				holder.textTop = (TextView)convertView.findViewById(R.id.top);
				holder.textBot = (TextView)convertView.findViewById(R.id.bottom);
				holder.image = (ImageView)convertView.findViewById(R.id.img);
				holder.tick = (ImageView)convertView.findViewById(R.id.tick);
				convertView.setTag(holder);
			}
			else
			{
				holder = (ViewHolder) convertView.getTag();
			}
			
			holder.textTop.setText(list.get(position).longName);
			holder.textBot.setText(list.get(position).text);
			holder.image.setImageURI(list.get(position).getIcon());	
			
			if( list.get(position).isCompleted)
				holder.tick.setImageResource(R.drawable.btn_check_buttonless_on);
			else
				holder.tick.setImageResource(android.R.drawable.ic_delete);
			return convertView;
		}
		
		static class ViewHolder 
		{
			TextView textTop;
			TextView textBot;
			ImageView image;
			ImageView tick;
		}
    }
	
	@Override 
	public void onActivityResult(int requestCode, int resultCode, Intent data) {     
	  super.onActivityResult(requestCode, resultCode, data); 
	  switch(requestCode) 
	  { 
	    case (IS_SAVED) : 
	    { 
	      if (resultCode == Activity.RESULT_OK) 
	      { 
	    	  boolean tabIndex = data.getBooleanExtra("isSaved",false);
	    	  if( tabIndex )
	    	  {
	    		 list.get(data.getIntExtra("ref", -1)).isCompleted = true;
	    		 setListAdapter(dapt);
	    	  }
	    	  else
	    		  Toast.makeText(this, "Data not saved", Toast.LENGTH_SHORT).show();
	      } 
	      break; 
	    } 
	  } 
	}
	
	

}
