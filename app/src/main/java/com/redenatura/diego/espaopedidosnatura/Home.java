package com.redenatura.diego.espaopedidosnatura;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import me.zhanghai.android.materialprogressbar.IndeterminateProgressDrawable;


public class Home extends AppCompatActivity {

    private WebView web;
    private Toast toast;
    private long lastBackPressTime = 0;
    private AlertDialog alerta;
    private TextView frases;
    Tracker mTracker;
    private ProgressBar progresso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
        progresso = (ProgressBar) findViewById(R.id.progressBar);
        frases = (TextView) findViewById(R.id.frases);
        web = (WebView) findViewById(R.id.webView1);
        this.ChecarCompatibilidade();
        web.setWebViewClient(new ClienteWeb());

        progresso.setIndeterminateDrawable(new IndeterminateProgressDrawable(this));
        web.getSettings().setJavaScriptEnabled(true);
        web.clearCache(true);
        //web.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        web.loadUrl("http://m.rede.natura.net/espaco/diegotorres");

//        Avaliar();

        mTracker = ((Analytics_Exemplo) getApplication()).getTracker(Analytics_Exemplo.TrackerName.APP_TRACKER);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);


    }
*/

    @Override
    public void onBackPressed() {

        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Voltar")
                .setAction("Clicou em Voltar")
                .build());
        if(web.canGoBack()){
            web.goBack();

        }else {

            if (this.lastBackPressTime < System.currentTimeMillis() - 4000) {
                toast = Toast.makeText(this, "Pressione o Botão Voltar novamente para fechar o Aplicativo.", Toast.LENGTH_LONG);
                toast.show();
                this.lastBackPressTime = System.currentTimeMillis();
            } else {
                if (toast != null) {
                    toast.cancel();
                }
                super.onBackPressed();
            }
        }

    }

    private void ChecarCompatibilidade(){
         int versao = Build.VERSION.SDK_INT;

        if (versao >= 21){
            web.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }


    }

    /*private void Avaliar(){



        final SharedPreferences settings = getSharedPreferences(getString(R.string.avaliado), Context.MODE_PRIVATE);
        int valor_padrao_avaliar = 0;

        int obter_valor_avaliar = settings.getInt("avaliou", valor_padrao_avaliar);

        String LOGS = "logs";
        String v = String.valueOf(obter_valor_avaliar);
        Log.i(LOGS,v);



        if(obter_valor_avaliar == 0){

            SharedPreferences.Editor editor = settings.edit();
            editor.putInt("avaliou", 1);
            editor.commit();

        }else if(obter_valor_avaliar == 1){
            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle);

            builder.setTitle("Gostou do Nosso APP?");
            builder.setMessage("Promova o Bem Estar e Avalie nosso Aplicativo");
            builder.setNegativeButton("Mais Tarde", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    notificar();

                }
            });

            builder.setPositiveButton("Avalie", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {


                    SharedPreferences.Editor editor = settings.edit();
                    editor.putInt("avaliou", 2);
                    editor.commit();

                    mTracker.send(new HitBuilders.EventBuilder()
                            .setCategory("Avalie")
                            .setAction("Clicou em Avaliar")
                            .build());

                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.redenatura.diego.espaopedidosnatura")));


                }
            });

            alerta = builder.create();
            alerta.show();
        }
    }*/

    private class ClienteWeb extends WebViewClient{

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {

            progresso.setVisibility(View.VISIBLE);
            Home.this.progresso.setProgress(0);
            frases.setVisibility(View.VISIBLE);
            frases.setText("Gerando Bem Estar Bem");
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            progresso.setVisibility(View.GONE);
            frases.setVisibility(View.GONE);
            Home.this.progresso.setProgress(100);
            super.onPageFinished(view, url);
        }
    }

    public void notificar(){

        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        PendingIntent p = PendingIntent.getActivity(this, 0, new Intent(this, AbrirpelaNotify.class), 0);

        NotificationCompat.Builder b = new NotificationCompat.Builder(this);
        b.setTicker("exmplo");
        b.setContentTitle("Compre");
        b.setContentText("vamos comprar");
        b.setSmallIcon(R.drawable.loguinho);


        Notification n = b.build();
        nm.notify(R.drawable.loguinho, n);

    }



}