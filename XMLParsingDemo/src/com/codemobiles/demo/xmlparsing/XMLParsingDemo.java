package com.codemobiles.demo.xmlparsing;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class XMLParsingDemo extends Activity {

	private TextView orgXmlTxt;
	private TextView parsedXmlTxt;
	private Button parseBtn;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);

		try {
			setContentView(R.layout.main);
			orgXmlTxt = (TextView) findViewById(R.id.orgXMLTxt);
			parsedXmlTxt = (TextView) findViewById(R.id.parsedXMLTxt);
			parseBtn = (Button) findViewById(R.id.parseBtn);

			orgXmlTxt.setText(getOriginalMyXML());
			parseBtn.setOnClickListener(new View.OnClickListener() {

				public void onClick(View v) {
					try {
						parsedXmlTxt.setText(getParsedMyXML());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						orgXmlTxt.setText(e.getMessage());
					}
				}
			});
		} catch (Exception e) {
			orgXmlTxt.setText(e.getMessage());
		}

	}

	/**
	 * This method is called by OnCreate during startup to show the original XML file content
	 * @return
	 * @throws Exception
	 */
	private String getOriginalMyXML() throws Exception {
	  /* Load xml file being shown from raw resource folder */
    InputStream in = this.getResources().openRawResource(R.raw.myxml); 
		StringBuffer inLine = new StringBuffer();
		InputStreamReader isr = new InputStreamReader(in);
		BufferedReader inRd = new BufferedReader(isr);

		String text;
		while ((text = inRd.readLine()) != null) {
			inLine.append(text);
			inLine.append("\n");
		}
		in.close();
		return inLine.toString();
	}

	/**
	 * This methos is called when the Parsing Button is passed to begin parse XML file content
	 * @return
	 * @throws Exception
	 */
	private String getParsedMyXML() throws Exception {
		StringBuffer inLine = new StringBuffer();
		/* Get a SAXParser from the SAXPArserFactory. */
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();

		/* Get the XMLReader of the SAXParser we created. */
		XMLReader xr = sp.getXMLReader();
		/* Create a new ContentHandler and apply it to the XML-Reader */
		XMLHandler myExampleHandler = new XMLHandler();
		xr.setContentHandler(myExampleHandler);
		/* Load xml file from raw folder*/
    InputStream in = this.getResources().openRawResource(R.raw.myxml); 
    /* Begin parsing */
		xr.parse(new InputSource(in));
		XMLDataSet parsedExampleDataSet = myExampleHandler.getParsedData();
		inLine.append(parsedExampleDataSet.toString());
		in.close();
		return inLine.toString();
	}
}