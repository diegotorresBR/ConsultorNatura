package com.redenatura.diego.espaopedidosnatura;

/**
 * Created by diego on 01/10/15.
 */

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

public class nova_tela_inicio extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private WebView web, web2;
    private Toast toast;
    private long lastBackPressTime = 0;
    private AlertDialog alerta;
    private TextView frases;
    Tracker mTracker;
    private ProgressBar progresso;
    private FloatingActionButton fab;
    private boolean visivel = true;//define a visibilidade do botao flutuante de promoçoes
    private String home = "http://m.rede.natura.net/espaco/diegotorres";


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.novo_home2);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setVisibility(View.GONE);
            /*fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Utilize o Cupom SENSUAL em sua compra e ganhe 10% de Desconto." +
                            " *Valido ate 30/10", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                }
            });*/

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);

            iniciar_conteudo_interno();
            Avaliar();
        }

        @Override
        public void onBackPressed() {
            /*DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }*/

            enviar_evento("Voltar", "Clicou em Voltar");

             if(web.canGoBack()){
                web.goBack();

            }else {

                if (this.lastBackPressTime < System.currentTimeMillis() - 4000) {
                    toast = Toast.makeText(this, "Pressione novamente para fechar o Aplicativo.", Toast.LENGTH_LONG);
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

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_home, menu);
            return true;
        }

       /* @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();

            //noinspection SimplifiableIfStatement
            if (id == R.id.action_settings) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle);

                builder.setTitle("Natura Pedidos On-line");
                builder.setMessage("Esse Aplicativo foi desenvolvido por Consultores Natura Digital. Tenha toda a comodidade de pedir " +
                        "diretamente do seu smartphone sem sair de casa");
                builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //notificar();

                    }
                });
                alerta = builder.create();
                alerta.show();
            }

            return super.onOptionsItemSelected(item);
        }*/

        @SuppressWarnings("StatementWithEmptyBody")
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            // Handle navigation view item clicks here.


            int id = item.getItemId();

            if (id == R.id.nav_principal) {
                enviar_evento("Home", "Clicou no Home");
                //web2.setVisibility(View.GONE);
                web.loadUrl(home);
            } else if (id == R.id.nav_manage) {

                enviar_evento("Ferramentas", "Painel do Consultor");

                carregar_painel();

            } else if (id == R.id.nav_share) {

                enviar_evento("Avalie", "Clicou no Novo Avaliar");
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.redenatura.diego.espaopedidosnatura")));

            } else if (id == R.id.nav_sobre) {

                enviar_evento("Ferramentas", "Sobre");

                AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle);
                builder.setTitle("Natura Pedidos On-line");
                builder.setMessage("Esse Aplicativo foi desenvolvido por Consultores Natura Digital. Tenha toda a comodidade de pedir " +
                        "diretamente do seu smartphone sem sair de casa");
                builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //notificar();
                    }
                });
                alerta = builder.create();
                alerta.show();
                //notificar();
            } else if (id == R.id.cupom_menu){
                enviar_evento("Cupom", "Visualiza Cupom");
                Intent i = new Intent(getApplicationContext(), cupomActivity.class);
                startActivity(i);
            }

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }

    private void iniciar_conteudo_interno(){
        progresso = (ProgressBar) findViewById(R.id.loading);
        frases = (TextView) findViewById(R.id.frases_de_bem_estar);
        web = (WebView) findViewById(R.id.principal);
        ChecarCompatibilidade();

        progresso.setIndeterminateDrawable(new IndeterminateProgressDrawable(this));

        web.setWebViewClient(new ClienteWeb());
        web.getSettings().setJavaScriptEnabled(true);
        //web.clearCache(true);
        //this.web.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        web.loadUrl(home);

        //Avaliar();

        mTracker = ((Analytics_Exemplo) getApplication()).getTracker(Analytics_Exemplo.TrackerName.APP_TRACKER);

        web2 = (WebView) findViewById(R.id.painel);
        web2.setVisibility(View.GONE);
        web2.setWebViewClient(new ClienteWeb());
        web2.getSettings().setJavaScriptEnabled(true);

        Typeface fonte = Typeface.createFromAsset(getAssets(), "GillSansStd_Light.otf");
        frases.setTypeface(fonte);
    }

/*Inner Class que herda do WebViewClient. Aqui algumas modificaçoes foram feitas. A tela do loading foi
  personalizada. ProgressBar aparece ao carregar uma pagina some apos carregar a pagina inteira.
  Frases aparecem com o loading*/
    private class ClienteWeb extends WebViewClient {

        /*@Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }*/

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {

            progresso.setVisibility(View.VISIBLE);
            nova_tela_inicio.this.progresso.setProgress(0);
            frases.setVisibility(View.VISIBLE);
            frases.setText(frases());
            //super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            progresso.setVisibility(View.GONE);
            frases.setVisibility(View.GONE);
            nova_tela_inicio.this.progresso.setProgress(100);
            //super.onPageFinished(view, url);
        }
    }
/*As imagens dos produtos nao aparecem na versao Lollipop em diante. Metodo corrige isso. Importante
observar que a aplicaçao pode quebrar se esse metodo for executado em versoes anteriores ao lollipop*/
    private void ChecarCompatibilidade(){
        int versao = Build.VERSION.SDK_INT;

        if (versao >= 21){
            web.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

        }
    }

    public void carregar_painel(){

        //web2.setVisibility(View.VISIBLE);
        web.loadUrl("https://painel.rede.natura.net/login");
        enviar_evento("Ferramentas", "Painel CND");

    }

    private void Avaliar(){



        final SharedPreferences settings = getSharedPreferences(getString(R.string.avaliado), Context.MODE_PRIVATE);
        int valor_padrao_avaliar = 0;

        int obter_valor_avaliar = settings.getInt("avaliou", valor_padrao_avaliar);

        String LOGS = "logs";
        String v = String.valueOf(obter_valor_avaliar);
        Log.i(LOGS, v);



        if(obter_valor_avaliar == 0){

            SharedPreferences.Editor editor = settings.edit();
            editor.putInt("avaliou", 1);
            editor.commit();

        }else if(obter_valor_avaliar == 1){
            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle);
            builder.setTitle("Gostou do Nosso APP?");
            builder.setMessage("Promova o Bem Estar. Avalie nosso Aplicativo e nos ajude a melhorar. Avalie com G+1 no Google Play");
            builder.setNegativeButton("Mais Tarde", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //notificar();
                }
            });

            builder.setPositiveButton("Avalie", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    SharedPreferences.Editor editor = settings.edit();
                    editor.putInt("avaliou", 2);
                    editor.commit();

                    enviar_evento("Avalie", "Clicou em Avaliar");
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.redenatura.diego.espaopedidosnatura")));

                }
            });
            alerta = builder.create();
            alerta.show();
        }
    }
//mostra as frases no carregamento de uma pagina
    private String frases(){
        Resources r = getResources();
        String [] frases = r.getStringArray(R.array.frases);
        int total_frases = frases.length;
        final SharedPreferences settings = getSharedPreferences(getString(R.string.frases_), Context.MODE_PRIVATE);
        int valor_padrao_frases = 0;
        int obter_valor_frases = settings.getInt("frase", valor_padrao_frases);

        if(obter_valor_frases >=total_frases){
            --obter_valor_frases;
            SharedPreferences.Editor editor = settings.edit();
            editor.putInt("frase", valor_padrao_frases);
            editor.commit();

        }else if(obter_valor_frases < total_frases){
            SharedPreferences.Editor editor = settings.edit();
            int x = obter_valor_frases+1;
            editor.putInt("frase", x);
            editor.commit();
        }

        return frases[obter_valor_frases];
    }

    public void enviar_evento(String categoria, String acao){
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory(categoria)
                .setAction(acao)
                .build());
    }

    public void notificar(){

        int id = 1;


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