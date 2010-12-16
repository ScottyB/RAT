package swin.rat;

//	A spike that uses SharedPreferences to store comma separated value pairs
//
//	The applicaton is a simple dictonary that lets users look up a word
//	and add a definition if the word doesn't already have one

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class FileIOActivity extends Activity
{
	public static final String PREFS_NAME = "MyPrefFile";
	private SharedPreferences settings;
	private EditText txtWord;
	private EditText txtMeaning;
	private Button saveBttn;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
               
        
        settings = getSharedPreferences(PREFS_NAME,0);
        
        // Access views declared in main.xml
        txtWord = (EditText) findViewById(R.id.word);
        txtMeaning = (EditText) findViewById(R.id.meaning);
        saveBttn = (Button) findViewById(R.id.save);
        
        final Button goBttn = (Button) findViewById(R.id.go);
        
        
        goBttn.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View arg0)
			{
				String wordToLookup = txtWord.getText().toString();
				if( wordToLookup.length() != 0)
				{
					if( settings.contains(wordToLookup))
					{
						txtMeaning.setText( settings.getString(wordToLookup, "Error"));
						View bkgrd = findViewById(R.id.screen);
						bkgrd.setBackgroundColor(Color.BLACK);
						saveBttn.setVisibility(View.INVISIBLE);
					}
					else
					{
						View bkgrd = findViewById(R.id.screen);
						int orange = getResources().getColor(R.color.orange);
						bkgrd.setBackgroundColor(orange);
						txtMeaning.setText("");
						saveBttn.setVisibility(View.VISIBLE);
					}
				}
				
			}});

        
        	saveBttn.setOnClickListener(new OnClickListener()
        	{
        		@Override
        		public void onClick(View arg0) {
        			String def = txtMeaning.getText().toString();
        			String wordToLookup = txtWord.getText().toString();
        			if( def.length() != 0 )
        			{
        				// Edit the sharedpreferences file through the 
        				//		SharedPreferences.Editor interface
        				SharedPreferences.Editor editor = settings.edit();
        				editor.putString(wordToLookup, def);
        				editor.commit();
        			}
        			View bkgrd = findViewById(R.id.screen);
					bkgrd.setBackgroundColor(Color.BLACK);
        		}
        	});
        
        // Section of code used to add the X to the Edittext
        Drawable x = getResources().getDrawable(R.drawable.x);
        x.setBounds(0, 0, x.getIntrinsicWidth(), x.getIntrinsicHeight());
        txtWord.setCompoundDrawables(null, null, x, null);
        ImageView crossImageView = new ImageView(this);
        crossImageView.setImageDrawable(x);

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
}
