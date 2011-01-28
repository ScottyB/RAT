package swin.rat;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.CompoundButton;
import android.widget.ToggleButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class WebViewsActivity extends Activity
{
	private WebView web;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    
        web = (WebView)findViewById(R.id.web);
        
        ToggleButton  but = (ToggleButton) findViewById(R.id.but);
        web.loadUrl("http://douglasfils.blogspot.com/2007/07/terminal-shell-in-eclipse.html");
        but.setOnCheckedChangeListener( new OnCheckedChangeListener()
        {

			@Override
			public void onCheckedChanged(CompoundButton bttn, boolean state) {
				if (!bttn.isChecked())
					 web.loadUrl("http://douglasfils.blogspot.com/2007/07/terminal-shell-in-eclipse.html");
				else
				{
					InputStream in = null;
					ByteArrayOutputStream out = new ByteArrayOutputStream();
					AssetManager manager = getAssets();
					try {
						in = manager.open("test.html");
						byte [] b = new byte[1024];
						int len;
						while( (len = in.read(b)) != -1 )
						{
							out.write(b,0,len);
						}
						out.close();
						in.close();
									
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
					web.loadData(out.toString(),"text/html", "utf-8");
				}
				
			}
        	
        });
       
    }
    
    
}
