package br.com.secompufscar.secomp_ufscar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    //TODO:LENTIDAO, VERIFICAÇÃO DO CRASH E VERIFICAR SE A PESSOA ESTÁ UTILIZANDO DADOS OU NÃO ANTES DE MOSTRAR DIALOG, ERRO COM PERMISSOES

    private static final String TAG = "MapsActivity";
    private GoogleMap mMap;
    private LocationManager locationManager;
    private AlertDialog.Builder alertBuilder;
    private int Local = 5;
    private int Nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        alertBuilder = new AlertDialog.Builder(MapsActivity.this);

        alertBuilder.setMessage("Deseja usar dados móveis para obter sua localização?");

        alertBuilder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Nav = 1;
            }
        });

        alertBuilder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Nav = 0;
            }
        });

        AlertDialog dialog = alertBuilder.create();
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);
                mapFragment.getMapAsync(MapsActivity.this);

            }
        });
        dialog.show();



        Intent intent = getIntent();
        if (intent != null) {
            Bundle params = intent.getExtras();
            if (params != null) {
                Local = params.getInt("Local");
            }
        }

        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                alertBuilder = new AlertDialog.Builder(MapsActivity.this);

                alertBuilder.setMessage("Deseja usar dados móveis para obter sua localização?");

                alertBuilder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Nav = 1;
                    }
                });

                alertBuilder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Nav = 0;
                    }
                });
                AlertDialog dialog = alertBuilder.create();
                dialog.show();
            }
        }, 4000);*/
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {


        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (Nav == 1) {
            try {
                //locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
                //mMap = googleMap;

                Criteria criteria = new Criteria();

                //locationManager.getBestProvider(criteria, true);
                locationManager.getAllProviders();
                locationManager.requestLocationUpdates(locationManager.getBestProvider(criteria, true),3000, 10, this);

                //botoes de zoom e sua localizacao
                //mMap.getUiSettings().setZoomControlsEnabled(true);
                mMap.setMyLocationEnabled(true);
            } catch (SecurityException ex) {
                Log.e(TAG, "Error while acquiring location", ex);
            }
        } else {
            locationManager.getProvider(LocationManager.GPS_PROVIDER);
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 10, this);
        }

        LatLng DC = new LatLng(-21.979509, -47.880528);
        LatLng BentoPrado = new LatLng(-21.983667, -47.881668);
        LatLng Centro_UFSCar = new LatLng(-21.984163, -47.880243);


        mMap.addMarker(new MarkerOptions().position(DC).title("Departamento de Computação"));
        mMap.addMarker(new MarkerOptions().position(BentoPrado).title("Anfiteatro Bento Prado Júnior"));


        switch(Local){
            case 0:
                mMap.moveCamera(CameraUpdateFactory.newLatLng(Centro_UFSCar));
                mMap.moveCamera(CameraUpdateFactory.zoomTo(15));
                Toast.makeText(getBaseContext(),"Localização não encontrada", Toast.LENGTH_SHORT).show();
                break;
            case 1:
                mMap.moveCamera(CameraUpdateFactory.newLatLng(DC));
                mMap.moveCamera(CameraUpdateFactory.zoomTo(17));
                break;
            case 2:
                mMap.moveCamera(CameraUpdateFactory.newLatLng(BentoPrado));
                mMap.moveCamera(CameraUpdateFactory.zoomTo(17));
                break;
            case 5:
                mMap.moveCamera(CameraUpdateFactory.newLatLng(Centro_UFSCar));
                mMap.moveCamera(CameraUpdateFactory.zoomTo(15));
                break;
        }

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
