package com.taumu.rnDynamicSplash.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;

import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.common.ImageDecodeOptions;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.image.CloseableBitmap;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

  public static void setImageFromFresco(String uri, ImageView imageView, Context context) {
    ImagePipeline imagePipeline = Fresco.getImagePipeline();
    ImageDecodeOptions decodeOptions = ImageDecodeOptions.newBuilder()
      .build();

    ImageRequest request = ImageRequestBuilder
      .newBuilderWithSource(Uri.parse(uri))
      .setImageDecodeOptions(decodeOptions)
      .setAutoRotateEnabled(true)
      .setLocalThumbnailPreviewsEnabled(true)
      .setLowestPermittedRequestLevel(ImageRequest.RequestLevel.DISK_CACHE)
      .setProgressiveRenderingEnabled(false)
      .build();
    DataSource<CloseableReference<CloseableImage>> dataSource =
      imagePipeline.fetchImageFromBitmapCache(request, context);
    try {
      CloseableReference<CloseableImage> imageReference = dataSource.getResult();
      if (imageReference != null) {
        try {
          CloseableImage image = imageReference.get();
          if (image instanceof CloseableBitmap) {
            Bitmap bitmap = ((CloseableBitmap) image).getUnderlyingBitmap();
            imageView.setImageBitmap(bitmap);
          }
        } finally {
          CloseableReference.closeSafely(imageReference);
        }
      } else {
        Log.v(Constants.packageName, "Fresco cache miss");
      }
    } finally {
      dataSource.close();
    }
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
