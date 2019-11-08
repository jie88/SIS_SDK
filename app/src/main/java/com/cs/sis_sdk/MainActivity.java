package com.cs.sis_sdk;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.cs.sis.sdk.ui.SISPermissionActivity;
import com.cs.sis.sdk.ui.SISSetActivity;


public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }

  public void set(View view) {
    startActivity(new Intent(MainActivity.this, SISSetActivity.class));

  }

  public void permission(View view) {
    startActivity(new Intent(MainActivity.this, SISPermissionActivity.class));

  }


  public void demo(View view) {

  }

}
