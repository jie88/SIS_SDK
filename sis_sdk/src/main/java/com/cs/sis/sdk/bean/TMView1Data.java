//package com.cs.sis.sdk.bean;
//
//
//
//public class TMView1Data {
//  private transient TM1 tm;
//
//  public TM1 getTm() {
//    return tm;
//  }
//
//  public void setTm(TM1 tm) {
//    this.tm = tm;
//  }
//
//
//  public float getDataFloatByDataType(int dataType) {
//    String str = getDataStrByDataType(dataType);
//    if (str == "")
//      return 0.0f;
//    if (dataType == DataType_Gain)
//      return 0.0f;
//    float fData = Float.parseFloat(str);
//    return fData;
//  }
//  public String getDataStrByDataType(int dataType) {
//    int iIndex = dataType2Index(dataType);
//    if (iIndex == -1)
//      return "";
//    if (data == null)
//      return "";
//    return data[iIndex];
//  }
//
//
//}
