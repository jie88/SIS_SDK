package com.cs.sis.sdk.ui.view;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.cs.sis.sdk.R;

/**
 * author : ${CHENJIE} created at  2019-11-11 09:08 e_mail : chenjie_goodboy@163.com describle :
 */
public class MainListHeadView extends LinearLayout {

  private Context mContext;
  private View rootView;

  public MainListHeadView(Context context) {
    this(context,null);
  }

  public MainListHeadView(Context context, AttributeSet attrs) {
    this(context, attrs,0);
  }

  public MainListHeadView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    initView(context);
  }
  private void initView(Context context) {
    mContext = context;

    rootView = LayoutInflater.from(context).inflate(R.layout.sis_view_main_list_head, this);

  }

}
