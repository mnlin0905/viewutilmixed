package com.knowledge.mnlin.viewutilmixed.view;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.knowledge.mnlin.viewutilmixed.interfaces.FormatDoubleInterface;

import java.text.NumberFormat;

/**
 * Created on 2018/4/16
 * function : 可以格式化TextView中double数据格式的TextView
 * <p>
 * 对于0,则不做处理
 * 对于0.0,也不做处理
 * 对于0.01,也不做处理
 * 对于0.010,则应显示为0.01
 * <p>
 * 只重写了:
 * public final void setText(@StringRes int resid)
 * public void setText(CharSequence text, BufferType type)
 * public final void setText(@StringRes int resid, BufferType type)
 * public final void setText(CharSequence text)
 * 这四个方法的处理逻辑;
 * <p>
 * 对于方法:
 * public final void setText(char[] text, int start, int len)
 * 因为权限问题未做处理
 * <p>
 * setText方法中,如果传入非String类型(尝试接受SpannableString和SpannableStringBuilder类型),也不会进行处理,否则可能导致SpanString设置无效果
 * 对于传入的text如果长度超过了1000,同样不予处理,因为可能在递归调用时直接内存溢出
 * 对于单个捕获到的对象,如:10.0020,在字符串该部分必须拥有相同的span,否则处理会出错
 *
 * @author ACChain
 */

public class FormatDoubleTextView extends AppCompatTextView implements FormatDoubleInterface {
    private NumberFormat instance;

    {
        instance = NumberFormat.getInstance();
        instance.setGroupingUsed(false);
        instance.setMaximumFractionDigits(6);
        instance.setMinimumFractionDigits(1);
    }

    public FormatDoubleTextView(Context context) {
        super(context);
    }

    public FormatDoubleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FormatDoubleTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        try {
            if (!TextUtils.isEmpty(text) || text.length() > 1000) {
                if (text instanceof String) {
                    text = dispatchString(instance, text);
                } else if (text instanceof SpannableString) {
                    text = dispatchSpannableString(instance, (SpannableString) text);
                } else if (text instanceof SpannableStringBuilder) {
                    text = dispatchSpannableStringBuilder(instance, (SpannableStringBuilder) text);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.setText(text, type);
    }

}
