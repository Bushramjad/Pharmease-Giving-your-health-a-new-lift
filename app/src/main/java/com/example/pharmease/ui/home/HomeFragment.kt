package com.example.pharmease.ui.home

import android.content.Context
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
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.pharmease.*
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import java.io.IOException

class HomeFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener ,  GoogleMap.OnInfoWindowClickListener  {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var map: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this.requireActivity())

        return root
    }



    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
        fun getLaunchIntent(from: Context) = Intent(from, MapsActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }

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
            placeMarkerOnMap(currentLatLng)
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
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))

        val info = InfoWindowData("Pharmacy name", "Pharmacy address")
        val customInfoWindow = infoWindowAdaptar(this.requireActivity())

        map.setInfoWindowAdapter(customInfoWindow)

        val marker = map.addMarker(markerOptions)
        marker.tag = info

        map.moveCamera(CameraUpdateFactory.newLatLng(l1))



        val l2 = LatLng(33.749687, 72.874452)
        map.addMarker(MarkerOptions().position(l2).title("l2"))
        map.moveCamera(CameraUpdateFactory.newLatLng(l2))

        val l3 = LatLng(33.645687, 72.873252)
        map.addMarker(MarkerOptions().position(l3).title("l3"))
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
