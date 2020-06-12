package com.taumu.rnDynamicSplash.interfaces;

import android.support.annotation.NonNull;

public class ElementValue {
  public @NonNull String elementId;
  public @NonNull Integer type;
  public @NonNull String value;

  public ElementValue(@NonNull String elementId, @NonNull Integer type, @NonNull String value) {
    this.elementId = elementId;
    this.type = type;
    this.value = value;
  }
}
