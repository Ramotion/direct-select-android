package com.ramotion.directselect;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Space;

import java.util.ArrayList;
import java.util.Arrays;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;

/**
 * Represents a popup view with list of available options to choose from and selector view
 * at fixed position from top to indicate a selected option. Contains mail logic, animations, handlers, etc.
 */
@SuppressWarnings({"unused", "UnusedReturnValue", "unchecked"})
public class DSListView<T> extends RelativeLayout implements AbsListView.OnScrollListener {
    static int MOTION_EVENT_SOURCE = 42;

    private ArrayAdapter<T> adapter = null;
    private ArrayList<String> dataFromAttributes = null;

    private Context context;

    // Inner parts
    private ListView listView = null;
    private View selectorView = null;

    // State variables
    private boolean selectorAnimationsEnabled = true;
    private boolean pickerInitialized;
    private boolean readyToHide;
    private boolean animationInProgress;
    private boolean scrollInProgress;
    private boolean listViewIsShown;

    // Settings and selector position
    private int firstVisibleItem = 0;
    private int selectedItem = 0;
    private int cellsBeforeSelector = 0;
    private int cellHeight = 100;
    private float scaleFactorDelta = 0.3f;
    private int selectorTopMargin = 0;
    private int subScrollDuration = 100;
    private int topMarginCompensation = 0;
    private boolean selectorAnimationCenterPivot = false;

    // Attached picker box view to display selected value and trigger show/hide animations of DSListView
    private DSAbstractPickerBox<T> pickerBox;
    private int pickerBoxResId;
    private int cellTextSize;

    // Selector view background settings
    private int selectorBgColor = Color.parseColor("#116b2b66");
    private int selectorBgDrawable;

    public DSListView(Context context) {
        this(context, null);
    }

    public DSListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DSListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray styledAttrs = context.obtainStyledAttributes(attrs, R.styleable.DSListView);
        final int count = styledAttrs.getIndexCount();
        for (int i = 0; i < count; ++i) {
            int attr = styledAttrs.getIndex(i);
            if (attr == R.styleable.DSListView_cell_font_size) {
                this.cellTextSize = styledAttrs.getDimensionPixelSize(attr, 0);
            } else if (attr == R.styleable.DSListView_data_array) {
                String[] arr = getResources().getStringArray(styledAttrs.getResourceId(attr, 0));
                this.dataFromAttributes = new ArrayList<>(Arrays.asList(arr));
            } else if (attr == R.styleable.DSListView_scale_animations) {
                this.selectorAnimationsEnabled = styledAttrs.getBoolean(attr, true);
            } else if (attr == R.styleable.DSListView_selected_index) {
                this.selectedItem = styledAttrs.getInt(attr, 0);
            } else if (attr == R.styleable.DSListView_picker_box_view) {
                this.pickerBoxResId = styledAttrs.getResourceId(attr, 0);
            } else if (attr == R.styleable.DSListView_scale_animations_factor) {
                this.scaleFactorDelta = styledAttrs.getFloat(attr, 1.3f) - 1f;
            } else if (attr == R.styleable.DSListView_scale_animations_pivot_center) {
                this.selectorAnimationCenterPivot = styledAttrs.getBoolean(attr, false);
            } else if (attr == R.styleable.DSListView_selector_background) {
                try {
                    this.selectorBgColor = styledAttrs.getColor(attr, Color.parseColor("#116b2b66"));
                } catch (Exception e) {
                    this.selectorBgDrawable = styledAttrs.getResourceId(attr, 0);
                }
            }
        }
        styledAttrs.recycle();
        initPicker(context);
    }

    private void initPicker(Context context) {
        this.context = context;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        assert inflater != null;
        inflater.inflate(R.layout.ds_list_view, this, true);

        listView = findViewById(R.id.list_view);
        selectorView = findViewById(R.id.selector_view);

        if (selectorBgDrawable > 0)
            selectorView.setBackgroundResource(selectorBgDrawable);
        else
            selectorView.setBackgroundColor(selectorBgColor);

        // If string data array provided from xml - use it with default string array adapter
        if (null != dataFromAttributes)
            setAdapter((ArrayAdapter<T>) new DSDefaultAdapter(context, R.layout.ds_default_list_item,
                    dataFromAttributes, cellHeight, cellTextSize));

    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);

        // Init listView here, because we need to do some calculations when listView already has height
        if (pickerInitialized || null == adapter)
            return;

        int[] selectboxLocation = new int[2];

        if (null != this.pickerBox) {
            this.pickerBox.getLocationInWindow(selectboxLocation);
            this.cellHeight = this.pickerBox.getHeight();
        }

        if (null != this.adapter && this.adapter instanceof DSDefaultAdapter)
            ((DSDefaultAdapter) this.adapter).setCellHeight(cellHeight);

        // Calculate positions of selector and self position
        int[] selfLocation = new int[2];
        this.getLocationInWindow(selfLocation);
        selectorTopMargin = selectboxLocation[1] - selfLocation[1];
        if (selectorTopMargin < 0) selectorTopMargin = 0;

        // Move selectorView to required position, by the top margin
        if (selectorView.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) selectorView.getLayoutParams();
            p.height = cellHeight;
            p.setMargins(0, selectorTopMargin, 0, 0);
            selectorView.requestLayout();
        }

        topMarginCompensation = cellHeight - selectorTopMargin % cellHeight;
        cellsBeforeSelector = selectorTopMargin / cellHeight;
        cellsBeforeSelector += topMarginCompensation == 0 ? 0 : 1;

        // Add negative margin to list view to compensate remained space smaller than cell height
        if (listView.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) listView.getLayoutParams();
            p.setMargins(0, -topMarginCompensation, 0, 0);
            listView.requestLayout();
        }

        // Add empty space before list elements to make sure that all list items reachable
        for (int i = 0; i < cellsBeforeSelector; i++) {
            Space topSpace = new Space(context);
            topSpace.setMinimumHeight(cellHeight);
            listView.addHeaderView(topSpace);
        }

        // Add space after list elements to make sure that all list items reachable
        Space bottomSpace = new Space(context);
        bottomSpace.setMinimumHeight((DSListView.this.getHeight() - selectorTopMargin - cellHeight));
        listView.addFooterView(bottomSpace);

        // Set adapter and scroll listener
        listView.setAdapter(adapter);
        listView.setOnScrollListener(this);

        pickerInitialized = true;

        // Set initial selection
        this.listView.setSelection(selectedItem);

        if (null != pickerBox)
            this.pickerBox.onSelect(this.adapter.getItem(selectedItem), selectedItem);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (null == this.pickerBox && this.pickerBoxResId > 0)
            setPickerBox((DSAbstractPickerBox<T>) getRootView().findViewById(pickerBoxResId));
    }

    private void hideListView() {
        if (!readyToHide || animationInProgress || scrollInProgress || !listViewIsShown || adapter == null) return;

        readyToHide = false;
        animationInProgress = true;
        listView.setEnabled(false);

        this.setVisibility(View.INVISIBLE);

        this.pickerBox.onSelect(adapter.getItem(selectedItem), selectedItem);

        AlphaAnimation hideAnimation = new AlphaAnimation(1f, 0f);
        hideAnimation.setStartOffset(subScrollDuration);
        hideAnimation.setDuration(200);
        hideAnimation.setInterpolator(new DecelerateInterpolator());
        hideAnimation.setAnimationListener(new AnimationListenerAdapter() {
            @Override
            public void onAnimationEnd(Animation animation) {
                listViewIsShown = false;
                animationInProgress = false;
            }
        });
        DSListView.this.startAnimation(hideAnimation);

        // Scale picker box text animation if animations enabled
        if (selectorAnimationsEnabled && null != this.pickerBox) {
            ScaleAnimation scaleAnimation = new ScaleAnimation(1f + scaleFactorDelta, 1f, 1f + scaleFactorDelta, 1f,
                    Animation.RELATIVE_TO_SELF, selectorAnimationCenterPivot ? 0.5f : 0f, Animation.RELATIVE_TO_SELF, 0.5f);
            scaleAnimation.setInterpolator(new DecelerateInterpolator());
            scaleAnimation.setStartOffset(100 + subScrollDuration);
            scaleAnimation.setDuration(100);
            scaleAnimation.setFillAfter(true);
            this.pickerBox.getCellRoot().startAnimation(scaleAnimation);
        }
    }

    private void showListView() {
        if (animationInProgress || scrollInProgress || listViewIsShown) return;
        listView.setEnabled(true);
        animationInProgress = true;

        this.setVisibility(View.VISIBLE);
        this.bringToFront();
        this.readyToHide = false;

        // Scale picker box if animations enabled
        if (selectorAnimationsEnabled && null != this.pickerBox) {
            ScaleAnimation scaleAnimation = new ScaleAnimation(1f, 1f + scaleFactorDelta, 1f, 1f + scaleFactorDelta,
                    Animation.RELATIVE_TO_SELF, selectorAnimationCenterPivot ? 0.5f : 0f, Animation.RELATIVE_TO_SELF, 0.5f);
            scaleAnimation.setInterpolator(new AccelerateInterpolator());
            scaleAnimation.setDuration(100);
            scaleAnimation.setFillAfter(true);
            this.pickerBox.getCellRoot().startAnimation(scaleAnimation);
        }

        // Show picker view animation
        AlphaAnimation showAnimation = new AlphaAnimation(0f, 1f);
        showAnimation.setDuration(200);
        showAnimation.setInterpolator(new AccelerateInterpolator());
        showAnimation.setAnimationListener(new AnimationListenerAdapter() {
            @Override
            public void onAnimationEnd(Animation animation) {
                animationInProgress = false;
                listViewIsShown = true;
                hideListView();
            }
        });

        this.startAnimation(showAnimation);
    }

///////////////////////////////////////// CLIENT METHODS ///////////////////////////////////////////

    public void setAdapter(ArrayAdapter<T> adapter) {
        this.adapter = adapter;
        // If default adapter set - use mechanisms to do default_cell configuration
        if (null != this.adapter && this.adapter instanceof DSDefaultAdapter) {
            DSDefaultAdapter ad = (DSDefaultAdapter) this.adapter;
            ad.setCellHeight(cellHeight);
            ad.setCellTextSize(cellTextSize);
            if (this.pickerBox instanceof DSDefaultPickerBox)
                ad.setDsPickerBoxDefault((DSDefaultPickerBox) this.pickerBox);
        }
        listView.setAdapter(adapter);
    }

    public void setPickerBox(DSAbstractPickerBox<T> pickerBox) {
        this.pickerBox = pickerBox;
        if (this.pickerBox == null) return;
        this.pickerBox.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        DSListView.this.showListView();
                        break;
                    case MotionEvent.ACTION_UP:
                        DSListView.this.readyToHide = true;
                        hideListView();
                        break;
                }
                event.offsetLocation(0, selectorTopMargin + topMarginCompensation);
                event.setSource(MOTION_EVENT_SOURCE);
                DSListView.this.listView.onTouchEvent(event);
                return true;
            }
        });

        if (this.adapter != null && this.adapter instanceof DSDefaultAdapter && this.pickerBox instanceof DSDefaultPickerBox) {
            DSDefaultAdapter ad = (DSDefaultAdapter) this.adapter;
            DSDefaultPickerBox pbd = (DSDefaultPickerBox) this.pickerBox;
            ad.setDsPickerBoxDefault(pbd);
            pbd.setCellTextSize(cellTextSize);
        }
    }

    public void setSelectorBgColor(@ColorInt int chooserBgColor) {
        this.selectorBgColor = chooserBgColor;
        if (null != selectorView)
            selectorView.setBackgroundColor(chooserBgColor);
    }

    @SuppressLint("ResourceType")
    public void setSelectorBgDrawable(@DrawableRes int chooserBgDrawable) {
        this.selectorBgDrawable = chooserBgDrawable;
        if (chooserBgDrawable > 0 && null != selectorView)
            selectorView.setBackgroundResource(chooserBgDrawable);
    }

    public Integer getSelectedIndex() {
        return null == this.adapter ? null : this.selectedItem;
    }

    public void setSelectedIndex(int index) {
        this.selectedItem = index;

        if (pickerInitialized)
            this.listView.setSelection(index);

        if (null != pickerBox && null != this.adapter)
            this.pickerBox.onSelect(this.adapter.getItem(this.selectedItem), this.selectedItem);
    }

    public T getSelectedItem() {
        return null == this.adapter ? null : this.adapter.getItem(this.selectedItem);
    }

//////////////////////////////////////// GETTERS&SETTERS ///////////////////////////////////////////

    public boolean isSelectorAnimationsEnabled() {
        return this.selectorAnimationsEnabled;
    }

    public void setSelectorAnimationsEnabled(boolean selectorAnimationsEnabled) {
        this.selectorAnimationsEnabled = selectorAnimationsEnabled;
    }

/////////////////////////////////// ListView Scroll Listener ///////////////////////////////////////

    @Override
    public void onScrollStateChanged(AbsListView absListView, final int scrollState) {
        this.scrollInProgress = true;

        // If scroll not stopped - clear selector animations(hide/show scale) and return;
        if (scrollState != SCROLL_STATE_IDLE) {
//            if (null != this.pickerBox) {
//                Animation animation = DSListView.this.pickerBox.getAnimation();
//                if (animation != null)
//                    animation.cancel();
//
//            }
            return;
        }

        // So, scroll is stopped - we need to detect position and make subscroll to selected element
        this.scrollInProgress = false;

        // Get first visible child of list (invisible child are removed for recycling, so first visible child is always 0 index)
        View topChild = absListView.getChildAt(0);
        if (topChild == null) return;

        // Get position of of first visible child in dataFromAttributes array
        this.firstVisibleItem = this.listView.getFirstVisiblePosition();

        // Switch to next item if height of visible part of top view is smaller than half height of one row
        if (-topChild.getTop() > topChild.getHeight() / 2) {
            this.firstVisibleItem++;
        }

        // Dirty trick with post-delayed handler to avoid known "fixed" issue (https://issuetracker.google.com/issues/36952786)
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                listView.smoothScrollToPositionFromTop(firstVisibleItem, 0, subScrollDuration);
                hideListView();
            }
        }, 0);

        this.selectedItem = this.firstVisibleItem;
    }

    @Override
    public void onScroll(AbsListView listView, int firstVisible, int visibleItemCount, int totalItemCount) {
        if (!pickerInitialized || !selectorAnimationsEnabled) return;

        int selectorPosY = cellHeight * cellsBeforeSelector;
        int applyingRangeY = cellHeight;

        for (int i = 0; i < listView.getChildCount(); i++) {

            // Exclude elements that does not need to edit
            if (!(listView.getChildAt(i) instanceof FrameLayout))
                continue;

            ViewGroup itemRoot = (ViewGroup) listView.getChildAt(i);

            float deviation = 2f;
            if (itemRoot.getTop() > selectorPosY + applyingRangeY * deviation || itemRoot.getTop() < selectorPosY - applyingRangeY * deviation)
                continue;

            View cellContent = itemRoot.getChildAt(0);

            // Edit elements regarding to their position from selector
            float dy = Math.abs(itemRoot.getTop() - selectorPosY);
            if (!selectorAnimationCenterPivot) {
                cellContent.setPivotX(0);
                cellContent.setPivotY(cellContent.getHeight() / 2);
            }

            // Scale and "3d effect" for big scale factors on API>=LOLLIPOP
            if (dy <= applyingRangeY) {
                float k1 = 1 - (dy / applyingRangeY);
                float scale = 1 + scaleFactorDelta * k1;
                cellContent.setScaleX(scale);
                cellContent.setScaleY(scale);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                    itemRoot.setZ((dy <= applyingRangeY / 2) ? 2 : 1);

            } else {
                cellContent.setScaleX(1f);
                cellContent.setScaleY(1f);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                    itemRoot.setZ(0);
            }

        }
    }

}
