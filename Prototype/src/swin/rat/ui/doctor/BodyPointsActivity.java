package swin.rat.ui.doctor;

import java.util.ArrayList;
import java.util.Calendar;

import swin.rat.model.BodyPoint;
import swin.rat.model.Consultation;
import swin.rat.ui.RatApplication;
import swin.rat.util.Utils;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.RelativeLayout.LayoutParams;

public class BodyPointsActivity extends Activity implements OnTouchListener, OnClickListener
{
	static final private int HOME = 1;
	static final private int NEW = 2;
		
	//private Bundle mPassBundle;
	private ArrayList<BodyPoint> mPoints;
	private ArrayList<Point> mMarkers;
	private RelativeLayout mLayout;
	private Point mLastPoint;	// Required to prevent code triggering at point of touch
	private ImageView mImage;
	private float mScaleFactor;
	private int mViewCount;
	private RatApplication globals;
	
	@Override
	public void onCreate(Bundle b)
	{
		super.onCreate(b);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.doc_select_points);
		// Intialising
		mPoints = new ArrayList<BodyPoint>();
		mMarkers = new ArrayList<Point>();
		mLastPoint = new Point(0,0);
	//	mPassBundle = new Bundle();
		
		// Link code with UI components
		Button bttn = (Button) findViewById(R.id.newBttn);
		mLayout = (RelativeLayout) findViewById(R.id.layout);
		mImage = (ImageView) findViewById(R.id.img);
		mViewCount = mLayout.getChildCount();
		
		Utils.receiveClosingBroadcast(this);
		globals = (RatApplication)getApplicationContext();		
		Bitmap temp = BitmapFactory.decodeResource(getResources(),R.drawable.muscles);
		BitmapDrawable bit = new BitmapDrawable(temp);
        Matrix matrix = new Matrix();
        // resize the bit map
        
        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
	    int width = display.getWidth(); 
	    
	    int imgW = bit.getIntrinsicWidth();
	    int imgH = bit.getIntrinsicHeight();
	     
	    DisplayMetrics metrics = new DisplayMetrics();
	    getWindowManager().getDefaultDisplay().getMetrics(metrics);
	    int height = 0;
	    
	    switch (metrics.densityDpi) {
	        case DisplayMetrics.DENSITY_HIGH:
	             height = display.getHeight() - 48;
	            break;
	        case DisplayMetrics.DENSITY_LOW:
	           
	            height = display.getHeight() - 32;
	            break;
	        case DisplayMetrics.DENSITY_MEDIUM:
	            height = display.getHeight() - 24;
	            break;
	        default:
	          // Log.i("display", "Unknown density");
	    }    
	    	    
	     mScaleFactor=1f;
	     Bitmap resizedBitmap;
	     if(width < imgW || height < imgH )
	     {
	    	 mScaleFactor = (float)width / (float)imgW; 
	    	 if(height < imgH)
		     {
	    		 mScaleFactor = (float)height / (float)imgH;
	    	 }
	    	 	matrix.postScale(mScaleFactor,mScaleFactor);
	         // recreate the new Bitmap
	        resizedBitmap = Bitmap.createBitmap(temp, 0, 0,imgW, imgH, matrix, true); 
	     }	
	     else
	     {
	    	 resizedBitmap = temp;
	     }
	     mImage.setBackgroundDrawable(new BitmapDrawable(resizedBitmap));
		mImage.setOnTouchListener(this);
	     
		bttn.setOnClickListener(this);
		
		// Draw points if any
		Bundle bun = new Bundle();
		bun = getIntent().getExtras();
		if( bun != null )
		{
			loadPoints();
			ArrayList<String> points = new ArrayList<String>();
			points = bun.getStringArrayList("points");
			for( String s: points)
			{
				for ( BodyPoint bo: mPoints)
				{
					if( s.equals(bo.bodyPointName))
					{
						mMarkers.add(new Point(bo.x+75,bo.y));
						ImageView img = new ImageView(this);
						img.setBackgroundResource(R.drawable.doc_body_marker);
						LayoutParams Params = new LayoutParams(MarginLayoutParams.WRAP_CONTENT,MarginLayoutParams.WRAP_CONTENT);
						Params.setMargins(bo.x+75, bo.y, 0, 0);
						mLayout.addView(img, Params);
						mLastPoint = new Point(bo.x+75,bo.y );
						
					}
				}
			}
		}
		
	}
	
	@Override
	public void onBackPressed()
	{
		globals.patient.newestConsultation().clearPoints();
		super.onBackPressed();
	}
	
	@Override
	public void onClick(View arg0) 
	{
		if( mViewCount != mLayout.getChildCount())
		{
			loadPoints();
			Intent myIntent = new Intent(BodyPointsActivity.this, SelectionActivity.class);
			
			ArrayList<BodyPoint> temp = new ArrayList<BodyPoint>();
			Calendar now = Calendar.getInstance();
			for(int i=0; i<mMarkers.size(); i++)
			{
				BodyPoint bodyPoint = mPoints.get(findClosest(mMarkers.get(i)));
				if(!temp.contains(bodyPoint))
				{
					temp.add(bodyPoint);
				}
				
			}
			Consultation con = new Consultation(now,temp,"Some Notes");
			globals.patient.consultations.add(con);
			//myIntent.putExtras(mPassBundle);
			startActivity(myIntent);
		}
		else
		{
			Toast.makeText(this, "Please select a body part", Toast.LENGTH_SHORT).show();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu( Menu menu)
	{
		menu.add(1,HOME,0,"Home").setIcon(R.drawable.ic_menu_home);
		menu.add(1,NEW,1,"NEW").setIcon(android.R.drawable.ic_menu_add);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
    {
		Intent myIntent = new Intent();
		switch(item.getItemId())
		{
			case HOME: 
				globals.clearPatient();
				Utils.returnHome(this);
				break;
			case NEW:
				myIntent.setClassName("swin.rat.ui.doctor","swin.rat.ui.doctor.NewPatientActivity");
				startActivity(myIntent);
				break;
			default:
				return false;
		}
		return true;
    }
	
	// Returns the index of the closest body part
    public int findClosest( Point aPoint)
    {
    	if(!mPoints.isEmpty())
    	{
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
    
	
	// Checks if point is near a marker and removes the marker if it is
	private boolean isNearMarker( int touchX, int touchY )
	{
		if( !mMarkers.isEmpty())
		{
			for(int i=0; i<mMarkers.size(); i++)
			{
				double distance = BodyPoint.distanceFrom(touchX, touchY,  mMarkers.get(i));
								
				if( distance < 40 * mScaleFactor )
				{
					mLayout.removeViewAt(i+mViewCount);
					mMarkers.remove(i);
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public boolean onTouch(View view, MotionEvent event) 
	{
		if(event.getAction() == MotionEvent.ACTION_DOWN)
		{
			int x = Math.round(event.getX())+mImage.getLeft();
			int y = Math.round(event.getY())+mImage.getTop();
			if( !isNearMarker(x,y) )
			{
				ImageView img = new ImageView(this);
				img.setBackgroundResource(R.drawable.doc_body_marker);
				LayoutParams Params = new LayoutParams(MarginLayoutParams.WRAP_CONTENT,MarginLayoutParams.WRAP_CONTENT);
				Params.setMargins(x, y, 0, 0);
				mLayout.addView(img, Params);
				mMarkers.add(new Point(x,y));
				mLastPoint = new Point(x,y);
			}
		}
		return false;
	}
	
	/**
	 * Adds the x offset of the imageview due to center positioning
	 * @param aX a x coordinate to scale to the screen
	 * @return new x coordinate
	 */
	private int scaleX( int aX )
	{
		return (int)(aX * mScaleFactor) + mImage.getLeft();
	}
	
	private int scaleY( int aY )
	{
		return (int)(aY * mScaleFactor) + mImage.getTop();
	}
		
	private void loadPoints()
	{
		int [] x = getResources().getIntArray(R.array.x);
		int [] y = getResources().getIntArray(R.array.y);
	       
	    mPoints.add(new BodyPoint("Stomach",scaleX(x[0]),scaleY(y[0])));
	    mPoints.add(new BodyPoint("Upper Leg",scaleX(x[1]),scaleY(y[1])));
	    mPoints.add(new BodyPoint("Ankle",scaleX(x[2]),scaleY(y[2])));
	    mPoints.add(new BodyPoint("Ankle",scaleX(x[3]),scaleY(y[3])));
	    mPoints.add(new BodyPoint("Upper Arm",scaleX(x[4]),scaleY(y[4])));
	    mPoints.add(new BodyPoint("Upper Arm",scaleX(x[5]),scaleY(y[5])));
	}
	
		
}
