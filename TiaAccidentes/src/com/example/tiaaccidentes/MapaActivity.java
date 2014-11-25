package com.example.tiaaccidentes;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MapaActivity extends Activity  implements 
							GooglePlayServicesClient.ConnectionCallbacks, 
							GooglePlayServicesClient.OnConnectionFailedListener, ExecuteJson{

	
	MapFragment mMapFragment;
	private GoogleMap mMap;
	private LocationClient locationclient;
	List<Marker> markers;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		locationclient = new LocationClient(this, this, this);
		markers = new ArrayList<Marker>();
	}
	
	public void getData()
	{
		try {
			URI add = new URI("https://proyecto-portoo.c9.io/locations.json");
			new JsonParsing(this, this). execute(add);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		setUpMapIfNeeded();
		getData();
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		locationclient.connect();
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		locationclient.disconnect();
		super.onStop();
	}
	
	private void setUpMapIfNeeded() {
		MapFragment fragmap = (MapFragment)getFragmentManager().findFragmentById(R.id.map);
		mMap = fragmap.getMap();
		if (mMap != null) {
	        markers.clear();
	        mMap.clear();
	    }
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "Failed to get position", Toast.LENGTH_LONG).show();
	}

	@Override
	public void onConnected(Bundle arg0) {
		// TODO Auto-generated method stub
		if (locationclient.isConnected()) {
			Location location = locationclient.getLastLocation();
			LatLng actual = new LatLng(location.getLatitude(), location.getLongitude());
			CameraUpdate update = CameraUpdateFactory.newLatLngZoom(actual,12);
			if (mMap!=null)
				mMap.animateCamera(update);
		}

	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
	}

	@Override
	public void JsonParse(JSONArray obj) {
		// TODO Auto-generated method stub
		if (obj != null) {
		if (mMap!=null){
			try {
				Reporte reports[] = new Reporte[obj.length()]; 
				for (int i = 0; i < obj.length(); i++) {
					JSONObject item = obj.getJSONObject(i);
					Reporte re = new Reporte(item.getInt("id"), 
							item.getString("address"), 
							item.getDouble("latitude"), 
							item.getDouble("longitude"), 
							item.getString("fecha"), 
							item.getInt("incidencia"), item.getString("url"));
					reports[i]= re;
				}
				for (int i = 0; i < reports.length; i++) {
					MarkerOptions opt = new MarkerOptions();
					opt.position(new LatLng(reports[i].getLatitude(), reports[i].getLongitude()));
					opt.title(reports[i].getAdress());
					opt.snippet("Fecha: "+reports[i].getTime() + " Incidencia: "+reports[i].getIncidencia());
					switch (reports[i].getIncidencia())
					{
						case NULA: opt.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
								   opt.alpha(0.3f);	break;
						case BAJA: opt.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)); break;
						case MEDIA: opt.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)); break;
						case ALTA: opt.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));break;
					}
					markers.add(mMap.addMarker(opt));
				}
			} catch (JSONException e) {
				Toast.makeText(this, "Failed to parse json: "+e.getMessage(), Toast.LENGTH_LONG).show();
			}
		} else {
			Toast.makeText(this, "Failed to get Map", Toast.LENGTH_LONG).show();
		}
		}
	}
}
