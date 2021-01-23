package com.redenatura.diego.espaopedidosnatura;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.redenatura.diego.espaopedidosnatura.rede.natura.diego.model.Cupom;
import com.redenatura.diego.espaopedidosnatura.rede.natura.diego.model.CupomAdapter;
import com.redenatura.diego.espaopedidosnatura.rede.natura.diego.model.CupomREST;

import java.util.ArrayList;

/**
 * Created by diego on 06/11/15.
 */
public class cupomActivity extends AppCompatActivity {
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cupom);

        lv = (ListView)findViewById(R.id.listView);

        CupomREST cupomREST = new CupomREST();
        try {
            ArrayList<Cupom> listaCupom = cupomREST.execute().get();

            CupomAdapter cuponsAdapter = new CupomAdapter(this, listaCupom);
            lv.setAdapter(cuponsAdapter);

        } catch (Exception e) {
            e.printStackTrace();
            gerarToast(e.getMessage());
        }

    }

    private void gerarToast(CharSequence message) {
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast
                .makeText(getApplicationContext(), message, duration);
        toast.show();
    }
}
