package com.cs.http_lib.net.transaction;


import com.google.gson.Gson;

/**
 * author : ${CHENJIE} created at  2019-11-08 22:21 e_mail : chenjie_goodboy@163.com describle :
 */
public abstract class TransactionResponse {

  /**
   * 解析响应结果
   *
   * 当框架无法对响应报文进行正常解析时，可在此重写该方法进行返回报文体解析
   * 如果仅仅是不同业务功能对返回值的处理差异，则不应在此进行特别处理，而应在具体业务模块中实现
   *
   * @param result 请求返回报文
   * @param <T> 目标类型
   * @return 响应对象
   * @throws TransactionException 交易异常
   */
  public <T> T parseResult(String result) throws TransactionException {
    Gson gson = new Gson();
    return (T) gson.fromJson(result, getClass());
  }

  public String toJson(){
    Gson gson=new Gson();
    return gson.toJson(this).toString();
  }

}
