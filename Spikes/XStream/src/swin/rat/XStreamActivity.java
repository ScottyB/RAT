package swin.rat;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class XStreamActivity extends Activity
{
	 XStream xstream;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
       
        
        xstream = new XStream(new DomDriver());
        xstream.alias("animal", Animal.class);
        xstream.alias("type", Integer.class);
        read();
        write();
    }
    
    private void read()
    {
    	InputStream ins = getResources().openRawResource(R.raw.test);
    	ObjectInputStream ois = null;
    	ArrayList<Animal> animals = new ArrayList<Animal>();
    	try {
    		ois = xstream.createObjectInputStream(ins);
			   	
	    	
	    	for(;;)
	    		animals.add((Animal)ois.readObject());
	    	
	    	
    	
    	} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (EOFException e)
		{	
			// All the objects have been read
			Log.i("tag", ""+ animals.size());
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			try {
				ois.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
    	
    	
			
		
    }
    
    
    private void write()
	{
    	Animal ani = new Animal("Fish",0,false,true);
       
        FileOutputStream out;
    	String xml = xstream.toXML(ani);
    			
		Log.i("tag","stucked");
		try {
			out = openFileOutput("test.xml",Context.MODE_APPEND);
			
			out.write(xml.getBytes());
			out.close();
		
		} catch (FileNotFoundException e) 
		{
			Log.i("tag","stuck");
			e.printStackTrace();
		} catch (IOException e)
		{
			
			e.printStackTrace();
		}
		
	}
}
