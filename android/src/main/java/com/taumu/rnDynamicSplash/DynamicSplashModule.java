package com.taumu.rnDynamicSplash;

import android.content.Context;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.taumu.rnDynamicSplash.interfaces.ImageLoad;
import com.taumu.rnDynamicSplash.utils.Constants;
import com.taumu.rnDynamicSplash.utils.FileUtils;
import com.taumu.rnDynamicSplash.utils.Helpers;

import static com.taumu.rnDynamicSplash.DynamicSplash.mDynamicSplash;

public class DynamicSplashModule extends ReactContextBaseJavaModule {
  private Context context;

  public DynamicSplashModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.context = reactContext;
  }

  @Override
  public String getName() {
    return Constants.packageName;
  }

  @ReactMethod
  public void hide() {
    mDynamicSplash.hide(getCurrentActivity());
  }

  @ReactMethod
  public void setConfigs(String configs, Promise promise) {
    Helpers.setJsonConfigs(context, configs);
    promise.resolve(null);
  }

  @ReactMethod
  public void getConfigs(Promise promise) {
    String jsonConfigs = Helpers.getJsonConfigs(context);
    if (jsonConfigs != null) {
      promise.resolve(jsonConfigs);
    } else {
      promise.reject("0", "Configs not exist");
    }
  }

  @ReactMethod
  public void downloadImage(Promise promise, String imageUri) {
    new DynamicSplashDownLoad.DownloadAsyncTask().execute(new ImageLoad(promise, imageUri));
  }

  @ReactMethod
  public void deleteImages() {
    FileUtils.deleteFiles();
  }
}
