package com.cs.sis.sdk.base.ui;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

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

  private TextView topTxtLeft, topTxtTitle, topTxtRight;

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


  /**
   * 只显示标题
   * @param title
   */
  protected void initTitleTop(String title) {
    initTop("", title, "");
  }

  /**
   * 显示左边按钮和标题
   * @param left
   * @param title
   */
  protected void initLeftTop(String left, String title) {
    initTop(left, title, "");
  }

  /**
   * 显示标题和右边按钮
   * @param title
   * @param right
   */
  protected void initRightTop(String title, String right) {
    initTop("", title, right);
  }

  /**
   * 显示左边按钮，标题，右边按钮
   * @param left
   * @param title
   * @param right
   */
  protected void initTop(String left, String title, String right) {

    if(null!=topTxtLeft) {
      if (TextUtils.isEmpty(left)) {
        topTxtLeft.setVisibility(View.GONE);
      }else {
        topTxtLeft.setText(left);
        topTxtLeft.setVisibility(View.VISIBLE);

      }
    }else {
      SISLogUtil.d("topTxtLeft null");
    }


    if(null!=topTxtTitle) {
      if (TextUtils.isEmpty(title)) {
        topTxtTitle.setVisibility(View.GONE);
      }else {
        topTxtTitle.setText(title);
        topTxtTitle.setVisibility(View.VISIBLE);

      }
    }else {
      SISLogUtil.d("topTxtTitle null");
    }


    if(null!=topTxtRight) {
      if (TextUtils.isEmpty(right)) {
        topTxtRight.setVisibility(View.GONE);
      }else {
        topTxtRight.setText(right);
        topTxtRight.setVisibility(View.VISIBLE);

      }
    }else {
      SISLogUtil.d("topTxtRight null");
    }
  }

  protected View getDefaultTopBar() {
   View topView = mInflater.inflate(R.layout.sis_view_default_top_bar, null);
    topTxtLeft = (TextView) topView.findViewById(R.id.topTxtLeft);
    topTxtTitle = (TextView) topView.findViewById(R.id.topTxtTitle);
    topTxtRight = (TextView) topView.findViewById(R.id.topTxtRight);
    topTxtLeft.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        topBarLeftClick();
      }
    });

    topTxtRight.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        topBarRightClick();
      }
    });

    return topView;
  }

  /**
   * 顶部栏左边点击处理，子类重写
   */
  protected void topBarLeftClick() {

  }

  /**
   * 顶部栏右边点击处理，子类重写
   */
  protected void topBarRightClick() {

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
