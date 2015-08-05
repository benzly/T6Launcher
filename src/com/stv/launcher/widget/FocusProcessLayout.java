package com.stv.launcher.widget;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

public class FocusProcessLayout extends RelativeLayout {

    private AnimatorSet mAnimatorSet;

    private ScaleXUpdateListener mScaleXUpdateListener;
    private ScaleYUpdateListener mScaleYUpdateListener;

    private float mCurrentScaleX;
    private float mCurrentScaleY;

    private float mUnFocusScaleX = 1.0f;
    private float mUnFocusScaleY = 1.0f;

    private float mFocusScaleX = 1.2f;
    private float mFocusScaleY = 1.2f;


    public FocusProcessLayout(Context context) {
        this(context, null);
    }

    public FocusProcessLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FocusProcessLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mAnimatorSet = new AnimatorSet();
        mAnimatorSet.setDuration(100);

        mCurrentScaleX = 1.0f;
        mCurrentScaleY = 1.0f;

        mScaleXUpdateListener = new ScaleXUpdateListener();
        mScaleYUpdateListener = new ScaleYUpdateListener();
    }

    protected View getTarget() {
        return this;
    }

    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
        if (gainFocus) {
            if (mAnimatorSet != null) {
                mAnimatorSet.cancel();
            }

            ObjectAnimator xScaleAnim = ObjectAnimator.ofFloat(getTarget(), "scaleX", mCurrentScaleX, mFocusScaleX);
            ObjectAnimator yScaleAnim = ObjectAnimator.ofFloat(getTarget(), "scaleY", mCurrentScaleY, mFocusScaleY);
            xScaleAnim.addUpdateListener(mScaleXUpdateListener);
            yScaleAnim.addUpdateListener(mScaleYUpdateListener);
            mAnimatorSet.playTogether(xScaleAnim, yScaleAnim);
            mAnimatorSet.start();
        } else {
            if (mAnimatorSet != null) {
                mAnimatorSet.cancel();
            }

            ObjectAnimator xScaleAnim = ObjectAnimator.ofFloat(getTarget(), "scaleX", mCurrentScaleX, mUnFocusScaleX);
            ObjectAnimator yScaleAnim = ObjectAnimator.ofFloat(getTarget(), "scaleY", mCurrentScaleY, mUnFocusScaleY);
            xScaleAnim.addUpdateListener(mScaleXUpdateListener);
            yScaleAnim.addUpdateListener(mScaleYUpdateListener);
            mAnimatorSet.playTogether(xScaleAnim, yScaleAnim);
            mAnimatorSet.start();
        }
    }

    class ScaleXUpdateListener implements AnimatorUpdateListener {

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            mCurrentScaleX = (Float) animation.getAnimatedValue("scaleX");
        }

    }

    class ScaleYUpdateListener implements AnimatorUpdateListener {

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            mCurrentScaleY = (Float) animation.getAnimatedValue("scaleY");
        }

    }
}
