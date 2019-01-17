package com.mfreiman.driverapp.controller;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mfreiman.driverapp.R;

import com.mfreiman.driverapp.model.backend.FactoryMethed;
import com.mfreiman.driverapp.model.entities.Ride;
import com.mfreiman.driverapp.model.datasource.FireBase_DBManager;
import java.util.ArrayList;

import static android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP;
import static com.mfreiman.driverapp.controller.AppChannels.FOREGROUND_CHANNEL_ID;
import static com.mfreiman.driverapp.controller.AppChannels.NEW_RIDE_CHANNEL_ID;

public   class   MyService extends Service {

    Context context;
    FireBase_DBManager manager;
    public NotificationManagerCompat notificationManager;
    private LocationManager locationManager;
  static   String IdOfDriver;
    private LocationListener locationListener;
    public static ArrayList<Ride> OldList = new ArrayList<Ride>();
    public static ArrayList<Ride> NewList = new ArrayList<Ride>();
    Location locationB = new Location("B");//= new Location(to);
    Location locationA = new Location("A");

    public void onCreate() {
        super.onCreate();
        OldList.clear();
        notificationManager = NotificationManagerCompat.from(this);
        CreateForegroundNotification();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        IdOfDriver = intent.getStringExtra("IdOfDriver");
        locationA.setLatitude(Double.valueOf(intent.getStringExtra("Latitude")));
        locationA.setLongitude(Double.valueOf(intent.getStringExtra("Longitude")));
        NewList.clear();
        NewList = FactoryMethed.getManager().getListOfRelevantRides(locationA);
        for (Ride ride : NewList) {
            OldList.add(ride);
        }



        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    NewList = FactoryMethed.getManager().getListOfRelevantRides(locationA);
                                    for (Ride r : NewList) {
                                        boolean temp = false;
                                        for (Ride old : OldList)
                                            if (old.getKey().equals(r.getKey().toString()) == true)
                                                temp = true;

                                        if (temp == false) {

                                            AlertOnNewRide();
                                            OldList.clear();
                                            for (Ride r_ : NewList)
                                                OldList.add(r_);
                                            break;
                                        }
                                    }
                                    handler.postDelayed(this, 10000);
                                }
                            }
                , 20000);

        return START_STICKY;
    }

    private void AlertOnNewRide() {


        Intent notificationIntent = new Intent(MyService.this, MainActivity.class);


        TaskStackBuilder taskStackBuilder = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            taskStackBuilder = TaskStackBuilder.create(MyService.this);

            taskStackBuilder.addParentStack(MainActivity.class);
            taskStackBuilder.addNextIntent(notificationIntent);

            PendingIntent pendingIntent = taskStackBuilder.
                    getPendingIntent(0, 0);

            Notification newRideNotification = new NotificationCompat.Builder(MyService.this, NEW_RIDE_CHANNEL_ID)
                    .setContentTitle("New travel")
                    .setContentText("There is a new relevant travel").setSmallIcon(R.drawable.ic_local_taxi_black_24dp)
                    .setAutoCancel(true)
                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                    .setPriority(Notification.PRIORITY_MAX)
                    .setDefaults(Notification.DEFAULT_VIBRATE)
                    .setContentIntent(pendingIntent)
                    .build();

            notificationManager.notify(2, newRideNotification);
        }
    }

    public void CreateForegroundNotification() {
        Intent notificationIntent = new Intent(this, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);
        Intent exitApplicationIntent = new Intent(this, ExitApp.class);
        PendingIntent exitApplicationPendingIntent = PendingIntent.getActivity(this, 0,
                exitApplicationIntent, 0);
        Notification notification = new NotificationCompat.Builder(this, FOREGROUND_CHANNEL_ID)
                .setContentTitle("GetTaxi")
                .setContentText("GetTaxi is running on background")
                .setSmallIcon(R.drawable.ic_local_taxi_black_24dp)
                .setColor(Color.argb(255, 244, 140, 37))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .addAction(R.drawable.ic_stop_black_24dp, "Disconnect", exitApplicationPendingIntent)
                .setContentIntent(pendingIntent)
                .build();

        startForeground(1, notification);

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }
}