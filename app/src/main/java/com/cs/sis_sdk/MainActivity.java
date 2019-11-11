package com.cs.sis_sdk;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.cs.http_lib.net.ResultListener;
import com.cs.http_lib.net.transaction.TransactionException;
import com.cs.sis.sdk.SISSdkController;

import com.cs.sis_sdk.protocol.LoginRequest;
import com.cs.sis_sdk.protocol.LoginResponse;


public class MainActivity extends AppCompatActivity {
  private Activity mContext;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    mContext = this;
    setContentView(R.layout.activity_main);

  }


  public void set(View view) {
    SISSdkController.getInstance().startSet(mContext);
  }

  public void permission(View view) {
    SISSdkController.getInstance().startPermission(mContext);
  }


  public void demo(View view) {
    SISSdkController.getInstance().startDemo(mContext);
  }


  public void request(View view) {
    LoginRequest loginRequest = new LoginRequest();
    loginRequest.username = "";
    loginRequest.send(mContext, new ResultListener<LoginResponse>(mContext) {
      @Override
      public void onSuccess(LoginResponse result) {
        Toast.makeText(mContext,result.toJson(),Toast.LENGTH_SHORT);
        Log.d("Tag","result " + result.toJson());
      }

      /**
       * 如果需要单独处理错误信息，可以重载该方法，
       * 不需要则可以不需要写改方法
       */
      @Override
      protected void onErrorHandle(TransactionException e) {
        super.onErrorHandle(e);
      }
    });

  }

}
