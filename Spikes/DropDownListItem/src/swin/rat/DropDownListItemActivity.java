package swin.rat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemLongClickListener;

public class DropDownListItemActivity extends Activity 
{
	private ExpandableListView list;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
         list = (ExpandableListView)findViewById(R.id.list); 
         list.setOnItemLongClickListener( new OnItemLongClickListener()
         {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View v,	int position, long arg3) 
			{
				Log.w("tag", "yes "+ position);
				// TODO Auto-generated method stub
				return false;
			}
        	 
         });
       
 // Construct Expandable List
    final String NAME = "name";
    
    final LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    final ArrayList<HashMap<String, String>> headerData = new ArrayList<HashMap<String, String>>();

    final HashMap<String, String> group1 = new HashMap<String, String>();
    group1.put(NAME, "Group 1");
    headerData.add( group1 );
    
    final HashMap<String, String> group2 = new HashMap<String, String>();
    group2.put(NAME, "Group 3");
    headerData.add( group2 );
    
    final ArrayList<ArrayList<HashMap<String, Object>>> childData = new ArrayList<ArrayList<HashMap<String, Object>>>();

    final ArrayList<HashMap<String, Object>> group1data = new ArrayList<HashMap<String, Object>>();
    childData.add(group1data);
   
    final ArrayList<HashMap<String, Object>> group2data = new ArrayList<HashMap<String, Object>>();
    childData.add(group2data);
    // Set up some sample data in both groups
    
        final HashMap<String, Object> map = new HashMap<String,Object>();
        map.put(NAME, "This is where some simple notes will be shown concerning this session. " );
        
       group1data.add(map);
       group2data.add(map);

    list.setAdapter( new SimpleExpandableListAdapter(
            this,
            headerData,
            android.R.layout.simple_expandable_list_item_1,
            new String[] { NAME },            // the name of the field data
            new int[] { android.R.id.text1 }, // the text field to populate with the field data
            childData,
            0,
            null,
            new int[] {}
        ) {
            @Override
            public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) 
            {
                final View v = super.getChildView(groupPosition, childPosition, isLastChild, convertView, parent);
                // Notes go here
                
                ((TextView)v).setText( (String) ((Map<String,Object>)getChild(groupPosition, childPosition)).get(NAME) );
            
                return v;
            }

            @Override
            public View newChildView(boolean isLastChild, ViewGroup parent) 
            {
            	return layoutInflater.inflate(android.R.layout.simple_expandable_list_item_1, null, false);
            }
        }
    );

	
    
	
    }

	


    
}
