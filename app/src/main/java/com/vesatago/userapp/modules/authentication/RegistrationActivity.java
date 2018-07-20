package com.vesatago.userapp.modules.authentication;

import android.app.Dialog;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.vesatago.userapp.R;
import com.vesatago.userapp.helper.Validations;
import com.vesatago.userapp.modules.MainActivity;

import java.util.concurrent.TimeUnit;

public class RegistrationActivity extends AppCompatActivity  {

//    implements View.OnClickListener, AdapterView.OnItemSelectedListener

    EditText ed_name,ed_mobileno,ed_landsize,ed_villagename, ed_farmadd;
    RadioGroup radiogrp_vehical,radiogrp_equipment;
    private RadioButton radioVehical,radioEquipment;
    TextView tv_submit,tv_needhelp;
    Validations validations;


    TextView farmAddText, farmSizeText, farmAcre;

    //vehicle Dialog

    //Spinner spinnar_vheclename;
    //EditText ed_licenceno,ed_vn_state,ed_vn_rtono,rd_vn_no,ed_v_capacity;
    //TextView tv_dsubmit;

    RadioButton landSelectionYes,landSelectionNo;

    private static final String TAG = "Register User";

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference mUsers = db.collection("users");

    private static int MAX_PHONE_LENGTH = 10;
    private static int MAX_LAND_SIZE_LENGTH = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        validations=new Validations();

        // init();

        ed_name=findViewById(R.id.ed_name);
        ed_mobileno=findViewById(R.id.ed_mobileno);
        ed_landsize=findViewById(R.id.ed_landsize);
        ed_villagename=findViewById(R.id.ed_villagename);
        ed_farmadd = findViewById(R.id.land_add);

        ed_mobileno.setFilters(new InputFilter[]{new InputFilter.LengthFilter(MAX_PHONE_LENGTH)});
        ed_landsize.setFilters(new InputFilter[]{new InputFilter.LengthFilter(MAX_LAND_SIZE_LENGTH)});


        //TextViews
        farmSizeText = findViewById(R.id.land_size_text);
        farmAcre = findViewById(R.id.acre_text);
        farmAddText = findViewById(R.id.farm_add_text);

        landSelectionYes = findViewById(R.id.land_yes);

        landSelectionYes.setChecked(true);

    }

    private boolean isValid(){
        if(validations.isBlank(ed_name)){
            editTextAlert(ed_name, "कृपया आपले नाव प्रविष्ट करा");
            return  false;
        }

        if(validations.isBlank(ed_landsize)){
            editTextAlert(ed_landsize, "कृपया जमीनीचा आकार प्रविष्ट करा");
            return  false;
        }

        if(validations.isBlank(ed_farmadd)){
            editTextAlert(ed_farmadd, "कृपया आपला पत्ता प्रविष्ट करा");
            return  false;
        }

        if(ed_landsize.getText().length()>3){
            editTextAlert(ed_landsize,"कृपया जमीन क्षेत्र योग्य करा");
            return false;
        }


        if(ed_mobileno.getText().length()<10){
            editTextAlert(ed_mobileno,"कृपया फोन नंबर योग्य करा");
            return false;
        }
        return  true;
    }

    private void editTextAlert(EditText et, String s) {
        et.setError(s);

    }

    public void onRadioBtnClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.land_yes:
                if (checked)
                    // Pirates are the best
                    ed_farmadd.setVisibility(View.VISIBLE);
                ed_landsize.setVisibility(View.VISIBLE);

                farmSizeText.setVisibility(View.VISIBLE);
                farmAcre.setVisibility(View.VISIBLE);
                farmAddText.setVisibility(View.VISIBLE);
                break;
            case R.id.land_no:
                if (checked)
                    // Ninjas rule
                    ed_farmadd.setVisibility(View.INVISIBLE);
                ed_landsize.setVisibility(View.INVISIBLE);

                farmSizeText.setVisibility(View.INVISIBLE);
                farmAcre.setVisibility(View.INVISIBLE);
                farmAddText.setVisibility(View.INVISIBLE);

                break;

        }
    }

    public void saveData(View v){

        String name = ed_name.getText().toString();
        String villageName = ed_villagename.getText().toString();

        int mobile = 0, landSize=0;
        try{
            mobile = Integer.parseInt(ed_mobileno.getText().toString());
            landSize = Integer.parseInt(ed_landsize.getText().toString());

        }
        catch (NumberFormatException e){
            Log.d(TAG,"not a number");
        }
        String landAdd = ed_farmadd.getText().toString();


        if(isValid()){
            SaveRegisteredUserData userData = new SaveRegisteredUserData(landAdd,name,villageName,
                    mobile,landSize);

            mUsers.add(userData)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(RegistrationActivity.this, "वापरकर्त्याने यशस्वीरित्या नोंदणीकृत केले", Toast
                                    .LENGTH_SHORT).show();

                            Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(RegistrationActivity.this,
                                    "नोंदणी करताना समस्या!",
                                    Toast
                                            .LENGTH_SHORT)
                                    .show();
                        }
                    });
        }
        else
        {
            Toast.makeText(RegistrationActivity.this,
                    "नोंदणी करताना समस्या!", Toast
                            .LENGTH_SHORT).show();
        }



    }

//    private void init() {
//
//        ed_name=findViewById(R.id.ed_name);
//        ed_mobileno=findViewById(R.id.ed_mobileno);
//        ed_landsize=findViewById(R.id.ed_landsize);
//        ed_villagename=findViewById(R.id.ed_villagename);
//
//        radiogrp_vehical=findViewById(R.id.radiogrp_vehical);
//        radiogrp_equipment=findViewById(R.id.radiogrp_equipment);
//
//        tv_submit=findViewById(R.id.tv_submit);
//        tv_needhelp=findViewById(R.id.tv_needhelp);
//
//        tv_submit.setOnClickListener(this);
//        tv_needhelp.setOnClickListener(this);
//
//
//        radiogrp_vehical.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//
//            @Override
//            public void onCheckedChanged (RadioGroup group,int checkedId){
//
//
//
//                if (checkedId == R.id.v_yes) {
//                    DialogVehicalRegistration();
//                }
//            }
//
//
//        });
//
//
//        radiogrp_equipment.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//
//            @Override
//            public void onCheckedChanged (RadioGroup group,int checkedId){
//
//
//
//                if (checkedId == R.id.e_yes) {
//                    DialogEquipmentRegistration();
//                }
//            }
//
//
//        });
//
//
//
//
//    }
//
//    private void DialogVehicalRegistration() {
//
//      Dialog  dialog_VehicalRegistration= new Dialog(this);
//
//        String[] arrayVehicleName = { getString(R.string.vehicle_name)+" निवडा", "USA", "China", "Japan", "Other"};
//
//        dialog_VehicalRegistration.setContentView(R.layout.dialog_vehicle);
//        spinnar_vheclename=dialog_VehicalRegistration.findViewById(R.id.spinnar_vheclename);
//        ed_licenceno=dialog_VehicalRegistration.findViewById(R.id.ed_licenceno);
//        ed_vn_state=dialog_VehicalRegistration.findViewById(R.id.ed_vn_state);
//        ed_vn_rtono=dialog_VehicalRegistration.findViewById(R.id.ed_vn_rtono);
//        rd_vn_no=dialog_VehicalRegistration.findViewById(R.id.rd_vn_no);
//        ed_v_capacity=dialog_VehicalRegistration.findViewById(R.id.ed_v_capacity);
//
//        tv_dsubmit=dialog_VehicalRegistration.findViewById(R.id.tv_submit);
//
//        spinnar_vheclename.setOnItemSelectedListener(this);
//
//        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,arrayVehicleName);
//        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        //Setting the ArrayAdapter data on the Spinner
//        spinnar_vheclename.setAdapter(aa);
//
//        tv_dsubmit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(validations.isBlank(ed_licenceno)){
//                    editTextAlert(ed_licenceno,  getString(R.string.error_please_enter_icence_no));
//                }
//                if(validations.isBlank(ed_vn_state)){
//                    editTextAlert(ed_vn_state,  getString(R.string.error_please_enter_this_field));
//                }
//                if(validations.isBlank(ed_vn_rtono)){
//                    editTextAlert(ed_vn_rtono,  getString(R.string.error_please_enter_this_field));
//                }
//                if(validations.isBlank(rd_vn_no)){
//                    editTextAlert(rd_vn_no,  getString(R.string.error_please_enter_this_field));
//                }
//
//
//
//            }
//        });
//
//
//
//
//        dialog_VehicalRegistration.show();
//    }
//
//    private void DialogEquipmentRegistration() {
//
//        Dialog  dialog_EquipmentRegistration= new Dialog(this);
//
//        dialog_EquipmentRegistration.setContentView(R.layout.dialog_equipment);
//        //  dialog_prebookAlertPopup.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//     //   dialog_EquipmentRegistration.setCancelable(false);
//
//
//        dialog_EquipmentRegistration.show();
//    }
//
//    @Override
//    public void onClick(View v) {
//
//
//        switch (v.getId()){
//            case R.id.tv_submit:
//
//                if(isValid()){
//
//                }
//                break;
//
//            case R.id.tv_needhelp:
//                break;
//        }
//    }
//
//    private boolean isValid() {
//        if(validations.isBlank(ed_name)){
//            editTextAlert(ed_name,getString(R.string.error_name));
//            return false;
//        }
//        if(validations.isBlank(ed_mobileno)){
//            editTextAlert(ed_mobileno,getString(R.string.error_mobile));
//            return false;
//        }
//
//        if(validations.isValidPhone(ed_mobileno)){
//            editTextAlert(ed_mobileno,getString(R.string.error_mobile));
//            return false;
//        }
//
//        if(validations.isBlank(ed_landsize)){
//            editTextAlert(ed_landsize, getString(R.string.error_land_size));
//            return false;
//        }
//
//        if(ed_landsize.getText().length()<0){
//            editTextAlert(ed_landsize,"\n" +
//                    getString(R.string.error_land_size));
//            return false;
//        }
//        if(validations.isBlank(ed_villagename)){
//            editTextAlert(ed_villagename,  getString(R.string.error_villagename));
//            return false;
//        }
//
//        int selectedvehical=radiogrp_vehical.getCheckedRadioButtonId();
//        radioVehical=(RadioButton)findViewById(selectedvehical);
//
//        if (radiogrp_vehical.getCheckedRadioButtonId() == -1) {
//
//            Toast.makeText(this, "Please select vehicle option", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//
//        int selectedEquip=radiogrp_equipment.getCheckedRadioButtonId();
//        radioEquipment=(RadioButton)findViewById(selectedEquip);
//
//        if (radiogrp_equipment.getCheckedRadioButtonId() == -1) {
//
//            Toast.makeText(this, "Please select equipment option", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//
//
//        return true;
//    }
//
//    private void editTextAlert(EditText edittext, String s) {
//        edittext.setError(s);
//
//    }
//
//
//    @Override
//    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> parent) {
//
//    }
}
