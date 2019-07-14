package dscountr.app.co.ke.dscountr_android_app.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import dscountr.app.co.ke.dscountr_android_app.R;
import dscountr.app.co.ke.dscountr_android_app.model.FeaturedRetailers;
import dscountr.app.co.ke.dscountr_android_app.view.adapters.FeaturedRetailersAdapter;
import dscountr.app.co.ke.dscountr_android_app.view.utils.BottomNavigationBehavior;
import dscountr.app.co.ke.dscountr_android_app.view.utils.SharedPrefManager;

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

        BottomNavigationView bvNavigation = findViewById(R.id.bvNavigation);
        bvNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // attaching bottom sheet behaviour - hide / show on scroll
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bvNavigation.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehavior());
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_categories:
                    Toast.makeText(MainActivity.this, "Product Categories",Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.navigation_offers:
                    Toast.makeText(MainActivity.this, "Special Offers Just For You",Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.navigation_gifts:
                    Toast.makeText(MainActivity.this, "Free to Claim",Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.navigation_discounts:
                    Toast.makeText(MainActivity.this, "Bundled Discounts",Toast.LENGTH_SHORT).show();
                    return true;
            }
            return false;
        }
    };

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
                if (SharedPrefManager.getInstance(getApplicationContext()).getKeyToken() != null){
                    Intent profile = new Intent(MainActivity.this, ProfileActivity.class);
                    startActivity(profile);
                }else{
                    Toast.makeText(MainActivity.this, "Sign in to view your profile.",Toast.LENGTH_SHORT).show();
                }
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
                Intent sign_in = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(sign_in);
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
