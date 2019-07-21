package dscountr.app.co.ke.dscountr_android_app.view.registration.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;

import dscountr.app.co.ke.dscountr_android_app.R;
import dscountr.app.co.ke.dscountr_android_app.view.Main2Activity;
import dscountr.app.co.ke.dscountr_android_app.view.MainActivity;
import dscountr.app.co.ke.dscountr_android_app.view.utils.SharedPrefManager;

import static android.content.Context.MODE_PRIVATE;

public class WelcomeFragment extends Fragment implements Toolbar.OnMenuItemClickListener, Button.OnClickListener{

    SharedPrefManager sharePreferenceManager;
    TextView tvWelcome;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View gender = inflater.inflate(R.layout.fragment_welcome, container, false);
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setOnMenuItemClickListener(this);

        Button btnStartNow = gender.findViewById(R.id.btnStartNow);
        btnStartNow.setOnClickListener(this);

        sharePreferenceManager = SharedPrefManager.getInstance(getActivity().getApplicationContext());
        tvWelcome = gender.findViewById(R.id.tvWelcome);

        LinearLayout llVerifyBack = gender.findViewById(R.id.llVerifyBack);
        llVerifyBack.setOnClickListener(this);

        loadWelcomeName();

        return gender;
    }

    private void loadWelcomeName(){
        if (sharePreferenceManager.getKeyFirstName() != null){
            tvWelcome.setText(String.format("Welcome %s!", sharePreferenceManager.getKeyFirstName()));
        }else{
            tvWelcome.setText("Missing!");
            tvWelcome.setTextColor(Color.parseColor("#FC4500"));
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
                Toast.makeText(getActivity(), "Gender help.",Toast.LENGTH_SHORT).show();
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStartNow:
                Intent main = new Intent(getActivity(), Main2Activity.class);
                startActivity(main);
                getActivity().overridePendingTransition(R.transition.slide_in, R.transition.slide_out);
                getActivity().finish();
                break;
            case R.id.llVerifyBack:
                Toast.makeText(getActivity(), "You clicked the back button.",Toast.LENGTH_SHORT).show();
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