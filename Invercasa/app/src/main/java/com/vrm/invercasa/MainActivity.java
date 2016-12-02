package com.vrm.invercasa;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vrm.invercasa.fragments.ProductFragment;
import com.vrm.invercasa.fragments.ExtraFragment;
import com.vrm.invercasa.listeners.OnMainListener;
import com.vrm.invercasa.model.UserEntity;
import com.vrm.invercasa.utils.PrefManager;

public class MainActivity extends AppCompatActivity implements OnMainListener{
    private UserEntity user;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private TextView txtName, txtEmail, txtCaps;
    private Toolbar toolbar;
    private LinearLayout profile;
    public static int navItemIndex = 0;
    public static String CURRENT_TAG = "0" ;
    private String[] activityTitles;
    private Handler handler;
    private PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        handler = new Handler();
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navHeader = navigationView.getHeaderView(0);
        profile = (LinearLayout) navHeader.findViewById(R.id.profile);
        txtName = (TextView) navHeader.findViewById(R.id.textViewName);
        txtEmail = (TextView) navHeader.findViewById(R.id.textViewEmail);
        txtCaps = (TextView) navHeader.findViewById(R.id.textViewCaps);
        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);
        prefManager = new PrefManager(this);
        ui();
        if (savedInstanceState == null) {
            navItemIndex = 1;
            CURRENT_TAG = "1";
            loadHomeFragment();
        }
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditProfileActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("USER", user);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void ui() {
        loadNavHeader();
        setUpNavigationView();
    }

    private void loadNavHeader() {
        user = (UserEntity) getIntent().getExtras().getSerializable("USER");
        txtName.setText(user.getFullName());
        String email = user.getEmail();
        txtEmail.setText(email.substring(0, email.indexOf("@") + 4) + "...");
        txtCaps.setText("" + user.getName().charAt(0) + user.getLastName().charAt(0));
    }

    private void loadHomeFragment() {
        selectNavMenu();
        setToolbarTitle();
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();
            return;
        }
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };
        if (mPendingRunnable != null)
            handler.post(mPendingRunnable);
        drawer.closeDrawers();
        invalidateOptionsMenu();
    }

    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                return new ProductFragment();
            case 1:
                return new ExtraFragment();
        }
        return null;
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void setUpNavigationView() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_all:
                        navItemIndex = 0;
                        CURRENT_TAG = "0";
                        break;
                    case R.id.nav_last:
                        navItemIndex = 1;
                        CURRENT_TAG = "1";
                        break;
                    case R.id.nav_help:
                        startActivity(new Intent(MainActivity.this, TermsActivity.class));
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_about:
                        startActivity(new Intent(MainActivity.this, AboutActivity.class));
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_logout:
                        logout();
                        drawer.closeDrawers();
                        return true;
                    default:
                        navItemIndex = 0;
                }
                menuItem.setChecked(!menuItem.isChecked());
                loadHomeFragment();
                return true;
            }
        });
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }
        else {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        final SearchView searchView = (SearchView)MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        SearchManager searchManager = (SearchManager)getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    private void logout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.action_logout));
        builder.setMessage(getString(R.string.prompt_confirm_logout));
        builder.setPositiveButton(R.string.prompt_yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                prefManager.setLoggedUser(-1);
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        });
        builder.setNegativeButton(R.string.prompt_no, null);
        builder.show();
    }
}