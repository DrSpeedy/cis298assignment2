package edu.kvcc.cis298.cis298assignment2;

import android.content.Context;

import java.util.Hashtable;

/**
 * Created by doc on 9/27/16.
 */

public class TempScaleContainer {
    private Hashtable<String, TempScale> mTempScales;
    private Context mAppContext;

    public TempScaleContainer(Context context) {
        mTempScales = new Hashtable<>();
        mAppContext = context;
    }

    public void add(int stringResId, double boilingPoint, double freezingPoint) {
        String name = mAppContext.getString(stringResId);
        TempScale tempScale = new TempScale(name, boilingPoint, freezingPoint);

        mTempScales.put(name, tempScale);
    }

    public TempScale get(int stringResId) {
        String key = mAppContext.getString(stringResId);
        return get(key);
    }

    public TempScale get(String key) {
        return mTempScales.get(key);
    }

    public TempScale get(CharSequence key) { return mTempScales.get(key.toString()); }

    public void remove(int stringResId) {
        String key = mAppContext.getString(stringResId);
        remove(key);
    }

    public void remove(String key) {
        mTempScales.remove(key);
    }
}
