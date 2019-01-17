package com.mfreiman.driverapp.model.datasource;

import android.location.Location;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mfreiman.driverapp.model.backend.DB_manager;
import com.mfreiman.driverapp.model.entities.Customer;
import com.mfreiman.driverapp.model.entities.Driver;
import com.mfreiman.driverapp.model.entities.Ride;
import com.mfreiman.driverapp.model.backend.FactoryMethed;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public  class FireBase_DBManager implements DB_manager {

    static public ArrayList<Ride> Rides;
    static public ArrayList<Ride> TakenRides;
    static public ArrayList<Driver> Drivers;
    static public ArrayList<Customer> Customers;
    private FirebaseDatabase rootRef;
    static boolean f = true;

    public FireBase_DBManager() {
        Rides = new ArrayList<Ride>();
        TakenRides = new ArrayList<Ride>();
        Drivers = new ArrayList<Driver>();
        Customers=new ArrayList<Customer>();

        rootRef = FirebaseDatabase.getInstance();


        DatabaseReference ref1 = rootRef.getReference("Travels");
        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Rides.clear();

                Ride r;
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for (DataSnapshot child : children) {
                    r = new Ride();
                    r.setFirstName((String) child.child("FirstName").getValue());
                    r.setLastName((String) child.child("LastName").getValue());
                    r.setCustomerID((String) child.child("CustomerID").getValue());
                    r.setStartTime((String) child.child("StartTIME").getValue());
                    r.setStartLocation((String) child.child("starLocation").getValue());
                    r.setEndLocation((String) child.child("endLocation").getValue());
                    r.setCellPhone((String) child.child("PhoneNumber").getValue());
                    r.setDistance((String) child.child("Distance").getValue());
                    r.setKey((String) child.getKey());
                    Rides.add(r);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        DatabaseReference ref2 = rootRef.getReference("Travels_");
        ref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                TakenRides.clear();
                Ride r;
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for (DataSnapshot child : children) {
                    r = new Ride();
                    r.setFirstName((String) child.child("FirstName").getValue());
                    r.setLastName((String) child.child("LastName").getValue());
                    r.setCustomerID((String) child.child("CustomerID").getValue());
                    r.setStartTime((String) child.child("StartTIME").getValue());
                    r.setStartLocation((String) child.child("starLocation").getValue());
                    r.setEndLocation((String) child.child("endLocation").getValue());
                    r.setCellPhone((String) child.child("PhoneNumber").getValue());
                    r.setDistance((String) child.child("Distance").getValue());
                    r.setIdDriver((String) child.child("IdDriver").getValue());
                    r.setKey((String) child.getKey());
                    TakenRides.add(r);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        DatabaseReference ref3 = rootRef.getReference("Drivers");
        ref3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Drivers.clear();

                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for (DataSnapshot c : children) {
                    Driver d = new Driver();
                    d.setId((String) c.child("id").getValue());
                    d.setEmail((String) c.child("email").getValue());
                    d.setName((String) c.child("name").getValue());
                    d.setCell((String) c.child("PhoneNumber").getValue());
                    Drivers.add(d);
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Customers.clear();
        DatabaseReference ref4 = rootRef.getReference("Customer");
        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                    Customers.clear();
                Customer c;
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for (DataSnapshot child : children) {
                    c = new Customer();
                  c.setId((String) child.child("id").getValue());
                    c.setMailAdd((String) child.child("email").getValue());
                    c.setName((String) child.child("name").getValue());
                    c.setPhone((String) child.child("PhoneNumber").getValue());

                    Customers.add(c);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void AddNewDriver(final String Id, final Driver d) {

        DatabaseReference userNameRef = rootRef.getReference("Drivers").child(Id);
        ValueEventListener eventListener = new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference userNameRef = database.getReference("Drivers/" + Id);
                    userNameRef.child("name").setValue(d.getName());
                    userNameRef.child("id").setValue(d.getId());
                    userNameRef.child("email").setValue(d.getEmail());
                    userNameRef.child("PhoneNumber").setValue(d.getCell());
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        userNameRef.addListenerForSingleValueEvent(eventListener);


    }

    @Override
    public void UpdateTravel(final String NumOfRide, final String IdDriver, final Ride ride) {

        DatabaseReference rootRef2 = FirebaseDatabase.getInstance().getReference("Travels_");

        final DatabaseReference userNameRef2 = rootRef2.child(NumOfRide);
        ValueEventListener eventListener = new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {

                    DatabaseReference userNameRef1 = FirebaseDatabase.getInstance().getReference("Travels/" + NumOfRide);
                    DatabaseReference userNameRef2 = FirebaseDatabase.getInstance().getReference("Travels_/" + NumOfRide);
                    userNameRef2.child("FirstName").setValue(ride.getFirstName());
                    userNameRef2.child("LastName").setValue(ride.getLastName());
                    userNameRef2.child("CustomerID").setValue(ride.getCustomerID());
                    userNameRef2.child("starLocation").setValue(ride.getStartLocation());
                    userNameRef2.child("endLocation").setValue(ride.getEndLocation());
                    userNameRef2.child("PhoneNumber").setValue(ride.getCellPhone());
                    userNameRef2.child("StartTIME").setValue(ride.getStartTime());
                    userNameRef2.child("Distance").setValue(ride.getDistance());
                    userNameRef2.child("IdDriver").setValue(IdDriver);
                    userNameRef1.removeValue();
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        userNameRef2.addListenerForSingleValueEvent(eventListener);


    }

    @Override
    public ArrayList<Ride> GetFinishedRides() {
        return null;
    }

    @Override
    public ArrayList<Ride> getRidesList() {
        return Rides;
    }

    public Ride GetByIndex(int index) {
        return Rides.get(index);
    }

    @Override
    public void AddRideToList(Ride r) {
        Rides.add(r);
    }

    @Override
    public void setRidesList(List<Ride> ls) {
        Rides = ((ArrayList<Ride>) ls);
    }


    public void MoveTravelAndDELETE(Ride r, String key) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Travels_/" + key);
        ref.removeValue();
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("Travels");
        DatabaseReference userNameRef = rootRef.child(key);
        userNameRef.child("FirstName").setValue(r.getFirstName());
        userNameRef.child("LastName").setValue(r.getLastName());
        userNameRef.child("CustomerID").setValue(r.getCustomerID());
        userNameRef.child("starLocation").setValue(r.getStartLocation());
        userNameRef.child("endLocation").setValue(r.getEndLocation());
        userNameRef.child("StartTIME").setValue(r.getStartTime());
        userNameRef.child("PhoneNumber").setValue(r.getCellPhone());


    }

    @Override
    public ArrayList<Ride> GetTakenRides(String DriverId) {
        ArrayList<Ride> Relevant = new ArrayList<Ride>();
        Relevant.clear();
        for (Ride r : TakenRides
                ) {
            if (DriverId.toString().equals(r.getIdDriver().toString())) {
                Relevant.add(r);
            }

        }
        return Relevant;
    }

    @Override
    public Driver getDriver(String idDriver) {

        Driver dNew = new Driver();
        for (Driver d_ : Drivers
                ) {
            if (d_.getId().toString().equals(idDriver.toString()))

                return d_;
        }
        return dNew;
    }

    public ArrayList<Driver> getDriverList() {


        return Drivers;
    }

    @Override
    public ArrayList<Ride> getListOfRelevantRides(Location a) {
        ArrayList<Ride> RidesByDistance = new ArrayList<Ride>();
        float[] dis=new float[1];
        for (Ride r : Rides) {
            Location c = new Location("C");
            Location d = new Location("D");
            String[] seperate = r.getStartLocation().split(",");
            String[] seperate_ = r.getEndLocation().split(",");
            c.setLatitude(Double.valueOf(seperate[0]));
            c.setLongitude(Double.valueOf(seperate[1]));
            d.setLatitude(Double.valueOf(seperate_[0]));
            d.setLongitude(Double.valueOf(seperate_[1]));
            double distance1 = a.distanceTo(c);
            double distance2 = a.distanceTo(d);
            if (distance1 < 40000 || distance2 < 40000) {
                RidesByDistance.add(r);

            }

        }
        return RidesByDistance;
    }

    @Override
    public String GetCustomerNameById(String Id) {
        Customer temp=new Customer();
        for (Customer c:Customers
             ) {
            if(Id.toString().equals(c.getId().toString()))
            {
                return c.getName().toString();
            }

        }
        return temp.getName().toString();
    }

    @Override
    public void UpdateDriver(final Driver d) {
        DatabaseReference rootRef2 = rootRef.getReference("Drivers");

        final DatabaseReference userNameRef2 = rootRef2.child(d.getId());
        ValueEventListener eventListener = new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                DatabaseReference userNameRef2 = rootRef.getReference("Drivers/" + d.getId());
                userNameRef2.child("PhoneNumber").setValue(d.getCell());
                userNameRef2.child("email").setValue(d.getEmail());
                userNameRef2.child("name").setValue(d.getName());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        userNameRef2.addListenerForSingleValueEvent(eventListener);
    }

    @Override
    public String DriverIdBycell(String num) {
        for (Driver driver :
                Drivers) {
            if (driver.getCell().toString().equals(num))
                return driver.getId();


        }
        return "";
    }

}