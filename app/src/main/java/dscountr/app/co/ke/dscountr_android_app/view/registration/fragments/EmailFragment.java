package dscountr.app.co.ke.dscountr_android_app.view.registration.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dscountr.app.co.ke.dscountr_android_app.R;
import dscountr.app.co.ke.dscountr_android_app.view.Help;

public class EmailFragment extends Fragment implements Toolbar.OnMenuItemClickListener, Button.OnClickListener{

    String phone_number = null, phone_number_verification_code = null, first_name = null, last_name = null;
    TextInputLayout tlenterEmail;
    TextInputEditText enterEmail;
    String EMAIL_PATTERN = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View email = inflater.inflate(R.layout.fragment_email, container, false);
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setOnMenuItemClickListener(this);
        Button btnEmail = email.findViewById(R.id.btnEmail);
        btnEmail.setOnClickListener(this);

        Bundle args = this.getArguments();
        if(args != null){
            phone_number = args.getString("phone_number");
            phone_number_verification_code = args.getString("phone_number_verification_code");
            first_name = args.getString("first_name");
            last_name = args.getString("last_name");
        }

        tlenterEmail = email.findViewById(R.id.tlenterEmail);
        enterEmail = email.findViewById(R.id.enterEmail);

        return email;
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
                Intent i = new Intent(getActivity(), Help.class);
                startActivity(i);
                getActivity().overridePendingTransition(R.transition.slide_in, R.transition.slide_out);
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnEmail:
                emailAddressValidation();
                break;
            default:
                break;

        }
    }

    private void emailAddressValidation(){
        String email = enterEmail.getText().toString();
        if(TextUtils.isEmpty(email)){
            tlenterEmail.setError("Please enter email address.");
            enterEmail.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.error_bottom_edittext));
        }else if (!validateEmail(email)) {
            tlenterEmail.setError("Please enter valid email address.");
            enterEmail.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.error_bottom_edittext));
        }else{
            tlenterEmail.setError(null);
            tlenterEmail.setErrorEnabled(false);
            enterEmail.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bottom_edittext));

            Bundle bundle = new Bundle();
            bundle.putString("phone_number", phone_number);
            bundle.putString("email", email);
            bundle.putString("first_name", first_name);
            bundle.putString("last_name", last_name);
            Fragment verifyMobile = new DateOfBirthFragment();
            verifyMobile.setArguments(bundle);
            loadFragment(verifyMobile);
        }
    }

    public boolean validateEmail(String email) {
        Pattern p = Pattern.compile(EMAIL_PATTERN);
        Matcher m = p.matcher(email);
        return m.matches();
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