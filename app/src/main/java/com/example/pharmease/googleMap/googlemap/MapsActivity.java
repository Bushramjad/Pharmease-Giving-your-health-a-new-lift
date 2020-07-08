package com.example.pharmease.googleMap.googlemap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.pharmease.map.GetNearbyPlacesData;
import com.example.pharmease.pharmacy.AllPharmaciesModel;
import com.example.pharmease.pharmacy.MedicineDataClass;
import com.example.pharmease.pharmacy.pharmacymodel;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.pharmease.R;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import io.paperdb.Paper;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashSet;


public class MapsActivity extends Fragment implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {


    private GoogleMap mMap;
    double latitude;
    double longitude;
    private int PROXIMITY_RADIUS = 10000;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private Marker mCurrLocationMarker;
    private LocationRequest mLocationRequest;
    private Button btnHospital;
    private ArrayList<String> pharmacylist = new ArrayList<>();
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference ref = database.getReference().child("GooglePlacesPharmacy");
    private DatabaseReference pharma = database.getReference().child("pharmacies");
    private TextView pharmacySearch ;



    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("notif", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        Log.d("notif", token);

                    }
                });


        return rootView;
    }
    private static final String[] PHARMACIES = new String[] {
            "Medi Green Pharmacy", "B&S Pharmacy 2", "The Pharmacist Pharmacy", "Medmart Pharmacy", "Suleman Ali shah Pharmacy","Al Khidmat Medical Store & Clinic","Shah medicos Thatta","Zyaan Pharmacy","Spain","Spain","Spain"
    };


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Paper.init(this.requireActivity());

        btnHospital = view.findViewById(R.id.signout);
//        pharmacySearch = view.findViewById(R.id.et_location);


        AutoCompleteTextView SearchTextView = view.findViewById(R.id.searchTextView);

        ArrayAdapter<String> adapt = new ArrayAdapter<String>(this.requireActivity(), R.layout.dropdown, R.id.textAutoComplete, PHARMACIES);
        SearchTextView.setThreshold(1); //will start working from first character
        SearchTextView.setAdapter(adapt);



        SearchTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    String search = SearchTextView.getText().toString();
                    searchByMedicine(search);
                    searchByPharmacy(search);

                    return true;
                }
                return false;
            }

        });

    }

    private void searchByPharmacy(String search)
    {
        mMap.clear();
        Query query = ref.orderByChild("place_name").equalTo(search);
        Log.e("search ", "" + search);


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Log.e("Count ", "" + snapshot.getChildrenCount());
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    NearByPharmacy post = postSnapshot.getValue(NearByPharmacy.class);

                    String placeName = post.getPlace_name();
                    LatLng latLng = new LatLng(post.getLat(), post.getLng());
                    String vicinity = post.getVicinity();

                    showMarkerOnSearch(placeName, vicinity, latLng);

                    Polyline line = mMap.addPolyline(new PolylineOptions()
                            .add(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()), new LatLng(post.getLat(), post.getLng()))
                            .width(5)
                            .color(Color.GRAY));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    private void searchByMedicine(String search) {

        mMap.clear();

        pharma.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                    DatabaseReference a = database.getReference().child("pharmacies").child(postSnapshot.getKey()).child("medicines");
                    a.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot mSnapshot) {

                            for (DataSnapshot postshot : mSnapshot.getChildren()) {

                                MedicineDataClass post = postshot.getValue(MedicineDataClass.class);
                                String name = post.getName();

                                Log.e("search",search);
                                if (search.equals(name)) {
                                    String keypharmacy = postshot.getKey();

                                    DatabaseReference b = database.getReference().child("pharmacies").child(postSnapshot.getKey());

                                    b.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot nSnapshot) {

                                            for (DataSnapshot postSnap : nSnapshot.getChildren()) {

                                                pharmacymodel p = nSnapshot.getValue(pharmacymodel.class);
                                                String pname = p.getName();

                                                if (!pharmacylist.contains(pname)) {
                                                    pharmacylist.add(pname);
                                                }
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                        }
                                    });
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        Log.e("pharmacydata", String.valueOf(pharmacylist));

        for (int i = 0; i < pharmacylist.size(); i++) {
            searchByPharmacy(pharmacylist.get(i));
        }

    }

    private void showMarkerOnSearch(String placeName, String vicinity, LatLng latLng)
    {
        infoWindowAdaptar markerInfoWindowAdapter = new infoWindowAdaptar(getContext());
        InfoWindowData info = new InfoWindowData();
        info.setPlace_name(placeName);
        info.setVicinity(vicinity);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title(placeName + " : " + vicinity);
        Marker m = mMap.addMarker(markerOptions);
        mMap.setInfoWindowAdapter(markerInfoWindowAdapter);
        m.setTag(info);
        m.showInfoWindow();
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
    }


    private boolean CheckGooglePlayServices()
    {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(this.requireActivity());
        if(result != ConnectionResult.SUCCESS)
        {
            if(googleAPI.isUserResolvableError(result))
            {
                googleAPI.getErrorDialog(this.requireActivity(), result, 0).show();
            }
            return false;
        }
        return true;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this.requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }


        btnHospital.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                mMap.clear();
                MarkerOptions markerOptions = new MarkerOptions();

                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        Log.e("Count ", "" + snapshot.getChildrenCount());
                        for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                            NearByPharmacy post = postSnapshot.getValue(NearByPharmacy.class);
                            Log.e("Get Data", post.toString());

                            String placeName = post.getPlace_name();
                            LatLng latLng = new LatLng(post.getLat(), post.getLng());
                            String vicinity = post.getVicinity();

                            infoWindowAdaptar markerInfoWindowAdapter = new infoWindowAdaptar(getContext());
                            InfoWindowData info = new InfoWindowData();
                            info.setPlace_name(placeName);
                            info.setVicinity(vicinity);

                            markerOptions.position(latLng);
                            markerOptions.title(placeName + " : " + vicinity);
                            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.mapbox_marker_icon_default));
                            Marker m = mMap.addMarker(markerOptions);

                            mMap.setInfoWindowAdapter(markerInfoWindowAdapter);

                            m.setTag(info);
//                            m.showInfoWindow();

                            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                            mMap.animateCamera(CameraUpdateFactory.zoomTo(14));

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("The read failed: " + databaseError.getCode());
                    }
                });
            }
        });

    }




    protected synchronized void buildGoogleApiClient()
    {
        mGoogleApiClient = new GoogleApiClient.Builder(this.requireActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this.requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    private String getUrl(double latitude, double longitude, String nearbyPlace)
    {
        StringBuilder googlePlacesUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlacesUrl.append("location=" + latitude + "," + longitude);
        googlePlacesUrl.append("&radius=" + PROXIMITY_RADIUS);
        googlePlacesUrl.append("&type=" + nearbyPlace);
        googlePlacesUrl.append("&sensor=true");
//        googlePlacesUrl.append("&key=" + "AIzaSyCF-c39wcyMQ9myCVzq1TtCF1Bw_K_LwJE");
        googlePlacesUrl.append("&key=" + "AIzaSyAq6r49usffhc7KvK9YW9KNg-TmrBNBDd4");
        Log.d("getUrl", googlePlacesUrl.toString());
        return (googlePlacesUrl.toString());
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onLocationChanged(Location location)
    {
        Log.d("onLocationChanged", "entered");
        mLastLocation = location;
        if (mCurrLocationMarker != null)
        {
            mCurrLocationMarker.remove();
        }
        //Place current location marker
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
//        markerOptions.position(latLng);
//        markerOptions.title("Current Position");
//        markerOptions.icon(BitmapDescriptorFactory.defaultMarker());
//        mCurrLocationMarker = mMap.addMarker(markerOptions);

        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(14));
        Toast.makeText(getActivity(),"Your Current Location", Toast.LENGTH_LONG).show();

        Log.d("onLocationChanged", String.format("latitude:%.3f longitude:%.3f",latitude,longitude));

        //stop location updates
        if (mGoogleApiClient != null)
        {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            Log.d("onLocationChanged", "Removing Location Updates");
        }
        Log.d("onLocationChanged", "Exit");

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public boolean checkLocationPermission()
    {
        //statusCheck();
        if (ContextCompat.checkSelfPermission(this.requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this.requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION))
            {
                ActivityCompat.requestPermissions(this.requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);

            } else
            {
                ActivityCompat.requestPermissions(this.requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else
        {
            return true;
        }
    }


    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this.requireActivity());
        builder.setMessage("Your location seems to be disabled, do you want to enable it to proceed?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    public void onClick(final DialogInterface dialog, final int id)
                    {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener()
                {
                    public void onClick(final DialogInterface dialog, final int id)
                    {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.setTitle("Location is disabled");
        alert.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults)
    {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    if (ContextCompat.checkSelfPermission(this.requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        if (mGoogleApiClient == null)
                        {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

                } else {

                    Toast.makeText(getActivity(), "Permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

        }
    }
}
