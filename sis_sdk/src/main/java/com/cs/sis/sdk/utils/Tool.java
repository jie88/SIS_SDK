package com.cs.sis.sdk.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tool {
    public static final String Tag = "SPIC-300";
    private static Tool Instance = null;
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static DecimalFormat df0 = new DecimalFormat("#####0");
    public static DecimalFormat df1 = new DecimalFormat("#####0.0");
    public static DecimalFormat df2 = new DecimalFormat("#####0.00");
    public static DecimalFormat df3 = new DecimalFormat("#####0.000");
    public static DecimalFormat df4 = new DecimalFormat("#####0.0000");
    public static DecimalFormat df5 = new DecimalFormat("#####0.00000");
    public static DecimalFormat df6 = new DecimalFormat("#####0.000000");
    public static DecimalFormat df14 = new DecimalFormat("@@@@");
    public static DecimalFormat df15 = new DecimalFormat("@@@@@");
    public static DecimalFormat df04E = new DecimalFormat("0.0000E0");
    public static DecimalFormat df01E = new DecimalFormat("0.0E0");

    static DecimalFormat df7 = new DecimalFormat("#####0.0000000000");

    public static float fFomat(float f) {
        return Float.parseFloat(df7.format(f));
    }

    public static Tool getInstance() {
        if (Instance == null)
            Instance = new Tool();
        return Instance;
    }

    public static float getNewtonInt(float[] xa, float[] ya, int n, float x) {
        // Newton's D-value
        int i, k = 1;
        float u;
        for (i = 1; i <= n - 2; i++) {
            if (x <= xa[i]) {
                k = i;
                break;
            } else {
                k = n - 1;
            }
        }
        u = (x - xa[k - 1]) / (xa[k] - xa[k - 1]);
        return ya[k - 1] + u * (ya[k] - ya[k - 1]);
    }




    public static boolean CreateTestFile() {
        // 循环测试自动保存
        String autoSaveName = null;
        autoSaveName = "test";
        String path = android.os.Environment.getExternalStorageDirectory()
                + "/" + autoSaveName + ".sp";
        File mAutoSaveFile = new File(path);
        if (mAutoSaveFile.exists()) {
            return true;
        }
        try {
            mAutoSaveFile.createNewFile();
            if (mAutoSaveFile.exists()) {
                mAutoSaveFile.delete();
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }


    public static boolean execRootCmdSlient(String cmd) {
        try {
            Process localProcess = Runtime.getRuntime().exec("/system/bin/sh");
            Object localObject = localProcess.getOutputStream();
            DataOutputStream localDataOutputStream = new DataOutputStream(
                    (OutputStream) localObject);
            String str = String.valueOf(cmd);
            localObject = str + "\n";
            localDataOutputStream.writeBytes((String) localObject);
            localDataOutputStream.flush();
            localDataOutputStream.writeBytes("exit\n");
            localDataOutputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @SuppressWarnings("deprecation")
    public static String execRootCmd(String cmd) {
        String result = "";
        DataOutputStream dos = null;
        DataInputStream dis = null;

        try {
            Process p = Runtime.getRuntime().exec("");
            dos = new DataOutputStream(p.getOutputStream());
            dis = new DataInputStream(p.getInputStream());
            System.out.println(cmd);

            dos.writeBytes(cmd + "\n");
            dos.flush();
            dos.writeBytes("exit\n");
            dos.flush();

            String line = null;
            while ((line = dis.readLine()) != null) {
                result += line;
            }
            p.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (dos != null) {
                try {
                    dos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (dis != null) {
                try {
                    dis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;

    }

    public static int execRootCmdSilent(String cmd) {
        int result = -1;
        DataOutputStream dos = null;

        try {
            Process p = Runtime.getRuntime().exec("su");
            dos = new DataOutputStream(p.getOutputStream());
            System.out.println(cmd);

            dos.writeBytes(cmd + "\n");
            dos.flush();
            dos.writeBytes("exit\n");
            dos.flush();
            p.waitFor();
            result = p.exitValue();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (dos != null) {
                try {
                    dos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * 安装APK文件
     */
    public void installApk(Context mContext, File apkfile) {
        if (!apkfile.exists()) {
            return;
        }
        // 通过Intent安装APK文件
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setDataAndType(Uri.parse("file://" + apkfile.toString()),
                "application/vnd.android.package-archive");
        mContext.startActivity(i);
    }

    /* 卸载apk */
    public static void uninstallApk(Context context, String packageName) {
        Uri uri = Uri.parse("package:" + packageName);
        Intent intent = new Intent(Intent.ACTION_DELETE, uri);
        context.startActivity(intent);
    }

    @SuppressLint("UseValueOf")
    public static byte[] intToByte(int number) {
        int temp = number;
        byte[] b = new byte[4];
        for (int i = 0; i < b.length; i++) {
            b[i] = new Integer(temp & 0xff).byteValue();// 将最低位保存在最低位
            temp = temp >> 8; // 向右移8位
        }
        return b;
    }

    public static int byteToInt(byte[] b) {
        int s = 0;
        int s0 = b[1] & 0xff;// 最高位
        int s1 = b[0] & 0xff;
        s0 <<= 8;
        s = s0 | s1;
        return s;
    }

    private static final String FILENAME_POWER_KEY = "/sys/devices/platform/virtual-key/key";

    // 通过传按键实现响应
    public static boolean WriteKeyForRespond(String key) {
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(FILENAME_POWER_KEY), 256);
        } catch (IOException e1) {
            e1.printStackTrace();
            return false;
        }

        boolean res = false;

        try {
            writer.write(key);
            res = true;
        } catch (IOException e) {
            res = false;
            Log.d(Tool.Tag, "IO Exception when", e);
        }

        try {
            writer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return res;
    }

    private static final String FILENAME_BACKLIGHT = "/sys/devices/platform/pwm-backlight.0/backlight/pwm-backlight.0/brightness";

    // 读取背光亮度
    @SuppressWarnings("resource")
    public static int getbrightness() {
        String backlight = "";
        int brightness = 0;
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(FILENAME_BACKLIGHT));
            if ((backlight = reader.readLine()) != null) {
                brightness = Integer.parseInt(backlight);
                return brightness;
            } else
                return brightness;
        } catch (IOException e1) {
            e1.printStackTrace();
            return brightness;
        }

    }

    private static final String FILENAME_BATTERY = "/sys/devices/platform/mxc-fake-battery/power_supply/battery/status";

    // 读取背光亮度
    @SuppressWarnings("resource")
    public static boolean getbatterystatus() {
        String str = "";
        boolean status = false;
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(FILENAME_BATTERY));
            if ((str = reader.readLine()) != null) {
                if (str.indexOf("Not") >= 0)
                    status = false;
                else
                    status = true;
                return status;
            } else
                return status;
        } catch (IOException e1) {
            e1.printStackTrace();
            return status;
        }

    }

    // 设置屏幕灭屏时间为 30分钟
    public void setSystemOffTime(Context mContext, int minute) {
        try {
            Settings.System.putInt(mContext.getContentResolver(),
                    Settings.System.SCREEN_OFF_TIMEOUT,
                    minute * 60 * 1000);
        } catch (Exception localException) {
            localException.printStackTrace();
        }
    }

    public static float getMax(float[] list, int num) {
        if (list == null || num > list.length) {
            return 0.0f;
        }
        float max = 0.0f;
        for (int i = 0; i < num; i++) {
            if (list[i] > max) {
                max = list[i];
            }
        }
        return max;
    }

    public static float getMin(float[] array) {
        if (array == null) {
            return 0;
        }
        float tempMin = array[0];
        for (float a : array) {
            if (tempMin > a) {
                tempMin = a;
            }
        }
        return tempMin;
    }

    public static float getAvg(float[] list, int num) {
        if (list == null || num > list.length) {
            return 0.0f;
        }
        float fAvg = 0.0f;
        for (int i = 0; i < num; i++) {
            fAvg += list[i];
        }
        fAvg /= num;
        return fAvg;
    }

    /**
     * 获得应用版本号
     *
     * @param context
     * @return
     */

    public static String packageName(Context context) {
        PackageManager manager = context.getPackageManager();
        String name = null;
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            name = info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return name;
    }

    /**
     * 获得应用名称
     *
     * @param context
     * @return
     */
    public static String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /* Java 验证Ip是否合法 */
    public boolean isIPAddress(String ipaddr) {
        boolean flag = false;
        Pattern pattern = Pattern.compile(
                "\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b");
        Matcher m = pattern.matcher(ipaddr);
        flag = m.matches();
        return flag;
    }

    public static String getDate(String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());
    }

    public static void showToast(final String str, final Activity cxt) {
        if (cxt != null && str != null) {
            cxt.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(cxt, str, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
