package com.taumu.rnDynamicSplash;

import android.app.Activity;
import android.app.Dialog;
import android.content.res.Resources;
import android.os.Build;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.taumu.rnDynamicSplash.utils.Config;
import com.taumu.rnDynamicSplash.utils.Constants;
import com.taumu.rnDynamicSplash.utils.ElementValue;
import com.taumu.rnDynamicSplash.utils.Helpers;
import com.taumu.rnDynamicSplash.utils.SplashData;

import java.lang.ref.WeakReference;

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

          if (splashData != null && !mDialog.isShowing()) {
            Resources resources = activity.getResources();
            mDialog = new Dialog(activity, resources.getIdentifier(splashData.themeName, "theme", activity.getPackageName()));
            mDialog.setContentView(resources.getIdentifier(splashData.layoutName, "layout", activity.getPackageName()));
            mDialog.setCancelable(false);

            for (ElementValue elementData : splashData.elementsData) {
              int id = resources.getIdentifier(elementData.elementId, "id", activity.getPackageName());

              if (elementData.type == Constants.ImageType) {
                ImageView mSplashImage = mDialog.findViewById(id);

                //TODO return custom image working for showing before rn
                Helpers.setImageFromFresco(elementData.value, mSplashImage, activity);
              } else if (elementData.type == Constants.TextType) {
                TextView mSplashImage = mDialog.findViewById(id);

                mSplashImage.setText(elementData.value);
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
