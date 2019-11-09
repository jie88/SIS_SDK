# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
#混淆时不生成大小写混合的类名
-dontusemixedcaseclassnames
#不忽略非公共的类库
-dontskipnonpubliclibraryclasses
#混淆过程中打印详细信息
-verbose

#关闭优化
-dontoptimize
#不预校验
-dontpreverify

# Annotation注释不能混淆
-keepattributes *Annotation*
#对于NDK开发 本地的native方法不能被混淆
-keepclasseswithmembernames class * {
    native <methods>;
}
#保持View的子类里面的set、get方法不被混淆（*代替任意字符）
-keepclassmembers public class * extends android.view.View {
   void set*(***);
   *** get*();
}

#保持Activity子类里面的参数类型为View的方法不被混淆，如被XML里面应用的onClick方法
# We want to keep methods in Activity that could be used in the XML attribute onClick
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

#保持枚举类型values()、以及valueOf(java.lang.String)成员不被混淆
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

#保持实现Parcelable接口的类里面的Creator成员不被混淆
-keepclassmembers class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator CREATOR;
}

#保持R类静态成员不被混淆
-keepclassmembers class **.R$* {
    public static <fields>;
}

#不警告support包中不使用的引用
-dontwarn android.support.**
-keep class android.support.annotation.Keep
-keep @android.support.annotation.Keep class * {*;}
#保持使用了Keep注解的方法以及类不被混淆
-keepclasseswithmembers class * {
    @android.support.annotation.Keep <methods>;
}
#保持使用了Keep注解的成员域以及类不被混淆
-keepclasseswithmembers class * {
    @android.support.annotation.Keep <fields>;
}
-keepclasseswithmembers class * {
    @android.support.annotation.Keep <init>(...);
}



# QMUI
-keep class **_FragmentFinder { *; }
-keep class com.qmuiteam.qmui.arch.record.** { *; }
-keep class androidx.fragment.app.* { *; }
-keep class android.support.v4.app.* { *; }