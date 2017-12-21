package com.ramotion.directselect.examples.simple;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;

import com.ramotion.directselect.DSListView;
import com.ramotion.directselect.R;

import java.util.List;

public class AdvancedExampleActivity extends AppCompatActivity {

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.advanced_example_activity);

        List<AdvancedExampleDataPOJO> exampleDataset = AdvancedExampleDataPOJO.getExampleDataset();

        ArrayAdapter<AdvancedExampleDataPOJO> adapter = new AdvancedExampleAdapter(getApplicationContext(), R.layout.advanced_example_list_item, exampleDataset);

        DSListView<AdvancedExampleDataPOJO> pickerView = findViewById(R.id.ds_nba_picker);

        pickerView.setAdapter(adapter);

    }

}
