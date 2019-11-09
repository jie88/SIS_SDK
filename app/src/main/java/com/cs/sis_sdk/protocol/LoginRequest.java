package com.cs.sis_sdk.protocol;


import com.cs.http_lib.net.BaseRequest;

/**
 * author : ${CHENJIE} created at  2019-11-09 08:18 e_mail : chenjie_goodboy@163.com describle :
 */
public class LoginRequest extends BaseRequest<LoginResponse> {

  /**
   * username : 15521080780 password : jie09108122 machinecode : BEB0CE78-B708-4594-BCD6-08D8AA698C22
   * appVersion : 2.00.108 appkey : EverSpex
   */

  public LoginRequest() {
    super(LoginResponse.class);
  }


  @Parameter
  public String username = "15521080780";
  @Parameter
  public String password = "jie09108122";
  @Parameter
  public String machinecode = "45345672345098";

  @Parameter
  public String appkey = "EverSpex";



}
