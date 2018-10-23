/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;


public class MainActivity extends AppCompatActivity implements View.OnKeyListener {
EditText etUser,etPass;
TextView tvSwitch;
Boolean signUpMode=true;
Button btn;
ImageView imageView;
RelativeLayout back;
    public void nextActivity(){
        Intent intent=new Intent(this,userList.class);
        startActivity(intent);
    }



  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    setTitle("INSTAGRAM");
    tvSwitch= (TextView) findViewById(R.id.Switch);
    etUser= (EditText) findViewById(R.id.usernameEditText);
    etPass= (EditText) findViewById(R.id.passwordEditText);
    btn= (Button) findViewById(R.id.signUpButton);
    imageView= (ImageView) findViewById(R.id.imageView);
    back= (RelativeLayout) findViewById(R.id.backLayout);
    if(ParseUser.getCurrentUser()!=null)
    {
      nextActivity();
    }
    imageView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              InputMethodManager inputMethodManager= (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
              inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
          }
      });
      back.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              InputMethodManager inputMethodManager= (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
              inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
          }
      });
      etPass.setOnKeyListener(this);
    etUser.setOnKeyListener(this);
    tvSwitch.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(signUpMode)
        {
          signUpMode=false;
          tvSwitch.setText("or,SignUp");
          btn.setText("Login");
        }
        else
        {
          signUpMode=true;
          tvSwitch.setText("or,Login");
          btn.setText("SignUp");
        }
      }
    });
    ParseAnalytics.trackAppOpenedInBackground(getIntent());
  }

  public void signUpClicked(View view) {
  if(etUser.getText().toString().equals("")||etPass.getText().toString().equals(""))
  {
    Toast.makeText(this,"Empty password or username",Toast.LENGTH_SHORT).show();
  }
  else
  {
    if(signUpMode)
    {
    ParseUser user=new ParseUser();
    user.setUsername(etUser.getText().toString());
    user.setPassword(etPass.getText().toString());
    user.signUpInBackground(new SignUpCallback() {
      @Override
      public void done(ParseException e) {
        if(e==null)
        {
          Toast.makeText(MainActivity.this,"SignUp successful",Toast.LENGTH_SHORT).show();
          nextActivity();
        }
        else
        {
          e.printStackTrace();
          Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();

        }
      }
    });
  }
  else {
      ParseUser user=new ParseUser();
      user.logInInBackground(etUser.getText().toString(), etPass.getText().toString(), new LogInCallback() {
        @Override
        public void done(ParseUser user, ParseException e) {
          if(e==null&&user!=null)
          {
            Toast.makeText(MainActivity.this,"Login successful",Toast.LENGTH_SHORT).show();
            nextActivity();
          }
          else
          {
            e.printStackTrace();
            Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
          }
        }
      });
    }
  }
}

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
      Log.i("event",event.getAction()+"");
      if(keyCode==KeyEvent.KEYCODE_ENTER && event.getAction()==KeyEvent.ACTION_DOWN)
        btn.callOnClick();
        return false;
    }
}