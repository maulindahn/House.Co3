package com.example.hana.rentcostumes;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by user pc on 26/10/2016.
 */

public class LoginActivity extends Activity {
    private static final String TAG = RegisterActivity.class.getSimpleName();
    private Button btnLogin, btnLinkToRegister, btnLinkToForgotLogin;
    private EditText loginEmail, loginPassword;
    private FirebaseAuth mFirebaseAuth;
    SessionActivity sessionActivity;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Get Firebase auth instance
        mFirebaseAuth = FirebaseAuth.getInstance();
        sessionActivity = new SessionActivity();

        loginEmail = (EditText) findViewById(R.id.email);
        loginPassword = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLinkToRegister = (Button) findViewById(R.id.btnLinkToRegister);
        btnLinkToForgotLogin = (Button) findViewById(R.id.btnLinkToForgotLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        btnLinkToRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(i);
                finish();
            }
        });

        btnLinkToForgotLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ForgotLogin.class);
                startActivity(i);
                finish();
            }
        });
    }

        public void login() {
            Log.d(TAG, "Login");

            if (!validate()) {
                onLoginFailed();
                return;
            }
            btnLogin.setEnabled(false);

            final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this, R.style.MyTheme_ProgressDialog_);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Authenticating...");
            progressDialog.show();

            String email = loginEmail.getText().toString();
            String password = loginPassword.getText().toString();

            if(email.equals("dimas@coba.com") && password.equals("dimas")){
                // TODO: Implement your own authentication logic here.
                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                // On complete call either onLoginSuccess or onLoginFailed
                                onLoginSuccess();
                                // onLoginFailed();
                                progressDialog.dismiss();
                            }
                        }, 3000);
            } else {
                Toast.makeText(getApplicationContext(), "Salah", Toast.LENGTH_SHORT).show();
            }


        }

        public void onLoginSuccess() {
            btnLogin.setEnabled(true);
            sessionActivity.setPreferences(this, "status", "isLoggedIn");
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
            finish();
        }

        public void onLoginFailed() {
            Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
            btnLogin.setEnabled(true);
        }

        public boolean validate() {
            boolean valid = true;
            String email = loginEmail.getText().toString();
            String password = loginPassword.getText().toString();

            if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                loginEmail.setError("enter a valid email address");
                valid = false;
            } else {
                loginEmail.setError(null);
            }

            if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
                loginPassword.setError("between 4 and 10 alphanumeric characters");
                valid = false;
            } else {
                loginPassword.setError(null);
            }

            return valid;
        }

    private void tombolback() {
        AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
        alert.setTitle("Information");
        alert.setMessage("Do you want to exit");
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        alert.show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                tombolback();
                return true;
            default:
                return super.onKeyDown(keyCode, event);
        }
    }
}
