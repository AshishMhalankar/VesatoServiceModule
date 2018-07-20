package com.vesatago.userapp.modules.authentication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.vesatago.userapp.R;
import com.vesatago.userapp.helper.Validations;

import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static String TAG="Login";
    TextView tv_sign,tv_register,tv_needhelp;
    EditText ed_mobile;
    Validations validations;

    private FirebaseAuth mAuth;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    String mVerificationId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        validations=new Validations();
        init();


        mAuth = FirebaseAuth.getInstance();

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                Log.d(TAG, "onVerificationCompleted:" + phoneAuthCredential);
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Log.w(TAG, "onVerificationFailed", e);
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    ed_mobile.setError("Invalid phone number.");
                }
            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);

                Log.d(TAG, "onCodeSent:" + s);
                mVerificationId = s;
                mResendToken = forceResendingToken;

            }

        };

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = task.getResult().getUser();
                            startActivity(new Intent(LoginActivity.this, OtpVerificationAtivity.class));
                            finish();
                        } else {
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(getApplicationContext(),"invalid",Toast
                                        .LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    private void init() {

        tv_sign=findViewById(R.id.tv_sign);
        tv_register=findViewById(R.id.tv_submit);
        tv_needhelp=findViewById(R.id.tv_needhelp);

        ed_mobile=findViewById(R.id.ed_mobile);

        tv_sign.setOnClickListener(this);
        tv_register.setOnClickListener(this);
        tv_needhelp.setOnClickListener(this);
    }

    public void sendOTP(View v){
        if (!validatePhoneNumber()) {
            return;
        }
        startPhoneNumberVerification(ed_mobile.getText().toString());
    }


    private void startPhoneNumberVerification(String phoneNumber) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }

    private boolean validatePhoneNumber() {
        String phoneNumber = ed_mobile.getText().toString();
        if (TextUtils.isEmpty(phoneNumber) && phoneNumber.length()<10) {
            ed_mobile.setError("चुकीचा फोन नंबर");
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_sign:

                if(isValid())
                {
                    Intent intent=new Intent(this,OtpVerificationAtivity.class);
                    startActivity(intent);
                }

            break;

            case R.id.tv_submit:

                Intent intentRegister=new Intent(this,RegistrationActivity.class);
                startActivity(intentRegister);

                break;
            case R.id.tv_needhelp:

                break;
        }
    }

    private boolean isValid() {


        if(validations.isBlank(ed_mobile)){
            editTextAlert(ed_mobile, getString(R.string.error_mobile));
            return  false;
        }

        if(ed_mobile.getText().length()<10){
            editTextAlert(ed_mobile,getString(R.string.error_mobile));
            return false;
        }
        return  true;
    }

    private void editTextAlert(EditText ed_mobileno, String s) {
        ed_mobileno.setError(s);

    }
}
