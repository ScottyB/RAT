package swin.rat.ui.doctor;

import java.util.ArrayList;
import java.util.List;

import swin.rat.model.PrescribedTask;
import swin.rat.model.TaskFactory;
import swin.rat.model.TaskTemplate;
import swin.rat.ui.RatApplication;
import swin.rat.util.Utils;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class CustomizeTasksActivity extends Activity implements OnClickListener
{
	private ArrayList<TableRow> rows;
	private RatApplication globals;
	@Override
	public void onCreate(Bundle b)
	{
		super.onCreate(b);
		setContentView(R.layout.doc_customize_tasks);
		Utils.receiveClosingBroadcast(this);
		rows = new ArrayList<TableRow>();
		TableLayout table = (TableLayout) findViewById(R.id.table);
		globals = (RatApplication) getApplicationContext();
		
		for(int i=0; i < globals.selectedTasks.size();i++)
		{
			TableRow arow = generateRow(globals.selectedTasks.get(i));
			table.addView( arow, new TableLayout.LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
			rows.add(arow);
		
		}
		Button reviewBttn = (Button) findViewById(R.id.reviewBttn);
		reviewBttn.setOnClickListener(this);
	}
	
	private TableRow generateRow( TaskTemplate task   )
	{
		TableRow temp = new TableRow(this);
		temp.setLayoutParams(new LayoutParams(
				LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
		TextView txt = new TextView(this);
		txt.setText(task.shortName);
		txt.setTextSize(20);
		temp.addView( txt );
		
		EditText val = new EditText(this);
		EditText val2 = new EditText(this);
		InputFilter fil = new InputFilter.LengthFilter(2);
		InputFilter [] filters = new InputFilter[1];
		
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
	
	private List<PrescribedTask> generateTasks()
	{
		List<PrescribedTask> tasks = new ArrayList<PrescribedTask>();
		for( int i=0; i<rows.size(); i++)
		{
			String reps = ((EditText)rows.get(i).getChildAt(1)).getText().toString();
			String freq = ((EditText)rows.get(i).getChildAt(2)).getText().toString();
			tasks.add(TaskFactory.getPrescribedTask(globals.selectedTasks.get(i), Integer.parseInt(reps), Integer.parseInt(freq)));
		}
		return tasks;
	}

	@Override
	public void onClick(View arg0) 
	{
		globals.patient.newestConsultation().tasks = (ArrayList<PrescribedTask>) generateTasks();
		Intent atent = new Intent(CustomizeTasksActivity.this, ReviewTasksActivity.class);
		startActivity(atent);
		
	}

	
}
