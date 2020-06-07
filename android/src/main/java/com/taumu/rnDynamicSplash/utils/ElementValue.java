package com.taumu.rnDynamicSplash.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class ElementValue {
  public @Nullable String startDate;
  public @Nullable String endDate;
  public @Nullable String lang;
  public String value;

  public ElementValue(@NonNull String value, @Nullable String lang, @Nullable String startDate, @Nullable String endDate) {
    this.startDate = startDate;
    this.endDate = endDate;
    this.lang = lang;
    this.value = value;
  }

  public ElementValue() {}

  public static ElementValue getDataFromJson(@NonNull JSONObject value) {
    try {
      ElementValue elementValue = new ElementValue();
      if (value.has("startDate")) elementValue.startDate = value.getString("startDate");
      if (value.has("endDate")) elementValue.endDate = value.getString("endDate");
      if (value.has("lang")) elementValue.lang = value.getString("lang");
      elementValue.value = value.getString("value");

      return elementValue;
    } catch (JSONException error) {
      Log.v(Constants.packageName, "Error on parsing elementValue JSON");

      return null;
    }
  }
}
