# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\Android_studio\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
##}
-optimizationpasses 5          # 指定代码的压缩级别

-dontusemixedcaseclassnames   # 是否使用大小写混合

-dontpreverify           # 混淆时是否做预校验

-verbose                # 混淆时是否记录日志
#
-dontskipnonpubliclibraryclasses #指定不去忽略非公共库的类的成员

-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*  # 混淆时所采用的算法

-keep public class * extends android.app.Activity      # 保持哪些类不被混淆
-keep public class * extends android.app.Application   # 保持哪些类不被混淆
-keep public class * extends android.app.Service       # 保持哪些类不被混淆
-keep public class * extends android.content.BroadcastReceiver  # 保持哪些类不被混淆
-keep public class * extends android.content.ContentProvider    # 保持哪些类不被混淆
-keep public class * extends android.app.backup.BackupAgentHelper # 保持哪些类不被混淆
-keep public class * extends android.preference.Preference        # 保持哪些类不被混淆
-keep public class com.android.vending.licensing.ILicensingService    # 保持哪些类不被混淆


-keepattributes *Annotation* #保护代码中的Annotation不被混淆

-keepattributes Signature #避免混淆泛型

#生成映射文件
-verbose
-printmapping proguardMapping.txt
#抛出出异常的行号
-keepattributes SourceFile,LineNumberTable

-keepclasseswithmembernames class * {  # 保持 native 方法不被混淆
    native <methods>;
}


-keepclasseswithmembers class * extends android.view.View{# 保持自定义控件类不被混淆
  *** get*();
  void set*(***);
  public <init>(android.content.Context);
   public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclassmembers class * extends android.app.Activity { # 保留在activity的方法中参数是view的方法
    public void *(android.view.View);
}
-keepclassmembers enum * {     # 保持枚举 enum 类不被混淆
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep class * implements android.os.Parcelable { # 保持 Parcelable 不被混淆
    public static final android.os.Parcelable$Creator *;
}

#保持support包不被混淆
-keep public class * extends android.support.**

#保持 Serializable 不被混淆
-keepnames class * implements java.io.Serializable
-keepclassmembers class * implements java.io.Serializable{

 static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    !private <fields>;
    !private <methods>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

#保持R下的所有类及其方法，都不能被混淆
#不混淆资源类
-keepclassmembers class **.R$* {
    public static <fields>;
}

#保持onXXEvent的回调，不能被混淆
-keepclassmembers class *{
  void *(**On*Event);
}
#Xutils3
-keepattributes Signature,*Annotation*
-keep public class org.xutils.** {
    public protected *;
}
-keep public interface org.xutils.** {
    public protected *;
}
-keepclassmembers class * extends org.xutils.** {
    public protected *;
}
-keepclassmembers @org.xutils.db.annotation.* class * {*;}
-keepclassmembers @org.xutils.http.annotation.* class * {*;}
-keepclassmembers class * {
    @org.xutils.view.annotation.Event <methods>;
}

# Gson
-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*

# Gson specific classes
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }

# Application classes that will be serialized/deserialized over Gson
-keep class com.shizhanzhe.szzschool.Bean.** { *; }

# 极光推送
-dontoptimize
-dontpreverify
-dontwarn cn.jpush.**
-keep class cn.jpush.** { *; }

# OkHttp3
-dontwarn com.squareup.okhttp3.**
-keep class com.squareup.okhttp3.** { *;}
-dontwarn okio.**
# 支付宝
#-libraryjars libs/alipaySingle-20170510.jar

-keep class com.alipay.android.app.IAlixPay{*;}
-keep class com.alipay.android.app.IAlixPay$Stub{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
-keep class com.alipay.sdk.app.PayTask{ public *;}
-keep class com.alipay.sdk.app.AuthTask{ public *;}
# ShareSDK
-keep class cn.sharesdk.**{*;}
-keep class com.sina.**{*;}
-keep class **.R$* {*;}
-keep class **.R{*;}
-keep class com.mob.**{*;}
-dontwarn com.mob.**
-dontwarn cn.sharesdk.**
-dontwarn **.R$*

# glide 的混淆代码
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
# banner 的混淆代码
-keep class com.youth.banner.** {
    *;
 }
 #okhttp
 -keepattributes Signature
 -keepattributes *Annotation*
 -keep class com.squareup.okhttp.** { *; }
 -keep interface com.squareup.okhttp.** { *; }
 -dontwarn com.squareup.okhttp.**

 #okio
 -keep class sun.misc.Unsafe { *; }
 -dontwarn java.nio.file.*
 -dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
 -dontwarn okio.**
 #视频
-ignorewarnings 	 # 忽略警告，避免打包时某些警告出现
-optimizationpasses 5	 #  指定代码的压缩级别
-dontusemixedcaseclassnames	 # 是否使用大小写混合
-dontskipnonpubliclibraryclasses	 # 是否混淆第三方jar
-verbose	 # 混淆时是否记录日志
-dontpreverify	 # 混淆时是否做预校验

-keep class com.easefun.polyvsdk.**{*;}	 # 保持哪些类不被混淆
-keep class tv.danmaku.ijk.media.**{*;}	 # 保持哪些类不被混淆
-keep public class tv.danmaku.ijk.media.player.IjkMediaPlayer {*;} # 保持这个类的类变量不被混淆
-keep @interface tv.danmaku.ijk.media.**{*;}	 # 保持哪些类不被混淆
-keep class com.nostra13.universalimageloader.**{*;}	# 保持哪些类不被混淆
-keep class org.apache.http.**{*;}	 # 保持哪些类不被混淆
-keep class org.apache.commons.**{*;}	 # 保持哪些类不被混淆
-keep public class com.tencent.bugly.**{*;}	 # 保持哪些类不被混淆

-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}
-keep class android.support.**{*;}
-keepattributes EnclosingMetho

-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}

#地区3级联动选择器

-keep class com.lljjcoder.**{
	*;
}

-dontwarn demo.**
-keep class demo.**{*;}
-dontwarn net.sourceforge.pinyin4j.**
-keep class net.sourceforge.pinyin4j.**{*;}
-keep class net.sourceforge.pinyin4j.format.**{*;}
-keep class net.sourceforge.pinyin4j.format.exception.**{*;}