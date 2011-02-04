package swin.rat.ui.doctor;

import java.util.ArrayList;

import swin.rat.model.Feedback;
import swin.rat.model.PrescribedTask;
import swin.rat.ui.RatApplication;
import swin.rat.util.Utils;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ConsultationFeedbackActivity extends Activity
{
	static final private int HOME = 1;
	static final private int SESSION = 2;
	static final private int SELECTION = 3;
	
	@Override
	public void onCreate(Bundle b)
	{
		super.onCreate(b);
		setContentView(R.layout.doc_consultation_feedback);
		Utils.receiveClosingBroadcast(this);
		Button bttn = (Button) findViewById(R.id.newBttn);
				
		TableLayout table = (TableLayout) findViewById(R.id.table); 
		bttn.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0) 
			{
				
				
			}
			
		});
		
		Log.i("tag","before globals");
		RatApplication globals = (RatApplication)getApplicationContext();
		
		Bundle bund = new Bundle();
		bund = this.getIntent().getExtras();
		int pos = bund.getInt("pos");
		
		for(int i=0; i<globals.patient.consultations.get(pos).tasks.size(); i++)
		{
			Log.i("tag","in loop");
			TableRow arow = generateRow(globals.patient.consultations.get(pos).tasks.get(i));
			table.addView( arow, new TableLayout.LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
		}
		
		
		
	}
	
	private TableRow generateRow( PrescribedTask task)
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
		ImageView img = new ImageView(this);
		
		if( !task.feedback.hasData())
		{
			val.setText("-");
			val2.setText("-");
			temp.addView(val);
			temp.addView(val2);
			return temp;
		}
		
		
		
		
		val.setTextSize(20);
		val2.setTextSize(20);
		
		val.setText(task.feedback.reps.toString());
		val2.setText(task.feedback.freq.toString());
		
				
		
		temp.addView(val);
		temp.addView(val2);
		
	
		if( task.feedback.wasFun)
			img.setImageResource(R.drawable.btn_check_buttonless_on);
		else
			img.setImageResource(android.R.drawable.ic_delete);
		
		temp.addView(img);

		return temp;
	}
	
	@Override
	public boolean onCreateOptionsMenu( Menu menu)
	{
		menu.add(1,HOME,0,"Home").setIcon(R.drawable.ic_menu_home);
		menu.add(1,SESSION,1,"Session").setIcon(R.drawable.ic_menu_compose);
		menu.add(1,SELECTION,2,"Selection").setIcon(R.drawable.ic_menu_account_list);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
    {
		Intent myIntent = new Intent();
		switch(item.getItemId())
		{
			case HOME: 
				Utils.returnHomeNoMessage(this);
				break;
			case SESSION:
				myIntent.setClassName("swin.rat","swin.rat.SessionActivity");
				startActivity(myIntent);
				break;
			case SELECTION:
				myIntent = new Intent(ConsultationFeedbackActivity.this, SelectionActivity.class);
				startActivity(myIntent);
				break;
			default:
				return false;
		}
		
		return true;
    }
}
