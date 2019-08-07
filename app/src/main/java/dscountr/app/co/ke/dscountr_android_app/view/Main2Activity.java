package dscountr.app.co.ke.dscountr_android_app.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

import dscountr.app.co.ke.dscountr_android_app.R;
import dscountr.app.co.ke.dscountr_android_app.view.registration.RegisterActivity;
import dscountr.app.co.ke.dscountr_android_app.view.utils.SharedPrefManager;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Toolbar.OnMenuItemClickListener, View.OnClickListener {


    SharedPrefManager sharePreferenceManager;
    public static String TAG = Main2Activity.class.getSimpleName();
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        toolbar = findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.main_menu);
        toolbar.setOnMenuItemClickListener(this);

        sharePreferenceManager = SharedPrefManager.getInstance(getApplicationContext());

        initToolBar();
        initNavigation();
        loadFullName(); // FirebaseAuth.getInstance().getCurrentUser() != null

        TextView fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Start Shopping...", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onResume(){
        super.onResume();
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

    private void loadFullName() {

        NavigationView navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);
        View hView = navigationView.getHeaderView(0);
        RelativeLayout layout = hView.findViewById(R.id.rlNavigationHeader);
        final TextView headerName = hView.findViewById(R.id.navHeaderTitle);
        TextView headerProfileName = hView.findViewById(R.id.profViewLink);
        ImageView imageView = hView.findViewById(R.id.imageView1);
        LinearLayout linearLayout = hView.findViewById(R.id.profileNameLink);

        if (sharePreferenceManager.getKeyToken() != null) {
            headerName.setText(String.format("%s %s", sharePreferenceManager.getKeyFirstName(), sharePreferenceManager.getKeyLastName()));
        } else {
            layout.setBackgroundResource(R.drawable.side_nav_signout);
            layout.setPadding(0,32,24,0);
            if (sharePreferenceManager.getKeyFirstName() != null && sharePreferenceManager.getKeyLastName() != null)
                headerName.setText(R.string.sign_in);
            else
                headerName.setText(R.string.continue_reg);
            headerName.setTextColor(Color.parseColor("#FFFFFF"));
            headerName.getResources().getDimensionPixelOffset(R.dimen.nav_header_title_spacing);
            headerProfileName.setVisibility(View.GONE);
            imageView.setPadding(32,30,0,0);
            linearLayout.setPadding(32,40,12,0);
            headerName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (headerName.getText().toString().equals(getResources().getString(R.string.sign_in))){
                        Intent i = new Intent(Main2Activity.this, LoginActivity.class);
                        overridePendingTransition(R.transition.slide_in, R.transition.slide_out);
                        startActivity(i);
                    }else if (headerName.getText().toString().equals(getResources().getString(R.string.continue_reg))){
                        Intent i = new Intent(Main2Activity.this, LoginActivity.class);
                        overridePendingTransition(R.transition.slide_in, R.transition.slide_out);
                        startActivity(i);
                    }else{
                        Intent profile = new Intent(Main2Activity.this, ProfileActivity.class);
                        overridePendingTransition(R.transition.slide_in, R.transition.slide_out);
                        startActivity(profile);
                    }
                }
            });

        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.miSearch:
                Toast.makeText(Main2Activity.this, "Search.", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.miNotification:
                Toast.makeText(Main2Activity.this, "Notifications.", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.miNewShoppingList:
                Toast.makeText(Main2Activity.this, "New Shopping List.", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.miCredits:
                Toast.makeText(Main2Activity.this, "Earn Credits", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.miShopnow:
                Toast.makeText(Main2Activity.this, "Shop Now", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return false;
        }
    }

    @SuppressLint("RestrictedApi")
    public void initToolBar() {
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
        TextView textView = headView.findViewById(R.id.profViewLink);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG, "clicking textview");
                Intent i = new Intent(Main2Activity.this, ProfileActivity.class);
                overridePendingTransition(R.transition.slide_in, R.transition.slide_out);
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

        } else if (id == R.id.nav_support) {
            Intent i = new Intent(Main2Activity.this, Help.class);
            startActivity(i);
        } else if (id == R.id.nav_sign_out) {
            sharePreferenceManager.clearAccount();
            Intent i = new Intent(Main2Activity.this, RegisterActivity.class);
            startActivity(i);
            overridePendingTransition(R.transition.slide_in, R.transition.slide_out);
            finish();
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
                Toast.makeText(Main2Activity.this, "Notifications.", Toast.LENGTH_SHORT).show();
                break;

            default:
                break;

        }
    }
}
