package com.redenatura.diego.espaopedidosnatura;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

/**
 * Created by diego on 27/10/15.
 */
public class Notificacao extends Service {


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        notificar();
        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void notificar(){

        int id = 1;

        Log.i("Tche", "bolo doido");


        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        PendingIntent p = PendingIntent.getActivity(this, 0, new Intent(this, AbrirpelaNotify.class), PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder b = new NotificationCompat.Builder(this);
        //b.setTicker("exmplo");
        b.setContentIntent(p);
        b.setContentTitle("Meus Pedidos Natura");
        b.setContentText("Confira as ofertas da Semana no seu app de Pedidos Natura");
        b.setSmallIcon(R.drawable.loguinho);
        b.setColor(-65536);


        b.setAutoCancel(true);
        Notification n = b.build();

        nm.notify(id, n);

    }
}
