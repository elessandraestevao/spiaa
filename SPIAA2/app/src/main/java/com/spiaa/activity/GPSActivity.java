package com.spiaa.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.spiaa.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GPSActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);


        //LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        /*if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    public void requestPermissions(@NonNull String[] permissions, int requestCode)
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.

            locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, new LocationListener() {


                @Override
                public void onLocationChanged(Location location) {
                    TextView latitude = (TextView) findViewById(R.id.latitude);
                    TextView longitude = (TextView) findViewById(R.id.longitude);
                    TextView time = (TextView) findViewById(R.id.time);
                    TextView acuracy = (TextView) findViewById(R.id.acuracy);
                    TextView provider = (TextView) findViewById(R.id.provider);

                    if (location != null) {
                        latitude.setText("Latitude: " + location.getLatitude());
                        longitude.setText("Longitude: " + location.getLongitude());
                        acuracy.setText("Precisão: " + location.getAccuracy());

                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                        time.setText("Data: " + sdf.format(location.getTime()) );
                        provider.setText(location.getProvider());
                    }
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {
                    Toast.makeText(getApplicationContext(), "Status alterado", Toast.LENGTH_LONG).show();

                }

                @Override
                public void onProviderEnabled(String provider) {
                    Toast.makeText(getApplicationContext(), "Provider Habilitado", Toast.LENGTH_LONG).show();

                }

                @Override
                public void onProviderDisabled(String provider) {
                    Toast.makeText(getApplicationContext(), "Provider Desabilitado", Toast.LENGTH_LONG).show();

                }
            }, null);



        }*/


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_g, menu);
        return true;
    }

}
