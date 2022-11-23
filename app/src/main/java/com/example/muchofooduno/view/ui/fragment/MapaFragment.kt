package com.example.muchofooduno.view.ui.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PackageManagerCompat
import androidx.navigation.fragment.findNavController
import com.example.muchofooduno.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.type.LatLng
import java.util.jar.Manifest


class MapaFragment : Fragment(), OnMapReadyCallback {

    private lateinit var googleMap:GoogleMap
    lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth= Firebase.auth
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =inflater.inflate(R.layout.fragment_mapa, container, false)
        val mapaFragment=this.childFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment
        mapaFragment.getMapAsync(this)

        return view
    }

    override fun onMapReady(map: GoogleMap) {
        val colombia= com.google.android.gms.maps.model.LatLng(4.570868,-74.297333)
        map?.let{
            this.googleMap=it
            map.addMarker(MarkerOptions().position(colombia))
        }
        enableLocation()

    }
    private fun islocationPermissionGrated()=ContextCompat.checkSelfPermission(this.requireContext(),
        android.Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED

    @SuppressLint("MissingPermission")
    private fun enableLocation(){
         if(!::googleMap.isInitialized)return
        if(islocationPermissionGrated()){
            googleMap.isMyLocationEnabled=true
        }else{
            requestLocatioPermission()
        }
    }
    companion object{
        const val REQUEST_CODE_LOCATION=0
    }
    private fun requestLocatioPermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(
                this.requireActivity(),android.Manifest.permission.ACCESS_FINE_LOCATION
        )){
            Toast.makeText(context,"Requiere activar permisos en ajustes", Toast.LENGTH_SHORT).show()
        }else{
            ActivityCompat.requestPermissions(this.requireActivity(), arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE_LOCATION)
        }
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            REQUEST_CODE_LOCATION ->
                if(grantResults.isNotEmpty()&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    googleMap.isMyLocationEnabled=true
                }else{
                    Toast.makeText(context,"Para activar localizacion aceptar permisos",Toast.LENGTH_SHORT).show()
                }else->{}
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btm=view.findViewById<BottomNavigationView>(R.id.btmnavigation)
        btm.setOnNavigationItemReselectedListener {
            when(it.itemId){
                R.id.home -> findNavController().navigate(R.id.action_mapaFragment_to_homeFragment)
                R.id.perfil -> findNavController().navigate(R.id.action_mapaFragment_to_platosFragment)
                R.id.cerrar -> {
                    firebaseAuth.signOut()
                    findNavController().navigate(R.id.action_pizzaFragment_to_loginActivity)
                    true
                }
            }
        }
    }
}