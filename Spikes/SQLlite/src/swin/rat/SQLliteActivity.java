package swin.rat;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class SQLliteActivity extends Activity implements OnClickListener 
{
    
    private final String TableName = "PeopleTable";
	private SQLiteDatabase myDB;
    private String Data;
    private TextView text;
	
	/** Called when the activity is first created. */
 	@Override
 	public void onCreate(Bundle savedInstanceState) 
 	{
 		super.onCreate(savedInstanceState);
 		setContentView(R.layout.main);
 		text = (TextView) findViewById(R.id.text);
 		
 		this.deleteDatabase("DatabaseName");
 		final Button button2 = (Button) findViewById(R.id.button2);
 		button2.setOnClickListener(new OnClickListener() {
 		    public void onClick(View v) {
 		    	startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=cxLG2wtE7TM")));
 		       
 		    }
 		});
 		
 	
 		myDB= null;
 		Data="";
 
 		/* Create a Database. */
 		try 
 		{
 			myDB = this.openOrCreateDatabase("DatabaseName", MODE_PRIVATE, null);
 
 			/* Create a Table in the Database. */
 			myDB.execSQL("CREATE TABLE IF NOT EXISTS "  + TableName  + " (Name VARCHAR, Country VARCHAR, Age INT(3));");
 
 			/* Insert data to a Table*/
 			myDB.execSQL("INSERT INTO " + TableName + " (Name, Country, Age)"  + " VALUES ('Saranga', 'New Zealand', 43);");
  			myDB.execSQL("INSERT INTO " + TableName + " (Name, Country, Age)"  + " VALUES ('Robert', 'FarAway', 333);");
 			
 			displayTable();
 		
 		}
 		catch(Exception e) 
 		{
 			Log.e("Error", "Error", e);
 		} 
 		finally 
 		{	
 			if (myDB != null)
 				myDB.close();
 		}
 		
 		final Button button = (Button) findViewById(R.id.button);
 		button.setOnClickListener(this); 
 		
 	}
 	
 	@Override
 	public void onDestroy()
 	{
 		super.onDestroy();
 		myDB.close();
 		this.deleteDatabase("DatabaseName");
 	}

	@Override
	public void onClick(View arg0) {
		    EditText v1 = (EditText)findViewById(R.id.txt1);
		    String value1 = v1.getText().toString();
	    	
		    EditText v2 = (EditText)findViewById(R.id.txt2);
	    	String value2 = v2.getText().toString();
	    	
	    	EditText v3 = (EditText)findViewById(R.id.txt3);
	    	String value3 = v3.getText().toString();

 			myDB = this.openOrCreateDatabase("DatabaseName", MODE_PRIVATE, null);
	    	myDB.execSQL("INSERT INTO " + TableName + " (Name, Country, Age)"  + " VALUES ('"+value1+"', '"+value2+"', "+value3+");");
	    	
	    	displayTable();
	    	myDB.close();
		
	}
		
 	/**
 	 * Database must be open before this method is called. Does not close database either;	
 	 */
 	private void displayTable()
 	{
 		Data = "";
 		Cursor c = myDB.rawQuery("SELECT * FROM " + TableName , null);
 		 
		int Column1 = c.getColumnIndex("Name");
		int Column2 = c.getColumnIndex("Country");
		int Column3 = c.getColumnIndex("Age");

		// Check if our result was valid.
		c.moveToFirst();
		if (c != null) 
		{
			do{
			// Loop through all Results
				Data =Data +c.getString(Column1) + " " + c.getString(Column2) + " " + c.getInt(Column3) + "\n";
		    }while(c.moveToNext());
		}
		text.setText(Data);
	}
 	
 	
}