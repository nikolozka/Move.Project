package com.triskelion.move;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.android.gms.maps.model.LatLng;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class SnapToRoad extends AsyncTask<Void, Void, Void> {

private static final String TAG = SnapToRoad.class.getSimpleName();

private double lat;
private double lng;



public SnapToRoad(LatLng location){
	lat=location.latitude;
	lng=location.longitude;
	
}

@Override
protected Void doInBackground(Void... params) {
    Reader rd = null;
    try {
        URL url = new URL("http://maps.google.com/maps/api/directions/xml?origin="+lat+",0&destination="+lng+",0&sensor=true");
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
}