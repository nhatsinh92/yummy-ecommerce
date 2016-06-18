package me.kimhieu.yummy.ecommerceproject;

import android.app.Application;

import com.auth0.core.Strategies;
import com.auth0.facebook.FacebookIdentityProvider;
import com.auth0.lock.Lock;
import com.auth0.lock.LockContext;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class YummyApplication extends Application{
    public void onCreate() {
        super.onCreate();

        LockContext.configureLock(
                new Lock.Builder()
                        .loadFromApplication(this)
                        .withIdentityProvider(Strategies.Facebook, new FacebookIdentityProvider(this))
                        .closable(true)
        );
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(configuration);
    }}
