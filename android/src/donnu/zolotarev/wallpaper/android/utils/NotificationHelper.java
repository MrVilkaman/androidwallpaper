package donnu.zolotarev.wallpaper.android.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import donnu.zolotarev.wallpaper.android.R;

public class NotificationHelper {


    public static  void create(Context context, String title, String message, Intent intent){
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification nb = new Notification(R.drawable.ic_status_bar,title,System.currentTimeMillis());//.(context)
        // 2-я часть
        nb.setLatestEventInfo(context, title, message, PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT ));

        // ставим флаг, чтобы уведомление пропало после нажатия
        nb.flags |= Notification.FLAG_AUTO_CANCEL;

        try {
//            Notification notification = null;
           /* if (Build.VERSION_CODES.JELLY_BEAN <= Build.VERSION.SDK_INT){
                notification = nb.build();
            } else {
                notification = nb.getNotification();
            }*/
            manager.notify(0, nb); // отображаем его пользователю.
           // inc++;
        } catch (Exception e ){
        //    Toast.makeText(context, "notif error", Toast.LENGTH_LONG).show();
        }
    }
}
