package com.cs.sis.sdk.base.ui;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;

import com.cs.sis.sdk.R;
import com.cs.sis.sdk.utils.SISLogUtil;
import com.cs.sis.sdk.utils.ToastUtils;



/**
 * author : ${CHENJIE} created at  2019-11-07 23:29 e_mail : chenjie_goodboy@163.com describle :
 */
public abstract class BaseActivity extends AppCompatActivity {

  public LinearLayout mMainLayout;

  //顶部导航栏
  protected View topBar;
  //内容区
  public View mContentView;


  protected Activity mContext;


  private LayoutInflater mInflater;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    mContext = this;
    setContentView(initContentView());
    findViews();
  }

  private View initContentView() {
    mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    mMainLayout = new LinearLayout(this);
    mMainLayout.setOrientation(LinearLayout.VERTICAL);
    topBar = initTopBar();
    if (null != topBar) {
      ViewGroup.LayoutParams iabParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams
          .MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
      mMainLayout.addView(topBar, iabParams);
    }

    mContentView = mInflater.inflate(getLayoutResId(), null);

    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.MATCH_PARENT,
        LinearLayout.LayoutParams.MATCH_PARENT);
    mMainLayout.addView(mContentView, params);

    setContentView(mMainLayout);


    return mMainLayout;
  }

  protected abstract int getLayoutResId();

  protected abstract void findViews();

  //初始化顶部导航栏，可重写该方法，传入自定义View
  protected View initTopBar() {
    return getDefaultTopBar();
  }


  protected View getDefaultTopBar() {
    //实现状态栏图标和文字颜色为暗色

    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    View topView = mInflater.inflate(R.layout.sis_view_default_top_bar, null);
    return topView;
  }

  /**
   * 省略强转寻找控件
   */
  protected <T> T $(int id) {
    return (T) findViewById(id);
  }


  public void showToast(String msg) {
    SISLogUtil.d(msg);
    ToastUtils.showShort(mContext, msg);
    //MyGlobal.showToast(msg);
  }

}
