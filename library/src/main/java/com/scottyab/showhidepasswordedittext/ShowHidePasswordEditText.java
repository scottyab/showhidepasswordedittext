package com.scottyab.showhidepasswordedittext;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;


/**
 * Custom version of EditText that shows and hides password onClick of the visibility icon
 */
public class ShowHidePasswordEditText extends AppCompatEditText {

  private boolean isShowingPassword = false;
  private Drawable drawableEnd;
  private Rect bounds;
  private boolean leftToRight = true;

  @DrawableRes private int visiblityIndicatorShow = R.drawable.ic_visibility_grey_900_24dp;
  @DrawableRes private int visiblityIndicatorHide = R.drawable.ic_visibility_off_grey_900_24dp;
  private boolean monospace;

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
      TypedArray attrsArray =
              getContext().obtainStyledAttributes(attrs, R.styleable.ShowHidePasswordEditText);

      visiblityIndicatorShow = attrsArray.getResourceId(R.styleable.ShowHidePasswordEditText_drawable_show, visiblityIndicatorShow);
      visiblityIndicatorHide = attrsArray.getResourceId(R.styleable.ShowHidePasswordEditText_drawable_hide, visiblityIndicatorHide);
      monospace = attrsArray.getBoolean(R.styleable.ShowHidePasswordEditText_monospace, true);


      attrsArray.recycle();
    }

    leftToRight = isLeftToRight();

    isShowingPassword = false;
    setInputType(EditorInfo.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_VARIATION_PASSWORD, true);

    if(!monospace) {
      setTypeface(Typeface.DEFAULT);
    }

    if(!TextUtils.isEmpty(getText())){
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

  private boolean isLeftToRight(){
    // If we are pre JB assume always LTR
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1){
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
    if (leftToRight && right != null){
      drawableEnd = right;
    }
    else if (!leftToRight && left != null){
      drawableEnd = left;
    }

    super.setCompoundDrawables(left, top, right, bottom);
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {

    if (event.getAction() == MotionEvent.ACTION_UP && drawableEnd != null) {
      bounds = drawableEnd.getBounds();
      final int x = (int) event.getX();

      //check if the touch is within bounds of drawableEnd icon
      if ((leftToRight && (x >= (this.getRight() - bounds.width()))) ||
              (!leftToRight &&  (x <= (this.getLeft() + bounds.width())))){
          togglePasswordVisability();
          //use this to prevent the keyboard from coming up
          event.setAction(MotionEvent.ACTION_CANCEL);
      }
    }
    return super.onTouchEvent(event);
  }

  private void showPasswordVisibilityIndicator(boolean show) {
    if (show) {

      Drawable drawable = isShowingPassword?
              ContextCompat.getDrawable(getContext(), visiblityIndicatorHide):
              ContextCompat.getDrawable(getContext(), visiblityIndicatorShow);

      setCompoundDrawablesWithIntrinsicBounds(leftToRight?null:drawable, null, leftToRight?drawable:null, null);

    } else {
      setCompoundDrawables(null, null, null, null);
    }
  }

  private void togglePasswordVisability() {
    if (isShowingPassword) {
      setInputType(EditorInfo.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_VARIATION_PASSWORD, true);
    } else {
      setInputType(EditorInfo.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD, true);
    }
    isShowingPassword = !isShowingPassword;
    showPasswordVisibilityIndicator(true);
  }

  @Override
  protected void finalize() throws Throwable {
    drawableEnd = null;
    bounds = null;
    super.finalize();
  }


  private void setInputType(int inputType, boolean keepState) {
    int selectionStart = -1;
    int selectionEnd = -1;
    if (keepState) {
      selectionStart = getSelectionStart();
      selectionEnd = getSelectionEnd();
    }
    setInputType(inputType);
    if (keepState) {
      setSelection(selectionStart, selectionEnd);
    }
  }


  public @DrawableRes int getVisiblityIndicatorShow() {
    return visiblityIndicatorShow;
  }

  public void setVisiblityIndicatorShow(@DrawableRes int visiblityIndicatorShow) {
    this.visiblityIndicatorShow = visiblityIndicatorShow;
  }

  public @DrawableRes int getVisiblityIndicatorHide() {
    return visiblityIndicatorHide;
  }

  public void setVisiblityIndicatorHide(@DrawableRes int visiblityIndicatorHide) {
    this.visiblityIndicatorHide = visiblityIndicatorHide;
  }

  /**
   *
   * @return true if the password is visable | false if hidden
   */
  public boolean isShowingPassword() {
    return isShowingPassword;
  }
}