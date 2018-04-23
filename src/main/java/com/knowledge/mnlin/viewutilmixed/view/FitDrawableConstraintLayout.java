package com.knowledge.mnlin.viewutilmixed.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created on 2018/1/16
 * function : 适应背景的ConstraintLayout布局
 *
 * @author ACChain
 */
public class FitDrawableConstraintLayout extends ConstraintLayout {
    public FitDrawableConstraintLayout(Context context) {
        super(context);
    }

    public FitDrawableConstraintLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FitDrawableConstraintLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Drawable background = getBackground();
        setMeasuredDimension(Math.max(getMeasuredWidth(), background.getIntrinsicWidth()), Math.max(getMeasuredHeight(), background.getIntrinsicHeight()));
        int widthSpec = View.MeasureSpec.makeMeasureSpec(background.getIntrinsicWidth(), View.MeasureSpec.EXACTLY);
        int heightSpec = View.MeasureSpec.makeMeasureSpec(background.getIntrinsicHeight(), View.MeasureSpec.EXACTLY);

        super.onMeasure(widthSpec, heightSpec);
    }
}
