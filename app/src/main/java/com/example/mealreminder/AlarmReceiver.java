package com.example.mealreminder;
/*
 * Created by Sushant Kale 22-05-2021.
 */
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import androidx.core.app.NotificationCompat;

import static android.content.Context.NOTIFICATION_SERVICE;

public class AlarmReceiver extends BroadcastReceiver {
   public static int NOTIFICATION_ID = 1;

    @Override
    public void onReceive(Context context, Intent intent) {
        sendNotification(context,intent);
    }

    private void sendNotification(Context context, Intent intent) {
        NotificationManager mNotificationManager = (NotificationManager)
                context.getSystemService(NOTIFICATION_SERVICE);

        String id = context.getString(R.string.string_id);
        NotificationCompat.Builder builder = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            CharSequence name = context.getString(R.string.channel_name);
            String description = context.getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(id, name, importance);
            mChannel.setDescription(description);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            notificationManager.createNotificationChannel(mChannel);
            builder = new NotificationCompat.Builder(context, mChannel.getId());
        }else{
            builder = new NotificationCompat.Builder(context);
        }
        builder.setSmallIcon(R.drawable.breakfast); // app icon
        builder.setChannelId(id);
        builder.setContentText(intent.getAction());
        builder.setContentTitle(context.getString(R.string.meal_reminder_title));
        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.bluetimer));
        builder.setNumber(1);
        // builder.setColor(40);
        builder.setAutoCancel(true);
        builder.setVibrate(new long[]{1000, 1000});
        builder.setWhen(System.currentTimeMillis());
        NOTIFICATION_ID = NOTIFICATION_ID + 1;
        mNotificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}
