package dscountr.app.co.ke.dscountr_android_app.view;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import dscountr.app.co.ke.dscountr_android_app.R;
import dscountr.app.co.ke.dscountr_android_app.view.utils.SharedPrefManager;

public class PersonalActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener, View.OnClickListener {

    TextView tvProfileEmail, tvProfileMobileValue, tvProfileGenderValue, tvProfileDOBValue, tvProfileNameValue;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.profile_menu);
        toolbar.setOnMenuItemClickListener(this);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_gray_24dp);

        sharedPrefManager = SharedPrefManager.getInstance(getApplicationContext());

        tvProfileNameValue = findViewById(R.id.tvProfileNameValue);
        tvProfileDOBValue = findViewById(R.id.tvProfileDOBValue);
        tvProfileGenderValue = findViewById(R.id.tvProfileGenderValue);
        tvProfileMobileValue = findViewById(R.id.tvProfileMobileValue);
        tvProfileEmail = findViewById(R.id.tvProfileEmail);

        loadProfile();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void loadProfile() {
        if (sharedPrefManager.getKeyFirstName() != null) {
            tvProfileNameValue.setText(String.format("%s %s", sharedPrefManager.getKeyFirstName().substring(0,1).toUpperCase()
                    + sharedPrefManager.getKeyFirstName().substring(1), sharedPrefManager.getKeyLastName().substring(0,1).toUpperCase()
                    + sharedPrefManager.getKeyLastName().substring(1)));
        } else {
            tvProfileNameValue.setText("Missing!");
            tvProfileNameValue.setTextColor(Color.parseColor("#FC4500"));
        }
        if (sharedPrefManager.getKeyDateOfBirth() != null) {
            tvProfileDOBValue.setText(sharedPrefManager.getKeyDateOfBirth());
        } else {
            tvProfileDOBValue.setText("Missing!");
            tvProfileDOBValue.setTextColor(Color.parseColor("#FC4500"));
        }

        if (sharedPrefManager.getKeyGender() != null) {
            tvProfileGenderValue.setText(sharedPrefManager.getKeyGender());
        } else {
            tvProfileGenderValue.setText("Missing!");
            tvProfileGenderValue.setTextColor(Color.parseColor("#FC4500"));
        }

        if (sharedPrefManager.getKeyPhoneNumber() != null) {
            tvProfileMobileValue.setText(sharedPrefManager.getKeyPhoneNumber());
        } else {
            tvProfileMobileValue.setText("Missing!");
            tvProfileMobileValue.setTextColor(Color.parseColor("#FC4500"));
        }

        if (sharedPrefManager.getKeyEmail() != null) {
            tvProfileEmail.setText(sharedPrefManager.getKeyEmail());
        } else {
            tvProfileEmail.setText("Missing!");
            tvProfileEmail.setTextColor(Color.parseColor("#FC4500"));
        }
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }
}
