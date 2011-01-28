package swin.rat.ui.patient;


import java.util.ArrayList;
import java.util.Collections;

import swin.rat.model.Task;
import swin.rat.ui.RatApplication;
import swin.rat.ui.doctor.R;
import swin.rat.util.PatLib;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class TaskListActivity extends ListActivity
{
	private static ArrayList<Task> list;
	@Override
	public void onCreate( Bundle b )
	{
		super.onCreate(b);
		list = new ArrayList<Task>();
		list = ((RatApplication)getApplicationContext()).allTasks;
		
		setListAdapter(new MyAdapter(this));
		PatLib.receiveClosingBroadcast(this);
		ListView lv = getListView();
		
		lv.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) 
			{
				RatApplication global = (RatApplication)getApplicationContext();
				global.task = global.allTasks.get(position);
				Intent myIntent = new Intent(TaskListActivity.this, FeedbackActivity.class);
				startActivity(myIntent);
				
			}
			
		});
		
		lv.setOnItemLongClickListener( new OnItemLongClickListener()
		{

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) 
			{
				RatApplication global = (RatApplication)getApplicationContext();
				global.task = global.allTasks.get(position);
				Bundle b = new Bundle();
				b.putInt("ref", position);
				Intent myIntent = new Intent(TaskListActivity.this, DisplayTaskActivity.class);
				myIntent.putExtras(b);
				startActivity(myIntent);
				return false;
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
				convertView.setTag(holder);
			}
			else
			{
				holder = (ViewHolder) convertView.getTag();
			}
			
			holder.textTop.setText(list.get(position).longName);
			holder.textBot.setText(list.get(position).text);
			holder.image.setImageURI(list.get(position).getIcon());			
			return convertView;
		}
		
		static class ViewHolder 
		{
			TextView textTop;
			TextView textBot;
			ImageView image;
		}
    }
}
