package com.mfreiman.driverapp.controller.Fragments;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.mfreiman.driverapp.R;
import com.mfreiman.driverapp.controller.Fragments.HistoryFragment;
import com.mfreiman.driverapp.model.backend.FactoryMethed;
import com.mfreiman.driverapp.model.entities.Ride;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;



@RequiresApi(api = Build.VERSION_CODES.O)
public class TravelRequestFragment extends Fragment {
    View myView;
    TextInputLayout textInputLayout;
    TextInputLayout timeTextInputLayout;
    TextInputLayout CellInputLayout;
    TextInputLayout KEYInputLayout;
    String SendSMSDEST;
    Button SendingSms;

    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.activity_travel_request_, container, false);

        findViews();

        return myView;
    }
String IdDriver;
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void findViews() {

        final Bundle bundle = this.getArguments();
        IdDriver=bundle.getString("IdOfDriver");
        textInputLayout = myView.findViewById(R.id.CustomerIdTextInputLayout);
        textInputLayout.getEditText().setText(bundle.getString("CustomerID"));

        timeTextInputLayout = myView.findViewById(R.id.TImeTextInputLayout);
        timeTextInputLayout.getEditText().setText(bundle.getString("Time"));
        CellInputLayout = myView.findViewById(R.id.CellPhoneInputLayout);
        CellInputLayout.getEditText().setText(bundle.getString("PhoneNumber"));
        final String keyToDelete = bundle.getString("KeyOfTravel");
        KEYInputLayout = myView.findViewById(R.id.KeyInputLayout);
        KEYInputLayout.getEditText().setText(keyToDelete);
        SendingSms = myView.findViewById(R.id.TakeItButton);
        SendSMSDEST = bundle.getString("PhoneNumber").toString();
        final LocalDateTime now = LocalDateTime.now();


        final Ride r = FactoryMethed.getManager().getRidesList().get(bundle.getInt("position"));
        SendingSms.setOnClickListener(new View.OnClickListener() {

            @TargetApi(Build.VERSION_CODES.O)
            @SuppressLint("StaticFieldLeak")
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                try {
                    SmsManager smsManager = SmsManager.getDefault();
                 //   smsManager.sendTextMessage(SendSMSDEST, null, "Hello,Im your driver and I ready to give a service to you. I LOVE YOU!", null, null);
                    smsManager.sendTextMessage("0523665685", null, "Hello,Im your driver and I ready to give a service to you. I LOVE YOU!", null, null);

                    new AsyncTask<Void, Void, String>() {
                        @RequiresApi(api = Build.VERSION_CODES.O)
                        @Override
                        protected void onPostExecute(String idResult) {


                            Toast.makeText(getContext(), "SMS Sent!",
                                    Toast.LENGTH_LONG).show();


                            final ACProgressFlower dialog = new ACProgressFlower.Builder(getContext())
                                    .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                                    .themeColor(Color.WHITE)
                                    .text("Loading")
                                    .fadeColor(Color.DKGRAY).build();
                            dialog.setCancelable(true);

                            HistoryFragment fragment = new HistoryFragment();

                            dialog.show();
Bundle bundle_=new Bundle();
                            bundle_.putString("IdDriver", IdDriver);
                            fragment.setArguments(bundle_);
                            FragmentManager fragmentManager = getActivity().getFragmentManager();
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
                            pdCanceller.postDelayed(progressRunnable, 6000);


                        }

                        @RequiresApi(api = Build.VERSION_CODES.O)
                        @Override
                        protected String doInBackground(Void... params) {
                            r.setStartTime(dtf.format(now));

                            FactoryMethed.getManager().UpdateTravel(keyToDelete, IdDriver, r);

                            return "";
                        }
                    }.execute();

                } catch (Exception e) {
                    Toast.makeText(getContext(),
                            "SMS faild, please try again later!",
                            Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });
    }
}


