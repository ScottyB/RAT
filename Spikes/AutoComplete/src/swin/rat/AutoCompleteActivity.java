package swin.rat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class AutoCompleteActivity extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        AutoCompleteTextView txt = (AutoCompleteTextView) findViewById(R.id.auto);
        ArrayList<String> tWords = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.values)));
        
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item, tWords);
              
        txt.setAdapter(adapter);
        
        tWords.add("Hello");
              
        adapter.add("Hell");
        adapter.add("House");
        adapter.add("Horse");
        
        
        
        ListView list1 = (ListView) findViewById(R.id.list);
        
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
        
        HashMap<String,String> temp = new HashMap<String,String>();
        temp.put("Line1", "Simple Adapter");
        temp.put("Line2", "A very simple Adapter");
        
        HashMap<String,String> temp1 = new HashMap<String,String>();
        temp1.put("Line1", "Nah");
        temp1.put("Line2", "Yah");
        
        list.add(temp);
        list.add(temp1);
        
        SimpleAdapter simpAdapter = new SimpleAdapter(
        		this,
        		list,
        		R.layout.list_item2,
        		new String [] {"Line1", "Line2"},
        		new int[] {R.id.img1, R.id.img2}  );
        
        list1.setAdapter(simpAdapter);
        
    }
}
