package dscountr.app.co.ke.dscountr_android_app.view.registration.fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import dscountr.app.co.ke.dscountr_android_app.R;

public class DateOfBirthFragment extends Fragment implements Toolbar.OnMenuItemClickListener, Button.OnClickListener, TextWatcher {

    public static String TAG = DateOfBirthFragment.class.getSimpleName();
    private static String dob = null;
    private androidx.fragment.app.FragmentManager fragmentManager;
    @SuppressLint("StaticFieldLeak")
    private static TextInputEditText enterDOB;
    private TextInputLayout tlenterDOB;
    String phone_number = null, phone_number_verification_code = null, email = null, email_verification_code = null, first_name = null, last_name = null;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View date_of_birth = inflater.inflate(R.layout.fragment_dob, container, false);
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setOnMenuItemClickListener(this);
        Button btnEmail = date_of_birth.findViewById(R.id.btnBOB);
        btnEmail.setOnClickListener(this);
        hideKeyboard();

        // get fragment manager so we can launch from fragment
        fragmentManager = ((AppCompatActivity)getActivity()).getSupportFragmentManager();

        tlenterDOB = date_of_birth.findViewById(R.id.tlenterDOB);
        enterDOB = date_of_birth.findViewById(R.id.enterDOB);
        enterDOB.addTextChangedListener(this);

        enterDOB.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    hideKeyboard();
                    showDatePicker();
                    return true;
                }
                return false;
            }
        });

        dob = "01-01-1990";
        enterDOB.setText(dob);
        enterDOB.setSelection(dob.length());

        Bundle args = this.getArguments();
        if(args != null){
            phone_number = args.getString("phone_number");
            phone_number_verification_code = args.getString("phone_number_verification_code");
            email = args.getString("email");
            email_verification_code = args.getString("email_verification_code");
            first_name = args.getString("first_name");
            last_name = args.getString("last_name");
        }

        return date_of_birth;
    }

    private void showDatePicker() {
        // create the datePickerFragment
        DialogFragment newFragment = new DatePickerFragment();
        // show the datePicker
        newFragment.show(fragmentManager, "datePicker");
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String dateOfBirth = enterDOB.getText().toString();
        if (TextUtils.isEmpty(dateOfBirth)) {
            tlenterDOB.setError("Please add your date of birth.");
            enterDOB.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.error_bottom_edittext));
        }else if(!isThisDateValid(dateOfBirth, "dd-mm-yyyy")){
            tlenterDOB.setError("Please add a valid date of birth.");
            enterDOB.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.error_bottom_edittext));
        }else {
            // set Error To Null
            tlenterDOB.setError(null);
            tlenterDOB.setErrorEnabled(false);
            enterDOB.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bottom_edittext));
        }
    }

    public boolean isThisDateValid(String dateToValidate, String dateFromat){
        if(dateToValidate == null){
            return false;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(dateFromat);
        sdf.setLenient(false);
        try {
            //if not valid, it will throw ParseException
            Date date = sdf.parse(dateToValidate);
            System.out.println(date);

        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar calendar = Calendar.getInstance();
            int year, month, day;
            try {
                if (dob != null){
                    Date dateOfBirth;
                    try {
                        SimpleDateFormat formatter = new SimpleDateFormat("dd-mm-yyyy");
                        dateOfBirth = formatter.parse(dob);
                        calendar.setTime(dateOfBirth);
                        year = calendar.get(Calendar.YEAR);
                        day = calendar.get(Calendar.DAY_OF_MONTH);
//                        month = calendar.get(Calendar.MONTH); // not accurate
                        SimpleDateFormat dateFormat = new SimpleDateFormat("mm"); // tried to rectify
                        month = Integer.parseInt(dateFormat.format(dateOfBirth)) - 1;
                    } catch (ParseException e) {
                        year = 1990;
                        month = 0;
                        day = 1;
                    }
                }else{
                    year = 1990;
                    month = 0;
                    day = 1;
                }
            }catch (NullPointerException e){
                year = 1990;
                month = 0;
                day = 1;
            }

            DatePickerDialog dpd = new DatePickerDialog(getActivity(), R.style.DatePickerDialogTheme, this,year,month,day); // AlertDialog.THEME_HOLO_LIGHT //
            colorizeDatePicker(dpd.getDatePicker());
            //Set a title for DatePickerDialog
           // dpd.setTitle("Set Date of Birth");

            // Create a new instance of DatePickerDialog and return it
            return dpd;
        }

        private void colorizeDatePicker(DatePicker datePicker) {
            Resources system = Resources.getSystem();
            int dayId = system.getIdentifier("day", "id", "android");
            int monthId = system.getIdentifier("month", "id", "android");
            int yearId = system.getIdentifier("year", "id", "android");

            NumberPicker dayPicker = datePicker.findViewById(dayId);
            NumberPicker monthPicker = datePicker.findViewById(monthId);
            NumberPicker yearPicker = datePicker.findViewById(yearId);

            setDividerColor(dayPicker);
            setDividerColor(monthPicker);
            setDividerColor(yearPicker);
        }

        private void setDividerColor(NumberPicker picker) {
            if (picker == null)
                return;

            final int count = picker.getChildCount();
            for (int i = 0; i < count; i++) {
                try {
                    Field dividerField = picker.getClass().getDeclaredField("mSelectionDivider");
                    dividerField.setAccessible(true);
                    ColorDrawable colorDrawable = new ColorDrawable(picker.getResources().getColor(R.color.colorPrimary));
                    dividerField.set(picker, colorDrawable);
                    picker.invalidate();
                } catch (Exception e) {
                    Log.w("setDividerColor", e);
                }
            }

        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            String setMonth = checkDigit(month, "month");
            String setDay = checkDigit(day, "day");
            dob = setDay + "-" + setMonth + "-" + year;
            enterDOB.setText(dob);
            enterDOB.setSelection(enterDOB.getText().length());
        }

        private String checkDigit(int number, String type) {
            if (type.equals("month")) {
                number = number + 1;
            }
            return number <= 9 ? "0" + number : String.valueOf(number);
        }
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
                Toast.makeText(getActivity(), "Date of birth help.",Toast.LENGTH_SHORT).show();
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBOB:
                validateDateOfBirth();
                break;
            default:
                break;

        }
    }

    private void validateDateOfBirth(){
        String date_of_birth = enterDOB.getText().toString();
        if (TextUtils.isEmpty(date_of_birth)) {
            tlenterDOB.setError("Please add your date of birth.");
            enterDOB.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.error_bottom_edittext));
        }else if(!isThisDateValid(date_of_birth, "dd-mm-yyyy")){
            tlenterDOB.setError("Please add a valid date of birth.");
            enterDOB.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.error_bottom_edittext));
        }else {
            // set Error To Null
            tlenterDOB.setError(null);
            tlenterDOB.setErrorEnabled(false);
            enterDOB.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bottom_edittext));

            Bundle bundle = new Bundle();
            bundle.putString("phone_number", phone_number);
            bundle.putString("email", email);
            bundle.putString("first_name", first_name);
            bundle.putString("last_name", last_name);
            bundle.putString("date_of_birth", date_of_birth);
            Fragment verifyGender = new GenderFragment();
            verifyGender.setArguments(bundle);
            loadFragment(verifyGender);
        }
    }

    private void hideKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
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