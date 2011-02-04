package swin.rat.ui.doctor;

import java.util.ArrayList;
import java.util.Collections;

import swin.rat.model.BodyPoint;
import swin.rat.model.TaskTemplate;
import swin.rat.ui.RatApplication;
import swin.rat.util.Utils;
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
	
	private boolean mState;
	
	private Gallery gallerySelection;
	private Gallery galleryTask;
	private SelectionAdapter selectAdap;

	
	private String [] parts2;
	private TextView name;
	private TextView des;
	
	private ListView lst;
		
	private Button bttn;
	
	private ArrayList<TaskTemplate> fTasks;			// All the Tasks stored in the xml file
	
	private ArrayList<TaskTemplate> selectedTasks;		// 
	private ArrayList<ArrayList<TaskTemplate>> sortedTasks;
	private ArrayList<String> bodyParts;			// For the list, copy of global bodyPartNames
	private int currentBodyPart; 			// Find the name by accessing bodyParts
	
	private TaskAdapter TaskAdap;
	
	private RatApplication globals;
	
	@Override
	public void onCreate(Bundle bundle)
	{
		super.onCreate(bundle);
		setContentView(R.layout.doc_selection);
		
		Utils.receiveClosingBroadcast(this);
		
		globals = ((RatApplication)getApplicationContext());
		
		// Link views with code
		bttn = (Button) findViewById(R.id.next);
		gallerySelection = (Gallery) findViewById(R.id.selection);
		galleryTask = (Gallery) findViewById(R.id.task);
		name = (TextView)findViewById(R.id.title);
		des = (TextView) findViewById(R.id.description);
		lst = (ListView) findViewById(R.id.list);
				
		// Intialisations
		selectAdap = new SelectionAdapter(this);
		fTasks = new ArrayList<TaskTemplate>();
		sortedTasks = new ArrayList<ArrayList<TaskTemplate>>();
		bodyParts = new ArrayList<String>();
		selectedTasks = new ArrayList<TaskTemplate>();
		gallerySelection.setSpacing(10);
		galleryTask.setSpacing(20);
		currentBodyPart = 0;
		TaskAdap = new TaskAdapter(this);
		lst.setOnItemClickListener(this);
		bttn.setOnClickListener(this);
		
		processBundle();
		
		fTasks = (ArrayList<TaskTemplate>) globals.taskFactory.tasks;
		
	
		sortTasks();
		galleryTask.setAdapter(TaskAdap);
		galleryTask.setOnItemClickListener(this);
		galleryTask.setOnItemLongClickListener(this);
		gallerySelection.setOnItemLongClickListener(this);
		
		
	}
	
	
	@Override
	protected void onResume()
	{
		super.onResume();
//		AcessObject temp = ((AcessObject)getApplicationContext());
//		ArrayList<Task> selected = new ArrayList<Task>();
//		selected = temp.getSelectedTasks();
//		if(selected != null)
//		{
//			for(Task e: selected)
//			{
//				if(selectedTasks.contains(e))
//				{
//					// If already in selected dont change
//					temp.setTask(null);
//				}
//				else
//				{
//					selectedTasks.add(e);
//					temp.setTask(null);
//					gallerySelection.setAdapter(selectAdap);
//				}
//			}
//		}
	}
	
	
	
	
	
	private void processBundle()
	{
		
		int last = globals.patient.consultations.size() - 1;
		bodyParts = BodyPoint.namesOfBodyPoints(globals.patient.consultations.get(last).getPoints());
		
		
		Collections.sort(bodyParts);
		name.setText(bodyParts.get(0) + " Tasks");
		des.setText("Tap an activity to add it to selection");
		parts2 = new String[bodyParts.size()];
        	
		lst.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, bodyParts.toArray(parts2)));
	}
	
	
	
	
	
	
	@Override
	public boolean onCreateOptionsMenu( Menu menu)
	{
		menu.add(1,HOME,0,"Home").setIcon(R.drawable.ic_menu_home);
		menu.add(1,DELETE,1,"Delete").setIcon(android.R.drawable.ic_menu_delete);
		
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
			galleryTask.setAdapter(TaskAdap);
			name.setText(bodyParts.get(position) + " Tasks");
		}
		
		if( parent.getId() == galleryTask.getId() )
		{
			TaskTemplate temp = new TaskTemplate();
			temp = sortedTasks.get(currentBodyPart).get(position);
			if( selectedTasks.contains(temp))
			{
				Toast.makeText(this, "This activity has been assigned already", Toast.LENGTH_SHORT).show();
			}
			else
			{
				selectedTasks.add( temp );
				gallerySelection.setAdapter(selectAdap);
			}
		}
		
		
	}
	
	// Adapter to be used for the gallery of selected tasks
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
			return selectedTasks.size();
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
			i.setImageURI(selectedTasks.get(position).getIcon());
			i.setLayoutParams(new Gallery.LayoutParams(100,100));
			i.setScaleType(ImageView.ScaleType.FIT_XY);
			i.setBackgroundResource(mGalleryBackground);
						
			return i;
			
		}
		
	}
	
	// Adapter for the gallery of tasks
	private class TaskAdapter extends BaseAdapter
	{
		private int mGalleryBackground;
		private Context context;
		TaskAdapter(Context c)
		{
			context = c;
			TypedArray a = obtainStyledAttributes(R.styleable.HelloGallery);
    		mGalleryBackground = a.getResourceId(R.styleable.HelloGallery_android_galleryItemBackground,0);
    		a.recycle();
    	}
		
		@Override
		public int getCount() 
		{
			return sortedTasks.get(currentBodyPart).size();
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
			i.setImageURI(sortedTasks.get(currentBodyPart).get(position).getPrimaryImage());
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
				Toast.makeText(this, "Please select some Tasks from the list", Toast.LENGTH_SHORT).show();
			}
			else
			{
				Intent myIntent = new Intent(SelectionActivity.this, CustomizeTasksActivity.class);
				((RatApplication)getApplicationContext()).selectedTasks = selectedTasks;
				startActivity(myIntent);
			}	
		}
		
		
	}
	
	

	private void sortTasks()
	{
		for( String s: bodyParts)
		{
			ArrayList<TaskTemplate> temp = new ArrayList<TaskTemplate>();
			for(TaskTemplate e:fTasks)
			{
				if( e.isForBodyPart(s))
				{
					temp.add(e);
				}
			}
			sortedTasks.add(temp);
		}
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View arg1, int position, long arg3) 
	{
		if( parent.getId() == galleryTask.getId())
		{
			RatApplication temp = ((RatApplication)getApplicationContext());
			temp.task =  sortedTasks.get(currentBodyPart).get(position);
			temp.bodyPartTasks = sortedTasks.get(currentBodyPart) ;
			Intent ent = new Intent( SelectionActivity.this, DisplayTaskActivity.class);
			Bundle b = new Bundle();
			temp.selectedTasks = selectedTasks;
			b.putInt("ref", position);
			ent.putExtras(b);
			
			startActivity(ent);
		}
		
		if( parent.getId() == gallerySelection.getId())
		{
			selectedTasks.remove(position);
			gallerySelection.setAdapter( selectAdap );
		}
		return false;
	}
}
