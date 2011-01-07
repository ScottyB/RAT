package swin.rat;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class InteractionFlow1Activity extends Activity implements OnTouchListener 
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
    
    private Bundle mBundle;
    private Button bttn;
    
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
       
       mBundle = new Bundle();
            
       mHotspot = BitmapFactory.decodeResource(getResources(), R.drawable.hotspots);
       img1.setBackgroundDrawable(new BitmapDrawable(mHotspot));
       img1.setVisibility(View.INVISIBLE);
      
     
        layout.setOnTouchListener(this);
        
       bttn = (Button) findViewById(R.id.next);
        bttn.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View arg0) 
			{
				Intent lInt = new Intent(InteractionFlow1Activity.this,SelectionActivity.class);
				lInt.putExtras(mBundle);
				startActivity(lInt);
				// OnFinish(); Will close the application when it returns to this screen !!!
			}
        });
   
    }

	@Override
	public boolean onTouch(View v, MotionEvent event) 
	{
		if( mBundle.isEmpty())
			bttn.setVisibility(View.INVISIBLE);
		else
			bttn.setVisibility(View.VISIBLE);
		if(event.getAction() == MotionEvent.ACTION_DOWN)
		{
			img1.setDrawingCacheEnabled(true); 
			mHotspot=Bitmap.createBitmap(img1.getDrawingCache()); 
			img1.setDrawingCacheEnabled(false);
			int colour = mHotspot.getPixel((int)event.getX(),(int)event.getY());
			if(colour == getResources().getColor(mAreas[0]))
			{
				//Arm
				if( mArmR.getVisibility() == View.INVISIBLE &&
						mArmL.getVisibility() == View.INVISIBLE )
				{
					mArmR.setVisibility(View.VISIBLE);
					mArmL.setVisibility(View.VISIBLE);
					mBundle.putString("arm","arm");
				}
				else
				{
					mArmR.setVisibility(View.INVISIBLE);
					mArmL.setVisibility(View.INVISIBLE);
					mBundle.remove("arm");
				}
			}
			else if(colour == getResources().getColor(mAreas[1]))
			{
				// Back
				if( mBack.getVisibility() == View.INVISIBLE  )
				{
					mBack.setVisibility(View.VISIBLE);
				    mBundle.putString("back", "back");
				}
				else
				{
					mBack.setVisibility(View.INVISIBLE);
					mBundle.remove("back");
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
					mBundle.putString("legs", "legs");
				}
				else
				{
					mLegR.setVisibility(View.INVISIBLE);
					mLegL.setVisibility(View.INVISIBLE);
					mBundle.remove("legs");
				}
			}
			else if(colour == getResources().getColor(mAreas[3]))
			{
				// Neck
				if( mNeck.getVisibility() == View.INVISIBLE )
				{
					mNeck.setVisibility(View.VISIBLE);
					mBundle.putString("neck", "neck");
				}
				else
				{
					mNeck.setVisibility(View.INVISIBLE);
					mBundle.remove("neck");
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
					mBundle.putString("thighs","thighs");
				}
				else
				{
					mThighR.setVisibility(View.INVISIBLE);
					mThighL.setVisibility(View.INVISIBLE);
					mBundle.remove("thighs");
				}
			}
			else
			{
				return false;
			}
			return true;
		}
		return false;
	}
	
	
	
	
	
}
