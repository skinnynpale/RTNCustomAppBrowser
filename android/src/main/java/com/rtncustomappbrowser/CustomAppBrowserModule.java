package com.rtncustomappbrowser;

import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.fbreact.specs.NativeCustomAppBrowserSpec;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;

import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;

public class CustomAppBrowserModule extends NativeCustomAppBrowserSpec  {

    public static String NAME = "RTNCustomAppBrowser";
    private final ReactApplicationContext reactContext;

    CustomAppBrowserModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

    @Override
    @NonNull
    public String getName() {
        return NAME;
    }


  @ReactMethod
  public void open(String url, @Nullable ReadableMap options, Promise promise) {
    final Activity activity = getCurrentActivity();
    RNInAppBrowser.getInstance().open(this.reactContext, options, promise, activity, url);
  }

  @ReactMethod
  public void close() {
    RNInAppBrowser.getInstance().close();
  }

  @ReactMethod
  public void isAvailable(final Promise promise) {
    RNInAppBrowser.getInstance().isAvailable(this.reactContext, promise);
  }

  public static void onStart(final Activity activity) {
    RNInAppBrowser.getInstance().onStart(activity);
  }

  @ReactMethod
  public void warmup(final Promise promise) {
    RNInAppBrowser.getInstance().warmup(promise);
  }

  @ReactMethod
  public void mayLaunchUrl(final String mostLikelyUrl, final ReadableArray otherUrls) {
    RNInAppBrowser.getInstance().mayLaunchUrl(mostLikelyUrl, otherUrls);
  }

}