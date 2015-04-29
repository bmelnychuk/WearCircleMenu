package com.bmelnychuk.wear.nav.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.sababado.circularview.Marker;

import java.util.List;

/**
 * Created by Bogdan Melnychuk on 4/26/15.
 */
public class IconicNavigationAdapter<E extends IconicNavigationAdapter.DrawableItem> extends CircularNavigationAdapter<E> {
    private Context context;

    public IconicNavigationAdapter(Context context, List<E> objects) {
        super(objects);
        this.context = context;
    }


    @Override
    public void setupMarker(int i, Marker marker) {
        super.setupMarker(i, marker);
        DrawableItem item = getItemAt(i);
        marker.setSrc(item.getDrawable(context));
    }

    public interface DrawableItem {
        Drawable getDrawable(Context context);
    }
}
