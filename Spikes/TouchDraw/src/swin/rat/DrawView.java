package swin.rat;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class DrawView extends View implements OnTouchListener 
{
    private static final String TAG = "DrawView";

    private List<Point> points = new ArrayList<Point>();
    
    private Paint paint = new Paint();
    
    private static final int RADIUS = 10;
    

    public DrawView(Context context) 
    {
        super(context);
        setFocusable(true);
        setFocusableInTouchMode(true);

        this.setOnTouchListener(this);

        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
    }

    @Override
    public void onDraw(Canvas canvas) 
    {
        for (Point point : points) 
        {
            canvas.drawCircle(point.x, point.y, this.RADIUS, paint);
            // Log.d(TAG, "Painting: "+point);
        }
    }
    
    private boolean detectPoint( int cX, int cY )
    {
    	this.setDrawingCacheEnabled(true); 
    	Bitmap b = Bitmap.createBitmap( this.getDrawingCache());   
    	this.setDrawingCacheEnabled(false); 
    	    	      
    	if( b == null )
    	{
    		return false;
    	}
    	else
    	{
    		if( b.getPixel(cX, cY) == Color.BLACK  )
    		{
    			return true;
    		}
    	}
    	return false;
    }
    

    public boolean onTouch(View view, MotionEvent event) 
    {
    	if(event.getAction() == event.ACTION_DOWN)
    	{
    		if( detectPoint((int)event.getX(), (int)event.getY()))
    		{
    			
    		}
    		else
    		{
    			Point point = new Point();
    			point.x = event.getX();
    			point.y = event.getY();
    			points.add(point);
    		}
    		invalidate();
    	}
        return true;
    }
    private class Point
    {
        float x, y;

        @Override
        public String toString() 
        {
            return x + ", " + y;
        }
    }
}

