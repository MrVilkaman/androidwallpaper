package donnu.zolotarev.wallpaper.android.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import donnu.zolotarev.wallpaper.android.R;
import donnu.zolotarev.wallpaper.android.utils.NotificationHelper;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationHelper.create(context, context.getString(R.string.app_name), context.getString(R.string.notif_msg),
                new Intent(context, MainActivity.class));

        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent2 = new Intent(context, AlarmReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent2, 0);
// Устанавливаем интервал срабатывания в 5 секунд.
        am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+((long)( 1000* 60*60* (23+Math.random()))), pi);

    }
}