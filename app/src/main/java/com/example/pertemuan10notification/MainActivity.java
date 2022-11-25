package com.example.pertemuan10notification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private NotificationManager mNotificationManager;
    private final static String CHANNEL_ID = "primary-channel";
    private int NOTIFICATION_ID = 0;
    private final static String ACTION_UPDATE_NOTIF = "action_update_notif";

    private NotificationReceiver mReceiver = new NotificationReceiver();

    Button btnTrigger0;
    Button btnTrigger;
    Button btnTrigger2;
    Button btnTrigger3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, "app notif", NotificationManager.IMPORTANCE_HIGH);
            mNotificationManager.createNotificationChannel(notificationChannel);
        }

        btnTrigger0 = findViewById(R.id.btnTrigger0);
        btnTrigger = findViewById(R.id.btnTrigger);
        btnTrigger2 = findViewById(R.id.btnTrigger2);
        btnTrigger3 = findViewById(R.id.btnTrigger3);

        btnTrigger0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendBasicNotification();
            }
        });

        btnTrigger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendNotification();
            }
        });

        btnTrigger2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateNotification();
            }
        });

        btnTrigger3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNotificationManager.cancel(NOTIFICATION_ID);
            }
        });

        registerReceiver(mReceiver, new IntentFilter(ACTION_UPDATE_NOTIF));
    }



    private NotificationCompat.Builder getNotificationBuilder(){
        Intent notificationIntent = new Intent(this, MainActivity2.class);
        PendingIntent notificationPendingIntent = PendingIntent.getActivity(this, NOTIFICATION_ID, notificationIntent, PendingIntent.FLAG_IMMUTABLE);
        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Selamat Anda Ter Prank")
                .setContentText("Oprec GDSC UGM Masih Extend Hiya Hiya hiYa")
                .setSmallIcon(R.drawable.ic_android_black_24dp)
                .setContentIntent(notificationPendingIntent);
        return notifyBuilder;
    }

    private void sendBasicNotification(){
        NotificationCompat.Builder notifyBuilder = getNotificationBuilder();
        mNotificationManager.notify(NOTIFICATION_ID,notifyBuilder.build());
    }

    private void sendNotification(){
        Intent updateIntent = new Intent(ACTION_UPDATE_NOTIF);
        PendingIntent updatePendingIntent = PendingIntent.getBroadcast(this, NOTIFICATION_ID, updateIntent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder notifyBuilder = getNotificationBuilder();
        notifyBuilder.addAction(R.drawable.ic_android_black_24dp, "update Notification", updatePendingIntent);
        mNotificationManager.notify(NOTIFICATION_ID,notifyBuilder.build());
    }

    private void updateNotification(){
        Bitmap androidImage = BitmapFactory.decodeResource(getResources(), R.drawable.img_ceo);
        NotificationCompat.Builder notifyBuilder = getNotificationBuilder();
        notifyBuilder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(androidImage)
                .setBigContentTitle("Notification updated!"));
        mNotificationManager.notify(NOTIFICATION_ID, notifyBuilder.build());
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    public class NotificationReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            String intentAction = intent.getAction();
            if (intentAction.equals(ACTION_UPDATE_NOTIF)){
                updateNotification();
                }
            }
        }
}