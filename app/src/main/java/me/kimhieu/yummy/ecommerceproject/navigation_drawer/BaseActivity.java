package me.kimhieu.yummy.ecommerceproject.navigation_drawer;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;
import me.kimhieu.yummy.ecommerceproject.R;
import me.kimhieu.yummy.ecommerceproject.explore.ExploreActivity;
import me.kimhieu.yummy.ecommerceproject.onsale.OnSaleActivity;
import me.kimhieu.yummy.ecommerceproject.utils.YummySession;

/**
 * This activity create a general navigation drawer.
 * Any activity extends this activity should have the same navigation drawer
 */
public class BaseActivity extends AppCompatActivity {

    private Toolbar toolbar;
    protected NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    DrawerLayout fullView;
    FrameLayout activityContainer;

    protected void onCreateDrawer() {

        // Initializing Toolbar and setting it as the actionbar
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        //Initializing NavigationView
        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        navigationView.setNavigationItemSelectedListener(new NavigationViewItemListener());

        // Initializing Drawer Layout and ActionBarToggle
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer){

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we don't want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we don't want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();

        UpdateHeader(this, navigationView);
    }

    public class NavigationViewItemListener implements NavigationView.OnNavigationItemSelectedListener {

        @Override
        public boolean onNavigationItemSelected(MenuItem menuItem) {
            //Checking if the item is in checked state or not, if not make it in checked state
            if(menuItem.isChecked()) {
                menuItem.setChecked(false);
            } else {
                menuItem.setChecked(true);
            }

            //Closing drawer on item click
            drawerLayout.closeDrawers();

            // TODO: Indicate the destination activity for each item
            switch (menuItem.getItemId()) {
                case R.id.explore:
                    BaseActivity.this.finish();
                    startActivity(new Intent(BaseActivity.this, ExploreActivity.class));
                    return true;
                case R.id.onsale:
                    BaseActivity.this.finish();
                    startActivity(new Intent(BaseActivity.this, OnSaleActivity.class));
                    return true;
                case R.id.cart:
                    return true;
                case R.id.settings:
                    return true;
                case R.id.logout:
                    BaseActivity.this.finish();
                    YummySession.userProfile = null;
                    return true;
                default:
                    return true;
            }
        }
    }

    public static void UpdateHeader (Context context, NavigationView navigationView){
        if (YummySession.userProfile != null) {
            View v = navigationView.getHeaderView(0);
            CircleImageView profilePicture = (CircleImageView) v.findViewById(R.id.header_profile_image);
            TextView textViewUserName = (TextView) v.findViewById(R.id.header_user_name);
            TextView textViewEmail = (TextView) v.findViewById(R.id.header_email);

            Glide.with(context)
                    .load(YummySession.userProfile.getPictureURL())
                    .into(profilePicture);
            textViewUserName.setText(YummySession.userProfile.getName());
            textViewEmail.setText(YummySession.userProfile.getEmail());
        }
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        fullView = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        activityContainer = (FrameLayout) fullView.findViewById(R.id.content_frame);
        getLayoutInflater().inflate(layoutResID, activityContainer, true);
        super.setContentView(layoutResID);
        onCreateDrawer();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }
}
