package dscountr.app.co.ke.dscountr_android_app.view.registration.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import dscountr.app.co.ke.dscountr_android_app.R;
import dscountr.app.co.ke.dscountr_android_app.view.utils.CodeEntryEditText;

public class VerifyMobileFragment extends Fragment implements Toolbar.OnMenuItemClickListener, Button.OnClickListener{

    public static String TAG = VerifyMobileFragment.class.getSimpleName();
    String phone_number = null;
    CodeEntryEditText enterNumberVerification;
    //firebase auth object
    private FirebaseAuth mAuth;
    //It is the verification id that will be sent to the user
    private String mVerificationId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View verify_mobile = inflater.inflate(R.layout.fragment_verify_mobile, container, false);
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setOnMenuItemClickListener(this);
        Button btnNumberVerificaton = verify_mobile.findViewById(R.id.btnNumberVerificaton);
        btnNumberVerificaton.setOnClickListener(this);

        LinearLayout llVerifyBack = verify_mobile.findViewById(R.id.llVerifyBack);
        TextView tvResendVerificationCode = verify_mobile.findViewById(R.id.tvResendVerificationCode);
        tvResendVerificationCode.setOnClickListener(this);
        llVerifyBack.setOnClickListener(this);

        //initializing objects
        mAuth = FirebaseAuth.getInstance();
        Bundle args = this.getArguments();
        if(args != null){
            phone_number = args.getString("phone_number");
            //sending the verification code to the number
            sendVerificationCode(phone_number);
        }

        enterNumberVerification = verify_mobile.findViewById(R.id.enterNumberVerification);

        return verify_mobile;
    }

    private void loadFragment(Fragment fragment) {
        // create a FragmentManager
        FragmentManager fragmentManager = getFragmentManager();
        // create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        // replace the FrameLayout with new Fragment
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit(); // save the changes
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.miRegistrationHelp:
                Toast.makeText(getActivity(), "Mobile verification code help.",Toast.LENGTH_SHORT).show();
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnNumberVerificaton:
                verifyCode();
                break;
            case R.id.llVerifyBack:
                Toast.makeText(getActivity(), "You clicked the back button.",Toast.LENGTH_SHORT).show();
                break;
            case R.id.tvResendVerificationCode:
                Toast.makeText(getActivity(), "Resend the number verification code.",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;

        }
    }

    private void verifyCode(){
        String phone_number_verification_code = enterNumberVerification.getText().toString();
        if (TextUtils.isEmpty(phone_number_verification_code)){
            Toast.makeText(getActivity(), "Please enter phone verification code.",Toast.LENGTH_SHORT).show();
        }else if (phone_number_verification_code.length() < 6){
            Toast.makeText(getActivity(), "Phone verification code isn't complete.",Toast.LENGTH_SHORT).show();
        }else{
            verifyVerificationCode(phone_number_verification_code);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private String getDeviceCountryCode() {
        String countryCode;

        // try to get country code from TelephonyManager service
        TelephonyManager tm = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        if(tm != null) {
            // query first getSimCountryIso()
            countryCode = tm.getSimCountryIso();
            if (countryCode != null && countryCode.length() == 2)
                return countryCode.toUpperCase();

            if (tm.getPhoneType() != TelephonyManager.PHONE_TYPE_CDMA) {
                // for 3G devices (with SIM) query getNetworkCountryIso()
                countryCode = tm.getNetworkCountryIso();
            }

            if (countryCode != null && countryCode.length() == 2)
                return countryCode.toUpperCase();
        }

        // if network country not available (tablets maybe), get country code from Google Location

        // general fallback to "KE"
        return "KE";
    }

    //the method is sending verification code
    //the country id is concatenated
    //you can take the country id as user input as well
    private void sendVerificationCode(String mobile) {
        String iso = getDeviceCountryCode();
        if (!mobile.startsWith("+")){
            switch (iso) {
                case "KE":
                    mobile = "+254" + mobile;
                    break;
                case "UG":
                    mobile = "+256" + mobile;
                    break;
                case "TZ":
                    mobile = "+255" + mobile;
                    break;
            }
        }
        Log.e(TAG, mobile);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                mobile,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallbacks);
    }

    //the callback to detect the verification status
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            //Getting the code sent by SMS
            String code = phoneAuthCredential.getSmsCode();

            //sometime the code is not detected automatically
            //in this case the code will be null
            //so user has to manually enter the code
            if (code != null) {
                enterNumberVerification.setText(code);
                //verifying the code
                verifyVerificationCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            //storing the verification id that is sent to the user
            mVerificationId = s;
        }
    };

    private void verifyVerificationCode(String code) {
        //creating the credential
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);

        //signing the user
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //verification successful
                            Bundle bundle = new Bundle();
                            bundle.putString("phone_number", phone_number);
                            bundle.putString("phone_number_verification_code", enterNumberVerification.getText().toString());
                            Fragment emailFragment = new EmailFragment();
                            emailFragment.setArguments(bundle);
                            loadFragment(emailFragment);

                        } else {

                            //verification unsuccessful.. display an error message

                            String message = "Something is wrong, we will fix it soon...";

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                message = "Invalid code entered...";
                            }

                            Toast.makeText(getActivity(), message,Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}