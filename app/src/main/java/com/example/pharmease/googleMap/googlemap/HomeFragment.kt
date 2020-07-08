package com.example.pharmease.googleMap.googlemap

//import com.google.android.libraries.places.api.Places
//import com.google.android.libraries.places.api.model.Place
//import com.google.android.libraries.places.api.net.PlacesClient
//import com.google.android.libraries.places.widget.Autocomplete
//import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import android.Manifest
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.pharmease.R
import com.example.pharmease.map.GetNearbyPlacesData
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.fragment_home.*
import java.io.IOException


//class HomeFragment : Fragment(), OnMapReadyCallback,
//    GoogleMap.OnMarkerClickListener ,
//    GoogleMap.OnInfoWindowClickListener,
//    GoogleApiClient.ConnectionCallbacks,
//    GoogleApiClient.OnConnectionFailedListener,
//    LocationListener {
//
//    private lateinit var mMap: GoogleMap
//    private lateinit var fusedLocationClient: FusedLocationProviderClient
//    private lateinit var lastLocation: Location
//
//    private lateinit var database: DatabaseReference
//    private val PROXIMITY_RADIUS = 10000
//
//    private var latitude = 0.0
//    private var longitude = 0.0
//    private var mCurrLocationMarker: Marker? = null
//
//    var mGoogleApiClient: GoogleApiClient? = null
//    var mLastLocation: Location? = null
//    var mLocationRequest: LocationRequest? = null
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//
//        val root = inflater.inflate(R.layout.fragment_home, container, false)
//
//
//        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
//
//        mapFragment.getMapAsync(this)
//
//        fusedLocationClient =
//            LocationServices.getFusedLocationProviderClient(this.requireActivity())
//
//        return root
//    }
//
//    override fun onMapReady(googleMap: GoogleMap) {
//        mMap = googleMap
//        mMap!!.mapType = GoogleMap.MAP_TYPE_NORMAL
//
//        //Initialize Google Play Services
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//                buildGoogleApiClient()
//                mMap!!.isMyLocationEnabled = true
//            }
//        } else {
//            buildGoogleApiClient()
//            mMap!!.isMyLocationEnabled = true
//        }
//        val btnRestaurant = findViewById<Button>(R.id.btnRestaurant)
//        btnRestaurant.setOnClickListener(object : View.OnClickListener {
//            var Restaurant = "restaurant"
//            override fun onClick(v: View) {
//                Log.d("onClick", "Button is Clicked")
//                mMap!!.clear()
//                val url = getUrl(latitude, longitude, Restaurant)
//                val DataTransfer = arrayOfNulls<Any>(2)
//                DataTransfer[0] = mMap
//                DataTransfer[1] = url
//                Log.d("onClick", url)
//                val getNearbyPlacesData = GetNearbyPlacesData()
//                getNearbyPlacesData.execute(*DataTransfer)
//                Toast.makeText(this@MapsActivity, "Nearby Restaurants", Toast.LENGTH_LONG).show()
//            }
//        })
//        val btnHospital = findViewById<Button>(R.id.btnHospital)
//        btnHospital.setOnClickListener(object : View.OnClickListener {
//            var Hospital = "pharmacy"
//            override fun onClick(v: View) {
//                Log.d("onClick", "Button is Clicked")
//                mMap!!.clear()
//                val url = getUrl(latitude, longitude, Hospital)
//                val DataTransfer = arrayOfNulls<Any>(2)
//                DataTransfer[0] = mMap
//                DataTransfer[1] = url
//                Log.d("onClick", url)
//                val getNearbyPlacesData = GetNearbyPlacesData()
//                getNearbyPlacesData.execute(*DataTransfer)
//                Toast.makeText(this@MapsActivity, "Nearby Hospitals", Toast.LENGTH_LONG).show()
//            }
//        })
//        val btnSchool = findViewById<Button>(R.id.btnSchool)
//        btnSchool.setOnClickListener(object : View.OnClickListener {
//            var School = "school"
//            override fun onClick(v: View) {
//                Log.d("onClick", "Button is Clicked")
//                mMap!!.clear()
//                if (mCurrLocationMarker != null) {
//                    mCurrLocationMarker!!.remove()
//                }
//                val url = getUrl(latitude, longitude, School)
//                val DataTransfer = arrayOfNulls<Any>(2)
//                DataTransfer[0] = mMap
//                DataTransfer[1] = url
//                Log.d("onClick", url)
//                val getNearbyPlacesData = GetNearbyPlacesData()
//                getNearbyPlacesData.execute(*DataTransfer)
//                Toast.makeText(this@MapsActivity, "Nearby Schools", Toast.LENGTH_LONG).show()
//            }
//        })
//    }
//
//    @Synchronized
//    protected fun buildGoogleApiClient() {
//        mGoogleApiClient = GoogleApiClient.Builder(this)
//                .addConnectionCallbacks(this)
//                .addOnConnectionFailedListener(this)
//                .addApi(LocationServices.API)
//                .build()
//        mGoogleApiClient.connect()
//    }
//
//    @SuppressLint("RestrictedApi")
//    override fun onConnected(bundle: Bundle?) {
//        mLocationRequest = LocationRequest()
//        mLocationRequest!!.interval = 1000
//        mLocationRequest!!.fastestInterval = 1000
//        mLocationRequest!!.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this)
//        }
//    }
//
//    private fun getUrl(latitude: Double, longitude: Double, nearbyPlace: String): String {
//        val googlePlacesUrl = StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?")
//        googlePlacesUrl.append("location=$latitude,$longitude")
//        googlePlacesUrl.append("&radius=$PROXIMITY_RADIUS")
//        googlePlacesUrl.append("&type=$nearbyPlace")
//        googlePlacesUrl.append("&sensor=true")
//        googlePlacesUrl.append("&key=" + "AIzaSyATuUiZUkEc_UgHuqsBJa1oqaODI-3mLs0")
//        Log.d("getUrl", googlePlacesUrl.toString())
//        return googlePlacesUrl.toString()
//    }
//
//    override fun onConnectionSuspended(i: Int) {}
//    override fun onLocationChanged(location: Location) {
//        Log.d("onLocationChanged", "entered")
//        mLastLocation = location
//        if (mCurrLocationMarker != null) {
//            mCurrLocationMarker!!.remove()
//        }
//        //Place current location marker
//        latitude = location.latitude
//        longitude = location.longitude
//        val latLng = LatLng(location.latitude, location.longitude)
//        val markerOptions = MarkerOptions()
//        markerOptions.position(latLng)
//        markerOptions.title("Current Position")
//        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
//        mCurrLocationMarker = mMap!!.addMarker(markerOptions)
//
//        //move map camera
//        mMap!!.moveCamera(CameraUpdateFactory.newLatLng(latLng))
//        mMap!!.animateCamera(CameraUpdateFactory.zoomTo(11f))
//        Toast.makeText(this@MapsActivity, "Your Current Location", Toast.LENGTH_LONG).show()
//        Log.d("onLocationChanged", String.format("latitude:%.3f longitude:%.3f", latitude, longitude))
//
//        //stop location updates
//        if (mGoogleApiClient != null) {
//            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this)
//            Log.d("onLocationChanged", "Removing Location Updates")
//        }
//        Log.d("onLocationChanged", "Exit")
//    }
//
//    override fun onConnectionFailed(connectionResult: ConnectionResult) {}
//    fun checkLocationPermission(): Boolean {
//        statusCheck()
//        return if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
//                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), MY_PERMISSIONS_REQUEST_LOCATION)
//            } else {
//                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), MY_PERMISSIONS_REQUEST_LOCATION)
//            }
//            false
//        } else {
//            true
//        }
//    }
//
//    fun statusCheck() {
//        val manager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
//        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//            buildAlertMessageNoGps()
//        }
//    }
//
//    private fun buildAlertMessageNoGps() {
//        val builder = AlertDialog.Builder(this)
//        builder.setMessage("Your location seems to be disabled, do you want to enable it to proceed?")
//                .setCancelable(false)
//                .setPositiveButton("Yes") { dialog, id -> startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)) }
//                .setNegativeButton("No") { dialog, id -> dialog.cancel() }
//        val alert = builder.create()
//        alert.setTitle("Location is disabled")
//        alert.show()
//    }
//
//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
//        when (requestCode) {
//            MY_PERMISSIONS_REQUEST_LOCATION -> {
//                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//                        if (mGoogleApiClient == null) {
//                            buildGoogleApiClient()
//                        }
//                        mMap!!.isMyLocationEnabled = true
//                    }
//                } else {
//                    Toast.makeText(this, "Permission denied", Toast.LENGTH_LONG).show()
//                }
//                return
//            }
//        }
//    }
//
//    companion object {
//        const val MY_PERMISSIONS_REQUEST_LOCATION = 99
//    }
//
//}