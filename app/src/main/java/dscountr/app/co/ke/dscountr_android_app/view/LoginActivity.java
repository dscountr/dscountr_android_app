package dscountr.app.co.ke.dscountr_android_app.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import dscountr.app.co.ke.dscountr_android_app.R;

public class LoginActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.registration_menu);
        toolbar.setOnMenuItemClickListener(this);
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.miRegistrationHelp:
                Toast.makeText(LoginActivity.this, "Sign in help.",Toast.LENGTH_SHORT).show();
                return true;
            default:
                return false;
        }
    }
}
