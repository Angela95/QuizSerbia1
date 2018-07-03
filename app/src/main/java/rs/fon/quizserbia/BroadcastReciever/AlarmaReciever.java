package rs.fon.quizserbia.BroadcastReciever;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import java.net.URL;

import rs.fon.quizserbia.MainActivity;
import rs.fon.quizserbia.R;

/**
 * Created by Comp on 7/1/2018.
 */

public class AlarmaReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        long when=System.currentTimeMillis();
        NotificationManager notificationManager=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent notificationIntent=new Intent(context, MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent=PendingIntent.getActivity(context,100,notificationIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        Uri alarmS= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        android.support.v4.app.NotificationCompat.Builder builder= new NotificationCompat.Builder(context,"default")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Ja volim Srbiju")
                .setContentText("Pozdrav! Testiraj svoje znanje o Srbiji")
                .setSound(alarmS)
                .setAutoCancel(true)
                .setWhen(when)

                .setContentIntent(pendingIntent)
                .setVibrate(new long[]{1000,1000,1000,1000,1000});
notificationManager.notify(100,builder.build());
    }
}
