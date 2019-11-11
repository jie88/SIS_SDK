package com.cs.sis.sdk;


import android.app.Activity;
import android.content.Intent;

import com.cs.sis.sdk.ui.SISMainActivity;
import com.cs.sis.sdk.ui.SISPermissionActivity;
import com.cs.sis.sdk.ui.SISSetActivity;

/**
 * author : ${CHENJIE} created at  2019-11-07 23:13 e_mail : chenjie_goodboy@163.com describle :
 */
public class SISSdkController {

  private static class SingletonHolder {
    private static final SISSdkController INSTANCE = new SISSdkController();
  }

  private SISSdkController() {
  }

  public static final SISSdkController getInstance() {
    return SingletonHolder.INSTANCE;
  }



  public void startPermission(Activity mActivity){
    mActivity.startActivity(new Intent(mActivity, SISPermissionActivity.class));
  }

  public void startSet(Activity mActivity){
    mActivity.startActivity(new Intent(mActivity, SISSetActivity.class));
  }
  public void startDemo(Activity mActivity){
    mActivity.startActivity(new Intent(mActivity, SISMainActivity.class));

  }

}
