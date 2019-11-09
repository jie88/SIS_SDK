package com.cs.http_lib.net;


import android.app.Activity;
import android.text.TextUtils;


import com.cs.http_lib.net.transaction.TransactionException;
import com.cs.http_lib.net.transaction.TransactionRequest;
import com.cs.http_lib.net.utils.SISLogUtil;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * * @author zhouhuaqiang * @COMPANY:杭州远方光电信息股份有限公司
 */
public abstract class BaseRequest<T extends BaseResponse> extends TransactionRequest<T> {

  //请求地址
  protected String BASE_URL = "http://www.17duibiao.com:80/auth/login";
  //protected String BASE_URL="https://kyfw.12306.cn/otn/";
  //http://www.17duibiao.com:80/update/check

  private ResultListener listener;
  protected Activity mActivity;

  public BaseRequest(Class<T> responseClass) {
    super(responseClass);
  }


  private static String apiKey;
  private static String secret;
  private static String token;
  private static String[] resultHeader = new String[3];

  private Map<String, String> getParamBody() {
    Map<String, String> requestParams = new HashMap<String, String>();

    // 获取所有定义的字段
    Field[] mFields = getClass().getFields();
    for (Field mField : mFields) {
      // 当前字段是否存在Parameter 注解
      if (!mField.isAnnotationPresent(Parameter.class)) {
        continue;
      }
      // 获取所有key-value值
      Parameter mParameter = mField.getAnnotation(Parameter.class);
      String parameterKey = mField.getName();
      String parameterValue = "";
      try {
        Object value = mField.get(this);
        if (null != value) {

          if (value instanceof String) {
            parameterValue = String.valueOf(value);
          }
        }
      } catch (Exception e) {
        SISLogUtil.e(e.toString());
      }
      // 如果定义空值不上送 直接下一轮循环
      if (TextUtils.isEmpty(parameterValue) && !mParameter.required()) {
        continue;
      }
      // 处理特殊情况 以特殊定义的值为准
      if (!TextUtils.isEmpty(mParameter.name())) {
        parameterKey = mParameter.name();
      }
      // 处理有特殊编码的字段
      if (!TextUtils.isEmpty(mParameter.charset())) {
        try {
          parameterValue = new String(parameterKey.getBytes(), mParameter.charset());
        } catch (UnsupportedEncodingException e) {
          SISLogUtil.e(e.toString());
        }
      }

      // 加入请求参数
      requestParams.put(parameterKey, parameterValue);

    }


    return requestParams;
  }

  private void get() {
    OkHttpUtils.getInstance().get(BASE_URL, new Callback() {
      @Override
      public void onFailure(@NotNull Call call, @NotNull IOException e) {
        SISLogUtil.e("result = Failure ");
        e.printStackTrace();
      }

      @Override
      public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
        String responseStr = response.body().string();
        SISLogUtil.d("responseStr = " + responseStr);
      }
    });
  }

  private void post() {

    OkHttpUtils.getInstance().post(BASE_URL, getParamBody(), null, new Callback() {
      @Override
      public void onFailure(@NotNull Call call, @NotNull final IOException e) {
        SISLogUtil.e("result = Failure ");
        e.printStackTrace();
        if (null != listener) {
          if(null!=mActivity){
            mActivity.runOnUiThread(new Runnable() {
              @Override
              public void run() {
                listener.onFailed(new TransactionException(e.getMessage()));
              }
            });
          }else {
            listener.onFailed(new TransactionException(e.getMessage()));
          }
        }
      }

      @Override
      public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
        displayHead(response);
        String responseStr = response.body().string();
        SISLogUtil.d("responseStr = " + responseStr);
        try {
         final T data = responseClass.newInstance().parseResult(responseStr);
          if (data instanceof BaseResponse) {
            if (data.status == 0) {
              //成功
              if (null != listener) {
                if(null!=mActivity){
                  mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                      listener.onSuccess(data);
                    }
                  });
                }else {
                  listener.onSuccess(data);
                }
              }
            } else {
             final TransactionException exception = new TransactionException(data.status, data.error, null);
              if (null != listener) {
                if(null!=mActivity){
                  mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                      listener.onFailed(exception);
                    }
                  });
                }else {
                  listener.onFailed(exception);
                }
              }
            }
          } else {

          }
        } catch (final Exception e) {
          if (e instanceof TransactionException) {
            if (null != listener) {
              if(null!=mActivity){
                mActivity.runOnUiThread(new Runnable() {
                  @Override
                  public void run() {
                    listener.onFailed((TransactionException) e);
                  }
                });
              }else {
                listener.onFailed((TransactionException) e);
              }

            }

          } else {
            if (null != listener) {
              if(null!=mActivity){
                mActivity.runOnUiThread(new Runnable() {
                  @Override
                  public void run() {
                    listener.onFailed(new TransactionException(e.getMessage()));
                  }
                });
              }else {
                listener.onFailed(new TransactionException(e.getMessage()));
              }

            }
          }
        }

      }
    });
  }


  private void displayHead(Response response) {
    //if (needHead) {
    Map<String, List<String>> headers = response.headers().toMultimap();
    String[] keys = {"token", "apiKey", "secret"};
    for (int i = 0; i < keys.length; i++) {
      List<String> messages = headers.get(keys[i]);
      if (messages != null) {
        resultHeader[i] = messages.get(0);
        System.out.println("Key = " + keys[i] + " value = " + resultHeader[i]);
      } else {
        System.out.println("Key = " + keys[i] + " value = ");
      }
    }
    token = resultHeader[0];
    apiKey = resultHeader[1];
    secret = resultHeader[2];
    // }
  }

  public void send(Activity context, ResultListener listener) {
    mActivity=context;
    this.listener = listener;
    post();
  }
}
