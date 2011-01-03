package swin.rat;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class DraggableListItemActivity extends Activity
{
	private ListView list;
	
	private static final int [] images = { R.drawable.duck, R.drawable.fish, R.drawable.turtle, R.drawable.mouse,R.drawable.mousee};
	private static final String [] names = { "Duck", "Fish", "Turtle", "Mouse", "Cow" };
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        list= (ListView) findViewById(R.id.list);
        
       // SimpleAdapter adapter = new SimpleAdapter(null, null, R.layout.list_item, null, null);
        
        
        list.setAdapter(new MyAdapter(this));
        
    }
    
    private static class MyAdapter extends BaseAdapter
    {
    	private LayoutInflater mInflater;
    	
    	public MyAdapter(Context context) 
    	{
    		mInflater = LayoutInflater.from(context);
    		 
    	}
		@Override
		public int getCount() 
		{
			return images.length;
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
		public View getView(int position, View convertView, ViewGroup parent) 
		{
			ViewHolder holder = new ViewHolder();
			if( convertView == null)
			{
				convertView = mInflater.inflate(R.layout.list_item, null);
				holder.text = (TextView)convertView.findViewById(R.id.txt);
				holder.image = (ImageView)convertView.findViewById(R.id.img);
				convertView.setTag(holder);
			}
			else
			{
				holder = (ViewHolder) convertView.getTag();
			}
			
			holder.text.setText(names[position]);
			holder.image.setImageResource(images[position]);
			
			return convertView;
		}
		
		static class ViewHolder 
		{
			TextView text;
			ImageView image;
		}
    }
}
