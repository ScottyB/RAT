package swin.rat;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;

public class ImageScalingActivity extends Activity implements OnTouchListener, OnClickListener
{
	private ArrayList<BodyPoint> mPoints;
	private TextView txt;
	private RelativeLayout layout;
	private ArrayList<Point> mMarkers;
	private ImageView mTouchSurface;
	private Button bttn;
	private float mScale;
	private float ratio;
	private Point lastPoint;
	
	private Bitmap resizedBitmap;
	
	// Views currently drawn
	private int views;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {   
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mTouchSurface = (ImageView) findViewById(R.id.img);
        mTouchSurface.setOnTouchListener(this);
        lastPoint = new Point();
        mPoints = new ArrayList<BodyPoint>();
        mMarkers = new ArrayList<Point>();
       
       
       
        Bitmap b = BitmapFactory.decodeResource(getResources(),R.drawable.human);
        Matrix matrix = new Matrix();
        // resize the bit map
        
        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
	    int width = display.getWidth(); 
	    int height = MeasureSpec.getSize(display.getHeight());
	    
	    int imgW = b.getWidth();
	    int imgH = b.getHeight();
	     
	     ratio=1;
	    
	     if(width < imgW || height < imgH)
	     {
	    	 ratio = (float)width / (float)imgW; 
	    	 
	    	 if(height < imgH)
		     {
	    		 ratio = (float)height / (float)imgH;
	    	 }
	    	 
	    	 
	    	 
	    	 matrix.postScale(ratio,ratio);
	         
	         // recreate the new Bitmap
	         resizedBitmap = Bitmap.createBitmap(b, 0, 0,imgW, imgH, matrix, true); 
	         
	         mTouchSurface.setBackgroundDrawable(new BitmapDrawable(resizedBitmap));
	     }
	     else
	     {
	    	  // Set image resource src to have android maintain aspect ratio
	         // Set background image to strectch to view
	         mTouchSurface.setImageResource(R.drawable.human);
	     }
        
        
        
       
        
        
        
        
        txt = (TextView) findViewById(R.id.txt);
       
        
        layout = (RelativeLayout) findViewById(R.id.layout);
       
        
        views = layout.getChildCount();
        
        mScale = getResources().getDisplayMetrics().density;
        
       
     
    
        bttn = (Button)findViewById(R.id.bttn);
        bttn.setOnClickListener(this);
        
        
    }
    
    
    
    // Returns the index of the closest body part
    public int findClosest( Point aPoint)
    {
    	if(!mPoints.isEmpty())
    	{
    		//Log.i("tag", "T:"+aPoint.x + ","+aPoint.y + " R:"+mPoints.get(2).x+","+mPoints.get(2).y+" P:"+mTouchSurface.getLeft()+","+mTouchSurface.getTop());
	    	//Log.e("tag", " R:"+mPoints.get(1).x+","+mPoints.get(1).y);
    		double distance = BodyPoint.distanceFrom(aPoint.x, aPoint.y, mPoints.get(0));
	    	int closest=0;
	    	for(int i=1; i<mPoints.size(); i++)
	    	{
	    		if( distance > BodyPoint.distanceFrom(aPoint.x, aPoint.y, mPoints.get(i)) )
	    		{
	    			
	    			distance = BodyPoint.distanceFrom(aPoint.x, aPoint.y, mPoints.get(i));
	    			closest = i;
	    		}
	    	}
	    	return closest;
    	}
    	return 0;
    }
    
   
    
    


	@Override
	public boolean onTouch(View view, MotionEvent event) 
	{
		if(event.getAction() == MotionEvent.ACTION_DOWN)
		{
			int x = Math.round(event.getX())+mTouchSurface.getLeft();
			int y = Math.round(event.getY())+mTouchSurface.getTop();
			if( !isNearMarker(x,y) )
			{
				ImageView img = new ImageView(this);
				img.setBackgroundResource(R.drawable.circle);
				LayoutParams Params = new LayoutParams(MarginLayoutParams.WRAP_CONTENT,MarginLayoutParams.WRAP_CONTENT);
				Params.setMargins(x, y, 0, 0);
				layout.addView(img, Params);
				mMarkers.add(new Point(x,y));
				lastPoint = new Point(x,y);
				
			}
			
			txt.setText( "x: "+Integer.toString(x)+" y: "+ Integer.toString(y));
			
		}
		
		return false;
	}
	
	private boolean isNearMarker( int touchX, int touchY )
	{
		if( !mMarkers.isEmpty())
		{
			for(int i=0; i<mMarkers.size(); i++)
			{
				double distance = BodyPoint.distanceFrom(touchX, touchY,  mMarkers.get(i));
								
				if( distance < 40 * mScale )
				{
					layout.removeViewAt(i+views);
					mMarkers.remove(i);
					return true;
				}
		
			}
		}
		return false;
	}

	

	@Override
	public void onClick(View view) 
	{
		int [] x = getResources().getIntArray(R.array.x);
		int [] y = getResources().getIntArray(R.array.y);
	        
		
		
	   //  txt.setText(Double.toString(x[1]*ratio+mTouchSurface.getLeft()));
	    
	        // Need to load points from xml file
	        // 75 refers to the position offest in the x direction from the left hand side
	        // 24 is from the top use mTouchSurface.getTop()
	     mPoints.add(new BodyPoint("Left Ankle",(int)((x[0]+ mTouchSurface.getLeft())*ratio),(int)(y[0]*ratio)));
	     mPoints.add(new BodyPoint("Neck",(int)((x[1]+ mTouchSurface.getLeft())*ratio),(int)(y[1]*ratio)));
	     int index = findClosest(lastPoint);
	    
	    
	   
	    txt.setText(mPoints.get(index).getName());
	    
	}
	

}
