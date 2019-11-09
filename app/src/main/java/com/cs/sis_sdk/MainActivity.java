package com.cs.sis_sdk;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.cs.http_lib.net.ResultListener;
import com.cs.http_lib.net.transaction.TransactionException;
import com.cs.sis.sdk.base.ui.BaseActivity;
import com.cs.sis.sdk.ui.SISPermissionActivity;
import com.cs.sis.sdk.ui.SISSetActivity;
import com.cs.sis.sdk.utils.SISLogUtil;
import com.cs.sis.sdk.utils.ToastUtils;
import com.cs.sis_sdk.protocol.LoginRequest;
import com.cs.sis_sdk.protocol.LoginResponse;


public class MainActivity extends BaseActivity {


  @Override
  protected int getLayoutResId() {
    return R.layout.activity_main;
  }

  @Override
  protected void findViews() {
    initTitleTop("SIS demo");
  }

  public void set(View view) {
    startActivity(new Intent(MainActivity.this, SISSetActivity.class));
  }

  public void permission(View view) {
    startActivity(new Intent(MainActivity.this, SISPermissionActivity.class));
  }


  public void demo(View view) {
  }


  public void request(View view) {
    LoginRequest loginRequest = new LoginRequest();
    loginRequest.username = "";
    loginRequest.send(mContext, new ResultListener<LoginResponse>(mContext) {
      @Override
      public void onSuccess(LoginResponse result) {
        ToastUtils.showShort(mContext, result.toJson());
        SISLogUtil.d("result " + result.toJson());
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
