package swin.rat;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class OutputActivity extends Activity implements OnClickListener
{
	// Menu values
	static final private int HOME = 1;
	static final private int DELETE = 2;
	static final private int EDIT = 3;
	
	private Context mContext;
	private ListView mList;
	
	private ArrayList<Integer> images;
	private String [] names;
	
	@Override
	public void onCreate(Bundle b)
	{
		super.onCreate(b);
		setContentView(R.layout.output);
		 
		mContext = this.getBaseContext();
		Utils.receiveClosingBroadcast(this);
		Button bttn = (Button) findViewById(R.id.newBttn);
		mList = (ListView) findViewById(R.id.list);
		
		bttn.setOnClickListener(this);
		populateList();
	}
	
	private void populateList()
	{
		Bundle b = getIntent().getExtras();
		
		//ArrayList<Integer> temps = new ArrayList<Integer>();
		images = b.getIntegerArrayList("activities");
		
		
		
		
		mList.setAdapter(new MyAdapter(this));
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
	
	 private class MyAdapter extends BaseAdapter
	    {
	    	private LayoutInflater mInflater;
	    	
	    	public MyAdapter(Context context) 
	    	{
	    		mInflater = LayoutInflater.from(context);
	    		 
	    	}
			@Override
			public int getCount() 
			{
				return images.size();
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
					convertView = mInflater.inflate(R.layout.image_text_list_item, null);
					holder.text = (TextView)convertView.findViewById(R.id.text);
					holder.image = (ImageView)convertView.findViewById(R.id.image);
					convertView.setTag(holder);
				}
				else
				{
					holder = (ViewHolder) convertView.getTag();
				}
				
				holder.text.setText("This is activity number "+position+" more soon :).");
				holder.image.setImageResource(images.get(position));
				
				return convertView;
			}
			
			class ViewHolder 
			{
				TextView text;
				ImageView image;
			}
	    }
}
