package swin.rat.ui.doctor;

import java.util.ArrayList;

import swin.rat.model.PrescribedTask;
import swin.rat.model.Task;
import swin.rat.ui.RatApplication;
import swin.rat.util.Utils;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;

public class ReviewTasksActivity extends Activity implements OnClickListener
{
	// Menu values
	static final private int HOME = 1;
	static final private int DELETE = 2;
	static final private int EDIT = 3;
	
	private Context mContext;
	private ListView mList;
	private RatApplication globals;
	private ArrayList<Integer> images;
	private String [] names;
	
	private ArrayList<Task> selected;  // Copy of global selectTasks
	
	@Override
	public void onCreate(Bundle b)
	{
		super.onCreate(b);
		setContentView(R.layout.doc_review_tasks);
		 
		mContext = this;
		Utils.receiveClosingBroadcast(this);
		Button bttn = (Button) findViewById(R.id.newBttn);
		
		selected = new ArrayList<Task>();
		bttn.setOnClickListener(this);
		
		globals = ((RatApplication)getApplicationContext());
		selected = globals.selectedTasks;
		
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
					//Utils.sendClosingBroadcast(mContext);
					globals.clearPatient();
					Utils.returnHomeNoMessage(mContext);
					
					
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
				globals.clearPatient();
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
