package dscountr.app.co.ke.dscountr_android_app.view.registration;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;

import dscountr.app.co.ke.dscountr_android_app.R;
import dscountr.app.co.ke.dscountr_android_app.view.Main2Activity;
import dscountr.app.co.ke.dscountr_android_app.view.registration.fragments.MobileFragment;
import dscountr.app.co.ke.dscountr_android_app.view.utils.SharedPrefManager;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.registration_menu);
        // load Fragment
        if (FirebaseAuth.getInstance().getCurrentUser() != null || SharedPrefManager.getInstance(getApplicationContext()).getKeyPhoneNumber() != null) {
            Intent main = new Intent(RegisterActivity.this, Main2Activity.class);
            startActivity(main);
            overridePendingTransition(R.transition.slide_in, R.transition.slide_out);
            finish();
        } else {
            loadFragment(new MobileFragment());
        }
    }

    private void loadFragment(Fragment fragment) {
        // create a FragmentManager
        FragmentManager fm = getFragmentManager();
        // create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        // replace the FrameLayout with new Fragment
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit(); // save the changes
    }
}
