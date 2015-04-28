package com.bmelnychuk.wear.nav;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.wearable.view.WatchViewStub;
import android.view.View;

import com.bmelnychuk.wear.nav.adapter.IconicItemClickAdapter;
import com.bmelnychuk.wear.nav.adapter.IconicNavigationAdapter;
import com.bmelnychuk.wear.nav.animation.AnimationsUtil;
import com.sababado.circularview.CircularView;

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
                IconicNavigationAdapter mAdapter = new IconicNavigationAdapter(MainActivity.this, getNavigationItems());
                circularView = (CircularView) findViewById(R.id.circular_view);
                circularView.setAdapter(mAdapter);

                //You can use CircularView.OnClickListener instead
                circularView.setOnCircularViewObjectClickListener(new IconicItemClickAdapter() {
                    @Override
                    public void onItemClick(CircularView circularView, IconicNavigationAdapter.IconicItem item) {
                        DetailActivity.DetialActivityInput detailsInput = new DetailActivity.DetialActivityInput();
                        detailsInput.color = item.getColor();

                        switch (((NavigationItem) item).getId()) {
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

                        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        intent.putExtra("input", detailsInput);
                        startActivity(intent);
                        circularView.setVisibility(View.GONE);
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

    public List<IconicNavigationAdapter.IconicItem> getNavigationItems() {
        List<IconicNavigationAdapter.IconicItem> result = new ArrayList<>();
        result.add(new NavigationItem(R.string.ic_headset, R.color.play_music, MUSIC));
        result.add(new NavigationItem(R.string.ic_book, R.color.play_books, BOOK));
        result.add(new NavigationItem(R.string.ic_movie, R.color.play_movies, MOVIE));
        result.add(new NavigationItem(R.string.ic_android, R.color.play_apps, ANDROID));
        result.add(new NavigationItem(R.string.ic_favorite, R.color.google_fit, FIT));
        return result;
    }

    private class NavigationItem implements IconicNavigationAdapter.IconicItem {
        private final int iconicText;
        private final int colorRes;
        private final int id;

        public NavigationItem(int iconicText, int colorRes, int id) {
            this.iconicText = iconicText;
            this.colorRes = colorRes;
            this.id = id;
        }

        @Override
        public int getIconicText() {
            return iconicText;
        }

        @Override
        public int getColor() {
            return colorRes;
        }

        public int getId() {
            return id;
        }
    }
}