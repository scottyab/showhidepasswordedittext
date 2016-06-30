package com.scottyab.showhidepasswordedittext;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;


/**
 * Custom version of EditText that shows and hides password onClick of the visibility icon
 */
public class ShowHidePasswordEditText extends EditText {

    private boolean isShowingPassword = false;
    private Drawable drawableEnd;
    private Rect bounds;
    private boolean leftToRight = true;
    private int tintColor = 0;

    @DrawableRes
    private int visiblityIndicatorShow;
    @DrawableRes
    private int visiblityIndicatorHide;


    public ShowHidePasswordEditText(Context context) {
        super(context);
        init(null);
    }

    public ShowHidePasswordEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ShowHidePasswordEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray attrsArray = getContext().obtainStyledAttributes(attrs, R.styleable.ShowHidePasswordEditText);

            visiblityIndicatorShow = attrsArray.getResourceId(R.styleable.ShowHidePasswordEditText_drawable_show, R.drawable.ic_visibility_grey_900_24dp);
            visiblityIndicatorHide = attrsArray.getResourceId(R.styleable.ShowHidePasswordEditText_drawable_hide, R.drawable.ic_visibility_off_grey_900_24dp);
            tintColor = attrsArray.getColor(R.styleable.ShowHidePasswordEditText_tint_color, 0);

            attrsArray.recycle();
        } else {
            visiblityIndicatorShow = R.drawable.ic_visibility_grey_900_24dp;
            visiblityIndicatorHide = R.drawable.ic_visibility_off_grey_900_24dp;
        }

        leftToRight = isLeftToRight();

        isShowingPassword = false;
        setTransformationMethod(PasswordTransformationMethod.getInstance());

        if (!TextUtils.isEmpty(getText())) {
            showPasswordVisibilityIndicator(true);
        }

        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    showPasswordVisibilityIndicator(true);
                } else {
                    showPasswordVisibilityIndicator(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private boolean isLeftToRight() {
        // If we are pre JB assume always LTR
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return true;
        }

        // Other methods, seemingly broken when testing though.
        // return ViewCompat.getLayoutDirection(this) == ViewCompat.LAYOUT_DIRECTION_RTL
        // return !ViewUtils.isLayoutRtl(this);

        Configuration config = getResources().getConfiguration();
        return !(config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL);
    }

    @Override
    public void setCompoundDrawables(Drawable left, Drawable top,
                                     Drawable right, Drawable bottom) {

        //keep a reference to the right drawable so later on touch we can check if touch is on the drawable
        if (leftToRight && right != null) {
            drawableEnd = right;
        } else if (!leftToRight && left != null) {
            drawableEnd = left;
        }

        super.setCompoundDrawables(left, top, right, bottom);
    }

    public void setTintColor(@ColorInt int tintColor) {
        this.tintColor = tintColor;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_UP && drawableEnd != null) {
            bounds = drawableEnd.getBounds();

            final int x = (int) event.getX();


            //check if the touch is within bounds of drawableEnd icon
            if ((leftToRight && (x >= (this.getRight() - bounds.width()))) ||
                    (!leftToRight && (x <= (this.getLeft() + bounds.width())))) {
                togglePasswordVisibility();

                //use this to prevent the keyboard from coming up
                event.setAction(MotionEvent.ACTION_CANCEL);
            }
        }

        return super.onTouchEvent(event);
    }

    private void showPasswordVisibilityIndicator(boolean show) {
        if (show) {
            Drawable original = isShowingPassword ?
                    ContextCompat.getDrawable(getContext(), visiblityIndicatorHide) :
                    ContextCompat.getDrawable(getContext(), visiblityIndicatorShow);
            original.mutate();

            if (tintColor == 0) {
                setCompoundDrawablesWithIntrinsicBounds(leftToRight ? null : original, null, leftToRight ? original : null, null);
            } else {
                Drawable wrapper = DrawableCompat.wrap(original);
                DrawableCompat.setTint(wrapper, tintColor);
                setCompoundDrawablesWithIntrinsicBounds(leftToRight ? null : wrapper, null, leftToRight ? wrapper : null, null);
            }
        } else {
            setCompoundDrawables(null, null, null, null);
        }
    }


    public void togglePasswordVisibility() {
        // Store the selection
        int selectionStart = this.getSelectionStart();
        int selectionEnd = this.getSelectionEnd();

        // Set transformation method to show/hide password
        if (isShowingPassword) {
            setTransformationMethod(PasswordTransformationMethod.getInstance());
        } else {
            setTransformationMethod(null);
        }

        // Restore selection
        this.setSelection(selectionStart, selectionEnd);

        // Toggle flag and show indicator
        isShowingPassword = !isShowingPassword;
        showPasswordVisibilityIndicator(true);
    }

    @Override
    protected void finalize() throws Throwable {
        drawableEnd = null;
        bounds = null;
        super.finalize();
    }


    public
    @DrawableRes
    int getVisiblityIndicatorShow() {
        return visiblityIndicatorShow;
    }

    public void setVisiblityIndicatorShow(@DrawableRes int visiblityIndicatorShow) {
        this.visiblityIndicatorShow = visiblityIndicatorShow;
    }

    public
    @DrawableRes
    int getVisiblityIndicatorHide() {
        return visiblityIndicatorHide;
    }

    public void setVisiblityIndicatorHide(@DrawableRes int visiblityIndicatorHide) {
        this.visiblityIndicatorHide = visiblityIndicatorHide;
    }

    /**
     * @return true if the password is visible | false if hidden
     */
    public boolean isShowingPassword() {
        return isShowingPassword;
    }

}