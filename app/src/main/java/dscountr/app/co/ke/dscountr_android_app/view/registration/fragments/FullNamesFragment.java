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

import dscountr.app.co.ke.dscountr_android_app.R;
import dscountr.app.co.ke.dscountr_android_app.view.Help;

public class FullNamesFragment extends Fragment implements Toolbar.OnMenuItemClickListener, Button.OnClickListener {

    TextInputEditText firstName;
    TextInputLayout tlfirstName;
    TextInputEditText lastName;
    TextInputLayout tllastName;

    String phone_number = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mobile = inflater.inflate(R.layout.fragment_names, container, false);
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setOnMenuItemClickListener(this);
        Button btnNumber = mobile.findViewById(R.id.btnNumber);
        btnNumber.setOnClickListener(this);

        firstName = mobile.findViewById(R.id.firstName);
        tlfirstName = mobile.findViewById(R.id.tlfirstName);
        lastName = mobile.findViewById(R.id.lastName);
        tllastName = mobile.findViewById(R.id.tllastName);

        Bundle args = this.getArguments();
        if(args != null){
            phone_number = args.getString("phone_number");
        }

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
            case R.id.btnNumber:
                fullNameValidation();
                break;
            case R.id.tvTerms:
                Toast.makeText(getActivity(), "Our terms and conditions.",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;

        }

    }

    private void fullNameValidation() {
        String first_name = firstName.getText().toString();
        String last_name = lastName.getText().toString();
        if(TextUtils.isEmpty(first_name)){

            tlfirstName.setError("Please enter First name.");
            firstName.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.error_bottom_edittext));

        } else if(TextUtils.isEmpty(last_name)) {

            tllastName.setError("Please enter First name.");
            lastName.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.error_bottom_edittext));
        } else {
            tlfirstName.setError(null);
            tllastName.setError(null);
            firstName.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bottom_edittext));
            lastName.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bottom_edittext));

            Bundle bundle = new Bundle();
            bundle.putString("phone_number", phone_number);
            bundle.putString("first_name", first_name);
            bundle.putString("last_name", last_name);
            Fragment emailFragment = new EmailFragment();
            emailFragment.setArguments(bundle);
            loadFragment(emailFragment);
        }

    }
}
