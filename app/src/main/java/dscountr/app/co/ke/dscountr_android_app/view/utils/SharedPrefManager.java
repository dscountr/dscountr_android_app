package dscountr.app.co.ke.dscountr_android_app.view.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {

    @SuppressLint("StaticFieldLeak")
    private static SharedPrefManager mInstance;
    @SuppressLint("StaticFieldLeak")
    private static Context mCtx;

    private static final String SHARED_PREF_NAME = "dscountr.";

    private static final String KEY_PHONE_NUMBER = "keyphonenumber";
    private static final String KEY_GENDER = "keygender";
    private static final String KEY_DATE_OF_BIRTH = "keydateofbirth";
    private static final String KEY_EMAIL = "keyemail";

    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    public String getKeyEmail(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_EMAIL, null);
    }

    public String getKeyGender(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_GENDER, null);
    }

    public String getKeyPhoneNumber(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_PHONE_NUMBER, null);
    }

    public String getKeyDateOfBirth(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_DATE_OF_BIRTH, null);
    }

    public void setKeyUser(String email, String phone_number, String gender, String date_of_birth) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PHONE_NUMBER, phone_number);
        editor.putString(KEY_GENDER, gender);
        editor.putString(KEY_DATE_OF_BIRTH, date_of_birth);
        editor.apply();
    }

    public String getSharedPrefName() {
        return SHARED_PREF_NAME;
    }

    public boolean clearAccount() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }
}
