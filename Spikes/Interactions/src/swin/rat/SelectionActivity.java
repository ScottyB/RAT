package swin.rat;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

public class SelectionActivity extends Activity implements OnItemClickListener, OnClickListener
{
	static final private int HOME = 1;
	static final private int DELETE = 2;
	static final public boolean STATE_SELECTION = true;
	static final public boolean STATE_DISPLAY = false;
	private boolean mState;
	
	private Gallery gallery;
	private ArrayList<Integer> exercises;
	private DisplayAdapter dap;
	private String mBack = "This is where the corresponding back exercise description will go";
	private String mNeck = "This is where the description for the neck exercise will go";
	private ArrayList<String> parts;
	private String [] parts2;
	private TextView name;
	private TextView des;
	
	private ListView lst;
	private Button deleteBttn;
	private ImageView img1;
	private ImageView img2;
	private ImageView img3;
	
	private Button bttn;
	
	@Override
	public void onCreate(Bundle bundle)
	{
		super.onCreate(bundle);
		setContentView(R.layout.selection);
		
		Utils.receiveClosingBroadcast(this);
		
		// Link views with code
		bttn = (Button) findViewById(R.id.next);
		gallery = (Gallery) findViewById(R.id.gallery);
		name = (TextView)findViewById(R.id.title);
		des = (TextView) findViewById(R.id.description);
		img1 = (ImageView) findViewById(R.id.view);
		img2 = (ImageView) findViewById(R.id.view1);
		img3 = (ImageView) findViewById(R.id.view2);
		deleteBttn = (Button) findViewById(R.id.delete);
		
		lst = (ListView) findViewById(R.id.list);
				
		// Intialisations
		parts = new ArrayList<String>(0); // For the list!!!!
		exercises = new ArrayList<Integer>();
		dap = new DisplayAdapter(this);
		
		parts2 = new String[parts.size()];
				
		gallery.setOnItemSelectedListener(new OnItemSelectedListener()
		{
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) 
			{
				if(exercises.get(gallery.getSelectedItemPosition()) == R.drawable.back1 )
				{
					name.setText("Back");
					des.setText(mBack);
					img1.setBackgroundResource(R.drawable.back2);
					img3.setBackgroundResource(R.drawable.back2);
					img2.setBackgroundResource(R.drawable.back2);
				}
				else
				{
					name.setText("Neck");
					des.setText(mNeck);
					img1.setBackgroundResource(R.drawable.neck2);
					img3.setBackgroundResource(R.drawable.neck2);
					img2.setBackgroundResource(R.drawable.neck2);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) 
			{
				name.setText("");
				des.setText("");
				img1.setBackgroundColor(Color.BLACK);
				img2.setBackgroundColor(Color.BLACK);
				img3.setBackgroundColor(Color.BLACK);
			}
		});
				
		lst.setOnItemClickListener(this);
		deleteBttn.setOnClickListener(this);
		
		bttn.setOnClickListener(this);
		
		
		processBundle();
	}
	
	private void processBundle()
	{
		Bundle bu = getIntent().getExtras();
		ArrayList<String> temp = new ArrayList<String>();
		if( bu.getBoolean("state") == SelectionActivity.STATE_SELECTION)
		{
			temp = bu.getStringArrayList("bodyPoints");
			setUpSelectionState( temp );
			if(bu.containsKey("gallery"))
			{
				if(bu.getBoolean("gallery"))
				{
					setUpGallery(bu.getStringArrayList("activities"));
				}
			}
		}
		else
		{
			// Display State
			temp = bu.getStringArrayList("activities");
			setUpDisplayState( temp );
		}
		
		
	}
	
	
	
	private void setUpSelectionState( ArrayList<String> bodyPoints )
	{
		if( bodyPoints.contains("Ankle"))
		{
			parts.add("Ankle");
			parts.add("Ankle");
			parts.add("Ankle");
			parts.add("Ankle");
		}
		if( bodyPoints.contains("Neck"))
		{
			parts.add("Neck");
			parts.add("Neck");
			parts.add("Neck");
			parts.add("Neck");
		}
		
		// Set up adapter for the list of exercises and the exercise icons in the gallery
		lst.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, parts.toArray(parts2)));
		
	}
	
	private void setUpDisplayState( ArrayList<String> activities )
	{
		setUpGallery(activities);
		lst.setVisibility(View.GONE);
		deleteBttn.setVisibility(View.GONE);
		bttn.setVisibility(View.GONE);
		gallery.setAdapter(dap);
	}
	
	
	private void setUpGallery( ArrayList<String> activities )
	{
		for( String str : activities)
		{
			if (str.contains("one"))
			{
				exercises.add(R.drawable.back1);
			}
			if( str.contains("two"))
			{
				exercises.add(R.drawable.neck1);
			}
		}
		gallery.setAdapter(dap);
	}
	
	@Override
	public boolean onCreateOptionsMenu( Menu menu)
	{
		menu.add(1,HOME,0,"Home").setIcon(R.drawable.ic_menu_home);
		menu.add(1,DELETE,1,"Delete").setIcon(android.R.drawable.ic_menu_delete);
		
		return true;
	}
	
	private void loadBodyPoints()
	{
		
	}
	
	
	@Override
	public void onPause()
	{
		super.onPause();
		// Save selection before changing???
	}
	
	@Override 
	public void onResume()
	{
		super.onResume();
		// Load preivous selection???
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
			default:
				return false;
		}
		
		return true;
    }
	
	// Select items from the list
	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int arg2, long arg3) 
	{
		
		if( ((TextView)view).getText().toString().contains("Ankle") )
		{
			exercises.add(R.drawable.back1);
		}
		if( ((TextView)view).getText().toString().contains("Neck") )
		{
			exercises.add(R.drawable.neck1);
		}
		gallery.setAdapter(dap);
	}
	
	// Display for the gallery
	private class DisplayAdapter extends BaseAdapter
	{
		private int mGalleryBackground;
		private Context context;
		DisplayAdapter(Context c)
		{
			context = c;
			TypedArray a = obtainStyledAttributes(R.styleable.HelloGallery);
    		mGalleryBackground = a.getResourceId(R.styleable.HelloGallery_android_galleryItemBackground,0);
    		a.recycle();
    	}
		
		@Override
		public int getCount() 
		{
			return exercises.size();
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
		public View getView(int position, View arg1, ViewGroup arg2) 
		{
			ImageView i = new ImageView(context);
			i.setImageResource(exercises.get(position));
			i.setLayoutParams(new Gallery.LayoutParams(100,100));
			i.setScaleType(ImageView.ScaleType.FIT_XY);
			i.setBackgroundResource(mGalleryBackground);
						
			return i;
			
		}
		
	}

	// Delete the position
	@Override
	public void onClick(View view) 
	{
		if(view.equals(deleteBttn)) 
		{
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Are you sure you want to delete exercise?")
			       .setCancelable(false)
			       .setPositiveButton("Yes", new DialogInterface.OnClickListener() 
			       {
			           public void onClick(DialogInterface dialog, int id) 
			           {
			        	  
			        		   int position = gallery.getSelectedItemPosition();
			        		   exercises.remove(position);
			        		   gallery.setAdapter(dap);
			        		   // Is gallery now empty?
			        		   if( gallery.getSelectedItem() == null)
			        			{
			        				name.setText("");
			        				des.setText("");
			        				img1.setBackgroundColor(Color.BLACK);
			        				img2.setBackgroundColor(Color.BLACK);
			        				img3.setBackgroundColor(Color.BLACK);
			        			}
			        	  
				       }
				   })
				      
				   .setNegativeButton("No", new DialogInterface.OnClickListener() 
				   {
				       public void onClick(DialogInterface dialog, int id) 
				       {
				            // Do nothing if user selects not to delete.  
				       }
				   });
			AlertDialog alert = builder.create();
			// Don't show dialog if nothing to delete!
			if(gallery.getSelectedItem() != null)
	  	   	{
				alert.show();
			}
		}
		if(view.equals(bttn))
		{
			if( gallery.getChildCount() == 0 )
			{
				Toast.makeText(this, "Please select some exercises from the list", Toast.LENGTH_SHORT).show();
			}
			else
			{
				Intent myIntent = new Intent(SelectionActivity.this, OutputActivity.class);
				Bundle b = new Bundle();
				
				b.putIntegerArrayList("activities", exercises);
				
				myIntent.putExtras(b);
				startActivity(myIntent);
			}	
		}
		
		
	}
}
