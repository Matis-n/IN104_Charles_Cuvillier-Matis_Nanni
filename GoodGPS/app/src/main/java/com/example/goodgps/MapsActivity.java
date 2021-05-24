// Copyright 2020 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.example.goodgps;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import com.google.android.gms.maps.GoogleMap.OnMyLocationClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;

import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.Polyline;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.os.Looper;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


/**
 * This demo shows how GMS Location can be used to check for changes to the users location.  The
 * "My Location" button uses GMS Location to set the blue dot representing the users location.
 * Permission for {@link android.Manifest.permission#ACCESS_FINE_LOCATION} is requested at run
 * time. If the permission has not been granted, the Activity is finished with an error message.
 */
public class MapsActivity extends AppCompatActivity
        implements
        OnMyLocationButtonClickListener,
        OnMyLocationClickListener,
        OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback,
OnMapLongClickListener{

    /**
     * Request code for location permission request.
     *
     * @see #onRequestPermissionsResult(int, String[], int[])
     */
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    /**
     * Flag indicating whether a requested permission has been denied after returning in
     * {@link #onRequestPermissionsResult(int, String[], int[])}.
     */
    private boolean permissionDenied = false;

    private GoogleMap map;
    private Button itinaryButton;
    /**True = allumé = trajet en hélico*/
    private Switch helicoSwitch;
    private List markersList = new ArrayList();
    private Marker marker;
    private TextView modeText;
    private boolean switchState=false;

    FusedLocationProviderClient fusedLocationProviderClient;
    LocationRequest locationRequest;

    Marker userLocationMarker;

    private static final String TAG = "MapsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        itinaryButton=(Button) findViewById(R.id.button2); //bouton démarrer l'itinéraire
        itinaryButton.setEnabled(false);
        helicoSwitch=(Switch) findViewById(R.id.darkSwitch); //button to switch between helico and car
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        modeText=(TextView) findViewById(R.id.modeText);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        locationRequest = LocationRequest.create();
        locationRequest.setInterval(500);
        locationRequest.setFastestInterval(500);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setOnMyLocationButtonClickListener(this);
        map.setOnMyLocationClickListener(this);
        enableMyLocation();
        map.setOnMapLongClickListener(this);


        /**True = allumé = trajet en hélico*/
        helicoSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be true if the switch is in the On position
                switchState=isChecked;
                Log.d("Switch",Boolean.toString(switchState));
                if (switchState==true){modeText.setText("Mode : Hélicoptère");}
                else{modeText.setText("Mode : Voiture ma gueule");}
            }
        });
        itinaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // The user just clicked the button 'Démarrer l'itinéraire'
                @SuppressLint("MissingPermission") Task<Location> locationTask = fusedLocationProviderClient.getLastLocation();

                android.view.animation.Animation animation = AnimationUtils
                        .loadAnimation(MapsActivity.this, R.anim.bounce);
                itinaryButton.startAnimation(animation);

                if(switchState==true){
/**Mode Hélicoptère*/
    locationTask.addOnSuccessListener(new OnSuccessListener<Location>() {
    @Override
    public void onSuccess(Location location) {
        LatLng userlatlng = new LatLng(location.getLatitude(), location.getLongitude());
        map.clear();
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(userlatlng)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.helico))
                .anchor((float) 0.5, (float) 0.5);
        userLocationMarker = map.addMarker(markerOptions);
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(userlatlng, 13));

        marker = map.addMarker(new MarkerOptions()
                .position(new LatLng((double) markersList.get(0), (double) markersList.get(1)))
                .title("destination")
                .draggable(false)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.heliport2)));
        Polyline red = map.addPolyline(new PolylineOptions()
                .color(Color.BLUE)
                .width(6)
                .add(new LatLng((double) markersList.get(0), (double) markersList.get(1)), userlatlng));

    }
});




            }
 /** Mode voiture*/
else{
    locationTask.addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        LatLng userlatlng = new LatLng(location.getLatitude(), location.getLongitude());
                        Log.d("userLatlng",userlatlng.toString());
                        map.clear();
                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(userlatlng)
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.simple_travel_car_top_view))
                                .anchor((float) 0.5, (float) 0.5);
                        userLocationMarker = map.addMarker(markerOptions);
                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(userlatlng, 13));

                        marker = map.addMarker(new MarkerOptions()
                                .position(new LatLng((double) markersList.get(0), (double) markersList.get(1)))
                                .title("destination")
                                .draggable(false)
                               // .icon(BitmapDescriptorFactory.fromResource(R.drawable.heliport2))
                                );
                        drawPolylines(userlatlng);

                    }
                });


                }

            }
        } );


    };


    private void drawPolylines(LatLng userlatLng) {

        // Checks, whether start and end locations are captured
        // Getting URL to the Google Directions API



        String url = getDirectionsUrl(userlatLng,new LatLng((double)markersList.get(0), (double)markersList.get(1)));
        Log.d("url", url + "");
        DownloadTask downloadTask = new DownloadTask();
        // Start downloading json data from Google Directions API
        downloadTask.execute(url);
    }
    private class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {

            String data = "";

            try {
                data = downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();


            parserTask.execute(result);

        }
    }


    /**
     * A class to parse the Google Places in JSON format
     */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {

            Log.d("result", result.toString());
            ArrayList points = null;
            PolylineOptions lineOptions = null;

            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList();
                lineOptions = new PolylineOptions();

                List<HashMap<String, String>> path = result.get(i);

                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                lineOptions.addAll(points);
                lineOptions.width(12);
                lineOptions.color(Color.RED);
                lineOptions.geodesic(true);

            }

// Drawing polyline in the Google Map for the i-th route
            map.addPolyline(lineOptions);
        }
    }

    private String getDirectionsUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        //API key
        String key = "key="+ "AIzaSyB3k2eMRj6q1cQ4zbqpnt19p6dQonhALow";
        // Sensor enabled
        String sensor = "sensor=false";
        String mode = "mode=driving";
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor + "&" + mode + "&" + key;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters ;


        return url;
    }

    /**
     * A method to download json data from url
     */
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.connect();

            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();
            Log.d("data", data);

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }


    LocationCallback locationCallback = new LocationCallback(){
        @Override
        public void onLocationResult(LocationResult locationResult){
            super.onLocationResult(locationResult);
            Log.d(TAG,"onLocationResult: " + locationResult.getLastLocation());
            if (map != null){
                setUserLocationMarker(locationResult.getLastLocation());
            }
        }
    };

    private void setUserLocationMarker(Location location){
        LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
        if (userLocationMarker == null){
            //create a new marker
            MarkerOptions markerOptions = new MarkerOptions();
            if (switchState==true){
                markerOptions.position(latLng)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.helico))
                        .anchor((float) 0.5,(float) 0.5);
            }
            else{
                markerOptions.position(latLng)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.simple_travel_car_top_view))
                    .anchor((float) 0.5,(float) 0.5);}

            userLocationMarker = map.addMarker(markerOptions);
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,13));
        } else {
            //use the old marker
            userLocationMarker.setPosition((latLng));

            //map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,13));
        }
    }

    @SuppressLint("MissingPermission")
    protected void startLocationUpdates(){
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
    }

    protected void stopLocationUpdates(){
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }

    @Override
    protected void onStart(){
        super.onStart();
        startLocationUpdates();
    }

    @Override
    protected void onStop(){
        super.onStop();
        stopLocationUpdates();
    }

    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if (map != null) {
                map.setMyLocationEnabled(true);
            }
        } else {
            // Permission to access the location is missing. Show rationale and request permission
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        }
    }


/** Quand on clique longtemps sur la map, on affiche un marqueur de descritpion lat,long
  */

    @Override
    public void onMapLongClick(LatLng latLng) {

        if (marker != null){
            marker.remove();
            markersList.remove(1);
            markersList.remove(0);
        }
        marker = map.addMarker(new MarkerOptions()
                .position(new LatLng(latLng.latitude, latLng.longitude))
                .title(String.valueOf(latLng.latitude)+", "+String.valueOf(latLng.longitude))
                .draggable(false)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.heliport2)));

        markersList.add(latLng.latitude);
        markersList.add(latLng.longitude);
        itinaryButton.setEnabled(true);

    }



    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation bouton a été cliqué", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "Je suis à :\n" + location, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults, Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else {
            // Permission was denied. Display an error message
            // Display the missing permission error dialog when the fragments resume.
            permissionDenied = true;
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (permissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            permissionDenied = false;
        }
    }

    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     */
    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getSupportFragmentManager(), "dialog");
    }

}

