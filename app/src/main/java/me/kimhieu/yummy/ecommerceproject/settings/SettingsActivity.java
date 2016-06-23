package me.kimhieu.yummy.ecommerceproject.settings;

import android.app.NotificationManager;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import me.kimhieu.yummy.ecommerceproject.R;
import me.kimhieu.yummy.ecommerceproject.navigation_drawer.BaseActivity;
import me.kimhieu.yummy.ecommerceproject.utils.YummySession;

public class SettingsActivity extends BaseActivity {

    private TextView textViewEmail;
    private Switch switchPushNotifications;
    private Switch switchEmailNotifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getSupportActionBar().setTitle("Settings");

        textViewEmail = (TextView) findViewById(R.id.text_view_setting_email);
        switchPushNotifications = (Switch) findViewById(R.id.switch_setting_push_notifications);
        switchEmailNotifications = (Switch) findViewById(R.id.switch_setting_email_notifications);

        textViewEmail.setText(YummySession.userProfile.getEmail());
        switchPushNotifications.setChecked(YummySession.pushNotification);
        switchEmailNotifications.setChecked(YummySession.emailNotification);

        switchPushNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String notificationContent = "";
                YummySession.notificationId ++;
                if (switchPushNotifications.isChecked()) {
                    notificationContent = "Push notifications on";
                    YummySession.pushNotification = true;
                }else {
                    notificationContent = "Push notifications off";
                    YummySession.pushNotification = false;
                }
                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(SettingsActivity.this)
                                .setSmallIcon(R.drawable.ic_comment)
                                .setContentTitle("Yummy Ecommerce")
                                .setContentText(notificationContent);
                // Sets an ID for the notification

                // Gets an instance of the NotificationManager service
                NotificationManager mNotifyMgr =
                        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                // Builds the notification and issues it.
                mNotifyMgr.notify(YummySession.notificationId, mBuilder.build());
            }
        });

        switchEmailNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (switchEmailNotifications.isChecked()) {
                    YummySession.emailNotification = true;
                }else{
                    YummySession.emailNotification = false;
                }
            }
        });
    }
}
