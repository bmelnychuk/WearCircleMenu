package com.bmelnychuk.wear.nav.adapter;

import com.sababado.circularview.CircularView;
import com.sababado.circularview.Marker;

/**
 * Created by bogdan.melnychuk on 28.04.2015.
 */
public abstract class IconicItemClickAdapter implements CircularView.OnClickListener {
    @Override
    public void onClick(CircularView circularView) {

    }

    @Override
    public void onMarkerClick(CircularView circularView, Marker marker, int i) {
        // TODO maybe I should handle ClassCastException
        IconicNavigationAdapter iconicAdapter = (IconicNavigationAdapter) circularView.getAdapter();
        IconicNavigationAdapter.IconicItem item = iconicAdapter.getItemAt(i);
        onItemClick(circularView, item);
    }

    public abstract void onItemClick(CircularView circularView, IconicNavigationAdapter.IconicItem item);
}
