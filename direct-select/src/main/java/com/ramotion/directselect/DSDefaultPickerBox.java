package com.ramotion.directselect;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Default implementation of Picker Box element for Strings to work "from the box"
 * with default cell layout
 */
public class DSDefaultPickerBox extends DSAbstractPickerBox<String> {

    private TextView textView;
    private ViewGroup cellRoot;

    public DSDefaultPickerBox(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public DSDefaultPickerBox(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(@NonNull Context context, AttributeSet attrs) {
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert mInflater != null;
        mInflater.inflate(R.layout.ds_default_picker_box, this, true);
        this.setClipChildren(false);
        this.setClipToPadding(false);
    }

    void setCellTextSize(int cellTextSize) {
        if (cellTextSize > 0)
            this.textView.setTextSize(cellTextSize);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.textView = findViewById(R.id.ds_default_cell_text);
        this.cellRoot = findViewById(R.id.ds_default_cell_root);
        this.cellRoot.setMinimumHeight(this.getHeight());
    }

    @Override
    public void onSelect(String value, int index) {
        this.textView.setText(value);
    }

    @Override
    public View getCellRoot() {
        return this.cellRoot;
    }

}
