package com.cs.sis.sdk.view.tm30;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.cs.sis.sdk.bean.TM1;
import com.cs.sis.sdk.bean.TMView1Data;
import com.cs.sis.sdk.utils.Tool;


@SuppressLint("ResourceAsColor")
public class TMView1 extends View {
  private static final int PADDING_LEFT = 100;
  private static final int PADDING_RIGHT = 50;
  private static final int PADDING_TOP = 200;
  private static final int PADDING_BOTTOM = 100;
  private static final int Y_LINE = 7;
  private static final int X_LINE = 9;
  public float[] Data_X = new float[401]; // 数据
  public float[] Data_Y = new float[401]; // 数据
  private static final int TEXT_SIZE = 25;
  private static final float INTERVAL = 0.2f;
  TM1 tTm = null;
  TMView1Data data = null;

  /**
   * 光谱数据
   */
  public  float[] spectrum_num;


  public TMView1(Context context, AttributeSet attr) {
    super(context, attr);
  }

  public void setInfo(DisplayMetrics dm, TMView1Data data) {
    tTm = data.getTm();
    this.data = data;
  }

  @SuppressLint("DrawAllocation")
  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);// 重写onDraw方法
    Paint paint1 = new Paint();
    paint1.setAntiAlias(true);// 去锯齿
    paint1.setColor(Color.BLACK);
    paint1.setStrokeWidth(2f);
    paint1.setTextSize(TEXT_SIZE);

    Paint p1 = new Paint();
    p1.setAntiAlias(true);
    p1.setStyle(Paint.Style.STROKE);
    p1.setColor(Color.GRAY);
    p1.setStrokeWidth(1);

    int chartHeight = this.getHeight() - PADDING_BOTTOM - PADDING_TOP;
    int chartWidth = this.getWidth() - PADDING_LEFT - PADDING_RIGHT;

    canvas.drawText("Rf     ", PADDING_LEFT, PADDING_TOP - 170, paint1);
    canvas.drawText("Rg     ", PADDING_LEFT, PADDING_TOP - 120, paint1);
    canvas.drawText("CCT(K)     ", PADDING_LEFT + 200, PADDING_TOP - 170, paint1);
    canvas.drawText("Duv     ", PADDING_LEFT + 200, PADDING_TOP - 120, paint1);
    canvas.drawText("u＇     ", PADDING_LEFT + 200, PADDING_TOP - 70, paint1);
    canvas.drawText("v＇     ", PADDING_LEFT + 200, PADDING_TOP - 20, paint1);
    canvas.drawText("SPD_REF", PADDING_LEFT - 25, PADDING_TOP - 20, paint1);
    canvas.drawText("Wavelength(nm)", PADDING_LEFT + chartWidth / 2 - 50, PADDING_TOP + chartHeight + 80, paint1);

    canvas.drawLine(PADDING_LEFT, PADDING_TOP, PADDING_LEFT,
        PADDING_TOP + chartHeight, paint1); //Y轴
    canvas.drawLine(PADDING_LEFT, PADDING_TOP + chartHeight, PADDING_LEFT + chartWidth,
        PADDING_TOP + chartHeight, paint1);//X轴

    for (int i = 0; i < X_LINE; i++) {// 横坐标
      canvas.drawLine(PADDING_LEFT + (chartWidth / (float) (X_LINE - 1)) * i,
          PADDING_TOP + chartHeight,
          PADDING_LEFT + (chartWidth / (float) (X_LINE - 1)) * i,
          PADDING_TOP + chartHeight + 10, paint1);
      canvas.drawText((380 + 50 * i) + "",
          PADDING_LEFT + (chartWidth / (float) (X_LINE - 1)) * i - 10,
          PADDING_TOP + chartHeight + 40, paint1);
      if (i != 0) {
        canvas.drawLine(PADDING_LEFT + (chartWidth / (float) (X_LINE - 1)) * i, PADDING_TOP,
            PADDING_LEFT + (chartWidth / (float) (X_LINE - 1)) * i,
            PADDING_TOP + chartHeight, p1);
      }
    }

    for (int i = 0; i < Y_LINE; i++) {
      canvas.drawLine(PADDING_LEFT - 10, PADDING_TOP + (chartHeight / (float) (Y_LINE - 1)) * i,
          PADDING_LEFT, PADDING_TOP + (chartHeight / (float) (Y_LINE - 1)) * i, paint1);
      canvas.drawText(Tool.df1.format((6 - i) * INTERVAL) + "",
          PADDING_LEFT - 55,
          PADDING_TOP + (chartHeight / (float) (Y_LINE - 1)) * i + 15, paint1);
      if (i != (Y_LINE - 1)) {
        canvas.drawLine(PADDING_LEFT, PADDING_TOP + (chartHeight / (float) (Y_LINE - 1)) * i,
            PADDING_LEFT + chartWidth,
            PADDING_TOP + (chartHeight / (float) (Y_LINE - 1)) * i, p1);
      }
    }

    paint1.setColor(Color.RED);
    canvas.drawLine(PADDING_LEFT + 20, PADDING_TOP + 20, PADDING_LEFT + 70,
        PADDING_TOP + 20, paint1);
    canvas.drawText("Test source", PADDING_LEFT + 80, PADDING_TOP + 30, paint1);

    paint1.setColor(Color.BLACK);
    canvas.drawLine(PADDING_LEFT + 300, PADDING_TOP + 20, PADDING_LEFT + 350,
        PADDING_TOP + 20, paint1);
    canvas.drawText("Reference source", PADDING_LEFT + 360, PADDING_TOP + 30, paint1);

    //绘制数据
    if (tTm != null) {
      paint1.setTextSize(TEXT_SIZE);
      canvas.drawText("Rf     " + Tool.df0.format(tTm.RF_all), PADDING_LEFT,
          PADDING_TOP - 170, paint1);
      canvas.drawText("Rg     " + Tool.df0.format(tTm.Rg), PADDING_LEFT,
          PADDING_TOP - 120, paint1);
      canvas.drawText("CCT(K)     " + Tool.df0.format(data.getDataFloatByDataType(TMView1Data.DataType_CCT)),
          PADDING_LEFT + 200, PADDING_TOP - 170, paint1);
      canvas.drawText("Duv     " + Tool.df5.format(data.getDataFloatByDataType(TMView1Data.DataType_duv)),
          PADDING_LEFT + 200, PADDING_TOP - 120, paint1);
      canvas.drawText("u＇     " + Tool.df4.format(data.getDataFloatByDataType(TMView1Data.DataType_u_)),
          PADDING_LEFT + 200, PADDING_TOP - 70, paint1);
      canvas.drawText("v＇     " + Tool.df4.format(data.getDataFloatByDataType(TMView1Data.DataType_v_)),
          PADDING_LEFT + 200, PADDING_TOP - 20, paint1);

      for (int i = 0; i < Data_X.length; i++) {
        Data_X[i] = PADDING_LEFT + i * (chartWidth / (float) (Data_X.length - 1));
        Data_Y[i] = PADDING_TOP + chartHeight - (chartHeight / (float) (Y_LINE - 1)) *
            (tTm.SPD_REF[i]) / INTERVAL;
      }
      for (int i = 0; i < tTm.SPD_REF.length - 1; i++) {
        canvas.drawLine(Data_X[i], Data_Y[i], Data_X[i + 1], Data_Y[i + 1], paint1);
      }

      paint1.setColor(Color.RED);
      float[] fB = new float[41];
      float fMax = 0;
      for (int i = 0; i < fB.length; i++) {
        fB[i] = spectrum_num[(spectrum_num.length - 1) /
            (fB.length - 1) * i];
        if (fMax < fB[i]) {
          fMax = fB[i];
        }
      }
      for (int i = 0; i < fB.length; i++) {
        fB[i] = fB[i] / fMax;
      }
      for (int i = 0; i < fB.length - 1; i++) {
        canvas.drawLine(PADDING_LEFT + i * (chartWidth / (float) (fB.length - 1)),
            PADDING_TOP + chartHeight - (chartHeight / (float) (Y_LINE - 1)) *
                (fB[i] / INTERVAL),
            PADDING_LEFT + (i + 1) * (chartWidth / (float) (fB.length - 1)),
            PADDING_TOP + chartHeight - (chartHeight / (float) (Y_LINE - 1)) *
                (fB[i + 1] / INTERVAL), paint1);
      }
    }
  }
}