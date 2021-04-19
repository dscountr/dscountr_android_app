package dscountr.app.co.ke.dscountr_android_app.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import dscountr.app.co.ke.dscountr_android_app.R;
import dscountr.app.co.ke.dscountr_android_app.model.FeaturedRetailers;
import dscountr.app.co.ke.dscountr_android_app.view.adapters.FeaturedRetailersAdapter;
import dscountr.app.co.ke.dscountr_android_app.view.utils.BottomNavigationBehavior;
import dscountr.app.co.ke.dscountr_android_app.view.utils.SharedPrefManager;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Toolbar.OnMenuItemClickListener, View.OnClickListener {

    public static String TAG = Main2Activity.class.getSimpleName();
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        toolbar = findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.main_menu);
        toolbar.setOnMenuItemClickListener(this);
        initToolBar();
        initNavigation();
        setSupportActionBar(toolbar);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
            case R.id.miNewShoppingList:
                Toast.makeText(MainActivity.this, "New Shopping List.",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.miCredits:
                Toast.makeText(MainActivity.this, "Earn Credits",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.miShopnow:
                Toast.makeText(MainActivity.this, "Shop Now",Toast.LENGTH_SHORT).show();
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

    public void initNavigation() {

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headView = navigationView.getHeaderView(0);
        RelativeLayout rlNavigationHeader = headView.findViewById(R.id.rlNavigationHeader);
        rlNavigationHeader.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG, "clicking textview");
                Intent i = new Intent(MainActivity.this,ProfileActivity.class);
                startActivity(i);
            }
        });
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlNotification:
                Log.e(TAG, "clicking notification");
                Toast.makeText(MainActivity.this, "Notifications.",Toast.LENGTH_SHORT).show();
                break;

            default:
                break;

        }
    }
}
