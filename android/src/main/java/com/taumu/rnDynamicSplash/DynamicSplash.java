package com.taumu.rnDynamicSplash;

import android.app.Activity;
import android.app.Dialog;
import android.content.res.Resources;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.taumu.rnDynamicSplash.utils.Config;
import com.taumu.rnDynamicSplash.utils.Constants;
import com.taumu.rnDynamicSplash.utils.ElementData;
import com.taumu.rnDynamicSplash.utils.Helpers;

import java.lang.ref.WeakReference;
import java.util.Locale;

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
          Resources resources = activity.getResources();
          mDialog = new Dialog(activity, resources.getIdentifier(config.themeResId, "theme", activity.getPackageName()));
          mDialog.setContentView(resources.getIdentifier(config.layoutResId, "layout", activity.getPackageName()));
          mDialog.setCancelable(false);
          boolean isHaveData = false;

          String lang = config.lang != null ? config.lang : Locale.getDefault().getLanguage();

          for (ElementData data : config.data) {
            int id = resources.getIdentifier(data.elementId, "id", activity.getPackageName());

            String value = Helpers.getValue(data.values, lang);

            if (value != null) {
              isHaveData = true;
              if (data.type == ElementData.ImageType) {
                ImageView mSplashImage = mDialog.findViewById(id);

                Helpers.setImageFromFresco(value, mSplashImage, activity);
              } else if (data.type == ElementData.TextType) {
                TextView mSplashImage = mDialog.findViewById(id);

                mSplashImage.setText(value);
              } else {
                Log.v(Constants.packageName, "Bad type provided:" + data.type);
              }
            }
          }

          if (isHaveData && !mDialog.isShowing()) {
            mDialog.show();
          }
        }
      }
    });
  }

  public void hide(Activity activity) {
    if (activity == null) {
      if (mActivity == null) {
        return;
      }
      activity = mActivity.get();
    }
    if (activity == null) return;
    activity.runOnUiThread(new Runnable() {
      @Override
      public void run() {
        if (mDialog != null && mDialog.isShowing()) {
          mDialog.dismiss();
          mDialog = null;
        }
      }
    });
  }
}
