package com.ramotion.directselect;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

public abstract class DSAbstractPickerBox<T> extends FrameLayout {

    public DSAbstractPickerBox(@NonNull Context context) {
        super(context);
    }

    public DSAbstractPickerBox(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public abstract void onSelect(T selectedItem, int selectedIndex);

    public abstract View getAnimatedPart();

}
