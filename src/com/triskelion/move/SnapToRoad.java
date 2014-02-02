package com.triskelion.move;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import com.google.android.gms.maps.model.LatLng;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class SnapToRoad extends AsyncTask<Void, Void, Void> {

private static final String TAG = SnapToRoad.class.getSimpleName();

private double lat;
private double lng;

private double newLat;
private double newLng;

private LatLng latLng;



public SnapToRoad(LatLng location){
	lat=location.latitude;
	lng=location.longitude;
	
}

@Override
protected Void doInBackground(Void... params) {
    Reader rd = null;
    try {
        URL url = new URL("http://maps.google.com/maps/api/directions/xml?origin="+lat+","+lng+"&destination="+lat+","+lng+"&sensor=true");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setReadTimeout(10000 /* milliseconds */);
        con.setConnectTimeout(15000 /* milliseconds */);
        con.connect();
        if (con.getResponseCode() == 200) {

            rd = new InputStreamReader(con.getInputStream());
            StringBuffer sb = new StringBuffer();
            final char[] buf = new char[1024];
            int read;
            while ((read = rd.read(buf)) > 0) {
                sb.append(buf, 0, read);
            }
            
            XmlPullParserFactory xmlFactoryObject = XmlPullParserFactory.newInstance();
            XmlPullParser myParser = xmlFactoryObject.newPullParser();
            myParser.setInput(new StringReader(sb.toString()));
            
            int event = myParser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) 
            {
               String name=myParser.getName();               
                  if(event==XmlPullParser.START_TAG){
                	  if(name.equals("end_location")){
                		  newLat=Double.parseDouble(myParser.nextText());
                          
                          
                      
                      
                      }

                  
               }		 
               event = myParser.next(); 					
            }


            Log.v(TAG, sb.toString());
            
        } 
        con.disconnect();
    } catch (Exception e) {
        Log.e("foo", "bar", e);
    } finally {
        if (rd != null) {
            try {
                rd.close();
            } catch (IOException e) {
                Log.e(TAG, "", e);
            }
        }
    }
    return null;
}

public LatLng getLoc(){
	return latLng;
}

}