package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputLayout;
import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnKeyListener, View.OnClickListener {
    final String[] username = {""};
    EditText LoginEmail;
    ImageView loginAppIcon;
    EditText loginPassword;
    Button loginBuLogin;
    TextView loginTvSignUp;
    TextInputLayout loginTextInputEmail;
    TextInputLayout loginPasswordInputUsername;
    LinearLayout loginLinearLayout;
    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {

        if(keyCode ==KeyEvent.KEYCODE_ENTER && event.getAction() ==KeyEvent.ACTION_DOWN) {
            login(v);
        }
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Text Me");
        Toolbar toolbar =findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        loginLinearLayout=findViewById(R.id.loginLinearLayout);
        LoginEmail =findViewById(R.id.LoginEmail);
        loginPassword =findViewById(R.id.loginPassword);
        loginBuLogin =findViewById(R.id.loginBuLogin);
        loginTvSignUp =findViewById(R.id.loginTvSignUp);
        loginAppIcon =findViewById(R.id.loginAppIcon);
        loginTextInputEmail =findViewById(R.id.loginTextInputEmail);
        loginPasswordInputUsername =findViewById(R.id.loginPasswordInputUsername);
        loginAppIcon.setOnClickListener(this);
        loginLinearLayout.setOnClickListener(this);
        if(ParseUser.getCurrentUser()!=null){
            MainApp();
        }
        //email check
        LoginEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(LoginEmail.getText().toString().isEmpty()){
                    loginTextInputEmail.setErrorEnabled(true);
                    loginTextInputEmail.setError("PLease enter your email!");
                }
            }
        });
        LoginEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(LoginEmail.getText().toString().isEmpty()){
                    loginTextInputEmail.setErrorEnabled(true);
                    loginTextInputEmail.setError("PLease enter your email!");
                }
               else if(checkForEmail(LoginEmail)==false){
                    loginTextInputEmail.setError("please, enter valid email address!");
                }
                else{
                    loginTextInputEmail.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                query();
            }
        });

        //password check
        loginPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(loginPassword.getText().toString().isEmpty()){
                    loginPasswordInputUsername.setErrorEnabled(true);
                    loginPasswordInputUsername.setError("PLease enter your password!");
                }
                else{
                    loginPasswordInputUsername.setErrorEnabled(false);
                }
            }
        });
        loginPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(loginPassword.getText().toString().isEmpty()){
                    loginPasswordInputUsername.setErrorEnabled(true);
                    loginPassword.setError("PLease enter your password!");
                }
                else {
                    loginPasswordInputUsername.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void query(){
        ParseQuery<ParseUser> query =ParseUser.getQuery();
        query.setLimit(1);
        query.whereEqualTo("email",LoginEmail.getText().toString());
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if(e==null){
                    if(objects.size()>0){
                        for (ParseUser user :objects){
                           username[0]=user.get("username").toString();
                            }
                        }
                    }
                }

        });

    }

    public void login(View view) {

        if(checkForEmail(LoginEmail)&&loginPassword.getText().toString().isEmpty()==false){
            try {
                query();
                ParseUser.logInInBackground(username[0], loginPassword.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if(user!=null){
                            MainApp();
                        }
                        else{
                           Toast.makeText(MainActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    //check Email
    @SuppressLint("DefaultLocale")
    public boolean checkForEmail(EditText edit) {
        String str = edit.getText().toString();
        if (android.util.Patterns.EMAIL_ADDRESS.matcher(str).matches()) {
            return true;
        }
        else{
            return false;

        }
    }

//remove keyboard
    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.loginLinearLayout||v.getId()==R.id.loginAppIcon){
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        }
    }

    public void LoginSignUpButton(View view) {
        Intent intent =new Intent(getApplicationContext(),signUpActivity.class);
        startActivity(intent);
    }
    public void MainApp(){
        Intent intent =new Intent(getApplicationContext(),MainAppActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        finish();
        System.exit(1);
        super.onBackPressed();
    }
}