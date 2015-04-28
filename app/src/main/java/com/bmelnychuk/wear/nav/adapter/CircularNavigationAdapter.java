package com.bmelnychuk.wear.nav.adapter;

import com.sababado.circularview.Marker;
import com.sababado.circularview.SimpleCircularViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bogdan Melnychuk
 */
public abstract class CircularNavigationAdapter<E> extends SimpleCircularViewAdapter {
    public static final int DEFAULT_RADIUS = 40;

    private List<E> objects;

    public CircularNavigationAdapter() {
        this(new ArrayList<E>());
    }

    public CircularNavigationAdapter(List<E> objects) {
        this.objects = objects;
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    public E getItemAt(int position) {
        return objects.get(position);
    }

    @Override
    public void setupMarker(int i, Marker marker) {
        marker.setFitToCircle(true);
        marker.setRadius(DEFAULT_RADIUS);
    }
}
