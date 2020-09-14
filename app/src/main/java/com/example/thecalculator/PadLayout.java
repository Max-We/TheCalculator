package com.example.thecalculator;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

public class PadLayout extends LinearLayout {
    private LinearLayout pad;

    private boolean mIsScaling = false;
    private ScaleGestureDetector scaleGestureDetector;

    public PadLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        pad = findViewById(R.id.Pad);
        scaleGestureDetector = new ScaleGestureDetector(context, new ScaleListener());
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        final int action = motionEvent.getAction();

        // Canceled event
        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            // Release the scaling.
            mIsScaling = false;
            return false;
        }

        switch (action) {
            case MotionEvent.ACTION_MOVE:
                // Already scaling? If so, intercept.
                if (mIsScaling) {
                    Log.d("PadLayout", "Already Scaling, returning true.");
                    return true;
                }

                if(scaleGestureDetector.onTouchEvent(motionEvent)) {
                    Log.d("PadLayout", "Scaling");
                    return true;
                } else {
                    Log.d("PadLayout", "No Scale detected, returning false.");
                    return false;
                }

        }

        return false;
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {

        private float factor = 1;
        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            return true;
        }

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            factor *= detector.getScaleFactor();
            factor = Math.max(1, Math.min(factor, ResourcesCompat.getFloat(getResources(), R.dimen.pad_large)));
            setPadScaleFactor(factor);

            return true;
        }

        private void setPadScaleFactor(float factor) {
            pad.setScaleX(factor);
            pad.setScaleY(factor);
            pad.setPivotX(0);
            pad.setPivotY(pad.getHeight());
        }
    }
}
