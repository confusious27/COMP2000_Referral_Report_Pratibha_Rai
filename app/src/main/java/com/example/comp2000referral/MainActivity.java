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

        bottomNavigationView.setVisibility(View.GONE);

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
            // show login first
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, new LoginFragment())
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
            int id = item.getItemId();
            Fragment selectedFragment;

            //for admin
            if (id == R.id.bottom_admin_books) {
                selectedFragment = new AdminBookFragment();
            } else if (id == R.id.bottom_admin_members) {
                selectedFragment = new AdminMembersFragment();
            } else if (id == R.id.bottom_admin_requests) {
                selectedFragment = new AdminRequestsFragment();
            } else if (id == R.id.bottom_catalogue) { //for users
                selectedFragment = new UserBookFragment();
            } else if (id == R.id.bottom_requests) {
                selectedFragment = "admin".equalsIgnoreCase(userType) //since both are requests
                        ? new AdminRequestsFragment()
                        : new UserRequestFragment();
            } else if (id == R.id.bottom_profile) {
                selectedFragment = new UserProfileFragment();
            } else {
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
        if (currentFragment instanceof ForgetFragment
                || currentFragment instanceof SignupFragment
                || currentFragment instanceof BookDetailFragment
                || currentFragment instanceof SettingsFragment
                || currentFragment instanceof AddBookFragment
                || currentFragment instanceof AdminBookDetailFragment
                || currentFragment instanceof AdminMembersFragment
                || currentFragment instanceof MembersDetailFragment
                || currentFragment instanceof AddMemberFragment
                || currentFragment instanceof RequestDetailFragment) {

            //members detail screen + edit members + add members
            //requests detail) {
            showToolbar();
        } else {
            hideToolbar();
        }
    }

    public void showToolbar() {
        topBar.setVisibility(View.VISIBLE);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(""); // no title
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_button); // custom back button

            // handle back button click
            topBar.setNavigationOnClickListener(v -> {
                // Simply pop the fragment back stack
                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    getSupportFragmentManager().popBackStack();
                } else {
                    // optional: if no fragments in back stack, finish activity
                    finish();
                }
            });
        }
    }

    public void hideToolbar() {
        if (topBar != null) {
            topBar.setVisibility(View.GONE);
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }

    // to direct the page from login to the right home screen
    public void onLoginSuccess(String userType) {
        this.userType = userType;

        bottomNavigationView.setVisibility(View.VISIBLE);
        bottomNavigationView.getMenu().clear();
        if ("admin".equalsIgnoreCase(userType)) {
            bottomNavigationView.inflateMenu(R.menu.bottom_menu_admin);
            bottomNavigationView.setSelectedItemId(R.id.bottom_admin_books);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, new AdminBookFragment())
                    .commit();
        } else {
            bottomNavigationView.inflateMenu(R.menu.bottom_menu);
            bottomNavigationView.setSelectedItemId(R.id.bottom_catalogue);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, new UserBookFragment())
                    .commit();

        }
    }

}