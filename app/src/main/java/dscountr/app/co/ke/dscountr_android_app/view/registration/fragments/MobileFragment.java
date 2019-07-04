package dscountr.app.co.ke.dscountr_android_app.view.registration.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import dscountr.app.co.ke.dscountr_android_app.R;

public class MobileFragment extends Fragment implements Toolbar.OnMenuItemClickListener, Button.OnClickListener{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mobile = inflater.inflate(R.layout.fragment_mobile, container, false);
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.mobile_menu);
        toolbar.setOnMenuItemClickListener(this);
        Button btnNumber = mobile.findViewById(R.id.btnNumber);
        btnNumber.setOnClickListener(this);

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
            case R.id.miMobileNumber:
                Toast.makeText(getActivity(), "Mobile number help.",Toast.LENGTH_SHORT).show();
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnNumber:
                loadFragment(new VerifyMobileFragment());
                break;
            case R.id.tvTerms:
                Toast.makeText(getActivity(), "Our terms and conditions.",Toast.LENGTH_SHORT).show();
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