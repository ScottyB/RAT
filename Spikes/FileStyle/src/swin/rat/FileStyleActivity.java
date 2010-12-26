package swin.rat;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class FileStyleActivity extends Activity
{
	private String FILENAME = "helloFile";
	private static final String TAG = "FileStyle";
	private TextView txtv;
	private EditText etxt;
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setTheme(android.R.style.Theme_Black);
        
        setContentView(R.layout.main);
        
        //this.setTheme(android.R.style.Theme_Light);
        
        
        txtv = (TextView) findViewById(R.id.textview);
        txtv.setMovementMethod(new ScrollingMovementMethod());
        readFile();
     
        final Button btn = (Button) findViewById(R.id.bttn);
        btn.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View arg0) {
				writeFile();
				readFile();
				etxt.setText("");
			}
        	
        });
        
        final ListView list = (ListView) findViewById(R.id.list);
        list.setOnItemClickListener(new OnItemClickListener()
        {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,long arg3) 
			{
				if( position == 0 )
				{
					Toast.makeText(FileStyleActivity.this, "" + position, Toast.LENGTH_SHORT).show();
				}
				else if(position == 1 )
				{
					FileStyleActivity.this.setTheme(android.R.style.Theme_Black);
				}
				else
				{
					
				}
			}
        	
        });
    }
    
    
    public void readFile() 
    {
    	FileInputStream fis;
    	DataInputStream dis;
    	StringBuilder temp = new StringBuilder();
    	try
    	{
    		fis = openFileInput(FILENAME);
    		dis = new DataInputStream(fis);
    		while(dis.available() != 0)
    		{
    			temp.append(dis.readLine());
    			temp.append("\n");
    		}
    		txtv.setText(temp);
    		fis.close();
    	}
    	catch( FileNotFoundException e)
    	{
    		Log.e(TAG, "File not found for reading from");
    	}
    	catch( IOException e)
    	{
    		Log.e(TAG, "IO Exception - Read file");
    	}
    }
    
    public void writeFile()
    {
    	FileOutputStream fos;
    	try
    	{
    		fos = openFileOutput(FILENAME, Context.MODE_APPEND);
    		etxt = (EditText) findViewById(R.id.text);
    		String string = etxt.getText().toString();
    		string += '\n';
    		fos.write(string.getBytes());
    		fos.close();
    	}
    	catch( FileNotFoundException e)
    	{
    		Log.e(TAG, "File not found for reading from");
    	}
    	catch( IOException e)
    	{
    		Log.e(TAG, "IO Exception - Read file");
    	}
    }
    
    @Override
    public void onDestroy()
    {
    	super.onDestroy();
    	// Called when orientation changes
    }
}
