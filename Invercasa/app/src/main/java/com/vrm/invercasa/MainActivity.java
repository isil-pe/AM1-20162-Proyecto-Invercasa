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
import android.widget.TextView;

import com.vrm.invercasa.fragments.ProductFragment;
import com.vrm.invercasa.fragments.TestFragment;

public class MainActivity extends AppCompatActivity {
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private TextView txtName, txtEmail;
    private Toolbar toolbar;
    public static int navItemIndex = 0;
    private static final String TAG_ALL = "all";
    private static final String TAG_LAST = "last";
    private static final String TAG_FEATURED = "featured";
    private static final String TAG_FAVOURITE = "favourite";
    private static final String TAG_RECENT = "recent";
    public static String CURRENT_TAG = TAG_ALL ;
    private String[] activityTitles;
    private Handler handler;

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
        txtName = (TextView)navHeader.findViewById(R.id.name);
        txtEmail = (TextView)navHeader.findViewById(R.id.email);
        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);
        loadNavHeader();
        setUpNavigationView();
        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_ALL;
            loadHomeFragment();
        }
    }

    private void loadNavHeader() {
        txtName.setText(getIntent().getStringExtra("fullname"));
        txtEmail.setText(getIntent().getStringExtra("email"));
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
                return new TestFragment();
            case 1:
                return new ProductFragment();
            case 2:
                return null;
            case 3:
                return null;
            case 4:
                return null;
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
                        CURRENT_TAG = TAG_ALL;
                        break;
                    case R.id.nav_last:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_LAST;
                        break;
                    case R.id.nav_featured:
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_FEATURED;
                        break;
                    case R.id.nav_favourite:
                        navItemIndex = 3;
                        CURRENT_TAG = TAG_FAVOURITE;
                        break;
                    case R.id.nav_recent:
                        navItemIndex = 4;
                        CURRENT_TAG = TAG_RECENT;
                        break;
                    case R.id.nav_help:
                        startActivity(new Intent(MainActivity.this, TermsActivity.class));
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_about:
                        startActivity(new Intent(MainActivity.this, SignUpActivity.class));
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
        else
            logout();
        //super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        final SearchView searchView = (SearchView)MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        SearchManager searchManager = (SearchManager)getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void logout(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage(getString(R.string.prompt_confirm_logout));
        alert.setPositiveButton(getString(R.string.prompt_yes), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });
        alert.setNegativeButton(getString(R.string.prompt_no), null);
        alert.setCancelable(false);
        alert.show();
    }
}