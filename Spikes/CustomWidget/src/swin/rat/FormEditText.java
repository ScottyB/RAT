package swin.rat;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class FormEditText extends EditText implements TextWatcher
{
	private Drawable drClear;
	private Rect rBounds;
	
	private boolean state; 
	
	static final private boolean INVALID = false;
	static final private boolean VALID = true;
	
	public FormEditText(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		initialize();
	}
	 
	public FormEditText(Context context, AttributeSet attrs)
	{
	    super(context, attrs);
	    initialize();
    }
    
	public FormEditText(Context context) 
    {
        super(context);
        initialize();
    }
	
	private void initialize()
	{
		this.addTextChangedListener(this);
		state = VALID;
	}
	
	
	@Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if(event.getAction() == MotionEvent.ACTION_UP && drClear != null)
        {
            
            final int x = (int)event.getX();
            final int y = (int)event.getY();
            
            //check to make sure the touch event was within the bounds of the drawable
            if(x>=(this.getRight()-rBounds.width()) && x<=(this.getRight()-this.getPaddingRight())
                    && y>=this.getPaddingTop() && y<=(this.getHeight()-this.getPaddingBottom()))
            {
                this.setText("");
                event.setAction(MotionEvent.ACTION_CANCEL);//use this to prevent the keyboard from coming up
            }
        }
        return super.onTouchEvent(event);
    }
	

	
	/**
	 * Called when you want the user to know that they have put in the wrong data
	 * State is either Valid or Invalid
	 */
	public void toggleState()
	{
		if(state == VALID)
		{
			state = INVALID;
			this.setBackgroundDrawable(getResources().getDrawable(android.R.drawable.edit_text));
		}
		else
		{
			state = VALID;
			this.setBackgroundDrawable(getResources().getDrawable(R.drawable.textfield_default));
		}
		
	}
	
	@Override
	public void afterTextChanged(Editable edit)
	{
		if( edit.length() != 0)
		{
			drClear = getResources().getDrawable(android.R.drawable.ic_input_delete);
			setCompoundDrawablesWithIntrinsicBounds(null,null,drClear,null);
			rBounds = drClear.getBounds();
		}
		else
		{
			setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
		}
	}

	// Unused methods that are required to use the TextWatcher interface
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,int arg3) {}
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}
	
}
