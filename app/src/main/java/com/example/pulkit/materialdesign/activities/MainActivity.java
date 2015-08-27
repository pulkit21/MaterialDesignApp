package com.example.pulkit.materialdesign.activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.pulkit.materialdesign.R;
import com.example.pulkit.materialdesign.fragments.FragmentBoxOffice;
import com.example.pulkit.materialdesign.fragments.FragmentSearch;
import com.example.pulkit.materialdesign.fragments.FragmentUpcoming;
import com.example.pulkit.materialdesign.fragments.NavDrawerFragment;
import com.example.pulkit.materialdesign.fragments.TabFragment;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;

public class MainActivity extends AppCompatActivity implements MaterialTabListener {

    private Toolbar toolbar;
    private MaterialTabHost tabHost;
    private ViewPager viewPager;

    public static final int MOVIES_SEARCH_RESULT = 0;
    public static final int MOVIES_HITS = 1;
    public static final int MOVIES_INCOMING = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.appBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        NavDrawerFragment navDrawerFragment = (NavDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentNavDrawer);
        navDrawerFragment.setUp(R.id.fragmentNavDrawer, (DrawerLayout)findViewById(R.id.drawer_layout), toolbar);

        tabHost = (MaterialTabHost) findViewById(R.id.materialTabHost);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);

        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                tabHost.setSelectedNavigationItem(position);
            }
        });
        for (int i =0; i < viewPagerAdapter.getCount(); i++) {
            tabHost.addTab(
                    tabHost.newTab().setText(viewPagerAdapter.getPageTitle(i)).setTabListener(this)
            );
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.navigate) {
            startActivity(new Intent(this,TextActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(MaterialTab materialTab) {
        viewPager.setCurrentItem(materialTab.getPosition());

    }

    @Override
    public void onTabReselected(MaterialTab materialTab) {

    }

    @Override
    public void onTabUnselected(MaterialTab materialTab) {

    }

    private class ViewPagerAdapter extends FragmentStatePagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
//            TabFragment tabFragment = TabFragment.getInstance(position);
//            return tabFragment;
            switch (position) {
                case MOVIES_SEARCH_RESULT:
                    fragment = FragmentSearch.newInstance("","");
                    break;
                case MOVIES_HITS:
                    fragment = FragmentBoxOffice.newInstance("","");
                    break;
                case MOVIES_INCOMING:
                    fragment = FragmentUpcoming.newInstance("","");
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getResources().getStringArray(R.array.tabs)[position];
        }
    }

}
