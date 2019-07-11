package dscountr.app.co.ke.dscountr_android_app.view.registration.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import dscountr.app.co.ke.dscountr_android_app.R;
import dscountr.app.co.ke.dscountr_android_app.view.utils.CodeEntryEditText;

public class VerifyEmailFragment extends Fragment implements Toolbar.OnMenuItemClickListener, Button.OnClickListener{

    String phone_number = null, phone_number_verification_code = null, email = null;
    CodeEntryEditText enterEmailVerification;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View verify_email = inflater.inflate(R.layout.fragment_verify_email, container, false);

        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setOnMenuItemClickListener(this);
        Button btnEmailVerificaton = verify_email.findViewById(R.id.btnEmailVerificaton);
        btnEmailVerificaton.setOnClickListener(this);

        LinearLayout llVerifyBack = verify_email.findViewById(R.id.llVerifyBack);
        TextView tvResendVerificationCode = verify_email.findViewById(R.id.tvResendVerificationCode);
        tvResendVerificationCode.setOnClickListener(this);
        llVerifyBack.setOnClickListener(this);

        Bundle args = this.getArguments();
        if(args != null){
            phone_number = args.getString("phone_number");
            phone_number_verification_code = args.getString("phone_number_verification_code");
            email = args.getString("email");
        }

        enterEmailVerification = verify_email.findViewById(R.id.enterEmailVerification);

        return verify_email;
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
                Toast.makeText(getActivity(), "Email verification code help.",Toast.LENGTH_SHORT).show();
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnEmailVerificaton:
                verifyEmailCode();
                break;
            case R.id.llVerifyBack:
                Toast.makeText(getActivity(), "You clicked the back button.",Toast.LENGTH_SHORT).show();
                break;
            case R.id.tvResendVerificationCode:
                Toast.makeText(getActivity(), "Resend the email verification code.",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;

        }
    }

    private void verifyEmailCode(){
        String email_verification_code = enterEmailVerification.getText().toString();
        if (TextUtils.isEmpty(email_verification_code)){
            Toast.makeText(getActivity(), "Please enter email verification code.",Toast.LENGTH_SHORT).show();
        }else if (email_verification_code.length() < 6){
            Toast.makeText(getActivity(), "Email verification code isn't complete.",Toast.LENGTH_SHORT).show();
        }else{
            Bundle bundle = new Bundle();
            bundle.putString("phone_number", phone_number);
            bundle.putString("phone_number_verification_code", phone_number_verification_code);
            bundle.putString("email", email);
            bundle.putString("email_verification_code", email_verification_code);
            Fragment verifyEmail = new DateOfBirthFragment();
            verifyEmail.setArguments(bundle);
            loadFragment(verifyEmail);
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
}