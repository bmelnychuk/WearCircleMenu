package com.bmelnychuk.wear.nav.animation;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * Created by Bogdan Melnychuk
 */
public class AnimationsUtil {
    public static void animateScaleIn(View view, long duration, Animator.AnimatorListener listener) {
        view.setScaleX(0f);
        view.setScaleY(0f);
        view.setVisibility(View.VISIBLE);

        AnimatorSet scaleSet = new AnimatorSet();
        scaleSet.playTogether(ObjectAnimator.ofFloat(view, View.SCALE_X, 1f),
                ObjectAnimator.ofFloat(view, View.SCALE_Y, 1f));
        scaleSet.setInterpolator(new AccelerateDecelerateInterpolator());
        scaleSet.setDuration(duration);
        if (listener != null) {
            scaleSet.addListener(listener);
        }
        scaleSet.start();
        //return scaleSet;
    }

    public static void animateSpinIn(View view, long duration, Animator.AnimatorListener listener) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(duration);
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(view, "rotation", -200, 0),
                ObjectAnimator.ofFloat(view, "alpha", 0, 1)
        );
        animatorSet.start();
    }
}
