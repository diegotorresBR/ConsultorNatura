package com.redenatura.diego.espaopedidosnatura.rede.natura.diego.model;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.redenatura.diego.espaopedidosnatura.R;

import java.util.ArrayList;

/**
 * Created by diego on 06/11/15.
 */
public class CupomAdapter extends BaseAdapter {
    Context context;
    ArrayList<Cupom> list;

    public CupomAdapter(Context context, ArrayList<Cupom> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.cupons_views, null);
        Cupom cupom = list.get(position);
        TextView titulo = (TextView) view.findViewById(R.id.titulo);
        titulo.setText(cupom.getTitulo());
        TextView descricao = (TextView) view.findViewById(R.id.descricao);
        descricao.setText(cupom.getDescricao());
        TextView validade = (TextView) view.findViewById(R.id.validade);
        validade.setText("Valido ate: " + cupom.getValidade());

        Typeface fonte_bold = Typeface.createFromAsset(context.getAssets(), "fonte_natura.ttf");
        titulo.setTypeface(fonte_bold);

        Typeface fonte_regeular = Typeface.createFromAsset(context.getAssets(), "GillSansStd_Light.otf");

        descricao.setTypeface(fonte_regeular);
        validade.setTypeface(fonte_regeular);

        return view;
    }
}
