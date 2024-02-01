package com.rtncustomappbrowser;

import androidx.annotation.Nullable;

import com.facebook.react.TurboReactPackage;
import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.module.model.ReactModuleInfo;
import com.facebook.react.module.model.ReactModuleInfoProvider;
import com.facebook.react.uimanager.ViewManager;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomAppBrowserPackage extends TurboReactPackage {

  @Nullable
  @Override
  public NativeModule getModule(String name, ReactApplicationContext reactContext) {
      if (name.equals(CustomAppBrowserModule.NAME)) {
          return new CustomAppBrowserModule(reactContext);
      } else {
          return null;
      }
  }

  @Override
  public ReactModuleInfoProvider getReactModuleInfoProvider() {
     return () -> {
         final Map<String, ReactModuleInfo> moduleInfos = new HashMap<>();
         moduleInfos.put(
                 CustomAppBrowserModule.NAME,
                 new ReactModuleInfo(
                         CustomAppBrowserModule.NAME,
                         CustomAppBrowserModule.NAME,
                         false, // canOverrideExistingModule
                         false, // needsEagerInit
                         true, // hasConstants
                         false, // isCxxModule
                         true // isTurboModule
         ));
         return moduleInfos;
     };
  }

      @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
      return Arrays.<NativeModule>asList(new CustomAppBrowserModule(reactContext));
    }

    // Deprecated from RN 0.47
    public List<Class<? extends JavaScriptModule>> createJSModules() {
      return Collections.emptyList();
    }

    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
      return Collections.emptyList();
    }
}