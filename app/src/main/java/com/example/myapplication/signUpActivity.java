package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputLayout;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class signUpActivity extends AppCompatActivity implements View.OnClickListener,View.OnKeyListener {

    boolean checkUserName =false ;
    boolean checkEmail =false;
    boolean checkPassword =false;
    boolean checkConfirmPassword=false;
    EditText signUpUsername;
    EditText signUpPassword;
    EditText signUpConfirmPassword;
    EditText signUpEmail;
    TextInputLayout signUpUserInputLayout;
    TextInputLayout signUpPasswordInputLayout;
    TextInputLayout signUpEmailInputLayout;
    TextInputLayout signUpConfirmPasswordInputLayout;
    Button signUpBuSignUp;
    LinearLayout signUpLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar =findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("SingUp");

        signUpUsername =findViewById(R.id.signUpUsername);
        signUpPassword =findViewById(R.id.signUpPassword);
        signUpConfirmPassword=findViewById(R.id.signUpConfirmPassword);
        signUpEmail=findViewById(R.id.signUpEmail);
        signUpUserInputLayout=findViewById(R.id.signUpUserInputLayout);
        signUpPasswordInputLayout=findViewById(R.id.signUpPasswordInputLayout);
        signUpConfirmPasswordInputLayout=findViewById(R.id.signUpConfirmPasswordInputLayout);
        signUpEmailInputLayout=findViewById(R.id.signUpEmailInputLayout);
        signUpBuSignUp =findViewById(R.id.signUpBuSignUp);
        signUpLinearLayout =findViewById(R.id.signUpLinearLayout);
        signUpLinearLayout.setOnClickListener(this);

        //username check
        signUpUsername.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(signUpUsername.getText().toString().isEmpty()){
                    signUpUserInputLayout.setErrorEnabled(true);
                    signUpUserInputLayout.setError("PLease enter your name!");
                }
            }
        });
        signUpUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(signUpUsername.getText().toString().isEmpty()){
                    signUpUserInputLayout.setErrorEnabled(true);
                    signUpUserInputLayout.setError("PLease enter your name!");
                    checkUserName =false;
                }else if(signUpUsername.getText().length()<5){
                    signUpUserInputLayout.setErrorEnabled(true);
                    signUpUserInputLayout.setError("Too short !");
                    checkUserName =false;
                }
                else if(signUpUsername.getText().length()>20){
                    signUpUserInputLayout.setErrorEnabled(true);
                    signUpUserInputLayout.setError("Too long !");
                    checkUserName =false;
                }
                else{
                    signUpUserInputLayout.setErrorEnabled(false);
                    checkUserName =true;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //email check
        signUpEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(signUpEmail.getText().toString().isEmpty()){
                    signUpEmailInputLayout.setErrorEnabled(true);
                    signUpEmailInputLayout.setError("PLease enter your email!");
                    checkEmail =false;
                }
            }
        });
        signUpEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(signUpEmail.getText().toString().isEmpty()){
                    signUpEmailInputLayout.setErrorEnabled(true);
                    signUpEmailInputLayout.setError("PLease enter your email!");
                    checkEmail =false;
                }
                else if(checkForEmail(signUpEmail)==false){
                    signUpEmailInputLayout.setError("please, enter valid email address!");
                    checkEmail =false;
                }
                else{
                    signUpEmailInputLayout.setErrorEnabled(false);
                    checkEmail =true;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //password check
        signUpPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(signUpPassword.getText().toString().isEmpty()){
                    signUpPasswordInputLayout.setErrorEnabled(true);
                    signUpPasswordInputLayout.setError("PLease enter your password!");
                    checkPassword=false;
                }
            }
        });
        signUpPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(signUpPassword.getText().toString().isEmpty()){
                    signUpPasswordInputLayout.setErrorEnabled(true);
                    signUpPasswordInputLayout.setError("PLease enter your password!");
                    checkPassword=false;
                }
                else if(signUpPassword.getText().toString().length()<8){
                    signUpPasswordInputLayout.setErrorEnabled(true);
                    signUpPasswordInputLayout.setError("Too Short!");
                    checkPassword=false;

                }
                else if(signUpPassword.getText().toString().length()>30){
                    signUpPasswordInputLayout.setErrorEnabled(true);
                    signUpPasswordInputLayout.setError("Too long!");
                    checkPassword=false;
                }
                else {
                    signUpPasswordInputLayout.setErrorEnabled(false);
                    checkPassword=true;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //RepeatPassword check
        signUpConfirmPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(signUpConfirmPassword.getText().toString().isEmpty()){
                    signUpConfirmPasswordInputLayout.setErrorEnabled(true);
                    signUpConfirmPasswordInputLayout.setError("PLease enter your password!");
                    checkConfirmPassword=false;
                }
            }
        });
        signUpConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(signUpConfirmPassword.getText().toString().isEmpty()){
                    signUpConfirmPasswordInputLayout.setErrorEnabled(true);
                    signUpConfirmPasswordInputLayout.setError("PLease enter your password!");
                    checkConfirmPassword=false;
                }
                else if(signUpConfirmPassword.getText().toString().length()<8){
                    signUpConfirmPasswordInputLayout.setErrorEnabled(true);
                    signUpConfirmPasswordInputLayout.setError("Too Short!");
                    checkConfirmPassword=false;
                }
                else if(signUpConfirmPassword.getText().toString().length()>30){
                    signUpConfirmPasswordInputLayout.setErrorEnabled(true);
                    signUpConfirmPasswordInputLayout.setError("Too long!");
                    checkConfirmPassword=false;
                }
                else if(signUpPassword.getText().toString().equals(signUpConfirmPassword.getText().toString())==false){
                    signUpConfirmPasswordInputLayout.setErrorEnabled(true);
                    Log.i("match","ok");
                    signUpConfirmPasswordInputLayout.setError("Passwords did't match");
                    checkConfirmPassword=false;
                }
                else {
                    signUpConfirmPasswordInputLayout.setErrorEnabled(false);
                    checkConfirmPassword=true;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            Intent intent =new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.signUpLinearLayout){
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        }
    }
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
    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if(keyCode ==KeyEvent.KEYCODE_ENTER && event.getAction() ==KeyEvent.ACTION_DOWN) {
            signUp(v);
        }
        return true;
    }
    public void signUp(View view) {

        if(checkEmail&&checkPassword&&checkConfirmPassword&&checkUserName){
            ParseUser user =new ParseUser();
            user.setUsername(signUpUsername.getText().toString());
            user.setEmail(signUpEmail.getText().toString());
            user.setPassword(signUpPassword.getText().toString());
            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if(e==null){
                        Intent intent =new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(signUpActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }
}