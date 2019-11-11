package com.cs.sis.sdk.bean;



public class TMView1Data {

  private String[] data = new String[100];
  public void setData(String[] data) {
    this.data = data;

  }

  static public int DataType_CCT = 8;
  static public int DataType_duv = 105;
  static public int DataType_u_ = 6;
  static public int DataType_v_ = 7;

  private transient TM1 tm;

  public TM1 getTm() {
    return tm;
  }

  public void setTm(TM1 tm) {
    this.tm = tm;
  }


  public float getDataFloatByDataType(int dataType) {
    String str = getDataStrByDataType(dataType);
    if (str == "") {
      return 0.0f;
    }
    float fData = Float.parseFloat(str);
    return fData;
  }
  public String getDataStrByDataType(int dataType) {
    int iIndex = dataType2Index(dataType);
    if (iIndex == -1) {
      return "";
    }
    if (data == null) {
      return "";
    }
    return data[iIndex];
  }

  public int dataType2Index(int dataType) {
//    for (int i = 0; i < listDataTypeNum; i++) {
//      if (listDataType[i] == dataType) {
//        return i;
//      }
//    }
    return -1;
  }

}
