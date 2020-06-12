package com.taumu.rnDynamicSplash;

import android.app.Activity;
import android.app.Dialog;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.taumu.rnDynamicSplash.interfaces.Config;
import com.taumu.rnDynamicSplash.utils.Constants;
import com.taumu.rnDynamicSplash.interfaces.ElementValue;
import com.taumu.rnDynamicSplash.utils.FileUtils;
import com.taumu.rnDynamicSplash.utils.Helpers;
import com.taumu.rnDynamicSplash.interfaces.SplashData;

import java.lang.ref.WeakReference;

import static com.taumu.rnDynamicSplash.utils.FileUtils.getFileName;
import static com.taumu.rnDynamicSplash.utils.FileUtils.getLocalBitmap;

public class DynamicSplash {
  private static Dialog mDialog;
  private static WeakReference<Activity> mActivity;

  static DynamicSplash mDynamicSplash;

  public DynamicSplash(final Activity activity, final Config config) {
    if (activity == null) return;

    mDynamicSplash = this;
    mActivity = new WeakReference<>(activity);

    activity.runOnUiThread(new Runnable() {
      @Override
      public void run() {
        if (!activity.isFinishing()) {
          SplashData splashData = Helpers.getValue(config);

          if (splashData != null && (mDialog == null || !mDialog.isShowing())) {
            Resources resources = activity.getResources();
            mDialog = new Dialog(activity, resources.getIdentifier(splashData.themeName, "style", activity.getPackageName()));
            mDialog.setContentView(resources.getIdentifier(splashData.layoutName, "layout", activity.getPackageName()));
            mDialog.setCancelable(false);

            for (ElementValue elementData : splashData.elementsData) {
              int id = resources.getIdentifier(elementData.elementId, "id", activity.getPackageName());

              if (elementData.type == Constants.ImageType) {
                ImageView mSplashImage = mDialog.findViewById(id);

                if (FileUtils.isUrl(elementData.value)) {
                  String fileName = getFileName(elementData.value);


                  String filePath = Constants.packageName + fileName;
                  Bitmap localBitmap = getLocalBitmap(filePath);
                  if (localBitmap != null) {
                    mSplashImage.setImageBitmap(localBitmap);
                  }
                } else {
                  mSplashImage.setImageResource(activity.getResources().getIdentifier(elementData.value , "drawable", activity.getPackageName()));
                }
              } else if (elementData.type == Constants.TextType) {
                TextView mSplashText = mDialog.findViewById(id);

                mSplashText.setText(elementData.value);
              } else {
                Log.v(Constants.packageName, "Bad type provided:" + elementData.type);
              }
            }

            mDialog.show();
          }
        }
      }
    });
  }

  void hide(Activity activity) {
    if (activity == null) {
      if (mActivity == null) {
        return;
      }
      activity = mActivity.get();
    }
    if (activity == null) return;

    final Activity _activity = activity;
    _activity.runOnUiThread(new Runnable() {
      @Override
      public void run() {
        if (mDialog != null && mDialog.isShowing()) {
          boolean isDestroyed = false;

          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
              isDestroyed = _activity.isDestroyed();
          }

          if (!_activity.isFinishing() && !isDestroyed) {
              mDialog.dismiss();
          }
          mDialog = null;
        }
      }
    });
  }
}
