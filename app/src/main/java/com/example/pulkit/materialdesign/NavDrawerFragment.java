package com.example.pulkit.materialdesign;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.Toolbar;


/**
 * A simple {@link Fragment} subclass.
 */
public class NavDrawerFragment extends Fragment {
    private View containerView;
    public static final String PREF_FILE_NAME="testpref";
    public static final String KEY_USER_LEARNED_DRAWER="user_learned_drawer";
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;

    private boolean mUserLearnedDrawer;
    private boolean mFromSavedInstanceState;

    public NavDrawerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserLearnedDrawer = Boolean.valueOf(readFromPreferences(getActivity(),KEY_USER_LEARNED_DRAWER,"false"));
        if (savedInstanceState != null) {
            mFromSavedInstanceState = true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nav_drawer, container, false);
    }

    public void setUp(int fragmentId ,DrawerLayout drawerLayout, final Toolbar toolbar) {
        containerView =getActivity().findViewById(fragmentId);
        mDrawerLayout =drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(),mDrawerLayout,toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!mUserLearnedDrawer) {
                    mUserLearnedDrawer=true;
                    saveToPreferences(getActivity(),KEY_USER_LEARNED_DRAWER, mUserLearnedDrawer+"");
                }
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

//            TODO: If you want to use the navigation Drawer below the toobar
//            TODO: then enable the belows code to use the color effect on your toolbar and change the layout in main from
//            TODo: activity_main to activity_main_appbar
//            @Override
//            public void onDrawerSlide(View drawerView, float slideOffset) {
//                if (slideOffset < 0.6) {
//                    toolbar.setAlpha(1 - slideOffset);
//                }
//            }
        };

        if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
            mDrawerLayout.openDrawer(containerView);
        }
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
    }

     public static void saveToPreferences(Context context, String preferenceName, String preferenceValue) {
         SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME,Context.MODE_PRIVATE);
         SharedPreferences.Editor editor = sharedPreferences.edit();
         editor.putString(preferenceName,preferenceValue);
         editor.apply();
     }

    public static String readFromPreferences(Context context, String preferenceName, String defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(preferenceName,defaultValue);

    }


}
