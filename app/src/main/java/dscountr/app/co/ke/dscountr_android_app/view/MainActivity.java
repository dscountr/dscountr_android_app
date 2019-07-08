package dscountr.app.co.ke.dscountr_android_app.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import dscountr.app.co.ke.dscountr_android_app.R;

public class MainActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.main_menu);
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.miSearch:
                Toast.makeText(MainActivity.this, "Search.",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.miNotification:
                Toast.makeText(MainActivity.this, "Notifications.",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.miProfile:
                Toast.makeText(MainActivity.this, "Profile.",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.miDiscount:
                Toast.makeText(MainActivity.this, "My Discount List.",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.miWatch:
                Toast.makeText(MainActivity.this, "My Watch List.",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.miBasket:
                Toast.makeText(MainActivity.this, "My Basket.",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.miSettings:
                Toast.makeText(MainActivity.this, "Settings.",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.miHelpSupport:
                Toast.makeText(MainActivity.this, "Help & Support.",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.miSignIn:
                Toast.makeText(MainActivity.this, "Sign In.",Toast.LENGTH_SHORT).show();
                return true;
            default:
                return false;
        }
    }
}
