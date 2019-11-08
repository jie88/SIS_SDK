package com.cs.sis.sdk.ui;


import com.cs.sis.sdk.R;
import com.cs.sis.sdk.base.ui.BaseActivity;


/**
 * 权限控制
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
