package com.cs.http_lib.net.transaction;


/**
 *
 */
public class TransactionException extends Exception {

  /**
   * 错误码
   */
  private int code =-1;

  /**
   *
   */
  public TransactionException() {
  }

  /**
   *
   */
  public TransactionException(String detailMessage) {
    super(detailMessage);
  }

  public TransactionException(int code, String detailMessage) {
    this(code,detailMessage,null);
  }

  /**
   *
   */
  public TransactionException(String detailMessage, Throwable throwable) {
    super(detailMessage, throwable);
  }

  public TransactionException(int code, String detailMessage, Throwable throwable) {
    super(detailMessage, throwable);
    this.code = code;
  }

  /**
   *
   */
  public TransactionException(Throwable throwable) {
    super(throwable);
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }
}




