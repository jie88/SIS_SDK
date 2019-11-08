package com.cs.sis.sdk.ui;


import com.cs.sis.sdk.R;
import com.cs.sis.sdk.base.ui.BaseActivity;

/**
 * author : ${CHENJIE} created at  2019-11-08 10:19 e_mail : chenjie_goodboy@163.com describle :
 */
public class SISPermissionActivity extends BaseActivity {

  @Override
  protected int getLayoutResId() {
    return R.layout.activity_permission;
  }

  @Override
  protected void findViews() {
    initLeftTop("返回", "权限获取");

  }
}
