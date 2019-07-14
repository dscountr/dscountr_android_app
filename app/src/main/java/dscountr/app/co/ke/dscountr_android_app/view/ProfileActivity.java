package dscountr.app.co.ke.dscountr_android_app.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import dscountr.app.co.ke.dscountr_android_app.R;

public class ProfileActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener, View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.profile_menu);
        toolbar.setOnMenuItemClickListener(this);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);

        RelativeLayout rlProfile = findViewById(R.id.rlProfile);
        rlProfile.setOnClickListener(this);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rlProfile:
                Intent profile = new Intent(ProfileActivity.this, PersonalActivity.class);
                startActivity(profile);
                break;
            default:
                break;

        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.miProfile:
                Toast.makeText(ProfileActivity.this, "Sign out",Toast.LENGTH_SHORT).show();
                return true;
            default:
                return false;
        }
    }
}
