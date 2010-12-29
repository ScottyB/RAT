package swin.rat;



import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class TouchDrawActivity extends Activity implements OnTouchListener 
{
   // DrawView drawView;
    private Bitmap mHotspot;
    RelativeLayout layout;    
    private static int [] mAreas = {R.color.Arms, R.color.Back, R.color.Legs,R.color.Neck,R.color.Thighs};
    private ImageView img1;  // Hot spot image
   
    private ImageView mNeck;
    private ImageView mBack;
    private ImageView mLegR;
    private ImageView mLegL;
    private ImageView mArmR;
    private ImageView mArmL;
    private ImageView mThighL;
    private ImageView mThighR;
    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // Connect to all the markers
        mNeck = (ImageView) findViewById(R.id.neck);
        mBack = (ImageView) findViewById(R.id.back);
        mArmR = (ImageView) findViewById(R.id.armR);
        mArmL = (ImageView) findViewById(R.id.armL);
        mThighR = (ImageView) findViewById(R.id.thighR);
        mThighL = (ImageView) findViewById(R.id.thighL);
        mLegR = (ImageView) findViewById(R.id.legR);
        mLegL = (ImageView) findViewById(R.id.legL);
        
       layout = (RelativeLayout) findViewById(R.id.layout);
       img1 = (ImageView) findViewById(R.id.img);
       
       
      layout.setBackgroundResource(R.drawable.human);
       
       //layout.setBackgroundResource( R.drawable.hotspots);
     
       mHotspot = BitmapFactory.decodeResource(getResources(), R.drawable.hotspots);
       img1.setBackgroundDrawable(new BitmapDrawable(mHotspot));
       img1.setVisibility(View.INVISIBLE);
      
      // layout.setBackgroundDrawable( new BitmapDrawable(mHotspot));
        layout.setOnTouchListener(this);
        
        
        
        Log.w("tag","H: ");
        
        // Set full screen view
        
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//
//        drawView = new DrawView(this);
//        setContentView(drawView);
//        drawView.requestFocus();
    }

	@Override
	public boolean onTouch(View v, MotionEvent event) 
	{
		if(event.getAction() == MotionEvent.ACTION_UP)
		{
			// Create marker image
			//img = new ImageView(TouchDrawActivity.this);
			
		//    Drawable mDrawable = (Drawable)getResources().getDrawable(R.drawable.circle);
			//mDrawable.setAlpha(100);
		    //img.setBackgroundDrawable(mDrawable);
						
			// Create image marker to show that this works
			//ImageView img3 = new ImageView(TouchDrawActivity.this);
			//img3.setBackgroundDrawable(mDrawable);
			
			//LayoutParams Params = new LayoutParams(MarginLayoutParams.WRAP_CONTENT,MarginLayoutParams.WRAP_CONTENT);
			//LayoutParams Params1 = new LayoutParams(MarginLayoutParams.WRAP_CONTENT,MarginLayoutParams.WRAP_CONTENT); 
			
			img1.setDrawingCacheEnabled(true); 
			mHotspot=Bitmap.createBitmap(img1.getDrawingCache()); 
			img1.setDrawingCacheEnabled(false);
			//int xPos=0;
			//int yPos=0;
			
			
			
			int colour = mHotspot.getPixel((int)event.getX(),(int)event.getY());
			
			if(colour == getResources().getColor(mAreas[0]))
			{
				
				if( mArmR.getVisibility() == View.INVISIBLE &&
						mArmL.getVisibility() == View.INVISIBLE )
				{
					mArmR.setVisibility(View.VISIBLE);
					mArmL.setVisibility(View.VISIBLE);
				}
				else
				{
					mArmR.setVisibility(View.INVISIBLE);
					mArmL.setVisibility(View.INVISIBLE);
				}
			}
			else if(colour == getResources().getColor(mAreas[1]))
			{
				// Back
				if( mBack.getVisibility() == View.INVISIBLE  )
				{
					mBack.setVisibility(View.VISIBLE);
				}
				else
				{
					mBack.setVisibility(View.INVISIBLE);
				}
			}
			else if(colour == getResources().getColor(mAreas[2]))
			{
				// Legs
				if( mLegR.getVisibility() == View.INVISIBLE &&
						mLegL.getVisibility() == View.INVISIBLE )
				{
					mLegR.setVisibility(View.VISIBLE);
					mLegL.setVisibility(View.VISIBLE);
				}
				else
				{
					mLegR.setVisibility(View.INVISIBLE);
					mLegL.setVisibility(View.INVISIBLE);
				}
			}
			else if(colour == getResources().getColor(mAreas[3]))
			{
				// Neck
				if( mNeck.getVisibility() == View.INVISIBLE )
				{
					mNeck.setVisibility(View.VISIBLE);
				}
				else
				{
					mNeck.setVisibility(View.INVISIBLE);
				}
			}
			else if(colour == getResources().getColor(mAreas[4]))
			{
				// Thighs
				if( mThighR.getVisibility() == View.INVISIBLE  &&
						mThighL.getVisibility() == View.INVISIBLE )
				{
					mThighR.setVisibility(View.VISIBLE);
					mThighL.setVisibility(View.VISIBLE);
				}
				else
				{
					mThighR.setVisibility(View.INVISIBLE);
					mThighL.setVisibility(View.INVISIBLE);
				}
			}
			else
			{
				return false;
			}
			
			Log.i("tag", "Points x:"+ (int)event.getX() +" y:" +(int)event.getY());
			
				
			
			
			return true;
		}
		
		return true;
	}
	
	
	
	
	
}
