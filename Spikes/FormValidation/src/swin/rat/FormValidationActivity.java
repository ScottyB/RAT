package swin.rat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.view.View.OnClickListener;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


import android.widget.Toast;


public class FormValidationActivity extends Activity implements OnClickListener
{
	private Pattern pattern;
	private Matcher matcher;
	private static final String EMAIL_PATTERN ="^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	private EditText txtWord;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        txtWord = (EditText) findViewById(R.id.num);
        txtWord.setBackgroundResource(R.drawable.textfield_selected);
       
        Button bttn = new Button(this);
        bttn.setText("Hello");
        
        
        
        
        EditText txt = (EditText) findViewById(R.id.email);
        txt.setOnClickListener(this); 
       
        
        Drawable x = getResources().getDrawable(android.R.drawable.ic_input_delete);
        
        ImageView crossImageView = new ImageView(this);
        crossImageView.setImageDrawable(x);
        txtWord.setCompoundDrawablesWithIntrinsicBounds(null, null, x, null);
        //add functionality to imageview and editext to clear text.
        crossImageView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // clear edit text
                txtWord.setText("");
            }

        });
        
        txtWord.setOnClickListener(new View.OnClickListener() 
        {
            public void onClick(View v) 
            {
            	
               txtWord.setText("");
            }

        });
    }
    
    public boolean validateEmail(final String text)
    {
    	pattern = Pattern.compile(EMAIL_PATTERN);
    	matcher = pattern.matcher(text);
    	return matcher.matches();
    }

	
	@Override
	public void onClick(View v) 
	{

		String txtEmail = ((EditText)v).getText().toString();
		Log.i("tag",":" + txtEmail);
		if( !validateEmail(txtEmail))
		{
			Toast.makeText(this, "Invalid Email address", 1).show();
			//NinePatchDrawable dr = (NinePatchDrawable)getResources().getDrawable(R.drawable.border4);
			
			//((EditText)v).setBackgroundDrawable(dr);
			
		}
		// TODO Auto-generated method stub
		
	}
}
