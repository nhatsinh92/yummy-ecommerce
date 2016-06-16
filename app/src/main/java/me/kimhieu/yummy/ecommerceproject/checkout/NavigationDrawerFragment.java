package me.kimhieu.yummy.ecommerceproject.checkout;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

/**
 * Created by zyk on 6/14/2016.
 */
public class NavigationDrawerFragment extends Fragment {
    private RecyclerView recyclerView;
    public static final String PREF_FILE_NAME = "testpref";
    public static final String KEY_USER_LEARNED_DRAWER = "user_learned_drawer";

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    //adapter
 //   private VivzAdapter adapter;
    ///
    private boolean mUserLearedDrawer;
    private boolean mFromSavedInstanceState;

    private View containerView;

    public NavigationDrawerFragment() {
        // Required empty public constructor
    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        mUserLearedDrawer = Boolean.getBoolean(readFromPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, "false"));
//        if (savedInstanceState != null) {
//            mFromSavedInstanceState = true;
//        }
//    }
//
//    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {
//        containerView = getActivity().findViewById(fragmentId);
//        mDrawerLayout = drawerLayout;
//        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
//            @Override
//            public void onDrawerOpened(View drawerView) {
//                super.onDrawerOpened(drawerView);
//                if (!mUserLearedDrawer) {
//                    mUserLearedDrawer = true;
//                    saveToPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, mUserLearedDrawer + "");
//
//                }
//                getActivity().invalidateOptionsMenu();
//
//
//            }
//
//            @Override
//            public void onDrawerClosed(View drawerView) {
//                super.onDrawerClosed(drawerView);
//                getActivity().invalidateOptionsMenu();
//
//
//            }
//
//            @Override
//            public void onDrawerSlide(View drawerView, float slideOffset) {
//                if (slideOffset < 0.6) {
//
//                    //Log.d("VIVZ","offset "+slideOffset);
//                    toolbar.setAlpha(1 - slideOffset);
//                }
//            }
//        };
//        if (!mUserLearedDrawer && !mFromSavedInstanceState) {
//            mDrawerLayout.openDrawer(containerView);
//        }
//        mDrawerLayout.setDrawerListener(mDrawerToggle);
//        mDrawerLayout.post(new Runnable() {
//            @Override
//            public void run() {
//                mDrawerToggle.syncState();
//            }
//        });
//    }
//    public static void saveToPreferences(Context context, String preferenceName, String preferenceValue) {
//        SharedPreferences sharePreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharePreferences.edit();
//        editor.putString(preferenceName, preferenceValue);
//        editor.apply();
//    }
//
//    public static String readFromPreferences(Context context, String preferenceName, String defaultValue) {
//        SharedPreferences sharePreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
//        return sharePreferences.getString(preferenceName, defaultValue);
//    }
}
