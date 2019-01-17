package com.mfreiman.driverapp.controller.Adapters;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.content.Context;
import android.content.OperationApplicationException;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mfreiman.driverapp.R;
import com.mfreiman.driverapp.model.backend.FactoryMethed;
import com.mfreiman.driverapp.model.entities.Customer;
import com.mfreiman.driverapp.model.entities.Ride;

import java.util.ArrayList;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

public class TakenRideAdapter extends ArrayAdapter<Ride> {



    private Context context;
    public ArrayList<Ride> Takenls;
    /*public RideAdapter(@NonNull Context context, int resource, Context context1) {
        super(context, resource);
        this.context = context;
        ls= FactoryMethed.getManager().getRidesList();
    }
    */
    private FragmentManager fm;
    public TakenRideAdapter(ArrayList<Ride> list, Context context)
    {
        super(context, R.layout.item_taken_ride,list);
        this.context = context;
        Takenls=list;

    }




    @Override
    public int getCount() {
        return Takenls.size();
    }


    public Ride getItem(int position) {
        return Takenls.get(position);
    }
    @Override
    public long getItemId(int position) {
        return 0;
    }




    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        final TakenRideHolder holder;


        // First let's verify the convertView is not null
        if (convertView == null) {
            // This a new view we inflate the new layout
            holder = new TakenRideHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_taken_ride,null,true);
            // Now we can fill the layout with the right values
            holder.ID = (TextView) v.findViewById(R.id.IDnumber_);
            holder.timeTV = (TextView) v.findViewById(R.id.timeTextView_);
            holder.Cellphone=v.findViewById(R.id.cellPhoneTV_);
            holder.distanceTV=(TextView) v.findViewById(R.id.DistanceTextView_);
            holder.AddContract=(Button) v.findViewById(R.id.ButtonAdd);
            holder.NameTv=(TextView) v.findViewById(R.id.NameTextView);
holder.AddContract.setOnClickListener(new View.OnClickListener() {
    @SuppressLint("StaticFieldLeak")
    @Override
    public void onClick(View v) {





   //     String  name= FactoryMethed.getManager().GetCustomerNameById(Takenls.get(position).getCustomerID());
//String FullName[]=name.split(" ");
        Toast.makeText(getContext(), "Contact Added!",
                Toast.LENGTH_LONG).show();

        //https://developer.android.com/reference/android/provider/ContactsContract.RawContacts
        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
        int rawContactInsertIndex = ops.size();

        ops.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                .build());
        //INSERT NAME
        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID,rawContactInsertIndex)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, Takenls.get(position).getFirstName() + " " + Takenls.get(position).getLastName()) // Name of the person
                .withValue(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME,Takenls.get(position).getLastName() ) // Name of the person
                .withValue(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, Takenls.get(position).getFirstName()) // Name of the person
                .build());
        //INSERT PHONE
        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID,   rawContactInsertIndex)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER,Takenls.get(position).getCellPhone() ) // Number of the person
                .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_WORK)
                .build()); //
        Uri newContactUri = null;
        //PUSH EVERYTHING TO CONTACTS
        try
        {

            ContentProviderResult[] res =context.getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
            if (res!=null && res[0]!=null)
                newContactUri = res[0].uri;
                //02-20 22:21:09 URI added contact:content://com.android.contacts/raw_contacts/612


        }
        catch (RemoteException e)
        {
            // error

            newContactUri = null;
        } catch (OperationApplicationException e) {
            e.printStackTrace();
        }
    }
});
            v.setTag(holder);
        }
        else {
            holder = (TakenRideHolder) v.getTag();
        }
        holder.timeTV.setText("Time= "+Takenls.get(position).getStartTime());
        holder.ID.setText("ID= "+Takenls.get(position).getCustomerID());
        holder.distanceTV.setText("Distance= "+Takenls.get(position).getDistance()+"km");
        holder.Cellphone.setText("Cellphone= "+Takenls.get(position).getCellPhone());
        holder.NameTv.setText("Name= "+Takenls.get(position).getFirstName()+" "+Takenls.get(position).getLastName());
        return v;
    }
    private static class TakenRideHolder {
        public TextView ID;
        public TextView timeTV;
        public TextView distanceTV;
     public  TextView Cellphone;
     public Button AddContract;
     public TextView NameTv;
    }

}
