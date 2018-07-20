package com.vesatago.userapp.modules.authentication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.vesatago.userapp.R;
import com.vesatago.userapp.helper.Validations;
import com.vesatago.userapp.modules.HomeFragment;
import com.vesatago.userapp.modules.MainActivity;

public class OtpVerificationAtivity extends AppCompatActivity {


    EditText ed_otp;
    TextView tv_submit;
    Validations validations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification_ativity);
        validations=new Validations();



        init();
    }

    private void init() {

        ed_otp=findViewById(R.id.ed_otp);
        tv_submit=findViewById(R.id.tv_submit);

        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValid()){
                    Intent intent=new Intent(OtpVerificationAtivity.this,MainActivity.class);
                    startActivity(intent);
                }
            }


        });
    }

    private boolean isValid() {
        if(validations.isBlank(ed_otp)){
            editTextAlert(ed_otp,getString(R.string.error_otp));
            return  false;
        }
        if(ed_otp.getText().length()>4){
            editTextAlert(ed_otp,getString(R.string.error_otp));
            return  false;
        }
        return true;
    }
    private void editTextAlert(EditText editText, String s) {
        editText.setError(s);

    }

}
