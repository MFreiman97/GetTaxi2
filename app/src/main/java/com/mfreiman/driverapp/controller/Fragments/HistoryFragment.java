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

import com.mfreiman.driverapp.R;
import com.mfreiman.driverapp.controller.Adapters.TakenRideAdapter;
import com.mfreiman.driverapp.model.backend.FactoryMethed;


public class HistoryFragment extends Fragment {

    View myView;

    TakenRideAdapter aAdpt;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView=inflater.inflate(R.layout.layout_taken_rides,container,false);
Bundle bundle=this.getArguments();
        ListView lv=myView.findViewById(R.id.lv_taken);
        aAdpt = new TakenRideAdapter(FactoryMethed.getManager().GetTakenRides(bundle.getString("IdDriver")),getContext());
        lv.setAdapter(aAdpt);
        getActivity().setTitle("History of Travels");
        return myView;
    }
}
