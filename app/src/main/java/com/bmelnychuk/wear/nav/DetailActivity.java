package com.bmelnychuk.wear.nav;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.TextView;

import java.io.Serializable;

public class DetailActivity extends Activity {

    private TextView mTextView;
    private DetailsActivityInput input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            input = (DetailsActivityInput) extras.getSerializable("input");
        }
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTextView = (TextView) stub.findViewById(R.id.text);
                if (input != null) {
                    mTextView.setText(input.text);
                    View parent = (View) mTextView.getParent();
                    parent.setBackgroundColor(getResources().getColor(input.color));
                }
            }
        });
    }

    public static class DetailsActivityInput implements Serializable {
        public String text;
        public int color;
    }
}
