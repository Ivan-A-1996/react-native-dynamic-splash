package com.taumu.rnDynamicSplash.utils;

import android.support.annotation.NonNull;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

class Parse {
  static List<SplashData> getSplashesDataFromJson(@NonNull JSONArray jsonData) {
    try {
      List<SplashData> data = new ArrayList<>();
      int index = 0;
      JSONObject elem = jsonData.getJSONObject(index);
      while (elem != null) {
        SplashData splashData = Parse.getSplashDataFromJson(elem);
        if (splashData != null) {
          data.add(splashData);
        }

        elem = jsonData.getJSONObject(++index);
      }

      if (data.size() == 0) {
        Log.v(Constants.packageName, "Provided splashesData is invalid");
      }

      return data;
    } catch (JSONException error) {
      Log.v(Constants.packageName, "Error on parsing splashesData JSON");

      return null;
    }
  }

  private static SplashData getSplashDataFromJson(JSONObject jsonSplashData) {
    try {
      List<ElementValue> values = new ArrayList<>();
      JSONArray jsonValues = jsonSplashData.getJSONArray("elementsData");
      int index = 0;
      JSONObject elem = jsonValues.getJSONObject(index);
      while (elem != null) {
        ElementValue value = Parse.getElementDataFromJson(elem);
        if (value != null) values.add(value);

        elem = jsonValues.getJSONObject(++index);
      }
      if (values.size() != 0) {
        SplashData data = new SplashData(values);

        if (jsonSplashData.has("themeName")) data.themeName = jsonSplashData.getString("themeName");
        if (jsonSplashData.has("layoutName")) data.layoutName = jsonSplashData.getString("layoutName");

        if (jsonSplashData.has("showCriteria")) {
          JSONObject jsonCriteria = jsonSplashData.getJSONObject("showCriteria");
          ShowCriteria showCriteria = new ShowCriteria();
          if (jsonCriteria.has("startDate")) showCriteria.startDate = jsonCriteria.getString("startDate");
          if (jsonCriteria.has("endDate")) showCriteria.endDate = jsonCriteria.getString("endDate");
          if (jsonCriteria.has("lang")) showCriteria.lang = jsonCriteria.getString("lang");
          if (jsonCriteria.has("theme")) showCriteria.lang = jsonCriteria.getString("theme");
          if (jsonCriteria.has("region")) showCriteria.lang = jsonCriteria.getString("region");
          data.showCriteria = showCriteria;
        }

        return data;
      } else {
        Log.v(Constants.packageName, "Provided splashData is invalid");

        return null;
      }
    } catch (JSONException error) {
      Log.v(Constants.packageName, "Error on parsing splashData JSON" + error.getMessage());

      return null;
    }
  }

  private static ElementValue getElementDataFromJson(@NonNull JSONObject value) {
    try {
      return new ElementValue(
        value.getString("elementId"),
        value.getInt("type"),
        value.getString("value")
      );
    } catch (JSONException error) {
      Log.v(Constants.packageName, "Error on parsing elementData JSON");

      return null;
    }
  }
}
