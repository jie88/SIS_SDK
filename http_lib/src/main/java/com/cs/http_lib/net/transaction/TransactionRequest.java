package com.cs.http_lib.net.transaction;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 *
 * @param <T>
 */
public abstract class TransactionRequest<T extends TransactionResponse>{







  /**
   * 交易结果对象类
   */
  protected Class<T> responseClass;

  public TransactionRequest(Class<T> responseClass) {
    this.responseClass = responseClass;
  }


  /**
   * 请求参数注释，标志该属性为请求参数
   */
  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.FIELD)
  public static @interface Parameter {

    /**
     * 参数名称
     */
    String name() default "";

    /**
     * 是否为必须参数，即如果是空值也要上传
     */
    boolean required() default true;

    /**
     * 参数的字符编码
     */
    String charset() default "";

  }

  /**
   * 是否使用请求缓存标志
   */
  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.FIELD)
  public static @interface RequestCache {

  }




}
