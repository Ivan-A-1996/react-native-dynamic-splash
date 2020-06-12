package com.taumu.rnDynamicSplash.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.facebook.react.bridge.Promise;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtils {

  public static boolean isUrl(String uri) {
    int index = uri.lastIndexOf("/") + 1;

    return index != 0;
  }

  public static String getFileName(String url) {
    int index = url.lastIndexOf("/") + 1;
    return url.substring(index);
  }

  public static Bitmap getLocalBitmap(String url) {
    if (url != null) {
      FileInputStream fis = null;
      try {
        fis = new FileInputStream(url);
        return BitmapFactory.decodeStream(fis);
      } catch (FileNotFoundException e) {
        e.printStackTrace();
        return null;
      } finally {
        if (fis != null) {
          try {
            fis.close();
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }
    } else {
      return null;
    }
  }

  public static void deleteFiles() {
    File appDir = new File(Constants.packageName);
    if (appDir.exists()) {
      File[] files = appDir.listFiles();
      for (int i = 0; i < files.length; i++) {
        File file = new File(files[i].getAbsolutePath());
        if (file.exists() && file.isFile()) {
          file.delete();
        }
      }
    }
  }

  public static boolean saveImage(Bitmap bitmap, String imageUri, Promise promise) {
    File appDir = new File(Constants.packageName);
    if (!appDir.exists()) {
      appDir.mkdir();
    }

    File file = new File(appDir, getFileName(imageUri));
    try {
      FileOutputStream fos = new FileOutputStream(file);
      // JPEG
      bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
      fos.flush();
      fos.close();

      return true;
    } catch (FileNotFoundException e) {
      promise.reject(e);
    } catch (IOException e) {
      promise.reject(e);
    }

    return false;
  }
}
