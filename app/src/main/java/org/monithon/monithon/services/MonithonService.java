package org.monithon.monithon.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONException;
import org.json.JSONObject;
import org.monithon.monithon.events.ProjectsFoundEvent;

import de.greenrobot.event.EventBus;

public class MonithonService extends Service {

    private EventBus eventBus =  EventBus.getDefault();
    public MonithonService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    LocationManager locationManager;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

        return super.onStartCommand(intent, flags, startId);
    }

    // Acquire a reference to the system Location Manager

    // Define a listener that responds to location updates
    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            // Called when a new location is found by the network location provider.
            makeUseOfNewLocation(location);
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {}

        @Override
        public void onProviderEnabled(String provider) {}

        @Override
        public void onProviderDisabled(String provider) {}
    };

    private void makeUseOfNewLocation(Location location) {
        AQuery a = new AQuery(this);
        String url = "http://monithon.org/projects?lonlat="+location.getLongitude()+","+location.getLatitude();
        a.ajax(url, JSONObject.class, new AjaxCallback<JSONObject>(){
            @Override
            public void callback(String url, JSONObject object, AjaxStatus status) {
                Log.d("CALLBACK", object.toString());
                try {
                    if(object.getInt("count") > 0){
                        eventBus.post(new ProjectsFoundEvent(object));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
