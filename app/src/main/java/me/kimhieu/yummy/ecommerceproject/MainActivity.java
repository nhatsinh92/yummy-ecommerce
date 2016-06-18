package me.kimhieu.yummy.ecommerceproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.auth0.core.Token;
import com.auth0.core.UserProfile;
import com.auth0.lock.Lock;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UserProfile profile = getIntent().getParcelableExtra(Lock.AUTHENTICATION_ACTION_PROFILE_PARAMETER);
        Token token = getIntent().getParcelableExtra(Lock.AUTHENTICATION_ACTION_TOKEN_PARAMETER);
        Log.d("NAME", profile.getName());
        Log.d("TOKEN", token.getAccessToken());
    }
}
