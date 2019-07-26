package dscountr.app.co.ke.dscountr_android_app.view.registration.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import dscountr.app.co.ke.dscountr_android_app.R;
import dscountr.app.co.ke.dscountr_android_app.model.User;
import dscountr.app.co.ke.dscountr_android_app.presenter.retrofit.MainApplication;
import dscountr.app.co.ke.dscountr_android_app.view.utils.SharedPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GenderFragment extends Fragment implements Toolbar.OnMenuItemClickListener, Button.OnClickListener {

    public static String TAG = GenderFragment.class.getSimpleName();
    String phone_number = null, phone_number_verification_code = null, email = null, email_verification_code = null, date_of_birth = null, gender = null, first_name = null, last_name = null;
    RadioButton radioMale, radioFemale;
    private ProgressDialog pd = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view_gender = inflater.inflate(R.layout.fragment_gender, container, false);
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setOnMenuItemClickListener(this);
        Button btnGender = view_gender.findViewById(R.id.btnGender);
        btnGender.setOnClickListener(this);

        LinearLayout llVerifyBack = view_gender.findViewById(R.id.llVerifyBack);
        llVerifyBack.setOnClickListener(this);

        Bundle args = this.getArguments();
        if (args != null) {
            phone_number = args.getString("phone_number");
            phone_number_verification_code = args.getString("phone_number_verification_code");
            email = args.getString("email");
            first_name = args.getString("first_name");
            last_name = args.getString("last_name");
            email_verification_code = args.getString("email_verification_code");
            SimpleDateFormat formatter = new SimpleDateFormat("dd-mm-yyyy");
            try {
                Date value = formatter.parse(args.getString("date_of_birth"));
                SimpleDateFormat dateStartedFormatter = new SimpleDateFormat("yyyy-mm-dd");
                date_of_birth = dateStartedFormatter.format(value);
            } catch (ParseException e) {
                e.printStackTrace();
                date_of_birth = "1990-01-01";
            }
        }

        RadioGroup rgChannel = view_gender.findViewById(R.id.rgChannel);
        radioMale = view_gender.findViewById(R.id.radioMale);
        radioFemale = view_gender.findViewById(R.id.radioFemale);
//
        // Add the Listener to the RadioGroup
        rgChannel.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Get the selected Radio Button
                RadioButton radioButton = group.findViewById(checkedId);
                gender = radioButton.getText().toString();
            }
        });

        return view_gender;
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
                Toast.makeText(getActivity(), "Gender help.", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnGender:
                validateGender();
                break;
            case R.id.llVerifyBack:
                Toast.makeText(getActivity(), "You clicked the back button.", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;

        }
    }

    private void validateGender() {
        if (gender != null) {
            if (pd == null) {
                pd = new ProgressDialog(getActivity());
                pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                pd.setTitle("User registration");
                pd.setMessage("Please wait...");
                pd.setIndeterminate(false);
            }
            pd.show();
            userRegistration(email, phone_number, date_of_birth, gender.substring(0, 1), first_name, last_name);
        } else {
            Toast.makeText(getActivity(), "Please select your gender to proceed.", Toast.LENGTH_SHORT).show();
        }
    }

    private void userRegistration(String email, String phone_number, String date_of_birth, String gender, String first_name, String last_name) {
        User user = new User(email, phone_number, date_of_birth, gender, first_name, last_name);

        MainApplication.apiManager.registerUser(user, new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User responseUser = response.body();
                if (response.isSuccessful() && responseUser != null) {
                    Toast.makeText(getActivity(), "Registration successful.", Toast.LENGTH_LONG).show();
                    SharedPrefManager.getInstance(getActivity()).setKeyUser(responseUser.getEmail(), responseUser.getPhone_number(), responseUser.getGender(), responseUser.getDate_of_birth(), responseUser.getFirst_name(), responseUser.getLast_name());
                    loadFragment(new WelcomeFragment());
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        if (jObjError.has("email")) {
                            Toast.makeText(getActivity(), jObjError.getString("email"), Toast.LENGTH_LONG).show();
                        } else if (jObjError.has("gender")) {
                            Toast.makeText(getActivity(), jObjError.getString("gender"), Toast.LENGTH_LONG).show();
                        } else if (jObjError.has("date_of_birth")) {
                            Toast.makeText(getActivity(), jObjError.getString("date_of_birth"), Toast.LENGTH_LONG).show();
                        } else if (jObjError.has("phone_number")) {
                            Toast.makeText(getActivity(), jObjError.getString("phone_number"), Toast.LENGTH_LONG).show();
                        } else if (jObjError.has("first_name")) {
                            Toast.makeText(getActivity(), jObjError.getString("first_name"), Toast.LENGTH_LONG).show();
                        } else if (jObjError.has("last_name")) {
                            Toast.makeText(getActivity(), jObjError.getString("last_name"), Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getActivity(), String.format("Response is %s", String.valueOf(response.code())), Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), String.format("Response is %s", String.valueOf(response.code())), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), String.format("Response is %s", String.valueOf(response.code())), Toast.LENGTH_LONG).show();
                    }
                }
                if (pd != null && pd.isShowing()) {
                    pd.dismiss();
                    pd = null;
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getActivity(), "Error is " + t.getMessage(), Toast.LENGTH_LONG).show();

                if (pd != null && pd.isShowing()) {
                    pd.dismiss();
                    pd = null;
                }
            }
        });
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