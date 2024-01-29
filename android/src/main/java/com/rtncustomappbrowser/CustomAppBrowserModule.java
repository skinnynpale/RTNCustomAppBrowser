package com.rtncustomappbrowser;

import androidx.annotation.NonNull;
import androidx.browser.customtabs.CustomTabsIntent;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;

import android.app.Activity;
import android.net.Uri;
import com.facebook.fbreact.specs.NativeCustomAppBrowserSpec;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.LifecycleEventListener;

public class CustomAppBrowserModule extends NativeCustomAppBrowserSpec implements LifecycleEventListener {

    public static String NAME = "RTNCustomAppBrowser";

    CustomAppBrowserModule(ReactApplicationContext context) {
        super(context);
        context.addLifecycleEventListener(this);
    }

    @Override
    @NonNull
    public String getName() {
        return NAME;
    }

    private Activity currentActivity;
    private Promise promise;

    @Override
    public void open(String url, Promise promise) {
        this.promise = promise;
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        currentActivity = getCurrentActivity();

        if (currentActivity != null) {
            customTabsIntent.launchUrl(currentActivity, Uri.parse(url));
        }
    }


    @ReactMethod
    public void close() {
        if (currentActivity != null) {
            currentActivity.finish();
            currentActivity = null;
            promise.resolve("dismiss");
        }
    }

    @Override
    public void onHostDestroy() {
        if (promise != null) {
            promise.resolve("close");
        }
    }

    @Override
    public void onHostResume() {
    }

    @Override
    public void onHostPause() {
    }
}