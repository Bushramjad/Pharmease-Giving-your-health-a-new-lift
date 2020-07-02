package com.example.pharmease

import android.app.Activity
import android.content.Intent
import android.location.Address
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.gms.maps.model.LatLng
import com.schibstedspain.leku.*
import com.schibstedspain.leku.locale.SearchZoneRect


private const val MAP_BUTTON_REQUEST_CODE = 1
private const val MAP_POIS_BUTTON_REQUEST_CODE = 2

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        


//        val locationPickerIntent = LocationPickerActivity.Builder()
//            .withLocation(41.4036299, 2.1743558)
//            .withGeolocApiKey("<PUT API KEY HERE>")
//            .withSearchZone("es_ES")
//            .withSearchZone(SearchZoneRect(LatLng(26.525467, -18.910366), LatLng(43.906271, 5.394197)))
//            .withDefaultLocaleSearchZone()
//            .shouldReturnOkOnBackPressed()
//            .withStreetHidden()
//            .withCityHidden()
//            .withZipCodeHidden()
//            .withSatelliteViewHidden()
//            .withGooglePlacesEnabled()
//            .withGoogleTimeZoneEnabled()
//            .withVoiceSearchHidden()
//            .withUnnamedRoadHidden()
//            .build(applicationContext)
//
//        startActivityForResult(locationPickerIntent, MAP_BUTTON_REQUEST_CODE)

    }


//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
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
//            } else if (requestCode == 2) {
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
//    }
}
