package dscountr.app.co.ke.dscountr_android_app.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
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
import dscountr.app.co.ke.dscountr_android_app.model.User;
import dscountr.app.co.ke.dscountr_android_app.presenter.retrofit.MainApplication;
import dscountr.app.co.ke.dscountr_android_app.view.utils.CountryData;
import dscountr.app.co.ke.dscountr_android_app.view.utils.SharedPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener, View.OnClickListener {

    public static String TAG = LoginActivity.class.getSimpleName();
    TextInputEditText enterNumber;
    TextInputLayout tlenterNumber;
    private ProgressDialog pd = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.registration_menu);
        toolbar.setOnMenuItemClickListener(this);

        Button btnNumber = findViewById(R.id.btnNumber);
        TextView tvTerms = findViewById(R.id.tvTerms);
        btnNumber.setOnClickListener(this);
        tvTerms.setOnClickListener(this);

        enterNumber = findViewById(R.id.enterNumber);
        tlenterNumber = findViewById(R.id.tlenterNumber);
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.miRegistrationHelp:
                Toast.makeText(LoginActivity.this, "Sign in help.", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnNumber:
                phoneNumberValidation();
                break;
            case R.id.tvTerms:
                Toast.makeText(LoginActivity.this, "Our terms and conditions.", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;

        }
    }

    private void phoneNumberValidation() {
        String phone_number = enterNumber.getText().toString();
        if (TextUtils.isEmpty(phone_number)) {
            tlenterNumber.setError("Please enter phone number.");
            enterNumber.setBackground(ContextCompat.getDrawable(LoginActivity.this, R.drawable.error_bottom_edittext));
        } else if (!isValidPhoneNumber(phone_number) || phone_number.length() != 10) {
            tlenterNumber.setError("Please enter valid phone number.");
            enterNumber.setBackground(ContextCompat.getDrawable(LoginActivity.this, R.drawable.error_bottom_edittext));
        } else {
            // set Error To Null
            tlenterNumber.setError(null);
            tlenterNumber.setErrorEnabled(false);
            enterNumber.setBackground(ContextCompat.getDrawable(LoginActivity.this, R.drawable.bottom_edittext));

            Bundle bundle = new Bundle();
            List list = Arrays.asList(CountryData.countryISO);
            int position = list.indexOf(getDeviceCountryCode());
            String code = CountryData.countryAreaCodes[position];
            if (pd == null) {
                pd = new ProgressDialog(LoginActivity.this);
                pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                pd.setTitle("User sign in");
                pd.setMessage("Please wait...");
                pd.setIndeterminate(false);
            }
            pd.show();
            userLogin("+" + code + phone_number);
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
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
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

    private void userLogin(String phone_number) {

        MainApplication.apiManager.loginUser(phone_number, new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User responseUser = response.body();
                if (response.isSuccessful() && responseUser != null) {
                    Log.e(TAG, responseUser.getPhone_number());
                    Toast.makeText(LoginActivity.this, "Sign in successful.", Toast.LENGTH_LONG).show();
                    SharedPrefManager.getInstance(getApplicationContext()).setKeyToken(responseUser.getToken());
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, String.format("Response is %s", String.valueOf(response.code())), Toast.LENGTH_LONG).show();
                }

                if (pd != null && pd.isShowing()) {
                    pd.dismiss();
                    pd = null;
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e(TAG, "Error is " + t.toString());
                Toast.makeText(LoginActivity.this, "Error is " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
