package com.vrm.invercasa;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vrm.invercasa.utils.PrefManager;

public class WelcomeActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private ViewPageAdapter ViewPageAdapter;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private int[] layouts;
    private Button btnSkip, btnNext;
    private PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefManager = new PrefManager(this);
        if (!prefManager.isFirstTime()) {
            gotoLogin();
            finish();
        }
        if (Build.VERSION.SDK_INT >= 21)
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        setContentView(R.layout.activity_welcome);
        ui();
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoLogin();
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current = viewPager.getCurrentItem() + 1;
                if (viewPager.getCurrentItem() + 1 < layouts.length)
                    viewPager.setCurrentItem(current);
                else
                    gotoLogin();
            }
        });
    }

    private void ui() {
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
        btnSkip = (Button) findViewById(R.id.skip);
        btnNext = (Button) findViewById(R.id.next);
        layouts = new int[]{
                R.layout.welcome_slide1,
                R.layout.welcome_slide2,
                R.layout.welcome_slide3
        };
        addBottomDots(0);
        changeStatusBarColor();
        ViewPageAdapter = new ViewPageAdapter();
        viewPager.setAdapter(ViewPageAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
    }

    private void addBottomDots(int currentPage) {
        dots = new TextView[layouts.length];
        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);
        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(getString(R.string.prompt_dot));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[currentPage]);
            dotsLayout.addView(dots[i]);
        }
        dots[currentPage].setTextColor(colorsActive[currentPage]);
    }

    private void gotoLogin() {
        prefManager.setFirstTime(false);
        startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
        finish();
    }

    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);
            if (position == layouts.length - 1) {
                btnNext.setText(getString(R.string.prompt_log_in));
                btnSkip.setVisibility(View.GONE);
            } else {
                btnNext.setText(getString(R.string.prompt_next));
                btnSkip.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    };

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    class ViewPageAdapter extends PagerAdapter {
        public ViewPageAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = ((LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(layouts[position], container, false);
            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }
}