package com.bmelnychuk.wear.nav.adapter;

import com.sababado.circularview.CircularView;
import com.sababado.circularview.Marker;

/**
 * Created by bogdan.melnychuk on 28.04.2015.
 */
public abstract class IconicItemClickAdapter<E extends IconicNavigationAdapter.DrawableItem> implements CircularView.OnClickListener {
    @Override
    public void onClick(CircularView circularView) {

    }

    @Override
    public void onMarkerClick(CircularView circularView, Marker marker, int i) {
        // TODO maybe I should handle ClassCastException
        IconicNavigationAdapter<E> iconicAdapter = (IconicNavigationAdapter) circularView.getAdapter();
        E item = iconicAdapter.getItemAt(i);
        onItemClick(circularView, item, marker);
    }

    public abstract void onItemClick(CircularView circularView, E item, Marker marker);
}
