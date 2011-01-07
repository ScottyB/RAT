package swin.rat;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ContextMenu;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.TextView;
import android.widget.Toast;

public class MenuActivity extends Activity
{
	static final private int HI= Menu.FIRST +2;
	
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        TextView txt = (TextView)findViewById(R.id.txt);
        registerForContextMenu(txt);
    }
    
    @Override
     public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) 
     	{  
        super.onCreateContextMenu(menu, v, menuInfo);  
            menu.setHeaderTitle("Context Menu");  
             menu.add(0, v.getId(), 0, "Action 1");  
             menu.add(0, v.getId(), 0, "Action 2");  
         }  
       
         @Override  
         public boolean onContextItemSelected(MenuItem item) 
         {  
             if(item.getTitle()=="Action 1"){function1(item.getItemId());}  
             else if(item.getTitle()=="Action 2"){function2(item.getItemId());}  
             else {return false;}  
         return true;  
         }  
           
         public void function1(int id){  
             Toast.makeText(this, "function 1 called", Toast.LENGTH_SHORT).show();  
         }  
         public void function2(int id){  
             Toast.makeText(this, "function 2 called", Toast.LENGTH_SHORT).show();  
         }  
     
         @Override
         public boolean onCreateOptionsMenu(Menu menu) 
         {
        	        	 
             MenuInflater inflater = getMenuInflater();
             inflater.inflate(R.menu.menu, menu);
             return true;
         }
    
         @Override
         public boolean onOptionsItemSelected(MenuItem item) 
         {
        	 
             switch (item.getItemId()) 
             {
                 case R.id.icon:    Toast.makeText(this, "You pressed the Icon!", Toast.LENGTH_LONG).show();
                                     break;
                 case R.id.text:     Toast.makeText(this, "You pressed the text!", Toast.LENGTH_LONG).show();
                                     break;
                 case R.id.icontext: Toast.makeText(this, "You pressed the icon and text!", Toast.LENGTH_LONG).show();
                                     break;
             }
             return true;
         }
   


    
}