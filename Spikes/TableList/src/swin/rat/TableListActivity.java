package swin.rat;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class TableListActivity extends Activity
{
	private ListView list;
	private String [] people = { "Mary Johnson", "Peter Jackson", "Arraon Jackson", "Katie Lou",
				"Smith Peter", "Sandy Phillips", "George Town", "Jane Peterson"};
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        this.setTheme(android.R.style.Theme_Translucent);
        list = (ListView)findViewById(R.id.listPeople);
        
        // To create your own list of views define a view in an xml layout and call it instead 
        // of android.R.layout.simple_list_item_1
        list.setAdapter(new ArrayAdapter<String>( this, android.R.layout.simple_list_item_1, people));
        
        list.setOnItemClickListener(new OnItemClickListener()
        {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int arg2,long arg3) 
			{
				TextView txt = (TextView)findViewById(R.id.selected);
				txt.setText(((TextView)view).getText());
				
			}
        	
        });
    }
}
