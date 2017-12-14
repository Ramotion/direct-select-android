package com.ramotion.directselect.examples.simple;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ramotion.directselect.DSPickerBox;
import com.ramotion.directselect.R;

public class SimpleExamplePickerBox extends DSPickerBox<SimpleExampleDataPOJO> {

    private TextView title;
    private ImageView icon;

    public SimpleExamplePickerBox(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public SimpleExamplePickerBox(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(@NonNull Context context, AttributeSet attrs) {
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert mInflater != null;
        mInflater.inflate(R.layout.simple_example_picker_box, this, true);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.title = findViewById(R.id.picker_box_text);
        this.icon = findViewById(R.id.picker_box_img);
    }

    @Override
    public void onSelect(SimpleExampleDataPOJO selectedItem, int selectedIndex) {
        this.title.setText(selectedItem.getTitle());
        this.icon.setImageResource(selectedItem.getIcon());
    }

    @Override
    public View getAnimatedPart() {
        return this;
    }
}
