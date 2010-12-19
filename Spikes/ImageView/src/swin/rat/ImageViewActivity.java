package swin.rat;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


public class ImageViewActivity extends Activity
{
	// probably better to store these in an xml file
	private Integer[] mImageIds = 
	{
		R.drawable.sample_0,
		R.drawable.sample_1,
		R.drawable.sample_2,
		R.drawable.sample_3,
		R.drawable.sample_4,
		R.drawable.sample_5,
		R.drawable.sample_6,
		R.drawable.sample_7
	};
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        
        // Code used when portrait
        Gallery g = (Gallery) findViewById(R.id.gallery);
        g.setAdapter(new ImageAdapter(this));

        g.setOnItemClickListener( new OnItemClickListener()
        {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) 
			{
				// When image selected/tapped
				ImageView img = (ImageView) findViewById(R.id.current);
				img.setImageResource(mImageIds[position]);
			}
        	
        });
        
        // Code used when landscape
        GridView gridview = (GridView) findViewById(R.id.grid);
        gridview.setAdapter(new ImageAdapter(this));
       
        gridview.setOnItemClickListener(new OnItemClickListener() 
        {
        	public void onItemClick(AdapterView<?> parent, View v, int position, long id) 
        	{
        		// When image selected/tapped
        		Toast.makeText(ImageViewActivity.this, "" + position, Toast.LENGTH_SHORT).show();
        	}
       });

        
    }
    
    
    public class ImageAdapter extends BaseAdapter
    {
    	int mGalleryBackground;
    	private Context mContext;
    	    	
    	public ImageAdapter(Context c)
    	{
    		mContext = c;
    		// An array used only for attributes received with the following function and obtainAttributes 
    		TypedArray a = obtainStyledAttributes(R.styleable.HelloGallery);
    		mGalleryBackground = a.getResourceId(R.styleable.HelloGallery_android_galleryItemBackground,0);
    		a.recycle();
    	}

		@Override
		public int getCount() 
		{
			return mImageIds.length;
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
			int width = getWindowManager().getDefaultDisplay().getWidth();
		    int height = getWindowManager().getDefaultDisplay().getHeight();
		    ImageView i = new ImageView(mContext);
		    
		    if ( width > height )
		    {
		    	 if (convertView == null) {  // if it's not recycled, initialize some attributes
		             i = new ImageView(mContext);
		             i.setLayoutParams(new GridView.LayoutParams(85, 85));
		             i.setScaleType(ImageView.ScaleType.CENTER_CROP);
		             i.setPadding(8, 8, 8, 8);
		         } else {
		             i = (ImageView) convertView;
		         }
		         i.setImageResource(mImageIds[position]);
		    }
		    else        	
		    {
		    	i.setImageResource(mImageIds[position]);
				i.setLayoutParams(new Gallery.LayoutParams(150,150));
				i.setScaleType(ImageView.ScaleType.FIT_XY);
				i.setBackgroundResource(mGalleryBackground);
			}
				
			return i;
		}
    	
    }
}

