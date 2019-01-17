package com.mfreiman.driverapp.controller;
import android.app.Activity;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.mfreiman.driverapp.R;
import com.mfreiman.driverapp.model.backend.FactoryMethed;
import com.mfreiman.driverapp.model.entities.Driver;

import java.util.regex.Pattern;

public class AddDriverActivity extends Activity implements View.OnClickListener {

    private TextInputLayout FirstNameAdd;
    private TextInputLayout LastNameAdd;
    private TextInputLayout IDAdd;
    private TextInputLayout EmailAdd;
    private Button GetStartAdd;
    String PhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_driver);
        PhoneNumber = getIntent().getStringExtra("phoneNumber");
        findViews();
    }

    private void findViews() {
        FirstNameAdd = findViewById(R.id.FirstNameEditText);
        LastNameAdd = findViewById(R.id.LastNameEditText);
        IDAdd = findViewById(R.id.IDEditText);
        EmailAdd = findViewById(R.id.EmailEditText);
        GetStartAdd = findViewById(R.id.GetStartedAdd);
        GetStartAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == GetStartAdd) {
            if (FirstNameAdd.getEditText().getText().toString().equals("")) {
                Toast.makeText(getBaseContext(), "First name can't be empty!", Toast.LENGTH_LONG).show();
                return;
            } else {
                if (Pattern.matches("[0-9]", FirstNameAdd.getEditText().getText().toString())) {
                    Toast.makeText(getBaseContext(), "First name can't contain numbers!", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    if (!Pattern.matches("[a-zA-Z]+", FirstNameAdd.getEditText().getText().toString())) {
                        Toast.makeText(getBaseContext(), "First name can't contain this character!\nOnly a-z", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
            }
            if(LastNameAdd.getEditText().getText().toString().equals("")) {
                Toast.makeText(getBaseContext(), "Last name can't be empty!", Toast.LENGTH_LONG).show();
                return;
            } else {
                if (Pattern.matches("[0-9]", LastNameAdd.getEditText().getText().toString())) {
                    Toast.makeText(getBaseContext(), "Last name can't contain numbers!", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    if (!Pattern.matches("[a-zA-Z]+", LastNameAdd.getEditText().getText().toString())) {
                        Toast.makeText(getBaseContext(), "Last name can't contain this character!\nOnly a-z", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
            }
            if (IDAdd.getEditText().getText().toString().length() != 9) {
                Toast.makeText(getBaseContext(), "ID must contain only 9 numbers!", Toast.LENGTH_LONG).show();
                return;
            }
            if (EmailAdd.getEditText().getText().toString().equals("")) {
                Toast.makeText(getBaseContext(), "Email can't be empty!", Toast.LENGTH_LONG).show();
                return;
            } else {
                if (!EmailAdd.getEditText().getText().toString().contains("@")) {
                    Toast.makeText(getBaseContext(), "Email must be with the character '@'!", Toast.LENGTH_LONG).show();
                    return;
                }
            }
            final Driver d = new Driver();
            d.setId(IDAdd.getEditText().getText().toString());
            d.setEmail(EmailAdd.getEditText().getText().toString());
            d.setName(FirstNameAdd.getEditText().getText().toString() + " " + LastNameAdd.getEditText().getText().toString());
            d.setId(IDAdd.getEditText().getText().toString());
            d.setCell(PhoneNumber.toString());
            new AsyncTask<Void, Void, String>() {
                @Override
                protected void onPostExecute(String idResult) {

                    Toast.makeText(getBaseContext(), "The Driver added successfully!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(AddDriverActivity.this, DataActivity.class);
                    intent.putExtra("IDdriver", d.getId().toString());
                    startActivity(intent);

                }

                @Override
                protected String doInBackground(Void... params) {

                    FactoryMethed.getManager().AddNewDriver(IDAdd.getEditText().getText().toString(), d);
                    return IDAdd.getEditText().toString();

                }
            }.execute();

        }
    }
}