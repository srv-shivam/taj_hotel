package com.example.tajhotel;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.tajhotel.Fragments.CartFragment;
import com.example.tajhotel.Fragments.HomeFragment;
import com.example.tajhotel.Fragments.ProfileFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    MeowBottomNavigation bottomNavigation;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    ImageView profileImage;
    ImageView profileImageDrawer;
    int selectedProfileImage;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        profileImage = findViewById(R.id.profile_image);
        drawerLayout = findViewById(R.id.drawerLayout);
        bottomNavigation = findViewById(R.id.navigation);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, toolbar
                , R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(MainActivity.this);

        addItemsToBottomNavigation();

        selectFragment();

        bottomNavigationButtonAction();

        changeProfileImage();

        selectedProfileImage = getIntent().getIntExtra("Selected Avatar", R.drawable.profile_img);
        selectedProfileImage(selectedProfileImage);
    }

    /**
     * selectedProfileImage method is used to change the profile image of the user
     *
     * @param selectedProfileImage
     */
    private void selectedProfileImage(int selectedProfileImage) {
        profileImage.setImageResource(selectedProfileImage);
        View view = navigationView.getHeaderView(0);
        profileImageDrawer = (ImageView) view.findViewById(R.id.profile_image_drawer);
        profileImageDrawer.setImageResource(selectedProfileImage);
    }

    /**
     * changeProfileImage method will help us to change the profile image of the User
     */
    private void changeProfileImage() {
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AvatarPractice.class);
                startActivity(intent);
                MainActivity.this.finish();
            }
        });
    }


    /**
     * This selectFragment method is used for selecting the clicked fragment
     * and passing to the Fragment Manager to open that particular Fragment
     */
    private void selectFragment() {
        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {

                Fragment selectedFragment = null;

                switch (item.getId()) {
                    case 1:
                        selectedFragment = new ProfileFragment();
                        break;
                    case 2:
                        selectedFragment = new HomeFragment();
                        break;
                    case 3:
                        selectedFragment = new CartFragment();
                        break;
                }

                loadFragment(selectedFragment);
            }
        });

        // By default Home Fragment will Open
        bottomNavigation.show(2, true);
    }


    /**
     * This method is used for onClickItem Listener for the bottom navigation items
     */
    private void bottomNavigationButtonAction() {
        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
            }
        });

        bottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
            }
        });
    }


    /**
     * This method is just adding the items for bottom navigation bar
     */
    private void addItemsToBottomNavigation() {
        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.ic_user_profile_person_profile_user_svgrepo_com));
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.ic_home_svgrepo_com));
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.ic_cart_svgrepo_com));
    }

    /**
     * This method is opening desired fragment with help of Fragment Manager
     *
     * @param selectedFragment
     */
    private void loadFragment(Fragment selectedFragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, selectedFragment)
                .commit();
    }


    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.my_profile:
                bottomNavigation.show(1, true);
                break;

            case R.id.cart:
                bottomNavigation.show(3, true);
                break;

            case R.id.my_orders:
                Toast.makeText(this, "My Orders Navigation Clicked", Toast.LENGTH_SHORT).show();
                break;

            case R.id.offers:
                Toast.makeText(this, "Offers Navigation Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.notifications:
                Intent intent = new Intent(MainActivity.this, Notifications.class);
                startActivity(intent);
                Toast.makeText(this, "Notifications Navigation Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.log_out:
                Toast.makeText(this, "Log Out Navigation Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.exit:
                finish();
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }
}