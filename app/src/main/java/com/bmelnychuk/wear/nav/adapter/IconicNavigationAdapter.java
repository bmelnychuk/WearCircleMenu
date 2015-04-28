package com.bmelnychuk.wear.nav.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.IdRes;

import com.bmelnychuk.wear.nav.R;
import com.github.johnkil.print.PrintDrawable;
import com.sababado.circularview.Marker;

import java.util.List;

/**
 * Created by Bogdan Melnychuk on 4/26/15.
 */
public class IconicNavigationAdapter extends CircularNavigationAdapter<IconicNavigationAdapter.IconicItem> {
    private Context context;

    public IconicNavigationAdapter(Context context, List<IconicItem> objects) {
        super(objects);
        this.context = context;
    }


    @Override
    public void setupMarker(int i, Marker marker) {
        super.setupMarker(i, marker);
        IconicItem item = getItemAt(i);
        // I prefer iconic fonts instead of images.
        Drawable iconicDrawable = new PrintDrawable.Builder(context)
                .iconText(item.getIconicText())
                .iconColor(item.getColor())
                .iconSize(R.dimen.icon_font_size)
                .iconFont("fonts/material-icon-font.ttf")
                .build();
        marker.setSrc(iconicDrawable);
    }

    public interface IconicItem {
        @IdRes
        int getIconicText();

        @IdRes
        int getColor();
    }
}
