package com.bmelnychuk.wear.nav;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.wearable.view.WatchViewStub;
import android.view.View;

import com.andexert.library.RippleView;
import com.bmelnychuk.wear.nav.adapter.IconicItemClickAdapter;
import com.bmelnychuk.wear.nav.adapter.IconicNavigationAdapter;
import com.bmelnychuk.wear.nav.animation.AnimationsUtil;
import com.github.johnkil.print.PrintDrawable;
import com.sababado.circularview.CircularView;
import com.sababado.circularview.Marker;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    public static final int MUSIC = 1;
    public static final int MOVIE = 2;
    public static final int BOOK = 3;
    public static final int FIT = 4;
    public static final int ANDROID = 5;

    public static final int DURATION = 600;

    private CircularView circularView;
    private RippleView ripple;

    private Animator.AnimatorListener defaultAnimListener = new AnimatorListenerAdapter() {
        @Override
        public void onAnimationStart(Animator animation) {
            super.onAnimationStart(animation);
            circularView.setVisibility(View.VISIBLE);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                IconicNavigationAdapter<NavigationItem> mAdapter = new IconicNavigationAdapter(MainActivity.this, getNavigationItems());
                circularView = (CircularView) findViewById(R.id.circular_view);
                ripple = (RippleView) findViewById(R.id.ripple);
                circularView.setAdapter(mAdapter);

                //You can use CircularView.OnClickListener instead
                circularView.setOnCircularViewObjectClickListener(new IconicItemClickAdapter<NavigationItem>() {
                    @Override
                    public void onItemClick(CircularView cView, NavigationItem item, Marker marker) {
                        ripple.setVisibility(View.VISIBLE);
                        ripple.animateRipple(marker.getX(), marker.getY());

                        // Issue posted on developers github account, reflection used as temp solution
                        // but I dont like the result, you can try it
                        // updateRippleColor(item.getColor());

                        final DetailActivity.DetailsActivityInput detailsInput = new DetailActivity.DetailsActivityInput();
                        detailsInput.color = item.getColor();

                        switch (item.getId()) {
                            case MUSIC:
                                detailsInput.text = "Google Music";
                                break;
                            case ANDROID:
                                detailsInput.text = "Google Android";
                                break;
                            case MOVIE:
                                detailsInput.text = "Google Movies";
                                break;
                            case FIT:
                                detailsInput.text = "Google Fit";
                                break;
                            case BOOK:
                                detailsInput.text = "Google Book";
                                break;
                        }

                        //This handler used so you user can see ripple effect
                        Handler myHandler = new Handler();
                        myHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                intent.putExtra("input", detailsInput);
                                startActivity(intent);
                                circularView.setVisibility(View.GONE);
                                ripple.setVisibility(View.GONE);
                            }
                        }, 1000);
                    }
                });

                // This handler is used for smooth start animation
                Handler myHandler = new Handler();
                myHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startAnimation();
                    }
                }, 600);
            }
        });
    }

    private void updateRippleColor(int color) {
        try {
            Field field = RippleView.class.getDeclaredField("rippleColor");
            field.setAccessible(true);
            field.setInt(ripple, color);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        startAnimation();
    }

    private void startAnimation() {
        if (circularView != null && circularView.getVisibility() != View.VISIBLE) {
            AnimationsUtil.animateScaleIn(circularView, DURATION, defaultAnimListener);
            AnimationsUtil.animateSpinIn(circularView, DURATION, null);
        }
    }

    public List<IconicNavigationAdapter.DrawableItem> getNavigationItems() {
        List<IconicNavigationAdapter.DrawableItem> result = new ArrayList<>();
        result.add(new NavigationItem(R.string.ic_headset, R.color.play_music, MUSIC));
        result.add(new NavigationItem(R.string.ic_book, R.color.play_books, BOOK));
        result.add(new NavigationItem(R.string.ic_movie, R.color.play_movies, MOVIE));
        result.add(new NavigationItem(R.string.ic_android, R.color.play_apps, ANDROID));
        result.add(new NavigationItem(R.string.ic_favorite, R.color.google_fit, FIT));
        return result;
    }

    private class NavigationItem implements IconicNavigationAdapter.DrawableItem {
        private final int iconicText;
        private final int colorRes;
        private final int id;

        public NavigationItem(int iconicText, int colorRes, int id) {
            this.iconicText = iconicText;
            this.colorRes = colorRes;
            this.id = id;
        }

        public int getColor() {
            return colorRes;
        }

        public int getId() {
            return id;
        }

        @Override
        public Drawable getDrawable(Context context) {
            // I prefer iconic fonts instead of images.
            return new PrintDrawable.Builder(context)
                .iconText(iconicText)
                .iconColor(colorRes)
                .iconSize(R.dimen.icon_font_size)
                .iconFont("fonts/material-icon-font.ttf")
                .build();
        }
    }
}