package dscountr.app.co.ke.dscountr_android_app.view.registration.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import java.util.Arrays;
import java.util.List;

import dscountr.app.co.ke.dscountr_android_app.R;
import dscountr.app.co.ke.dscountr_android_app.view.utils.CountryData;

public class MobileFragment extends Fragment implements Toolbar.OnMenuItemClickListener, Button.OnClickListener {

    TextInputEditText enterNumber;
    TextInputLayout tlenterNumber;
    CheckBox radioAgree;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View mobile = inflater.inflate(R.layout.fragment_mobile, container, false);
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setOnMenuItemClickListener(this);
        Button btnNumber = mobile.findViewById(R.id.btnNumber);
        TextView tvTerms = mobile.findViewById(R.id.tvTerms);
        btnNumber.setOnClickListener(this);
        tvTerms.setOnClickListener(this);

        enterNumber = mobile.findViewById(R.id.enterNumber);
        tlenterNumber = mobile.findViewById(R.id.tlenterNumber);
        radioAgree = mobile.findViewById(R.id.radioAgree);

        return mobile;
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
                Toast.makeText(getActivity(), "Mobile number help.", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnNumber:
                phoneNumberValidation();
                break;
            case R.id.tvTerms:
                Toast.makeText(getActivity(), "Our terms and conditions.", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;

        }
    }


    private void phoneNumberValidation() {
        String phone_number = enterNumber.getText().toString();
        if (!radioAgree.isChecked()) {
            radioAgree.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.error_bottom_edittext));
            Toast.makeText(getActivity(), "Please Agree to the Terms of Service and Privacy Policy", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(phone_number)) {
            tlenterNumber.setError("Please enter phone number.");
            enterNumber.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.error_bottom_edittext));
        } else if (!isValidPhoneNumber(phone_number) || phone_number.length() != 10) {
            tlenterNumber.setError("Please enter valid phone number.");
            enterNumber.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.error_bottom_edittext));
        } else {
            // set Error To Null
            tlenterNumber.setError(null);
            tlenterNumber.setErrorEnabled(false);
            enterNumber.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bottom_edittext));

            Bundle bundle = new Bundle();
            List list = Arrays.asList(CountryData.countryISO);
            int position = list.indexOf(getDeviceCountryCode());
            String code = CountryData.countryAreaCodes[position];
            bundle.putString("phone_number", phone_number.startsWith("+") ? phone_number : "+" + code + phone_number);
            Fragment verifyMobile = new VerifyMobileFragment();
            verifyMobile.setArguments(bundle);
            loadFragment(verifyMobile);
        }
    }

    /**
     * Validation of Phone Number
     */
    public boolean isValidPhoneNumber(String target) {
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        try {
            Phonenumber.PhoneNumber numberProto = phoneUtil.parse(target, getDeviceCountryCode());
            return phoneUtil.isValidNumber(numberProto);
        } catch (NumberParseException e) {
            System.err.println("NumberParseException was thrown: " + e.toString());
        }

        return false;
    }

    private String getDeviceCountryCode() {
        String countryCode;

        // try to get country code from TelephonyManager service
        TelephonyManager tm = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        if (tm != null) {
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

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}