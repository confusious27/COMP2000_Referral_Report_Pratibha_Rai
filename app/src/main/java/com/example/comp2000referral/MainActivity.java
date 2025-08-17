package com.example.comp2000referral;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

// for navigation
public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    Toolbar topBar;
    String userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userType = getIntent().getStringExtra("userType"); //gets the user type from login
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        topBar = findViewById(R.id.topBar);
        setSupportActionBar(topBar);

        // monitors fragment changes to show/hide the top bar
        getSupportFragmentManager().addOnBackStackChangedListener(this::updateToolbarVisibility);


        // loads correct menu
        if ("admin".equalsIgnoreCase(userType)) {
            bottomNavigationView.inflateMenu(R.menu.bottom_menu_admin);
        } else {
            bottomNavigationView.inflateMenu(R.menu.bottom_menu);
        }

        // shows the default fragment which is the "home" fragment based on userType
        if (savedInstanceState == null) {
            Fragment defaultFragment = "admin".equalsIgnoreCase(userType)
                    ? new AdminBookFragment()
                    : new UserBookFragment();

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, defaultFragment)
                    .commit();

            // set selected item in bottom nav
            bottomNavigationView.setSelectedItemId(
                    "admin".equalsIgnoreCase(userType)
                            ? R.id.bottom_admin_books
                            : R.id.bottom_catalogue
            );
        }

        // tells bottom nav to switch fragments
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment;

            switch (item.getItemId()) {
                //for admin
                case R.id.bottom_admin_books:
                    selectedFragment = new AdminBookFragment();
                    break;
                case R.id.bottom_admin_members:
                    selectedFragment = new AdminMembersFragment();
                    break;
                case R.id.bottom_admin_requests:
                    selectedFragment = new AdminRequestsFragment();
                    break;
                // for users
                case R.id.bottom_catalogue:
                    selectedFragment = new UserBookFragment();
                    break;
                case R.id.bottom_requests:
                    selectedFragment = "admin".equalsIgnoreCase(userType) //since both are requests
                            ? new AdminRequestsFragment()
                            : new UserRequestFragment();
                    break;
                case R.id.bottom_profile:
                    selectedFragment = new UserProfileFragment();
                    break;
                default:
                    return false;
            }

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, selectedFragment)
                    .commit();

            return true;
        });
    }

    //shows the back button
    private void updateToolbarVisibility() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
        if (currentFragment instanceof ForgetActivity
                || currentFragment instanceof SignupActivity
                ||currentFragment instanceof BookDetailFragment
                ||currentFragment instanceof SettingsFragment
                ||currentFragment instanceof AddBookFragment
                ||currentFragment instanceof AdminBookDetailFragment
                ||currentFragment instanceof AdminMembersFragment
                ||currentFragment instanceof MembersDetailFragment
                ||currentFragment instanceof AddMemberFragment

                //members detail screen + edit members + add members
                //requests detail) {
            showToolbar();
        } else {
            hideToolbar();
        }
    }

    public void showToolbar(String title) {
        topBar.setVisibility(View.VISIBLE);
        getSupportActionBar().setTitle(""); //no title
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_button); // custom back buttpm
        // handles the back button click
        topBar.setNavigationOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());
    }

    public void hideToolbar() {
        topBar.setVisibility(View.GONE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

}