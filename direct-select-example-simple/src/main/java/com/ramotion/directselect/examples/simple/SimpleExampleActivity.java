package com.ramotion.directselect.examples.simple;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;

import com.ramotion.directselect.DSListView;
import com.ramotion.directselect.R;

import java.util.List;

public class SimpleExampleActivity extends AppCompatActivity {

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_example_activity);

        List<SimpleExampleDataPOJO> exampleDataset = SimpleExampleDataPOJO.getExampleDataset();

        ArrayAdapter<SimpleExampleDataPOJO> adapter = new SimpleExampleAdapter(getApplicationContext(), R.layout.simple_example_list_item, exampleDataset);

        DSListView<SimpleExampleDataPOJO> pickerView = findViewById(R.id.ds_nba_picker);

        pickerView.setAdapter(adapter);

    }

}
