package com.mfreiman.driverapp.controller;

import android.Manifest;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.mfreiman.driverapp.R;
import com.mfreiman.driverapp.controller.Fragments.FragmentEditYourDetails;
import com.mfreiman.driverapp.controller.Fragments.HistoryFragment;
import com.mfreiman.driverapp.controller.Fragments.ListTravelsFragment;
import com.mfreiman.driverapp.model.backend.FactoryMethed;
import com.mfreiman.driverapp.model.entities.Driver;


import java.util.ArrayList;
import java.util.List;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

public class DataActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    final private int REQUEST_MULTIPLE_PERMISSIONS = 124;
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        DriverId = intent.getStringExtra("IDdriver");
        String name = "";
        for (Driver d : FactoryMethed.getManager().getDriverList()) {
            if (d.getId().equals(DriverId))
                name = d.getName();
        }
        tv = findViewById(R.id.textView2);
        tv.setText("Hello " + name + " !");
    }

    String DriverId;
    TextView tv;
    private LocationManager locationManager;
    private LocationListener locationListener;
    Location locationA = new Location("A");
    Location locationB = new Location("B");

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        DriverId = getIntent().getStringExtra("IDdriver");
        AccessContact();
        //= new Location(from);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                locationA.setLatitude(location.getLatitude());
                locationA.setLongitude(location.getLongitude());
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.INTERNET, Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION}, 10);
            }
            return;
        } else
            configureButton();

        final ACProgressFlower dialog = new ACProgressFlower.Builder(this)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .text("Loading")
                .fadeColor(Color.DKGRAY).build();
        dialog.setCanceledOnTouchOutside(true);
        ;

        dialog.show();

        Runnable progressRunnable = new Runnable() {

            @Override
            public void run() {
                dialog.cancel();

                locationB.setLongitude(locationA.getLongitude());
                locationB.setLatitude(locationA.getLatitude());
                Intent intent = new Intent(getBaseContext(), MyService.class);
                intent.putExtra("Latitude", String.valueOf(locationB.getLatitude()));
                intent.putExtra("Longitude", String.valueOf(locationB.getLongitude()));
                intent.putExtra("IdOfDriver", String.valueOf(locationB.getLongitude()));
                startService(intent);
            }
        };
        Handler pdCanceller = new Handler();
        pdCanceller.postDelayed(progressRunnable, 4000);

        String name = "";
        for (Driver d : FactoryMethed.getManager().getDriverList()) {
            if (d.getId().equals(DriverId))
                name = d.getName();
        }
        tv = findViewById(R.id.textView2);
        tv.setText("Hello " + name + " !");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    {
                                        locationB.setLongitude(locationA.getLongitude());
                                        locationB.setLatitude(locationA.getLatitude());
                                    }
                                    handler.postDelayed(this, 25000);
                                }
                            }
                , 15000);
    }

    @Override
    protected void onStart() {
        super.onStart();
        DriverId = getIntent().getStringExtra("IDdriver");
        String name = "";
        for (Driver d : FactoryMethed.getManager().getDriverList()) {
            if (d.getId().equals(DriverId))
                name = d.getName();
        }
        tv = findViewById(R.id.textView2);
        tv.setText("Hello " + name + " !");


    }


    private void configureButton() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        locationManager.requestLocationUpdates("gps", 5000, 0, locationListener);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 10:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    configureButton();
                return;
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.data, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_List_travel_layout) {

            final ACProgressFlower dialog = new ACProgressFlower.Builder(this)
                    .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                    .themeColor(Color.WHITE)
                    .text("Loading")
                    .fadeColor(Color.DKGRAY).build();
            dialog.setCanceledOnTouchOutside(true);


            FragmentManager fragmentManager = getFragmentManager();
            ListTravelsFragment fragment = new ListTravelsFragment();
            Bundle bundle = new Bundle();
            bundle.putDouble("Latitude", locationB.getLatitude());
            bundle.putDouble("Longitude", locationB.getLongitude());
            bundle.putString("DriverId", DriverId);
            fragment.setArguments(bundle);
            dialog.show();

            final FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.content_frame, fragment);

            Runnable progressRunnable = new Runnable() {

                @Override
                public void run() {


                    dialog.cancel();
                    transaction.commit();

                }
            };

            Handler pdCanceller = new Handler();
            pdCanceller.postDelayed(progressRunnable, 4000);

        } else if (id == R.id.nav_History) {

            final ACProgressFlower dialog = new ACProgressFlower.Builder(this)
                    .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                    .themeColor(Color.WHITE)
                    .text("Loading")
                    .fadeColor(Color.DKGRAY).build();
            dialog.setCanceledOnTouchOutside(true);
            ;

            HistoryFragment fragment_ = new HistoryFragment();
            dialog.show();
            FragmentManager fragmentManager = getFragmentManager();
            Bundle bundle = new Bundle();
            bundle.putString("IdDriver", DriverId);

            fragment_.setArguments(bundle);
            final FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.content_frame, fragment_);

            Runnable progressRunnable = new Runnable() {

                @Override
                public void run() {
                    dialog.cancel();
                    transaction.commit();

                }

            };
            Handler pdCanceller = new Handler();
            pdCanceller.postDelayed(progressRunnable, 4000);


        } else if (id == R.id.nav_EditYourDetails) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentEditYourDetails fragment = new FragmentEditYourDetails();
            Bundle bundle = new Bundle();
            Driver d = FactoryMethed.getManager().getDriver(DriverId);


            bundle.putString("PhoneNumber", d.getCell());
            bundle.putString("Email", d.getEmail());
            bundle.putString("id", d.getId());
            bundle.putString("name", d.getName());
            fragment.setArguments(bundle);
            final FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.content_frame, fragment);
            transaction.commit();


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void AccessContact()
    {
        List<String> permissionsNeeded = new ArrayList<String>();
        final List<String> permissionsList = new ArrayList<String>();
        if (!addPermission(permissionsList, Manifest.permission.READ_CONTACTS))
            permissionsNeeded.add("Read Contacts");
        if (!addPermission(permissionsList, Manifest.permission.WRITE_CONTACTS))
            permissionsNeeded.add("Write Contacts");
        if (permissionsList.size() > 0) {
            if (permissionsNeeded.size() > 0) {
                String message = "You need to grant access to " + permissionsNeeded.get(0);
                for (int i = 1; i < permissionsNeeded.size(); i++)
                    message = message + ", " + permissionsNeeded.get(i);
                showMessageOKCancel(message,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                                        REQUEST_MULTIPLE_PERMISSIONS);
                            }
                        });
                return;
            }

            requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                    REQUEST_MULTIPLE_PERMISSIONS);
            return;

        }
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean addPermission(List<String> permissionsList, String permission) {
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);

            if (!shouldShowRequestPermissionRationale(permission))
                return false;
        }
        return true;
    }
    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(DataActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
}