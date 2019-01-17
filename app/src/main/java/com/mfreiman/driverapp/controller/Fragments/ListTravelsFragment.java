package com.mfreiman.driverapp.controller.Fragments;

import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.location.Location;

import com.mfreiman.driverapp.R;
import com.mfreiman.driverapp.controller.Adapters.RideAdapter;
import com.mfreiman.driverapp.model.backend.FactoryMethed;
import com.mfreiman.driverapp.model.entities.Ride;

import java.util.ArrayList;

public class ListTravelsFragment extends Fragment {

    View myView;
    ArrayList<Ride> ls = new ArrayList<Ride>();
    RideAdapter aAdpt;
    Location locationD = new Location("D");//= new Location(from);

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.layout_list_travels, container, false);

        ListView lv = myView.findViewById(R.id.lv);
        Bundle bundle = this.getArguments();
        locationD.setLatitude(bundle.getDouble("Latitude"));
        locationD.setLongitude(bundle.getDouble("Longitude"));
        aAdpt = new RideAdapter(FactoryMethed.getManager().getListOfRelevantRides(locationD), getContext(), getChildFragmentManager(), bundle.getString("DriverId"));
        lv.setAdapter(aAdpt);
        getActivity().setTitle("Travel Requests");
        return myView;
    }
}