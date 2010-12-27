package swin.rat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


public class AnimationHttpActivity extends Activity
{
	private ProgressDialog dialog;
	private TextView data;
	private ImageView picture;
	private static final String TAG ="tag";
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        this.setTitle("On the way to Web services!!!");
        
        data = (TextView) findViewById(R.id.data);
        picture = (ImageView) findViewById(R.id.image);
               
        dialog = new ProgressDialog(this);
        dialog.setCancelable(true);
        dialog.setMessage("Loading...");
        
        // To change to a bar simply change the passed parameter
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setProgress(0);
        
        dialog.setMax(100);
        dialog.show();
        
        // create a thread for updating the progress bar
        Thread background = new Thread (new Runnable() 
        {
           public void run() 
           {
               try 
               {
                   // enter the code to be run while displaying the progressbar.
                   //
                   // This example is just going to increment the progress bar:
                   // So keep running until the progress value reaches maximum value
                   while (dialog.getProgress()<= dialog.getMax()) 
                   {
                       // wait 500ms between each update
                       Thread.sleep(500);
 
                       // active the update handler
                       progressHandler.sendMessage(progressHandler.obtainMessage());
                  
                   }
               } 
               catch (java.lang.InterruptedException e) 
               {
                   // if something fails do something smart
               }
           }
        });
 
        // start the background thread
        background.start();
        
        executeHttpGet();
        Bitmap temp = DownloadImage("http://www.scottawebsite.com/image.png");
        picture.setImageBitmap(temp);
    }
    // handler for the background updating
    // Handler used to send and process message and runnable objects
    Handler progressHandler = new Handler() 
    {
        public void handleMessage(Message msg) 
        {
        	if(dialog.getProgress() == dialog.getMax())
          	   dialog.dismiss();
        	else
        	dialog.incrementProgressBy(10);
        }
    };
    
    
    public void executeHttpGet()
    {
    	HttpClient client = new DefaultHttpClient();
    	HttpGet request = new HttpGet();
    	
    	try 
    	{
    		
			request.setURI(new URI("http://scottawebsite.com/data.txt"));
		} 
    	catch (URISyntaxException e1) 
    	{
			e1.printStackTrace();
		}
    	try 
    	{
    		HttpResponse response = client.execute(request);
    		
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String line;
			StringBuilder sb =  new StringBuilder();
			while ((line = rd.readLine()) != null) 
			{
					sb.append(line);
			}
			rd.close();
			String contentOfMyInputStream = sb.toString();
			
			data.setText(contentOfMyInputStream);
       	
		} 
    	catch (ClientProtocolException e) 
    	{    		
			e.printStackTrace();
		} 
    	catch (IOException e) 
		{
    		e.printStackTrace();
		}
   }
    
    private InputStream OpenHttpConnection(String urlString)throws IOException
    {
        InputStream in = null;
        int response = -1;
               
        URL url = new URL(urlString); 
        URLConnection conn = url.openConnection();
                 
        try
        {
            HttpURLConnection httpConn = (HttpURLConnection) conn;
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.connect(); 

            response = httpConn.getResponseCode();                 
            if (response == HttpURLConnection.HTTP_OK) 
            {
                in = httpConn.getInputStream();                                 
            }                     
        }
        catch (Exception ex)
        {
            throw new IOException("Error connecting");            
        }
        return in;     
    }

    
    private Bitmap DownloadImage(String URL)
    {        
        Bitmap bitmap = null;
        InputStream in = null;        
        try 
        {
            in = OpenHttpConnection(URL);
            Log.e(TAG,"TEST");
            bitmap = BitmapFactory.decodeStream(in);
            in.close();
        } 
        catch (IOException e1)
        {
            e1.printStackTrace();
        }
        return bitmap;                
    }

    
}
