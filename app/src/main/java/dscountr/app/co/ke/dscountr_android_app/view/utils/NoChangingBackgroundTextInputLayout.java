package dscountr.app.co.ke.dscountr_android_app.view.utils;

import android.content.Context;
import android.graphics.ColorFilter;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.AttributeSet;

import dscountr.app.co.ke.dscountr_android_app.R;

public class NoChangingBackgroundTextInputLayout extends TextInputLayout {
    public NoChangingBackgroundTextInputLayout(Context context) {
        super(context);
    }

    public NoChangingBackgroundTextInputLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoChangingBackgroundTextInputLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setError(@Nullable CharSequence error) {
        ColorFilter defaultColorFilter = getBackgroundDefaultColorFilter();
        super.setError(error);
        //Reset EditText's background color to default.
        updateBackgroundColorFilter(defaultColorFilter);
        Typeface tf = getResources().getFont(R.font.roboto_regular);
        setTypeface(tf);
    }

    @Override
    protected void drawableStateChanged() {
        ColorFilter defaultColorFilter = getBackgroundDefaultColorFilter();
        super.drawableStateChanged();
        //Reset EditText's background color to default.
        updateBackgroundColorFilter(defaultColorFilter);
        Typeface tf = getResources().getFont(R.font.roboto_regular);
        setTypeface(tf);
    }

    private void updateBackgroundColorFilter(ColorFilter colorFilter) {
        if(getEditText() != null && getEditText().getBackground() != null)
            getEditText().getBackground().setColorFilter(colorFilter);
    }

    @Nullable
    private ColorFilter getBackgroundDefaultColorFilter() {
        ColorFilter defaultColorFilter = null;
        if(getEditText() != null && getEditText().getBackground() != null)
            defaultColorFilter = DrawableCompat.getColorFilter(getEditText().getBackground());
        return defaultColorFilter;
    }
}

