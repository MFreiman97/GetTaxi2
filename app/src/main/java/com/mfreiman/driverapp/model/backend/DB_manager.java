package com.mfreiman.driverapp.model.backend;

import android.location.Location;

import com.mfreiman.driverapp.model.entities.Customer;
import com.mfreiman.driverapp.model.entities.Driver;
import com.mfreiman.driverapp.model.entities.Ride;

import java.util.ArrayList;
import java.util.List;

public interface DB_manager {

    void AddNewDriver(final String id,final Driver d);
    void UpdateTravel(String KeyOfTravel,String DriverId, Ride ride);
    ArrayList<Ride> GetFinishedRides();
    public   ArrayList<Ride> getRidesList();
    public void setRidesList(List<Ride> ls);
    public Ride GetByIndex(int index);
    public void AddRideToList(Ride r);
    public void MoveTravelAndDELETE(Ride r,String key);
    public   ArrayList<Ride> GetTakenRides(String DriverId);
    public Driver getDriver(String idDriver);
    public void UpdateDriver(final Driver d);
public String DriverIdBycell(String num);
    public ArrayList<Driver> getDriverList();
    public ArrayList<Ride> getListOfRelevantRides(Location a);
    public String GetCustomerNameById(String Id);

}
