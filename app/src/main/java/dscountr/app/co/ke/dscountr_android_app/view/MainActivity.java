package dscountr.app.co.ke.dscountr_android_app.view;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

import dscountr.app.co.ke.dscountr_android_app.R;
import dscountr.app.co.ke.dscountr_android_app.model.FeaturedRetailers;
import dscountr.app.co.ke.dscountr_android_app.view.adapters.FeaturedRetailersAdapter;

public class MainActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener, View.OnClickListener {
    private Toolbar toolbar;
    private static String TAG = MainActivity.class.getSimpleName();
    private RecyclerView rvFeaturedRetailers;
    private FeaturedRetailersAdapter mAdapter;
    ArrayList<FeaturedRetailers> featuredRetailers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.main_menu);
        toolbar.setOnMenuItemClickListener(this);
        initToolBar();

        rvFeaturedRetailers = findViewById(R.id.rvFeaturedRetailers);

        mAdapter = new FeaturedRetailersAdapter(this, featuredRetailers);

        rvFeaturedRetailers.setHasFixedSize(true);
        rvFeaturedRetailers.setLayoutManager(new LinearLayoutManager(this));
        rvFeaturedRetailers.setItemAnimator(new DefaultItemAnimator());
        rvFeaturedRetailers.setAdapter(mAdapter);
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

    @SuppressLint("RestrictedApi")
    public void initToolBar(){
        MenuBuilder menuBuilder = (MenuBuilder) toolbar.getMenu();
        menuBuilder.setOptionalIconsVisible(true);
        MenuItem menuItem = toolbar.getMenu().findItem(R.id.miNotification);
        RelativeLayout rlNotification = menuItem.getActionView().findViewById(R.id.rlNotification);
        rlNotification.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlNotification:
                Toast.makeText(MainActivity.this, "Notifications.",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;

        }
    }
}
