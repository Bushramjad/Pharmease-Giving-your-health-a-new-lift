package com.example.pharmease.googleMap.googlemap

import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.pharmease.R
import com.facebook.FacebookSdk.getApplicationContext
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.firebase.database.DatabaseReference
import com.schibstedspain.leku.*
import kotlinx.android.synthetic.main.fragment_home.*
import java.io.IOException
import java.util.*


class HomeFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener ,  GoogleMap.OnInfoWindowClickListener  {

    private lateinit var map: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location

    private lateinit var database: DatabaseReference


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_home, container, false)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this.requireActivity())


        Places.initialize(getApplicationContext(), "AIzaSyAtxRdEA6aZp0No21DFqnN3Rd9Ca1jQAn8")
        // Create a new Places client instance.
         var placesClient : PlacesClient = Places.createClient(this.requireActivity())


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


//         val a : () -> Unit = fun CheckGooglePlayServices() {
//            GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
//            var result = googleAPI.isGooglePlayServicesAvailable(this);
//            if(result != ConnectionResult.SUCCESS) {
//                if(googleAPI.isUserResolvableError(result)) {
//                    googleAPI.getErrorDialog(this, result,
//                        0).show();
//                }
//                return false;
//            }
//            return true;
//        }

        signout.setOnClickListener {

//            var Restaurant = "restaurant"
//            Log.d("onClick", "Button is Clicked")
//            map.clear()
//          //  val l = URL(latitude, longitude, Restaurant))
//            val url: String = getUrl(latitude, longitude, Restaurant)
//            val DataTransfer = arrayOfNulls<Any>(2)
//            DataTransfer[0] = map
//            DataTransfer[1] = url
//            Log.d("onClick", url)
//            val getNearbyPlacesData = GetNearbyPlacesData()
//            getNearbyPlacesData.execute(DataTransfer)
//            Toast.makeText(this.requireActivity(), "Nearby Restaurants", Toast.LENGTH_LONG)
//                .show()

            val fields : List<Place.Field> = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG)
            val intent  = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN,fields).build(this.requireActivity())

            startActivityForResult(intent,200)


//
//            try {
//                loadPlacePicker()
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }

        }
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
        private const val PLACE_PICKER_REQUEST = 3

    }



    private fun loadPlacePicker() {

//        val builder = PlacePicker.IntentBuilder()
//
//        try {
//            startActivityForResult(builder.build(this.requireActivity()), PLACE_PICKER_REQUEST)
//        } catch (e: GooglePlayServicesRepairableException) {
//            e.printStackTrace()
//        } catch (e: GooglePlayServicesNotAvailableException) {
//            e.printStackTrace()
//        }

        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location != null) {
                lastLocation = location
                val currentLatLng = LatLng(location.latitude, location.longitude)


                val locationPickerIntent = LocationPickerActivity.Builder()
                    .withLocation(location.latitude, location.longitude)
                    .withGeolocApiKey("<PUT API KEY HERE>")
                    .withSearchZone("es_ES")
//                    .withSearchZone(
//                        SearchZoneRect(
//                            LatLng(26.525467, -18.910366),
//                            LatLng(43.906271, 5.394197)
//                        )
//                    )

                    .withDefaultLocaleSearchZone()
                    .shouldReturnOkOnBackPressed()
                    .withStreetHidden()
                    .withCityHidden()
                    .withZipCodeHidden()
                    .withSatelliteViewHidden()
                    .withGooglePlacesEnabled()
                    .withGoogleTimeZoneEnabled()
                    .withVoiceSearchHidden()
                    .withUnnamedRoadHidden()
                    .build(this.requireActivity())

                startActivityForResult(locationPickerIntent, PLACE_PICKER_REQUEST)
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {


//        if (resultCode == Activity.RESULT_OK && data != null) {
//            Log.d("RESULT****", "OK")
//            if (requestCode == 1) {
//                val latitude = data.getDoubleExtra(LATITUDE, 0.0)
//                Log.d("LATITUDE****", latitude.toString())
//                val longitude = data.getDoubleExtra(LONGITUDE, 0.0)
//                Log.d("LONGITUDE****", longitude.toString())
//                val address = data.getStringExtra(LOCATION_ADDRESS)
//                Log.d("ADDRESS****", address.toString())
//                val postalcode = data.getStringExtra(ZIPCODE)
//                Log.d("POSTALCODE****", postalcode.toString())
//                val bundle = data.getBundleExtra(TRANSITION_BUNDLE)
//                Log.d("BUNDLE TEXT****", bundle.getString("test"))
//                val fullAddress = data.getParcelableExtra<Address>(ADDRESS)
//                if (fullAddress != null) {
//                    Log.d("FULL ADDRESS****", fullAddress.toString())
//                }
//                val timeZoneId = data.getStringExtra(TIME_ZONE_ID)
//                Log.d("TIME ZONE ID****", timeZoneId)
//                val timeZoneDisplayName = data.getStringExtra(TIME_ZONE_DISPLAY_NAME)
//                Log.d("TIME ZONE NAME****", timeZoneDisplayName)
//            }
//            else if (requestCode == 2) {
//                val latitude = data.getDoubleExtra(LATITUDE, 0.0)
//                Log.d("LATITUDE****", latitude.toString())
//                val longitude = data.getDoubleExtra(LONGITUDE, 0.0)
//                Log.d("LONGITUDE****", longitude.toString())
//                val address = data.getStringExtra(LOCATION_ADDRESS)
//                Log.d("ADDRESS****", address.toString())
//                val lekuPoi = data.getParcelableExtra<LekuPoi>(LEKU_POI)
//                Log.d("LekuPoi****", lekuPoi.toString())
//            }
//        }
//        if (resultCode == Activity.RESULT_CANCELED) {
//            Log.d("RESULT****", "CANCELLED")
//        }



    }

    private fun setUpMap() {
        if (ActivityCompat.checkSelfPermission(this.requireActivity(),
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this.requireActivity(),
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
            return
        }

        map.isMyLocationEnabled = true
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->   if (location != null) {
            lastLocation = location
            val currentLatLng = LatLng(location.latitude, location.longitude)
           // placeMarkerOnMap(currentLatLng)
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f))

        }
        }

    }


    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map.uiSettings.isZoomControlsEnabled = false
        map.uiSettings.isMyLocationButtonEnabled = false

        map.setOnMarkerClickListener(this)
        map.setOnInfoWindowClickListener(this)


        val l1 = LatLng(33.648689, 72.873252)
        val markerOptions = MarkerOptions()
        markerOptions.position(l1).title("Location Details")
            .snippet("I am custom Location Marker.")
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))

        val info = InfoWindowData(
            "Pharmacy name",
            "Pharmacy address"
        )
        val customInfoWindow = infoWindowAdaptar(this.requireActivity())

        map.setInfoWindowAdapter(customInfoWindow)

        val marker = map.addMarker(markerOptions)
        marker.tag = info

        map.moveCamera(CameraUpdateFactory.newLatLng(l1))



        val l2 = LatLng(33.749687, 72.874452)
        val m = map.addMarker(MarkerOptions().position(l2).title("l2"))
        m.tag = info
        map.moveCamera(CameraUpdateFactory.newLatLng(l2))

        val l3 = LatLng(33.645687, 72.873252)
       val n = map.addMarker(MarkerOptions().position(l3).title("l3"))
        n.tag = info
        map.moveCamera(CameraUpdateFactory.newLatLng(l3))

        setUpMap()

    }

    override fun onInfoWindowClick(marker: Marker)
    {
        //startActivity(Intent(this,Congratulations::class.java))
        Toast.makeText(activity, "Window in progress!", Toast.LENGTH_SHORT).show()

    }

    override fun onMarkerClick(p0: Marker?): Boolean {
        map.uiSettings.isZoomControlsEnabled = false
        map.uiSettings.isMyLocationButtonEnabled = false

        map.setOnMarkerClickListener(this)

        return false
    }

    private fun getAddress(latLng: LatLng): String {
        val geocoder = Geocoder(this.requireActivity())
        val addresses: List<Address>?
        val address: Address?
        var addressText = ""

        try {
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
            if (null != addresses && !addresses.isEmpty()) {
                address = addresses[0]
                for (i in 0 until address.maxAddressLineIndex) {
                    addressText += if (i == 0) address.getAddressLine(i) else "\n" + address.getAddressLine(i)
                }
            }
        } catch (e: IOException) {
            Log.e("MapsActivity", e.localizedMessage)
        }

        return addressText
    }

    private fun placeMarkerOnMap(location: LatLng) {

        val markerOptions = MarkerOptions().position(location)
        val titleStr = getAddress(location)
        markerOptions.title(titleStr)

        map.addMarker(markerOptions)
    }

}
