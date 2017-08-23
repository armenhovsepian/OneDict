package com.hyedesign.onedic.utilities;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Albaloo on 3/30/2017.
 */

public class FontResets {
    public static void setFont(Context context, ViewGroup viewGroup) {
        Typeface custom_font = Typeface.createFromAsset(context.getAssets(), "fonts/Vazir.ttf");
        int childCount = viewGroup.getChildCount();
        for (int y = 0; y < childCount; y++) {
            View child = viewGroup.getChildAt(y);
            if (child instanceof ViewGroup) {
                setFont(context, (ViewGroup) child);

                continue;
            }
            if (child instanceof TextView) {
                ((TextView) child).setTypeface(custom_font);

            }
        }

    }

}
