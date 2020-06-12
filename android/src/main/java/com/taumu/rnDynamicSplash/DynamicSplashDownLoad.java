package com.taumu.rnDynamicSplash;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.taumu.rnDynamicSplash.interfaces.ImageLoad;
import com.taumu.rnDynamicSplash.utils.FileUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DynamicSplashDownLoad {
  static class DownloadAsyncTask extends AsyncTask<ImageLoad, Void, String> {
    @Override
    protected void onPreExecute() {
      super.onPreExecute();
    }

    @Override
    protected String doInBackground(ImageLoad... params) {
      ImageLoad data = params[0];

      URL fileUrl;
      InputStream is = null;
      Bitmap bitmap = null;
      try {
        fileUrl = new URL(data.imageUri);
        HttpURLConnection conn = (HttpURLConnection) fileUrl.openConnection();
        conn.setDoInput(true);
        conn.connect();
        is = conn.getInputStream();
        bitmap = BitmapFactory.decodeStream(is);
      } catch (Exception e) {
        data.promise.reject(e);
      } finally {
        try {
          if (null != is) {
            is.close();
          }
        } catch (IOException e) {
          data.promise.reject(e);
        }
      }

      if (bitmap != null) {
        boolean result = FileUtils.saveImage(bitmap, data.imageUri, data.promise);
        if (result) {
          data.promise.resolve(null);
        }
      }

      return "";
    }
  }
}
