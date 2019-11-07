package com.cs.sis_sdk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.cs.sis.sdk.ui.SISSetActivity;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_list_base);
    startActivity(new Intent(MainActivity.this, SISSetActivity.class));
  }
}
