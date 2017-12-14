package com.ramotion.directselect.examples.basic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ramotion.directselect.DSListView;

public class BasicExampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ds_basic_example_activity);


        DSListView listview = findViewById(R.id.ds_picker);

        listview.getSelectedItem();
    }
}
