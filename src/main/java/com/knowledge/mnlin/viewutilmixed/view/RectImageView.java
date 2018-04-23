package com.knowledge.mnlin.viewutilmixed.view;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * Created on 2018/3/27
 * function : 宽高相同的ImageView
 *
 * @author ACChain
 */

public class RectImageView extends AppCompatImageView {
    public RectImageView(Context context) {
        super(context);
    }

    public RectImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RectImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //让高度等于宽度
        if (getMeasuredWidth() > 0 && getMeasuredHeight() > 0) {
            setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
        }
    }
}
