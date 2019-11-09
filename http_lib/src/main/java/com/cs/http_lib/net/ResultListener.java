package com.cs.http_lib.net;


import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;

import com.cs.http_lib.net.transaction.TransactionException;
import com.cs.http_lib.net.utils.ToastUtils;


/**
 * author : ${CHENJIE} created at  2019-11-09 21:01 e_mail : chenjie_goodboy@163.com describle :
 */
public abstract class ResultListener<T> {
  protected Activity mActivity;

  public ResultListener(Context context) {
    mActivity = getActFromContext(context);
  }

  public Activity getActFromContext(Context mContext) {
    if (mContext == null) {
      return null;
    } else if (mContext instanceof Activity) {
      return (Activity) mContext;
    } else if (mContext instanceof ContextWrapper) {
      return getActFromContext(((ContextWrapper) mContext).getBaseContext());
    }
    return null;
  }

  /**
   * 请求成功
   */
  public abstract void onSuccess(T result);

  /**
   * 请求失败
   */
  public void onFailed(TransactionException e) {
    onErrorHandle(e);
  }

  /**
   * 处理错误信息 子类可重写改方法
   */
  protected void onErrorHandle(final TransactionException e) {

    if (null != mActivity) {
      mActivity.runOnUiThread(new Runnable() {
        @Override
        public void run() {
          ToastUtils.showShort(mActivity, e.getMessage());
        }
      });
    }
  }

}
