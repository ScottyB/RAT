package swin.rat;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
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
import android.widget.AdapterView.OnItemLongClickListener;



public class SelectionActivity extends Activity implements OnItemClickListener, OnClickListener, OnItemLongClickListener
{
	static final private int HOME = 1;
	static final private int DELETE = 2;
	static final public boolean STATE_SELECTION = true;
	static final public boolean STATE_DISPLAY = false;
	private boolean mState;
	
	private Gallery gallerySelection;
	private Gallery galleryExercise;
	//private ArrayList<Integer> exercises;
	private SelectionAdapter selectAdap;

	
	private String [] parts2;
	private TextView name;
	private TextView des;
	
	private ListView lst;
		
	private Button bttn;
	
	private ArrayList<Exercise> fExercises;			// All the exercises store in the xml file
	
	private ArrayList<Exercise> selectedExercises;		// 
	private ArrayList<ArrayList<Exercise>> sortedExercises;
	private ArrayList<String> bodyParts;			// For the list
	private int currentBodyPart; 			// Find the name by accessing bodyParts
	
	private ExerciseAdapter exerciseAdap;
	
	
	
	@Override
	public void onCreate(Bundle bundle)
	{
		super.onCreate(bundle);
		setContentView(R.layout.selection);
		
		Utils.receiveClosingBroadcast(this);
		
				
		// Link views with code
		bttn = (Button) findViewById(R.id.next);
		gallerySelection = (Gallery) findViewById(R.id.selection);
		galleryExercise = (Gallery) findViewById(R.id.exercise);
		name = (TextView)findViewById(R.id.title);
		des = (TextView) findViewById(R.id.description);
		lst = (ListView) findViewById(R.id.list);
				
		// Intialisations
		//parts = new ArrayList<String>(0); // For the list!!!!
		//exercises = new ArrayList<Integer>();
		selectAdap = new SelectionAdapter(this);
		fExercises = new ArrayList<Exercise>();
		sortedExercises = new ArrayList<ArrayList<Exercise>>();
		bodyParts = new ArrayList<String>();
		selectedExercises = new ArrayList<Exercise>();
		gallerySelection.setSpacing(10);
		galleryExercise.setSpacing(20);
		
		currentBodyPart = 0;
		exerciseAdap = new ExerciseAdapter(this);
			
		lst.setOnItemClickListener(this);
		
		
		bttn.setOnClickListener(this);
			
		processBundle();
		
		Log.i("tag", "Bundle Passes");
		loadExercises();
		sortExercises();
		galleryExercise.setAdapter(exerciseAdap);
		galleryExercise.setOnItemClickListener(this);
		galleryExercise.setOnItemLongClickListener(this);
		gallerySelection.setOnItemLongClickListener(this);
	}
	
	
	@Override
	protected void onResume()
	{
		super.onResume();
		AcessObject temp = ((AcessObject)getApplicationContext());
		if( temp.getToAdd() )
		{
			if( selectedExercises.contains(temp.getExercise()))
			{
				Toast.makeText(this, "This activity has been assigned already", Toast.LENGTH_SHORT).show();
				temp.setToAdd(false);
			}
			else
			{
				selectedExercises.add(temp.getExercise());
				temp.setExercise(null);
				temp.setToAdd(false);
				gallerySelection.setAdapter(selectAdap);
			}
		}
	}
	
	private void processBundle()
	{
		Log.w("tag", "nope");
		Bundle bu = getIntent().getExtras();
		
		if( bu.getBoolean("state") == SelectionActivity.STATE_SELECTION)
		{
			
			bodyParts = bu.getStringArrayList("bodyPoints");
			Log.w("tag", "nope1");
			if(bu.containsKey("selection"))
			{
				if(bu.getBoolean("selection"))
				{
					setUpSelected(bu.getStringArrayList("activities"));
				}
			}
		}
		else
		{
			
		}
		
		name.setText(bodyParts.get(0) + " Exercises");
		des.setText("Tap an activity to add it to selection");
		parts2 = new String[bodyParts.size()];
        Collections.sort(bodyParts);	
		lst.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, bodyParts.toArray(parts2)));
	}
	
	
	
	
	
	private void setUpDisplayState( ArrayList<String> activities )
	{
		setUpSelected(activities);
		lst.setVisibility(View.GONE);
		
		bttn.setVisibility(View.GONE);
		gallerySelection.setAdapter(selectAdap);
	}
	
	
	private void setUpSelected( ArrayList<String> activities )
	{
		for( String str : activities)
		{
			for(Exercise e: fExercises)
			{
				if(str.equals(e.getShortName()))
				{
					selectedExercises.add(e);
				}
				
			}
		}
		gallerySelection.setAdapter(selectAdap);
		
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
	public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) 
	{
		if(parent.getId() == lst.getId() )
		{
			currentBodyPart = position;
			galleryExercise.setAdapter(exerciseAdap);
			name.setText(bodyParts.get(position) + " Exercises");
		}
		
		if( parent.getId() == galleryExercise.getId() )
		{
			Exercise temp = new Exercise();
			temp = sortedExercises.get(currentBodyPart).get(position);
			if( selectedExercises.contains(temp))
			{
				Toast.makeText(this, "This activity has been assigned already", Toast.LENGTH_SHORT).show();
			}
			else
			{
				selectedExercises.add( temp );
				gallerySelection.setAdapter(selectAdap);
			}
		}
		
		
	}
	
	// Display for the gallery
	private class SelectionAdapter extends BaseAdapter
	{
		private int mGalleryBackground;
		private Context context;
		SelectionAdapter(Context c)
		{
			context = c;
			TypedArray a = obtainStyledAttributes(R.styleable.HelloGallery);
    		mGalleryBackground = a.getResourceId(R.styleable.HelloGallery_android_galleryItemBackground,0);
    		a.recycle();
    	}
		
		@Override
		public int getCount() 
		{
			return selectedExercises.size();
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
			i.setImageURI(selectedExercises.get(position).getIcon());
			i.setLayoutParams(new Gallery.LayoutParams(100,100));
			i.setScaleType(ImageView.ScaleType.FIT_XY);
			i.setBackgroundResource(mGalleryBackground);
						
			return i;
			
		}
		
	}
	
	private class ExerciseAdapter extends BaseAdapter
	{
		private int mGalleryBackground;
		private Context context;
		ExerciseAdapter(Context c)
		{
			context = c;
			TypedArray a = obtainStyledAttributes(R.styleable.HelloGallery);
    		mGalleryBackground = a.getResourceId(R.styleable.HelloGallery_android_galleryItemBackground,0);
    		a.recycle();
    	}
		
		@Override
		public int getCount() 
		{
			return sortedExercises.get(currentBodyPart).size();
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
			i.setBackgroundColor(Color.WHITE);
			i.setImageURI(sortedExercises.get(currentBodyPart).get(position).getPrimaryImage());
			i.setLayoutParams(new Gallery.LayoutParams(250,250));
			i.setScaleType(ImageView.ScaleType.FIT_XY);
			i.setBackgroundResource(mGalleryBackground);
						
			return i;
			
		}
		
	}


	// Delete the position
	@Override
	public void onClick(View view) 
	{
		if(view.equals(bttn))
		{
			if( gallerySelection.getChildCount() == 0 )
			{
				Toast.makeText(this, "Please select some exercises from the list", Toast.LENGTH_SHORT).show();
			}
			else
			{
				Intent myIntent = new Intent(SelectionActivity.this, OutputActivity.class);
				Bundle b = new Bundle();
				
				//b.putIntegerArrayList("activities", exercises);
				
				myIntent.putExtras(b);
				startActivity(myIntent);
			}	
		}
		
		
	}
	
	private void loadExercises()
	{
		SAXParserFactory saxf = SAXParserFactory.newInstance();
		try {
			SAXParser saxp = saxf.newSAXParser();
			XMLReader xmlr = saxp.getXMLReader();
			ExerciseHandler handler = new ExerciseHandler();
			xmlr.setContentHandler(handler);
			InputStream in = this.getResources().openRawResource(R.raw.exercises);
			InputSource inputsource = new InputSource(in);
			xmlr.parse(inputsource);
			fExercises = (ArrayList<Exercise>)handler.getExercises();
			
		} catch (ParserConfigurationException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void sortExercises()
	{
		for( String s: bodyParts)
		{
			ArrayList<Exercise> temp = new ArrayList<Exercise>();
			for(Exercise e:fExercises)
			{
				if( e.isForBodyPart(s))
				{
					temp.add(e);
				}
			}
			sortedExercises.add(temp);
		}
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View arg1, int position, long arg3) 
	{
		if( parent.getId() == galleryExercise.getId())
		{
			AcessObject temp = ((AcessObject)getApplicationContext());
			temp.setExercise( sortedExercises.get(currentBodyPart).get(position));
			temp.setBodyExercises(sortedExercises.get(currentBodyPart));
			
			Intent ent = new Intent( SelectionActivity.this, DisplayExerciseActivity.class);
			Bundle b = new Bundle();
			b.putInt("ref", position);
			ent.putExtras(b);
			
			startActivity(ent);
		}
		
		if( parent.getId() == gallerySelection.getId())
		{
			selectedExercises.remove(position);
			gallerySelection.setAdapter( selectAdap );
		}
		return false;
	}
}
