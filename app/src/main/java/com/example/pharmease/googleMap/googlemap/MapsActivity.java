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
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
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

        AutoCompleteTextView SearchTextView = view.findViewById(R.id.searchTextView);

        ArrayAdapter<String> adapt = new ArrayAdapter<String>(this.requireActivity(), R.layout.dropdown, R.id.textAutoComplete, PHARMACIES);
        SearchTextView.setThreshold(1); //will start working from first character
        SearchTextView.setAdapter(adapt);

        SearchTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    mMap.clear();

                    String search = SearchTextView.getText().toString();
                    searchByMedicine(search);
                    searchByPharmacy(search);

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

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                    NearByPharmacy post = postSnapshot.getValue(NearByPharmacy.class);

                    String placeName = post.getPlace_name();
                    LatLng latLng = new LatLng(post.getLat(), post.getLng());
                    String vicinity = post.getVicinity();

//                    showMarkerOnSearch(placeName, vicinity, latLng);

                    showMarkerOnSearch(placeName, vicinity,post.getLat(), post.getLng());


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



        for (int i = 0; i < pharmacylist.size(); i++) {
            searchByPharmacy(pharmacylist.get(i));
        }

    }

    private void showMarkerOnSearch(String placeName, String vicinity, float lat, float lng)
    {
//        infoWindowAdaptar markerInfoWindowAdapter = new infoWindowAdaptar(getContext());
//        InfoWindowData info = new InfoWindowData();
//        info.setPlace_name(placeName);
//        info.setVicinity(vicinity);
//
//        MarkerOptions markerOptions = new MarkerOptions();
//        markerOptions.position(latLng);
//        markerOptions.title(placeName + " : " + vicinity);
//        Marker m = mMap.addMarker(markerOptions);
//        mMap.setInfoWindowAdapter(markerInfoWindowAdapter);
//
//        m.setTag(info);
//        m.showInfoWindow();
//
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));


        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("www.google.com")
                .appendPath("maps")
                .appendPath("dir")
                .appendPath("")
                .appendQueryParameter("api", "1")
                .appendQueryParameter("destination", lat  + "," + lng);
        String url = builder.build().toString();
        Log.d("Directions", url);
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);

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
