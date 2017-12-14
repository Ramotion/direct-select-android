package com.ramotion.directselect;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class DSPickerBoxDefault extends DSPickerBox<String> {

    private TextView textView;

    public DSPickerBoxDefault(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public DSPickerBoxDefault(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(@NonNull Context context, AttributeSet attrs) {
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert mInflater != null;
        mInflater.inflate(R.layout.ds_default_picker_box, this, true);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.textView = findViewById(R.id.picker_box_text);
    }

    @Override
    public void onSelect(String value, int index) {
        this.textView.setText(value);
    }

    @Override
    public View getAnimatedPart() {
        return this.textView;
    }

}
