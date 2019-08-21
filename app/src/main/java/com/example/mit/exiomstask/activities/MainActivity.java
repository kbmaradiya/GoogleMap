package com.example.mit.exiomstask.activities;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.mit.exiomstask.R;
import com.example.mit.exiomstask.utils.Constant;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getName();
    private HashMap<String, Place> placesArrayList;
    private final int AUTOCOMPLETE_REQUEST_CODE = 1;

    private Button btn_Start;
    private Button btn_start_static;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Log.e(TAG,"onCreate");
        init();

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.e(TAG,"onNewIntent");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG,"onResume");

    }

    private void init() {

        btn_Start = findViewById(R.id.btn_Start);
        btn_Start.setOnClickListener(this);

        btn_start_static = findViewById(R.id.btn_start_static);
        btn_start_static.setOnClickListener(this);

        placesArrayList = new HashMap<>();

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), getString(R.string.google_api_key));
        }
        Places.createClient(this);


        initializeForSource(R.id.autocomplete_fragment_source, Constant.SOURCE);
        initializeForSource(R.id.autocomplete_fragment_dest, Constant.DESTINATION);

    }

    private void initializeForSource(int autocomplete_fragment_source, final String hint) {
        final AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(autocomplete_fragment_source);

        autocompleteFragment.setCountry("IN");

        final EditText edtPlace = (EditText) autocompleteFragment.getView().findViewById(R.id.places_autocomplete_search_input);
        edtPlace.setHint("" + hint);

        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG));

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
                edtPlace.setText(place.getAddress());

                placesArrayList.put(hint, place);

            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);

            }

        });
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_Start:

                if (placesArrayList.size() >= 2) {
                    Intent intent = new Intent(MainActivity.this, MapActivity.class);
                    intent.putExtra("latlng", placesArrayList);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Select Source and Destination properly!", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.btn_start_static:


//                Constant.scheduleJob(this);

                String i="100";
                String k="100";

                Log.e(TAG,"== "+(i==k));
                Log.e(TAG,"== e "+(i.equals(k)));


                Intent intent = new Intent(MainActivity.this, MapActivity.class);
                startActivity(intent);


                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}

