package com.redenatura.diego.espaopedidosnatura;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by diego on 08/09/15.
 */
public class AbrirpelaNotify extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = new Intent(this, nova_tela_inicio.class);
        startActivity(i);
    }
}
