package swin.rat;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class AccessXMLDataActivity extends Activity implements OnClickListener
{
	
	 private Button loadBttn;
	 private ArrayList<Person> people = new ArrayList<Person>();
	 
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        people = new ArrayList<Person>();
        loadBttn = (Button) findViewById(R.id.load);
        loadBttn.setOnClickListener(this);
       
        
        
    }
    
    @Override
	public void onClick(View arg0) 
	{
    	try {
		
			//get a new instance of parser
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();
			XMLReader re = sp.getXMLReader(); 
			PeopleHandler ph = new PeopleHandler();
					
			re.setContentHandler(ph);
			
			InputStream in = this.getResources().openRawResource(R.raw.data);
			InputSource inputsource = new InputSource(in);
			
			
			re.parse(inputsource);
			
			people = (ArrayList<Person>) ph.getPeople();
			
			
			EditText test = (EditText) findViewById(R.id.fname);
			EditText test1 = (EditText) findViewById(R.id.sname);
			EditText test2 = (EditText) findViewById(R.id.age);
			ImageView test3 = (ImageView) findViewById(R.id.img);
			
			Log.i("tag",""+people.size());
			
			test.setText(people.get(0).getfFirstName().toString());
			test1.setText(people.get(0).getfSurname().toString());
			test2.setText(people.get(0).getfAge().toString());
			
			test3.setImageURI(people.get(0).getFsrcImage());
			Log.w("tag", "" + people.get(0).getFsrcImage());
			//Uri path = Uri.parse("android.resource://swin.rat/drawable/image");
			//test3.setImageURI(path);
			
		}catch(SAXException se) {
			Log.e("tag","1");
		}catch(ParserConfigurationException pce) {
			Log.e("tag","2");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("tag","3");
		} 
				
	}
    
    
    public String convertStreamToString(InputStream is)
               throws IOException 
               {
           /*
            * To convert the InputStream to String we use the
            * Reader.read(char[] buffer) method. We iterate until the
            * Reader return -1 which means there's no more data to
            * read. We use the StringWriter class to produce the string.
            */
            if (is != null) 
            {
                Writer writer = new StringWriter();
     
                char[] buffer = new char[1024];
                try {
                    Reader reader = new BufferedReader( new InputStreamReader(is, "UTF-8"));
                    int n;
                    while ((n = reader.read(buffer)) != -1) 
                    {
                        writer.write(buffer, 0, n);
                    }
                } finally {
                   is.close();
                }
                return writer.toString();
            } else {       
                return "";
            }
        }
}
