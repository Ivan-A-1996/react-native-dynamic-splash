package com.taumu.rnDynamicSplash.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;

import com.taumu.rnDynamicSplash.interfaces.Config;
import com.taumu.rnDynamicSplash.interfaces.ShowCriteria;
import com.taumu.rnDynamicSplash.interfaces.SplashData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.taumu.rnDynamicSplash.utils.FileUtils.getFileName;
import static com.taumu.rnDynamicSplash.utils.FileUtils.getLocalBitmap;

public class Helpers {
  public static SplashData getValue(@NonNull Config config) {
    Date currentDate = new Date();
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    int resultQuality = -1;
    SplashData result = null;
    for (SplashData splashData : config.splashesData) {
      int currentQuality = 0;
      if (splashData.showCriteria != null) {
        ShowCriteria showCriteria = splashData.showCriteria;
        Date startDate = null;
        Date endDate = null;
        try {
          startDate = format.parse(showCriteria.startDate);
          endDate = format.parse(showCriteria.endDate);
        } catch (ParseException e) {
          Log.v(Constants.packageName, "Date Parse error: " + e.getMessage());
        }
        if ((startDate == null || startDate.before(currentDate))
          && (endDate == null || endDate.after(currentDate))) {
          if (startDate != null && endDate != null) {
            currentQuality += 2;
          } else if (startDate != null || endDate != null) {
            currentQuality++;
          }
          if (config.currentLang != null && showCriteria.lang != null && config.currentLang.toLowerCase().startsWith(showCriteria.lang.toLowerCase())) {
            currentQuality ++;
          }
          if (config.currentTheme != null && config.currentTheme.equals(showCriteria.theme)) {
            currentQuality ++;
          }
          if (config.currentCountry != null && config.currentCountry.equals(showCriteria.country)) {
            currentQuality ++;
          }
        }
      }

      if (resultQuality < currentQuality) {
        resultQuality = currentQuality;
        result = splashData;
      }
    }

    return result;
  }

  public static String getJsonConfigs(Context context) {
    String configs = null;

    SharedPreferences preferences = context.getSharedPreferences(Constants.packageName, Context.MODE_PRIVATE);
    if (preferences.contains("configs")) {
      configs = preferences.getString("configs", null);
    }

    return configs;
  }

  public static void setJsonConfigs(Context context, String configs) {
    SharedPreferences preferences = context.getSharedPreferences(Constants.packageName, Context.MODE_PRIVATE);
    preferences.edit().putString("configs", configs).apply();
  }
}
