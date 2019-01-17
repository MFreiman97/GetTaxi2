package com.mfreiman.driverapp.controller.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.mfreiman.driverapp.R;
import com.mfreiman.driverapp.model.backend.FactoryMethed;
import com.mfreiman.driverapp.model.entities.Driver;

import java.util.jar.Attributes;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

public class FragmentEditYourDetails extends Fragment {

    View myView;
    TextInputLayout NameInputLayout;
    Button UpdateBt;
    TextInputLayout EmailInputLayout;
    TextInputLayout CellPhoneInputLayout;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_edit_driver_details, container, false);
        findViews();
        getActivity().setTitle("Edit yout Details");
        return myView;
    }

    private void findViews() {
        NameInputLayout = (TextInputLayout) myView.findViewById(R.id.NameInputLayout);
        EmailInputLayout = (TextInputLayout) myView.findViewById(R.id.EmailInputLayout);
        CellPhoneInputLayout = (TextInputLayout) myView.findViewById(R.id.CellPhoneInputLayout);
        UpdateBt = (Button) myView.findViewById(R.id.UpdateButton);


        final Driver d = new Driver();
        d.setId(getArguments().getString("id"));
        d.setEmail(getArguments().getString("Email"));
        d.setCell(getArguments().getString("PhoneNumber"));
        d.setName(getArguments().getString("name"));

        if (d != null) {
            NameInputLayout.getEditText().setText(d.getName());
            EmailInputLayout.getEditText().setText(d.getEmail());
            CellPhoneInputLayout.getEditText().setText(d.getCell());

            UpdateBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    d.setEmail(EmailInputLayout.getEditText().getText().toString());
                    d.setCell(CellPhoneInputLayout.getEditText().getText().toString());
                    d.setName(NameInputLayout.getEditText().getText().toString());

                    new AsyncTask<Void, Void, String>() {
                        @RequiresApi(api = Build.VERSION_CODES.M)
                        @Override
                        protected void onPostExecute(String idResult) {

                            final ACProgressFlower dialog = new ACProgressFlower.Builder(getContext())
                                    .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                                    .themeColor(Color.WHITE)
                                    .text("Loading")
                                    .fadeColor(Color.DKGRAY).build();
                            dialog.setCancelable(true);


                            dialog.show();
                            Runnable progressRunnable = new Runnable() {

                                @Override
                                public void run() {
                                    dialog.cancel();
                                    Toast.makeText(getContext(), "The Driver details updated successfully!", Toast.LENGTH_LONG).show();


                                }

                            };
                            Handler pdCanceller = new Handler();
                            pdCanceller.postDelayed(progressRunnable, 4000);


                        }

                        @Override
                        protected String doInBackground(Void... params) {

                            FactoryMethed.getManager().UpdateDriver(d);
                            return "";

                        }
                    }.execute();
                }
            });
        }
    }
}
