package com.example.hana.rentcostumes;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * Created by user pc on 28/10/2016.
 */

public class CheckoutCostume extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner inputSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout_costume);
        inputSize = (Spinner)findViewById(R.id.size);
        String list_size [] = {"Size", "XXS", "S", "M", "L", "XL", "XXL"};
        inputSize.setOnItemSelectedListener(this);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, list_size);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        inputSize.setAdapter(arrayAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
