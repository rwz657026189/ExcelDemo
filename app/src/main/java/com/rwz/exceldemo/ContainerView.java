package com.rwz.exceldemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

/**
 * date： 2021/1/17 22:23
 * author： rwz
 * description：
 **/
public class ContainerView extends LinearLayout {
    private static final String TAG = "ContainerView";
    private TextView mKeyView;
    private TextView mValueView;
    public ContainerView(Context context) {
        super(context);
    }

    public ContainerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ContainerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray type = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.container,
                0,0);
        int keyStyle = type.getResourceId(R.styleable.container_key_style, -1);
        int valueStyle = type.getResourceId(R.styleable.container_value_style, -1);
        Log.e(TAG, "init: keyStyle = " + (keyStyle == R.style.key) + ", " + valueStyle);
        if (keyStyle != -1 && valueStyle != -1) {
            mKeyView = new TextView(getContext(), null, 0, keyStyle);
            mKeyView.setText("Key");
            mKeyView.setTextAppearance(getContext(), keyStyle);
            mValueView = new TextView(getContext(), null, 0, valueStyle);
            mValueView.setText("Value");
            mValueView.setTextAppearance(getContext(), valueStyle);
            addView(mKeyView);
            addView(mValueView);
        }
        type.recycle();
    }

}
