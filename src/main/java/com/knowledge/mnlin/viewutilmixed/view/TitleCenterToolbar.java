package com.knowledge.mnlin.viewutilmixed.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

import com.knowledge.mnlin.viewutilmixed.R;

/**
 * Created on 2018/3/27
 * function : 标题居中的toolbar
 *
 * @author ACChain
 */

public class TitleCenterToolbar extends Toolbar {

    TextView mTvTitle;

    public TitleCenterToolbar(Context context) {
        this(context, null);
    }

    public TitleCenterToolbar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, android.support.v7.appcompat.R.attr.toolbarStyle);
    }

    public TitleCenterToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView();
    }

    /**
     * 初始化数据
     */
    private void initView() {
        inflate(getContext(), R.layout.layout_title_center_toolbar, this);
        mTvTitle=findViewById(R.id.tv_title);
    }

    /**
     * Returns the title of this toolbar.
     *
     * @return The current title.
     */
    @Override
    public CharSequence getTitle() {
        return TextUtils.isEmpty(mTvTitle.getText())?null:mTvTitle.getText();
    }

    /**
     * Set the title of this toolbar.
     * <p>
     * <p>A title should be used as the anchor for a section of content. It should
     * describe or name the content being viewed.</p>
     *
     * @param resId Resource ID of a string to set as the title
     */
    @Override
    public void setTitle(int resId) {
        mTvTitle.setText(resId);
    }

    /**
     * Set the title of this toolbar.
     * <p>
     * <p>A title should be used as the anchor for a section of content. It should
     * describe or name the content being viewed.</p>
     *
     * @param title Title to set
     */
    @Override
    public void setTitle(CharSequence title) {
        mTvTitle.setText(title);
    }

    /**
     * Sets the text color, size, style, hint color, and highlight color
     * from the specified TextAppearance resource.
     *
     * @param context
     * @param resId
     */
    @Override
    public void setTitleTextAppearance(Context context, int resId) {
        mTvTitle.setTextAppearance(context, resId);
    }

    /**
     * Sets the text color of the title, if present.
     *
     * @param color The new text color in 0xAARRGGBB format
     */
    @Override
    public void setTitleTextColor(int color) {
        mTvTitle.setTextColor(color);
    }
}
