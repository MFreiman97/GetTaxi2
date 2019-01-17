package com.mfreiman.driverapp.controller.Adapters;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.mfreiman.driverapp.R;
import com.mfreiman.driverapp.controller.Fragments.TravelRequestFragment;
import com.mfreiman.driverapp.model.entities.Ride;

import java.util.ArrayList;

public class RideAdapter extends ArrayAdapter<Ride> {
    private Context context;
 public ArrayList<Ride> ls;
    /*public RideAdapter(@NonNull Context context, int resource, Context context1) {
        super(context, resource);
        this.context = context;
        ls= FactoryMethed.getManager().getRidesList();
    }
    */
    String IdDriver;
    private  FragmentManager fm;
    public RideAdapter(ArrayList<Ride> list,Context context,FragmentManager fm, String idDriver)
    {
        super(context,R.layout.item_ride,list);
        this.context = context;
        ls=list;
        this.fm=fm;
        IdDriver=new String(idDriver);
    }




    @Override
    public int getCount() {
        return ls.size();
    }


    public Ride getItem(int position) {
        return ls.get(position);
    }
    @Override
    public long getItemId(int position) {
        return 0;
    }




    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        final RideHolder holder;


        // First let's verify the convertView is not null
        if (convertView == null) {
            // This a new view we inflate the new layout
            holder = new RideHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_ride,null,true);
            // Now we can fill the layout with the right values
             holder.ID = (TextView) v.findViewById(R.id.IDnumber);
            holder.timeTV = (TextView) v.findViewById(R.id.timeTextView);
            holder.ShowMorebt=(Button) v.findViewById(R.id.ShowBt);
            holder.distanceTV=(TextView) v.findViewById(R.id.DistanceTextView);
            v.setTag(holder);
        }
        else {
            holder = (RideHolder) v.getTag();
        }
        holder.timeTV.setText("Time= "+ls.get(position).getStartTime());
        holder.ID.setText("ID= "+ls.get(position).getCustomerID());
        holder.distanceTV.setText("Distance= "+ls.get(position).getDistance()+"km");
        holder.ShowMorebt.setTag(R.integer.btn_show_view, convertView);
        holder.ShowMorebt.setTag(R.integer.btn_show_pos, position);
        holder.ShowMorebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TravelRequestFragment f= new TravelRequestFragment();

                Integer pos = (Integer) holder.ShowMorebt.getTag(R.integer.btn_show_pos);

                Bundle bundle = new Bundle();
                bundle.putString("FirstName", ls.get(pos).getFirstName());
                bundle.putString("LastName", ls.get(pos).getLastName());
                bundle.putString("StartLocation",ls.get(pos).getStartLocation());
                bundle.putString("EndLocation", ls.get(pos).getEndLocation());
                bundle.putString("CustomerID", ls.get(pos).getCustomerID());
                bundle.putString("Time", ls.get(pos).getStartTime());
                bundle.putString("PhoneNumber", ls.get(pos).getCellPhone());
                bundle.putString("KeyOfTravel", ls.get(pos).getKey());
                bundle.putInt("position", position);
                bundle.putString("IdOfDriver",IdDriver);
                f.setArguments(bundle);

// create a FragmentTransaction to begin the transaction and replace the Fragment
                FragmentTransaction fragmentTransaction = fm.beginTransaction();

// replace the FrameLayout with new Fragment
                fragmentTransaction.replace(R.id.TravelFragment_frame, f);
                fragmentTransaction.commit(); // save the changes


            }
        });


        return v;
    }
    private static class RideHolder {
        public TextView ID;
        public TextView timeTV;
        public TextView distanceTV;
        public Button ShowMorebt;
    }



}
