package dscountr.app.co.ke.dscountr_android_app.view.registration.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import dscountr.app.co.ke.dscountr_android_app.R;

public class DateOfBirthFragment extends Fragment implements Toolbar.OnMenuItemClickListener, Button.OnClickListener, TextWatcher {

    public static String TAG = DateOfBirthFragment.class.getSimpleName();
    private static String dob = null;
    private android.support.v4.app.FragmentManager fragmentManager;
    @SuppressLint("StaticFieldLeak")
    private static TextInputEditText enterDoB;
    private TextInputLayout tlenterDOB;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View date_of_birth = inflater.inflate(R.layout.fragment_dob, container, false);
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setOnMenuItemClickListener(this);
        Button btnEmail = date_of_birth.findViewById(R.id.btnBOB);
        btnEmail.setOnClickListener(this);

        // get fragment manager so we can launch from fragment
        fragmentManager = ((AppCompatActivity)getActivity()).getSupportFragmentManager();

        tlenterDOB = date_of_birth.findViewById(R.id.tlenterDOB);
        enterDoB = date_of_birth.findViewById(R.id.enterDOB);
        enterDoB.addTextChangedListener(this);

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
        String dateOfBirth = enterDoB.getText().toString();
        if (TextUtils.isEmpty(dateOfBirth)) {
            tlenterDOB.setError("Please add your date of birth.");
            enterDoB.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.error_bottom_edittext));
        } else {
            // set Error To Null
            tlenterDOB.setError(null);
            tlenterDOB.setErrorEnabled(false);
        }
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

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(),
                    AlertDialog.THEME_HOLO_LIGHT, this,year,month,day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            String setMonth = checkDigit(month, "month");
            String setDay = checkDigit(day, "day");
            dob = setDay + "-" + setMonth + "-" + year;
            enterDoB.setText(dob);
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
                loadFragment(new GenderFragment());
                break;
            case R.id.enterDOB:
                showDatePicker();
                break;
            default:
                break;

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