package me.kimhieu.yummy.ecommerceproject.login;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.auth0.lock.Lock;
import com.auth0.lock.LockActivity;

import me.kimhieu.yummy.ecommerceproject.MainActivity;
import me.kimhieu.yummy.ecommerceproject.R;
import me.kimhieu.yummy.ecommerceproject.explore.ExploreActivity;
import me.kimhieu.yummy.ecommerceproject.onsale.OnSaleActivity;
import me.kimhieu.yummy.ecommerceproject.utils.ExploreLibrary;
import me.kimhieu.yummy.ecommerceproject.utils.YummySession;

import static com.auth0.lock.Lock.AUTHENTICATION_ACTION;

public class LoginActivity extends AppCompatActivity {

    private LocalBroadcastManager broadcastManager;
    private ImageButton imageButtonSignIn;
    private ImageButton imageButtonCreateAccount;

    private BroadcastReceiver authenticationReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            YummySession.userProfile = intent.getParcelableExtra(Lock.AUTHENTICATION_ACTION_PROFILE_PARAMETER);
            final Intent newIntent = new Intent(LoginActivity.this, ExploreActivity.class);
            newIntent.putExtras(intent);
            startActivity(newIntent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Find view by Id
        imageButtonSignIn = (ImageButton) findViewById(R.id.image_button_sign_in);
        imageButtonCreateAccount = (ImageButton) findViewById(R.id.image_button_create_account);

        // Set click event
        imageButtonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, LockActivity.class));
            }
        });

        imageButtonCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });

        // Listen from Auth0
        broadcastManager = LocalBroadcastManager.getInstance(this);
        broadcastManager.registerReceiver(authenticationReceiver, new IntentFilter(AUTHENTICATION_ACTION));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        broadcastManager.unregisterReceiver(authenticationReceiver);
    }
}
