package com.cs.sis.sdk.bean;


/**
 * author : ${CHENJIE} created at  2019-11-08 00:06 e_mail : chenjie_goodboy@163.com describle :
 */
public class SetBean {
  private String leftStr;
  private String rightStr;

  public SetBean(String leftStr, String rightStr) {
    this.leftStr = leftStr;
    this.rightStr = rightStr;
  }

  public String getLeftStr() {
    return leftStr;
  }

  public void setLeftStr(String leftStr) {
    this.leftStr = leftStr;
  }

  public String getRightStr() {
    return rightStr;
  }

  public void setRightStr(String rightStr) {
    this.rightStr = rightStr;
  }
}
