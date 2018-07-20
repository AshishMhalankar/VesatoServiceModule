package com.vesatago.userapp.modules.vehicle;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.vesatago.userapp.R;
import com.vesatago.userapp.helper.Validations;

import java.util.Calendar;

public class VehicleHireActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {


    private int mYear, mMonth, mDay, mHour, mMinute;
    TextView tv_time, tv_date, tv_submit;
    ImageView img_date, img_time;

    Spinner spinnar_market, spinnar_crop;
    RadioGroup radiogrp_sack;
    private RadioButton radiosack;

    Validations validations;
    String strCrop,strMarket;

    String[] arrayMarket = {"select", "नाशिक मार्केट यार्ड", "पिंपळ गाव", "गिरणारा","ओझर","सह्याद्री फार्म्स ", "Other"};
    String[] arrayCrop = {"select", "USA", "China", "Japan", "Other"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_hire);

        validations=new Validations();


        init();
    }

    private void init() {

        img_date = findViewById(R.id.img_date);
        img_time = findViewById(R.id.img_time);

        tv_date = findViewById(R.id.tv_date);
        tv_time = findViewById(R.id.tv_time);


        spinnar_market = findViewById(R.id.spinnar_market);
        spinnar_crop = findViewById(R.id.spinnar_crop);

        tv_submit = findViewById(R.id.tv_submit);
        radiogrp_sack = findViewById(R.id.radiogrp_sack);

        spinnar_market.setOnItemSelectedListener(this);
        spinnar_crop.setOnItemSelectedListener(this);

        ArrayAdapter ArrayCrop = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arrayCrop);
        ArrayCrop.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnar_crop.setAdapter(ArrayCrop);

        ArrayAdapter ArrayMarket = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arrayMarket);
        ArrayMarket.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnar_market.setAdapter(ArrayMarket);


        img_time.setOnClickListener(this);
        img_date.setOnClickListener(this);
        tv_submit.setOnClickListener(this);
    }

    private void timepicker() {


        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

                        tv_time.setText(hourOfDay + ":" + minute);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }


    private void datepicker() {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        tv_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_time:

                timepicker();
                break;

            case R.id.img_date:
                datepicker();

                break;

            case R.id.tv_submit:

                if(isValid()){

                }
                break;
        }
    }

    private boolean isValid() {
        if(strCrop.equals("select")){
            Toast.makeText(this, R.string.error_selectcrop, Toast.LENGTH_SHORT).show();
            return  false;
        }

        if(strMarket.equals("select")){
            Toast.makeText(this, R.string.error_selectmarket, Toast.LENGTH_SHORT).show();
            return  false;
        }

        int selectedEquip=radiogrp_sack.getCheckedRadioButtonId();
        radiosack=(RadioButton)findViewById(selectedEquip);

        if (radiogrp_sack.getCheckedRadioButtonId() == -1) {

            Toast.makeText(this, "Please select radio option", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(tv_date.getText().toString().equals(R.string.date)){
            Toast.makeText(this, R.string.error_selectdate, Toast.LENGTH_SHORT).show();
            return  false;
        }

        if(tv_time.getText().toString().equals(R.string.time)){
            Toast.makeText(this,  getString(R.string.error_time), Toast.LENGTH_SHORT).show();
            return  false;
        }


        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Spinner spinner = (Spinner) parent;
        if (spinner.getId() == R.id.spinnar_market) {
            strMarket=  parent.getItemAtPosition(position).toString();
        }
        if (spinner.getId() == R.id.spinnar_crop) {
            strCrop= parent.getItemAtPosition(position).toString();

        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
