package com.triskelion.move;

import java.io.IOException;
import java.io.StringReader;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

public class SimpleXmlPullApp{

	private static String xml; 
	private static String lat;
	private static String lng;
	
	public SimpleXmlPullApp(String string){
		xml=string;
			}
	

    public static LatLng pull ()
        throws XmlPullParserException, IOException
    {
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser xpp = factory.newPullParser();

        xpp.setInput(new StringReader( xml) );
        int eventType = xpp.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
         
        	if(eventType == XmlPullParser.START_TAG) {
        	 if(xpp.getName().equals("end_location")){
        		 lat=xpp.nextText();
        		 lng=xpp.nextText();
        	 }
         } 
        	xpp.next();
         
        }
        return new LatLng(Double.parseDouble(lat),Double.parseDouble(lng));
    }
}