package com.ramotion.directselect;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Represents a Picker Box for Direct Select with specified data type
 *
 * @param <T> data type for direct select and  picker box elements
 */
public abstract class DSAbstractPickerBox<T> extends FrameLayout {

    public DSAbstractPickerBox(@NonNull Context context) {
        super(context);
    }

    public DSAbstractPickerBox(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DSAbstractPickerBox(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * This method will be invoked when directselect select's a item
     *
     * @param selectedItem  data object of selected item
     * @param selectedIndex index of selected item
     */
    public abstract void onSelect(T selectedItem, int selectedIndex);

    /**
     * This method must return a root view of your custom cell in most cases.
     * This view and all his children views will be used for scroll/show animations if they are enabled
     */
    public abstract View getCellRoot();

}
