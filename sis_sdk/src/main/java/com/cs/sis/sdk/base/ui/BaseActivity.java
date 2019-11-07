package com.cs.sis.sdk.base.ui;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.cs.sis.sdk.R;
import com.cs.sis.sdk.utils.SISLogUtil;
import com.cs.sis.sdk.utils.ToastUtils;

import androidx.appcompat.app.AppCompatActivity;

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

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mContext = this;
    setContentView(initContentView());
    findViews();
  }

  private View initContentView() {
    mMainLayout = new LinearLayout(this);
    mMainLayout.setOrientation(LinearLayout.VERTICAL);
    topBar = initTopBar();
    if (null != topBar) {
      ViewGroup.LayoutParams iabParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams
          .MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
      mMainLayout.addView(topBar, iabParams);
    }
    LayoutInflater mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

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
    return null;
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
