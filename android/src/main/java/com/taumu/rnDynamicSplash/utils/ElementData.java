package com.taumu.rnDynamicSplash.utils;

import android.support.annotation.NonNull;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ElementData {
  public static final int ImageType = 0;
  public static final int TextType = 1;

  public @NonNull String elementId;
  public @NonNull int type;
  public List<ElementValue> values;

  public ElementData(@NonNull String elementId, @NonNull int type, @NonNull List<ElementValue> values) {
    this.elementId = elementId;
    this.type = type;
    this.values = values;
  }

  public static ElementData getElementDataFromJson(JSONObject elem) {
    try {
      List<ElementValue> values = new ArrayList<>();
      JSONArray jsonValues = elem.getJSONArray("values");
      int vindex = 0;
      JSONObject velem = jsonValues.getJSONObject(vindex);
      while (velem != null) {
        ElementValue value = ElementValue.getDataFromJson(velem);
        if (value != null) values.add(value);

        velem = jsonValues.getJSONObject(++vindex);
      }

      if (values.size() != 0) {
        return new ElementData(
          elem.getString("elementId"),
          elem.getInt("type"),
          values);
      } else {
        Log.v(Constants.packageName, "Provided \"data\" for " + elem.getString("elementId") + " is invalid");

        return null;
      }
    } catch (JSONException error) {
      Log.v(Constants.packageName, "Error on parsing elementData JSON");

      return null;
    }
  }
}
