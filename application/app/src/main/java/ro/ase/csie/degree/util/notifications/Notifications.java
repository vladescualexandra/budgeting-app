package ro.ase.csie.degree.util.notifications;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import ro.ase.csie.degree.R;

public class Notifications {

    private static final String CHANNEL_ID = "CHANNEL_ID";

    private static Notification buildNotification(Context context) {
        return new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
                .setContentTitle(context.getResources().getString(R.string.notification_content_title))
                .setContentText(context.getResources().getString(R.string.notification_content_text))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .build();
    }

    public static PendingIntent getPendingIntent(Context context) {
        Notification notification = buildNotification(context);
        Intent notificationIntent = new Intent(context, ReminderReceiver.class);
        notificationIntent.putExtra(ReminderReceiver.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(ReminderReceiver.NOTIFICATION, notification);
        return PendingIntent.getBroadcast(context,
                0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public static void scheduleNotification(Context context) {
        PendingIntent pendingIntent = getPendingIntent(context);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        assert alarmManager != null;
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, 0,
                AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    public static void cancelNotification(Context context) {
        PendingIntent pendingIntent = getPendingIntent(context);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        assert alarmManager != null;
        alarmManager.cancel(pendingIntent);
    }
}
