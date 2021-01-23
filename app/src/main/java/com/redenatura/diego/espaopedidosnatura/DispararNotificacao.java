package com.redenatura.diego.espaopedidosnatura;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by diego on 21/10/15.
 */
public class DispararNotificacao extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Intent i = new Intent(context, Notificacao.class);
        //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startService(i);
        Log.i("Tche", "foi");
        //nova_tela_inicio n = new nova_tela_inicio();
        //Bundle b = new Bundle();
        //n.onCreate(b);
        //n.notificar();
    }


}
