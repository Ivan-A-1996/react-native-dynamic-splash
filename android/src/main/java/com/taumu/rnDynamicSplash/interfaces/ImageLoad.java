package com.taumu.rnDynamicSplash.interfaces;

import com.facebook.react.bridge.Promise;

public class ImageLoad {
  public Promise promise;
  public String imageUri;

  public ImageLoad (Promise promise, String imageUri) {
    this.promise = promise;
    this.imageUri = imageUri;
  }
}
