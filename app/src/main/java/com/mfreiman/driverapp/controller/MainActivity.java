package com.mfreiman.driverapp.controller;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import com.mfreiman.driverapp.R;
import com.mfreiman.driverapp.model.backend.FactoryMethed;
//import com.onesignal.OneSignal;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

import static android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP;

public class MainActivity extends Activity {

    static ComponentName service = null;
    TextInputLayout cell;
    Button Continue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        FactoryMethed.getManager();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();

    }

    private void findViews() {
        cell = findViewById(R.id.PhoneTextInputLayout);
        Continue = findViewById(R.id.ContinueBut);
        Continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == Continue) {
                    if (cell.getEditText().getText().toString().length() < 10) {
                        Toast.makeText(getBaseContext(), "Too short number", Toast.LENGTH_LONG).show();
                        return;
                    }
                    String TempId = FactoryMethed.getManager().DriverIdBycell(cell.getEditText().getText().toString());
                    final Intent intent;
                    if (TempId.equals("")) {
                        intent = new Intent(MainActivity.this, AddDriverActivity.class);
                        intent.putExtra("phoneNumber", cell.getEditText().getText().toString());
                    } else {
                        intent = new Intent(MainActivity.this, DataActivity.class);
                        intent.putExtra("IDdriver", TempId.toString());

                    }

                    final ProgressBar progressDialog = new ProgressBar(getApplicationContext());
                    final ACProgressFlower dialog = new ACProgressFlower.Builder(MainActivity.this)
                            .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                            .themeColor(Color.WHITE)
                            .text("Loading")
                            .fadeColor(Color.DKGRAY).build();
                    dialog.setCanceledOnTouchOutside(true);
                    dialog.setCancelable(true);
                    dialog.show();

                    Runnable progressRunnable = new Runnable() {

                        @Override
                        public void run() {
                            dialog.dismiss();
                            dialog.cancel();
                            startActivity(intent);
                        }
                    };

                    Handler pdCanceller = new Handler();
                    pdCanceller.postDelayed(progressRunnable, 2000);
                }
            }
        });
    }


}
