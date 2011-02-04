package swin.rat.ui.doctor;

import java.util.ArrayList;

import swin.rat.model.PrescribedTask;
import swin.rat.model.TaskTemplate;
import swin.rat.ui.RatApplication;
import swin.rat.util.Utils;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

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
	
	private ArrayList<PrescribedTask> selected;  // Copy of global selectTasks
	
	@Override
	public void onCreate(Bundle b)
	{
		super.onCreate(b);
		setContentView(R.layout.doc_review_tasks);
		TableLayout table = (TableLayout) findViewById(R.id.table); 
		mContext = this;
		Utils.receiveClosingBroadcast(this);
		Button bttn = (Button) findViewById(R.id.newBttn);
		
		selected = new ArrayList<PrescribedTask>();
		bttn.setOnClickListener(this);
		
		globals = ((RatApplication)getApplicationContext());
		
		selected = globals.patient.newestConsultation().tasks;
		
		for( int i=0; i< selected.size(); i++)
		{
			TableRow arow = generateRow(selected.get(i));
			table.addView( arow, new TableLayout.LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
		}

	}
	
	private TableRow generateRow( PrescribedTask task   )
	{
		TableRow temp = new TableRow(this);
		temp.setLayoutParams(new LayoutParams(
				LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
		TextView txt = new TextView(this);
		txt.setText(task.shortName);
		txt.setTextSize(20);
		temp.addView( txt );
		
		TextView val = new TextView(this);
		TextView val2 = new TextView(this);
		InputFilter fil = new InputFilter.LengthFilter(2);
		InputFilter [] filters = new InputFilter[1];
		val.setTextSize(20);
		val2.setTextSize(20);
		val.setText(task.repetition.toString());
		
		val2.setText(task.freq.toString());
		filters[0] = fil;
		val.setMaxEms( 3 );
		val.setFilters( filters);
		val2.setFilters( filters);
		val.setInputType(InputType.TYPE_CLASS_NUMBER);
		val2.setInputType(InputType.TYPE_CLASS_NUMBER);
		
		temp.addView(val);
		temp.addView(val2);

		return temp;
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
					if(!globals.allPatients.contains(globals.patient))
					{
						globals.savePatient();
						globals.allPatients.add(globals.patient);
						
					}
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
	
	
}
