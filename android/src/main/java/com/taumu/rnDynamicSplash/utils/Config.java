package com.taumu.rnDynamicSplash.utils;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class Config {
  public String themeResId = Constants.defaultTheme;
  public String layoutResId = Constants.defaultLayout;
  public String lang = null;
  public List<ElementData> data;

  public Config(Context context) {
    String jsonConfigs = Helpers.getJsonConfigs(context);
    if (jsonConfigs != null) {
      try {
        JSONObject configs = new JSONObject(jsonConfigs);

        if (configs.has("themeResId")) this.themeResId = configs.getString("themeResId");
        if (configs.has("layoutResId")) this.layoutResId = configs.getString("layoutResId");
        if (configs.has("lang")) this.lang = configs.getString("lang");

        JSONArray data = configs.has("data") ? configs.getJSONArray("data") : null;
        this.data = Helpers.getDataListFromJson(data);
      } catch (JSONException error) {
        Log.v(Constants.packageName, "Error on parsing configs JSON");
      }
    }
  }
}
