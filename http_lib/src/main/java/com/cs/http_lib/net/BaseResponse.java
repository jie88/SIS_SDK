package com.cs.http_lib.net;


import com.cs.http_lib.net.transaction.TransactionResponse;

/**
 * author : ${CHENJIE} created at  2019-11-08 22:19 e_mail : chenjie_goodboy@163.com describle :
 */
public abstract class BaseResponse extends TransactionResponse {

  public int status;
  public String error;



}
