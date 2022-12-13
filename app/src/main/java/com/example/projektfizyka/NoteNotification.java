package com.example.projektfizyka;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NoteNotification extends Notification {
    private String ChannelID;
    private Context n_Context;

    public NoteNotification(String ChannelID, Context context) {
        this.ChannelID = ChannelID;
        this.n_Context = context;
    }

    public void CreateNoteNotification(String Title, String Content, int... id) {
        int noteID = (id.length>=1)? id[0]:0;
        NotificationCompat.Builder builder = new NotificationCompat.Builder(n_Context, ChannelID);
        builder.setContentTitle(Title);
        builder.setSmallIcon(R.drawable.ic_icon_foreground);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setStyle(new NotificationCompat.BigTextStyle().bigText(Content));
        builder.setAutoCancel(false);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(n_Context);
            managerCompat.notify(noteID, builder.build());
    }

    public void SetUpNoteNotificationManager(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(this.ChannelID, this.ChannelID, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = n_Context.getSystemService(NotificationManager.class);
            manager.createNotificationChannel((channel));
        }
    }
}
