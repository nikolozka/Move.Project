package com.triskelion.move;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends Activity implements LocationListener{
  
  private GoogleMap map;
  private SeekBar bar;
  private LocationManager locationManager;
  private static final long MIN_TIME = 400;
  private static final float MIN_DISTANCE = 1000;
  private LatLng location;
  private Location lc;
  private ArrayList<LatLng> points = new ArrayList<LatLng>();
  private ArrayList<Marker> markers = new ArrayList<Marker>();
  private Marker userMarker;
  private double Radius;
  private int score=0;
  private boolean zoom = false;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_map);
    map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
            .getMap();
    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    Criteria criteria = new Criteria();
    criteria.setAccuracy(Criteria.ACCURACY_FINE);
    
    locationManager.requestLocationUpdates(locationManager.NETWORK_PROVIDER, 5, 5, this); 
    locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 75, 5, this); 

    //map.setOnMapClickListener(listener);
    bar = (SeekBar) findViewById(R.id.seekBar1);
    bar.setOnSeekBarChangeListener(barListener);
  }
  
  /*OnMapClickListener listener = new OnMapClickListener(){

	@Override
	public void onMapClick(LatLng location) {
		//SnapToRoad snapper = new SnapToRoad(location);
		//snapper.execute();
		
		locations.add(location);
		map.addMarker(new MarkerOptions()
			.position(location)
			.icon(BitmapDescriptorFactory.defaultMarker())
			.title(location.latitude+" "+location.longitude)
		);
		
	}
	  
  };*/
  
  OnSeekBarChangeListener barListener = new OnSeekBarChangeListener(){
	  
	  
	  @Override       
	    public void onStopTrackingTouch(SeekBar seekBar) {      
	        // TODO Auto-generated method stub      
	    }       

	    @Override       
	    public void onStartTrackingTouch(SeekBar seekBar) {     
	        // TODO Auto-generated method stub      
	    }       

	    @Override       
	    public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {     
	        // TODO Auto-generated method stub      
	    	Radius=progress;
	    	
	    }       
	};   
  

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    return true;
  }
  
  public void generate(View view){
	  Button btn = (Button) findViewById(R.id.button1);
	  btn.setActivated(false);
	  double lat = location.latitude;
	  double lon = location.longitude;
	  for(int i=0; i<Radius; i++){
		  double dist = Math.random()*Radius/5000;
		  double angle = Math.random()*360;
		  points.add(new LatLng((lat+(dist*Math.cos(angle))),lon+(dist*Math.sin(angle))));		  
		  Marker marker = map.addMarker(new MarkerOptions()
		  	.position(points.get(i))
			.icon(BitmapDescriptorFactory.defaultMarker())
		);
		  markers.add(marker);
	  }
	  
		  
  }
  
  
  /*private LatLng addOnLine(LatLng ll1, LatLng ll2,double count){  // generally used geo measurement function

	  	double dLat = (ll2.latitude - ll1.latitude);
	    double dLon = (ll2.longitude - ll1.longitude);
	    double alpha = Math.atan(dLon/dLat);
	    
	    double newLat = ll1.latitude+0.003*Math.cos(alpha);
	    double newLon = ll1.longitude+0.003*Math.sin(alpha);
	    
	    return new LatLng(newLat,newLon);
	}*/
  
  /*private double measure(LatLng ll1, LatLng ll2){  // generally used geo measurement function
	    double R = 6378.137d; // Radius of earth in KM
	    double dLat = (ll2.latitude - ll1.latitude) * Math.PI / 180;
	    double dLon = (ll2.longitude - ll1.longitude) * Math.PI / 180;
	    double a = Math.sin(dLat/2) * Math.sin(dLat/2) +  
	    		Math.cos(ll1.latitude * Math.PI / 180) * 
	    		Math.cos(ll2.longitude * Math.PI / 180) *
	    		Math.sin(dLon/2) * Math.sin(dLon/2);
	   	double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
	    double d = R * c;
	    return d * 1000; // meters
	}*/
  
  
  
@Override
public void onLocationChanged(Location location) {
	// TODO Auto-generated method stub
	lc=location;
    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
    this.location=latLng;
    if(userMarker!=null) userMarker.remove();
    userMarker=map.addMarker(new MarkerOptions()
    	.position(latLng)
    	.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
    	.title("Move."));
    CameraUpdate cameraUpdate;
    if(zoom){
    	cameraUpdate= CameraUpdateFactory.newLatLngZoom(latLng, map.getCameraPosition().zoom);
    	}
    else{
        cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 14);
        zoom=true;}

    map.animateCamera(cameraUpdate);
    userMarker.setPosition(this.location);
    
    Context context = getApplicationContext();
	CharSequence text = "score: " + this.location;
	int duration = Toast.LENGTH_SHORT;

	Toast toast = Toast.makeText(context, text, duration);
	toast.show();

    for(int i=0; i<points.size(); i++){
    	if(isInReach(points.get(i))){
    		markers.get(i).remove();
    		markers.remove(i);
    		points.remove(i);
    		score++;
    		TextView view = (TextView)findViewById(R.id.editText1);
    		String sscore="score: "+score;
    		view.setText(sscore);
    	}
    }
	
}
@Override
public void onProviderDisabled(String provider) {
	// TODO Auto-generated method stub
	
}
@Override
public void onProviderEnabled(String provider) {
	// TODO Auto-generated method stub
	
}
@Override
public void onStatusChanged(String provider, int status, Bundle extras) {
	// TODO Auto-generated method stub
	
}

private boolean isInReach(LatLng locs){
	Location loc=new Location("");
	loc.setLatitude(locs.latitude);
	loc.setLongitude(locs.longitude);
	if(lc.distanceTo(loc)<2000){
		return true;				
	}
	else return false;
}


}