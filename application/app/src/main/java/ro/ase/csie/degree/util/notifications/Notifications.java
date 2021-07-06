package ro.ase.csie.degree.util.notifications;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.allyants.notifyme.NotifyMe;

import ro.ase.csie.degree.R;

public class Notifications {

    private final Context context;
    private final NotifyMe notifyMe;

    public Notifications(Context context) {
        this.context = context;
        this.notifyMe = buildNotify();
    }

    private NotifyMe buildNotify() {
        NotifyMe.Builder notifyMe = new NotifyMe.Builder(this.context);

        notifyMe.title(context.getResources().getString(R.string.notification_content_title));
        notifyMe.content(context.getResources().getString(R.string.notification_content_text));
        notifyMe.rrule("FREQ=DAILY;INTERVAL=1");
        notifyMe.small_icon(R.drawable.logo);
        notifyMe.delay(0);
        return notifyMe.build();
    }
}
