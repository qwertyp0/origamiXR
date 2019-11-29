package com.example.origamixr;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainHomeActivity extends AppCompatActivity {

    private FragmentManager mFragmentManager;
    /* TODO create the three fragments
    *  Home
    *  Saved
    *  Gallery
    * */

    private HomeFragment mHomeFragment = new HomeFragment();
    private SavedFragment mSavedFragment = new SavedFragment();
    private GalleryFragment mGalleryFragment = new GalleryFragment();
    private DrawerLayout mDrawerLayout;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_home_activity);

        setNavBars();

        mFragmentManager = getSupportFragmentManager();

        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.fragment_container, mHomeFragment);
        mFragmentTransaction.commit();
        mFragmentManager.executePendingTransactions();

    }

    private void setNavBars() {
        setSupportActionBar(findViewById(R.id.toolbar));
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionbar.setTitle("Home");

        mDrawerLayout = findViewById(R.id.drawer_layout);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        NavigationView navigationView = findViewById(R.id.side_navigation);


        navigation.setOnNavigationItemSelectedListener(
            item -> {
                FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();

                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        actionbar.setTitle("Home");
                        mFragmentTransaction.replace(R.id.fragment_container, mHomeFragment);
                        break;
                    case R.id.navigation_saved:
                        actionbar.setTitle("Saved");
                        mFragmentTransaction.replace(R.id.fragment_container, mSavedFragment);
                        break;
                    case R.id.navigation_gallery:
                        actionbar.setTitle("Gallery");
                        mFragmentTransaction.replace(R.id.fragment_container, mGalleryFragment);
                }

                mFragmentTransaction.commit();
                mFragmentManager.executePendingTransactions();
                return true;
            }
        );

        navigationView.setNavigationItemSelectedListener(
            menuItem -> {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                for (int i = 0; i < navigation.getMenu().size(); i++) {
                    navigation.getMenu().getItem(i).setCheckable(false);
                }

                FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
                switch (menuItem.getItemId()) {
                    case R.id.sidebar_suggest:
                        actionbar.setTitle("Suggest");
                        // mFragmentTransaction.replace(R.id.fragment_container, );
                        break;
                    case R.id.sidebar_settings:
                        actionbar.setTitle("Settings");
                        // mFragmentTransaction.replace(R.id.fragment_container, );
                        break;
                    case R.id.sidebar_contact_us:
                        actionbar.setTitle("Contact Us");
                        // mFragmentTransaction.replace(R.id.fragment_container, );
                        break;
                    case R.id.sidebar_about_us:
                        actionbar.setTitle("About Us");
                        // mFragmentTransaction.replace(R.id.fragment_container, );
                        break;
                    case R.id.sidebar_sign_out:

                }
                mFragmentTransaction.commit();
                mFragmentManager.executePendingTransactions();

                return true;
            }
        );

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
