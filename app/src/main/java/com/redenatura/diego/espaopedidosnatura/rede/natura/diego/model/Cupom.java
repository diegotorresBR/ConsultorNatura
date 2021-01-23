package com.redenatura.diego.espaopedidosnatura.rede.natura.diego.model;

import java.io.Serializable;

/**
 * Created by diego on 06/11/15.
 */
public class Cupom implements Serializable {

    private int id;
    private String titulo, descricao, validade;

    public void setId(int id) {
        this.id = id;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setValidade(String validade) {
        this.validade = validade;
    }

    public int getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getValidade() {
        return validade;
    }
}
