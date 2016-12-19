package com.onelinelibrary;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dhurv on 19-12-2016.
 */

public class Trains_two extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train);
        Spinner spinner = (Spinner) findViewById(R.id.spinner3);
        List<String> categories_type = new ArrayList<String>();
        categories_type.add("Tap to source Station");
        categories_type.add("Meerut");
        categories_type.add("Noida");
        categories_type.add("Delhi");

        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories_type);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter1);

        Spinner spinner1 = (Spinner) findViewById(R.id.spinner);
        List<String> categories_type1 = new ArrayList<String>();
        categories_type1.add("Tap to destination Station");
        categories_type1.add("Meerut");
        categories_type1.add("Noida");
        categories_type1.add("Delhi");

        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories_type1);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinner1.setAdapter(dataAdapter2);

    }
    

}
