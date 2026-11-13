# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/hassan/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include line and the file paths to fit your project structure.

# Keep Compose view model and state classes
-keepclassmembers class * extends androidx.lifecycle.ViewModel {
    <init>(...);
}

-keep class com.tariqnaeemdeveloper.colorlink.model.** { *; }
