package swin.rat;

import java.util.ArrayList;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

public class SelectionActivity extends Activity implements OnItemClickListener, OnClickListener
{
	private Gallery gallery;
	private ArrayList<Integer> exercises;
	private DisplayAdapter dap;
	private String mBack = "This is where the corresponding back exercise description will go";
	private String mNeck = "This is where the description for the neck exercise will go";
	
	private TextView name;
	private TextView des;
	
	private ImageView img1;
	private ImageView img2;
	private ImageView img3;
	
	
	@Override
	public void onCreate( Bundle savedInstance)
	{
		super.onCreate(savedInstance);
		setContentView(R.layout.land);
		
		Bundle b = getIntent().getExtras();
		final ArrayList<String> parts = new ArrayList<String>(0); // For the list!!!!
		
		exercises = new ArrayList<Integer>();
		
		gallery = (Gallery) findViewById(R.id.gallery);
		
		dap = new DisplayAdapter(this);
		
		gallery.setAdapter(dap);
		
		 name = (TextView)findViewById(R.id.title);
		 des = (TextView) findViewById(R.id.description);
		
		 img1 = (ImageView) findViewById(R.id.view);
		 img2 = (ImageView) findViewById(R.id.view1);
		 img3 = (ImageView) findViewById(R.id.view2);
		
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
		
		Button deleteBttn = (Button) findViewById(R.id.delete);
		deleteBttn.setOnClickListener(this);
			
		
	//exercises = new ImageView[0];
		
		if( b.containsKey("back"))
		{
			parts.add(b.getString("back"));
			parts.add("back");
			parts.add("back");
			parts.add("back");
			
			
		}
		if( b.containsKey("neck"))
		{
			parts.add(b.getString("neck"));
			parts.add("neck");
			parts.add("neck");
			parts.add("neck");
		}
		
		ListView lst = (ListView) findViewById(R.id.list);
				
		String [] parts2 = new String[parts.size()];
		
		parts.toArray(parts2);
		
		lst.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, parts.toArray(parts2)));
		lst.setOnItemClickListener(this);
		
		
	}

	// Select items from the list
	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int arg2, long arg3) 
	{
		
		if( ((TextView)view).getText().toString().contains("back") )
		{
			exercises.add(R.drawable.back1);
		}
		if( ((TextView)view).getText().toString().contains("neck") )
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
			i.setLayoutParams(new Gallery.LayoutParams(75,75));
			i.setScaleType(ImageView.ScaleType.FIT_XY);
			i.setBackgroundResource(mGalleryBackground);
						
			return i;
			
		}
		
	}

	// Delete the position
	@Override
	public void onClick(View arg0) 
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
	
	
}
