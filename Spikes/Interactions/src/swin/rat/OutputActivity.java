package swin.rat;

import java.util.ArrayList;



import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.GridView;
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
	
	private ArrayList<Exercise> selected;
	
	@Override
	public void onCreate(Bundle b)
	{
		super.onCreate(b);
		setContentView(R.layout.output);
		 
		mContext = this.getBaseContext();
		Utils.receiveClosingBroadcast(this);
		Button bttn = (Button) findViewById(R.id.newBttn);
		
		selected = new ArrayList<Exercise>();
		bttn.setOnClickListener(this);
		
		selected = ((AcessObject)getApplicationContext()).getSelectedExercises();
		
		GridView gridview = (GridView) findViewById(R.id.grid);
        gridview.setAdapter(new ImageAdapter(this));
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
	
	public class ImageAdapter extends BaseAdapter
    {
    	
    	private Context mContext;
    	    	
    	public ImageAdapter(Context c)
    	{
    		mContext = c;
    		
    	}

		@Override
		public int getCount() 
		{
			return selected.size();
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
			ImageView i = new ImageView(mContext);
		    
		    
	    	 if (convertView == null) 
	    	 {  // if it's not recycled, initialize some attributes
	             i = new ImageView(mContext);
	             i.setLayoutParams(new GridView.LayoutParams(100, 100));
	             i.setScaleType(ImageView.ScaleType.CENTER_CROP);
	             i.setPadding(5, 5, 5, 5);
	         } 
	    	 else 
	         {
	             i = (ImageView) convertView;
	         }
	         i.setImageURI(selected.get(position).getIcon());
		    return i;
		}
    	
    }
}
