package com.cs.http_lib.net;

import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author zhouhuaqiang
 * @COMPANY:杭州远方光电信息股份有限公司
 * @time 2017年5月16日上午9:33:53
 */
public class OkHttpUtils {

  private static OkHttpUtils mInstance;
  private OkHttpClient mHttpClient;
  public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

  private OkHttpUtils() {

    try {
      OkHttpClient.Builder builder = new OkHttpClient.Builder();
      builder.connectTimeout(10, TimeUnit.SECONDS);
      builder.readTimeout(10, TimeUnit.SECONDS);
      builder.writeTimeout(15, TimeUnit.SECONDS);
      mHttpClient=builder.build();
    } catch (Exception e) {

    }
  }

  public static OkHttpUtils getInstance() {
    if (mInstance == null) {
      mInstance = new OkHttpUtils();
    }
    return mInstance;
  }

  public void get(String url, Callback callback) {
    doRequest(buildRequest(url, null, null), callback);
  }

  public void post(String url, Map<String, String> paramBody, Map<String, String> paramHeader, Callback callback) {
    doRequest(buildRequest(url, paramBody, paramHeader), callback);
  }

  public void postUpload(String url, String paramBody, Map<String, String> paramHeader, Callback callback) {
    doRequest(buildRequestUpload(url, paramBody, paramHeader), callback);
  }


  public void uploadMultiFile(String url, Map<String, String> param, String fileName, List<File> files,
                              Callback callback) {
    System.out.println("url---->" + url);
    MultipartBody mBody = getMultipartBody(param, fileName, files);
    Request request = new Request.Builder().url(url).post(mBody).build();
    mHttpClient.newCall(request).enqueue(callback);
  }

  /**
   * @param url      下载连接
   * @param saveDir  储存下载文件的目录
   * @param listener 下载监听
   */
  public void download(final String url, Map<String, String> paramBody, Map<String, String> paramHeader,
                       final String saveDir, final String fileName, final OnDownloadListener listener) {
    Request request = buildRequest(url, paramBody, paramHeader);
    mHttpClient.newCall(request).enqueue(new Callback() {
      @Override
      public void onFailure(Call call, IOException e) {
        // 下载失败
        listener.onDownloadFailed();
      }

      @Override
      public void onResponse(Call call, Response response) {
        InputStream is = null;
        FileOutputStream fos = null;
        try {
          MessageDigest md = MessageDigest.getInstance("MD5");
          byte[] buf = new byte[2048];
          int len = 0;
          // 储存下载文件的目录
          String savePath = isExistDir(saveDir);
          is = response.body().byteStream();
          long total = response.body().contentLength();
          System.out.println("total--->" + total);
          File file = new File(savePath, fileName);
          System.out.println("file-->" + file.getPath());
          fos = new FileOutputStream(file);
          long sum = 0;
          int k = 0;
          while ((len = is.read(buf)) != -1) {
            fos.write(buf, 0, len);
            sum += len;
            int progress = (int) (sum * 1.0f / total * 100);
            // 下载中
            k++;
            if (k == 10 || progress == 100) {// 减缓发送次数
              k = 0;
              listener.onDownloading(progress);
            }
            md.update(buf, 0, len);
          }
          // BigInteger bi = new BigInteger(1, md.digest());
          // String sha = bi.toString(16);
          fos.flush();
          // 下载完成
          if (sum == total) {
            listener.onDownloadSuccess(savePath + "/" + fileName);
          } else {
            listener.onDownloadFailed();
          }

        } catch (Exception e1) {
          listener.onDownloadFailed();
        } finally {
          try {
            if (is != null) {
              is.close();
            }
          } catch (IOException e) {
          }
          try {
            if (fos != null) {
              fos.close();
            }
          } catch (IOException e) {
          }
        }
      }
    });
  }

  private void doRequest(final Request request, Callback callback) {
    mHttpClient.newCall(request).enqueue(callback);
  }

  private Request buildRequest(String url, Map<String, String> paramsBody, Map<String, String> paramsHeader) {
    System.out.println("url = " + url);
    Request.Builder builder = null;
    try {
      builder = new Request.Builder().url(url);
      if (paramsBody != null) {
        System.out.println("body string = " + paramsBody.keySet().toString());
        System.out.println("body value = " + paramsBody.values().toString());
        RequestBody body = builderFormData(paramsBody);
        builder.post(body);
      } else {
        builder.get();
      }
      Headers.Builder headerBuilder = new Headers.Builder();
      if (paramsHeader != null) {
        System.out.println("Header string = " + paramsHeader.keySet().toString());
        System.out.println("Header value = " + paramsHeader.values().toString());
        Iterator iterator = paramsHeader.entrySet().iterator();
        while (iterator.hasNext()) {
          Map.Entry entry = (Map.Entry) iterator.next();
          headerBuilder.add((String) entry.getKey(), (String) entry.getValue());
        }
        builder.headers(headerBuilder.build());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return builder.build();
  }

  private Request buildRequestUpload(String url, String paramsBody, Map<String, String> paramsHeader) {
    System.out.println("url---->" + url);
    Request.Builder builder = null;
    try {
      builder = new Request.Builder().url(url);
      if (paramsBody != null) {
        System.out.println("value-->" + paramsBody);
        RequestBody body = RequestBody.create(JSON, paramsBody);
        builder.post(body);
      } else {
        builder.get();
      }
      Headers.Builder headerBuilder = new Headers.Builder();
      if (paramsHeader != null) {
        System.out.println("string-->" + paramsHeader.keySet().toString());
        System.out.println("value-->" + paramsHeader.values().toString());
        Iterator iterator = paramsHeader.entrySet().iterator();
        while (iterator.hasNext()) {
          Map.Entry entry = (Map.Entry) iterator.next();
          headerBuilder.add((String) entry.getKey(), (String) entry.getValue());
        }
        builder.headers(headerBuilder.build());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return builder.build();
  }

  private RequestBody builderFormData(Map<String, String> params) {
    FormBody.Builder builder = new FormBody.Builder();
    if (params != null) {
      for (Map.Entry<String, String> entry : params.entrySet()) {
        builder.add(entry.getKey(), entry.getValue());
      }
    }
    return builder.build();
  }

  private MultipartBody getMultipartBody(Map<String, String> params, String fileName, List<File> files) {
    MultipartBody.Builder builder = new MultipartBody.Builder();
    builder.setType(MultipartBody.FORM);
    if (files != null) {
      for (File f : files) {
        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), f);
        builder.addFormDataPart(fileName, f.getName(), fileBody);
      }
    }
    if (params != null) {
      for (Map.Entry<String, String> entry : params.entrySet()) {
        builder.addFormDataPart(entry.getKey(), entry.getValue());
      }
      System.out.println("string-->" + params.keySet().toString());
      System.out.println("value-->" + params.values().toString());
    }
    return builder.build();
  }

  /**
   * @throws IOException 判断下载目录是否存在
   */
  public static String isExistDir(String saveDir) throws IOException {
    // 下载位置
    File downloadFile = new File(Environment.getExternalStorageDirectory(), saveDir);
    if (!downloadFile.mkdirs()) {
      downloadFile.createNewFile();
    }
    String savePath = downloadFile.getAbsolutePath();
    return savePath;
  }

  /**
   * @return 从下载连接中解析出文件名
   */

  private String getNameFromUrl(String url) {
    return url.substring(url.lastIndexOf("/") + 1);
  }

}
