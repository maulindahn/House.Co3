package com.example.hana.rentcostumes.activity;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.hana.rentcostumes.R;

/**
 * Created by user pc on 26/10/2016.
 */

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private EditText inputFirstName, inputLastName, inputEmail, inputUsername, inputPassword;
    private Spinner inputGender;
    private Button btnRegister, btnLinkToLicenseAgreement;

    String list_gender [] = {"Gender", "Male", "Female"};
    private static final String TAG = "SignupActivity";

//
    @Override
    public void onBackPressed() {
        Intent intent = new Intent (this, LoginActivity.class);
        startActivity(intent);

/*        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
*/
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setupView();
        inputGender.setOnItemSelectedListener(this);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, list_gender);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        inputGender.setAdapter(arrayAdapter);

        ActionBar actionBar = getActionBar();
        if(actionBar!=null)
            actionBar.setDisplayHomeAsUpEnabled(false);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        btnRegister.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this, R.style.MyTheme_ProgressDialog_);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String fname = inputFirstName.getText().toString();
        String lname = inputLastName.getText().toString();
        String email = inputEmail.getText().toString();
        String uname = inputUsername.getText().toString();
        String password = inputPassword.getText().toString();

        // TODO: Implement your own signup logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess();
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    public void onSignupSuccess() {
        btnRegister.setEnabled(true);
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        btnRegister.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String fname = inputFirstName.getText().toString();
        String lname = inputLastName.getText().toString();
        String email = inputEmail.getText().toString();
        String uname = inputUsername.getText().toString();
        String password = inputPassword.getText().toString();

        if (fname.isEmpty() || fname.length() < 3) {
            inputFirstName.setError("at least 3 characters");
            valid = false;
        } else {
            inputFirstName.setError(null);
        }

        if (lname.isEmpty() || lname.length() < 3) {
            inputLastName.setError("at least 3 characters");
            valid = false;
        } else {
            inputLastName.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            inputEmail.setError("enter a valid email address");
            valid = false;
        } else {
            inputEmail.setError(null);
        }

        if (uname.isEmpty() || uname.length() < 3) {
            inputUsername.setError("at least 3 characters");
            valid = false;
        } else {
            inputUsername.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            inputPassword.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            inputPassword.setError(null);
        }

        return valid;
    }

    private void setupView(){
        inputFirstName = (EditText) findViewById(R.id.firstname);
        inputLastName = (EditText) findViewById(R.id.lastname);
        inputEmail = (EditText) findViewById(R.id.email);
        inputUsername = (EditText) findViewById(R.id.username);
        inputPassword = (EditText) findViewById(R.id.password);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnLinkToLicenseAgreement = (Button) findViewById(R.id.btnLinkToLicenseAgreement);
        inputGender = (Spinner) findViewById(R.id.gender);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
