package com.taumu.rnDynamicSplash.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Config {
  String currentLang = null;
  String currentTheme = null;
  String currentCountry = null;
  List<SplashData> splashesData;

  public Config(Context context) {
    String jsonConfigs = Helpers.getJsonConfigs(context);
    if (jsonConfigs != null) {
      try {
        JSONObject configs = new JSONObject(jsonConfigs);

        if (configs.has("currentLang")) {
          this.currentLang = configs.getString("currentLang");
        } else {
          this.currentLang = Locale.getDefault().getLanguage();
        }
        if (configs.has("currentCountry")) {
          this.currentCountry = configs.getString("currentCountry");
        } else {
          this.currentCountry = Locale.getDefault().getCountry();
        }
        if (configs.has("currentTheme")) {
          this.currentTheme = configs.getString("currentTheme");
        } else {
          int nightModeFlags =
            context.getResources().getConfiguration().uiMode &
              Configuration.UI_MODE_NIGHT_MASK;
          switch (nightModeFlags) {
            case Configuration.UI_MODE_NIGHT_YES:
            case Configuration.UI_MODE_NIGHT_UNDEFINED:
              this.currentTheme = "night";
              break;
            case Configuration.UI_MODE_NIGHT_NO:
              this.currentTheme = "light";
              break;
          }
        }

        if (configs.has("splashesData")) {
          this.splashesData = Parse.getSplashesDataFromJson(configs.getJSONArray("splashesData"));
        } else {
          this.splashesData = new ArrayList<>();
        }
      } catch (JSONException error) {
        Log.v(Constants.packageName, "Error on parsing configs JSON");
      }
    }
  }
}
