package com.taumu.rnDynamicSplash.interfaces;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.taumu.rnDynamicSplash.interfaces.ElementValue;
import com.taumu.rnDynamicSplash.interfaces.ShowCriteria;
import com.taumu.rnDynamicSplash.utils.Constants;

import java.util.List;

public class SplashData {
  public @NonNull List<ElementValue> elementsData;
  public @NonNull String themeName = Constants.defaultTheme;
  public @NonNull String layoutName = Constants.defaultLayout;
  public @Nullable ShowCriteria showCriteria;

  public SplashData(@NonNull List<ElementValue> elementsData) {
    this.elementsData = elementsData;
  }
}
