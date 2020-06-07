package com.taumu.rnDynamicSplash.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Helpers {
  public static String getValue(@NonNull List<ElementValue> dataValues, @Nullable String currentLang) {
    Date currentDate = new Date();
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    int resultQuality = 0;
    String result = null;
    for (ElementValue dataValue : dataValues) {
      Date startDate = null;
      Date endDate = null;
      try {
        startDate = format.parse(dataValue.startDate);
        endDate = format.parse(dataValue.endDate);
      } catch (ParseException e) {
        Log.v(Constants.packageName, "Date Parse error: " + e.getMessage());
      }
      int currentQuality = 0;
      if ((startDate == null || startDate.before(currentDate))
        && (endDate == null || endDate.after(currentDate))) {
        if (startDate != null && endDate != null) {
          currentQuality += 2;
        } else if (startDate != null || endDate != null) {
          currentQuality++;
        }
        if (currentLang != null && dataValue.lang != null && currentLang.toLowerCase().startsWith(dataValue.lang.toLowerCase())) {
          currentQuality += 3;
        }

        if (resultQuality < currentQuality) {
          resultQuality = currentQuality;
          result = dataValue.value;
        }
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

  static List<ElementData> getDataListFromJson(JSONArray jsonData) {
    try {
      List<ElementData> data = new ArrayList<>();
      if (jsonData == null) return data;
      int index = 0;
      JSONObject elem = jsonData.getJSONObject(index);
      while (elem != null) {
        ElementData elementData = ElementData.getElementDataFromJson(elem);
        if (elementData != null) {
          data.add(elementData);
        }

        elem = jsonData.getJSONObject(++index);
      }

      if (data.size() == 0) {
        Log.v(Constants.packageName, "Provided \"data\" is invalid");
      }

      return data;
    } catch (JSONException error) {
      Log.v(Constants.packageName, "Error on parsing \"data\" JSON");

      return null;
    }
  }
}
