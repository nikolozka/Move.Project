package com.triskelion.move;
import java.util.ArrayList;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;
import android.location.LocationListener;
import android.location.LocationManager;
import android.content.Context;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends Activity implements LocationListener{
  
  private GoogleMap map;
  private LocationManager locationManager;
  private static final long MIN_TIME = 400;
  private static final float MIN_DISTANCE = 1000;
  private ArrayList<LatLng> locations = new ArrayList<LatLng>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_map);
    map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
            .getMap();
    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE, this); 
    map.setOnMapClickListener(listener);
  }
  
  OnMapClickListener listener = new OnMapClickListener(){

	@Override
	public void onMapClick(LatLng location) {
		SnapToRoad snapper = new SnapToRoad(location);
		snapper.execute();
		
	}
	  
  };

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    return true;
  }
@Override
public void onLocationChanged(Location location) {
	// TODO Auto-generated method stub
    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
    map.addMarker(new MarkerOptions()
    	.position(latLng)
    	.icon(BitmapDescriptorFactory.defaultMarker())
    	.title("Move."));
    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 14);
    map.animateCamera(cameraUpdate);
    locationManager.removeUpdates(this);
	
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


}