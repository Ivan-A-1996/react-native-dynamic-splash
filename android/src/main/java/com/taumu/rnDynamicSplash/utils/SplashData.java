package com.taumu.rnDynamicSplash.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

public class SplashData {
  public @NonNull List<ElementValue> elementsData;
  public @NonNull String themeName = Constants.defaultTheme;
  public @NonNull String layoutName = Constants.defaultLayout;
  @Nullable ShowCriteria showCriteria;

  SplashData(@NonNull List<ElementValue> elementsData) {
    this.elementsData = elementsData;
  }
}
