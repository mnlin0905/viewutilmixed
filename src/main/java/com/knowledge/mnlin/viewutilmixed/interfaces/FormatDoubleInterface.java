package com.knowledge.mnlin.viewutilmixed.interfaces;

import android.graphics.Point;
import android.support.annotation.NonNull;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.util.Pair;

import java.text.NumberFormat;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created on 2018/4/16
 * function : 接口,用于格式化double类型数据
 *
 * @author ACChain
 */

public interface FormatDoubleInterface {
    /**
     * 处理string类型数据
     */
    default String dispatchString(NumberFormat instance, @NonNull CharSequence text) {
        Pattern fraction = Pattern.compile("[\\d]+\\.[\\d]+0");
        Matcher matcher = fraction.matcher(text);
        StringBuffer buffer = new StringBuffer(text.length());
        while (matcher.find()) {
            String pre_format = matcher.group(0);

            //如果格式化成功,则将替换后的内容加入进入,如果替换失败,则将替换前的内容添加进入
            try {
                String res_format = instance.format(Double.parseDouble(pre_format)).replaceAll(",", "");
                matcher.appendReplacement(buffer, res_format);
            } catch (Exception e) {
                return text.toString();
            }
        }
        return matcher.appendTail(buffer).toString();
    }

    /**
     * 处理string类型数据
     */
    default Spanned dispatchSpannableString(NumberFormat instance, @NonNull Spanned text) {
        //保持一个对象数组,存储为span的前后位置,point中x和y分别表示起始和终止位置
        LinkedList<Pair<Object, Point>> spans = new LinkedList<>();

        Pattern fraction = Pattern.compile("[\\d]+\\.[\\d]+0");
        Matcher matcher = fraction.matcher(text);
        StringBuffer buffer = new StringBuffer(text.length());

        if (matcher.find()) {
            String pre_format = matcher.group(0);

            try {
                String res_format = instance.format(Double.parseDouble(pre_format)).replaceAll(",", "");
                matcher.appendReplacement(buffer, res_format);

                int match_start = matcher.start();
                int match_end = matcher.end();
                int new_end = match_start + res_format.length();

                Object[] ss = text.getSpans(0, text.length(), Object.class);
                for (Object s : ss) {
                    int start = 0;
                    int end = 0;

                    int span_start = text.getSpanStart(s);
                    int span_end = text.getSpanEnd(s);

                    if (span_end <= match_start) {
                        //如果完全在左区间,则保持原值不变
                        start = span_start;
                        end = span_end;
                    } else if (span_start >= match_end) {
                        //如果完全在右区间,则新值同步加或者减
                        start = span_start - match_end + new_end;
                        end = span_end - match_end + new_end;
                    } else if (span_start < match_start) {
                        //如果左侧在外左,右侧在外右;包含两者边界情况(正好和double字符串位置重叠)
                        start = span_start;
                        end = span_end - match_end + new_end;
                    }

                    Pair<Object, Point> span = new Pair<Object, Point>(s, new Point(start, end));
                    spans.add(span);
                }

            } catch (Exception e) {
                return text;
            }
        }

        //处理匹配到的double
        SpannableString spannableString = new SpannableString(matcher.appendTail(buffer));
        for (Pair<Object, Point> span : spans) {
            spannableString.setSpan(span.first, span.second.x, span.second.y, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }

        //检查是否已经替换完毕,如果完毕,就不在进行处理
        if (matcher.find()) {
            return dispatchSpannableString(instance, spannableString);
        } else {
            return spannableString;
        }
    }

    /**
     * 处理string类型数据
     */
    @Deprecated
    default SpannableStringBuilder dispatchSpannableStringBuilder(boolean deprecated, NumberFormat instance, @NonNull SpannableStringBuilder builder) {
        //保持一个对象数组,存储为span的前后位置,point中x和y分别表示起始和终止位置
        Pattern fraction = Pattern.compile("[\\d]+\\.[\\d]+0");
        Matcher matcher = fraction.matcher(builder);

        while (matcher.find()) {
            SpannableStringBuilder sub_builder = (SpannableStringBuilder) builder.subSequence(matcher.start(), matcher.end());
            builder = ((SpannableStringBuilder) builder.subSequence(0, matcher.start()))
                    .append(dispatchSpannableString(instance, sub_builder))
                    .append(builder.subSequence(matcher.end(), builder.length()));
            matcher = fraction.matcher(builder);
        }

        return builder;
    }

    /**
     * 处理string类型数据
     */
    default Spanned dispatchSpannableStringBuilder(NumberFormat instance, @NonNull SpannableStringBuilder builder) {
        return dispatchSpannableString(instance,new SpannableString(builder));
    }
}
