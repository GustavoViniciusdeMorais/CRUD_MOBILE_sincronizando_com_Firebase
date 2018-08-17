package br.com.gustavo.firebaseapp.model;

import com.google.firebase.database.DatabaseReference;

import br.com.gustavo.firebaseapp.config.ConfiguracaoFirebase;

/**
 * Created by Cliente on 20/09/2017.
 */

public class Produto{

    private String id;
    private String descricao;
    private String cor;
    private String fabricante;

    public Produto() {
    }

    public void salvar(){
        DatabaseReference firebase = ConfiguracaoFirebase.getFirebase();
        firebase.child("produtos").child(getId()).setValue(this);
    }

    public void deletar(){
        DatabaseReference firebase = ConfiguracaoFirebase.getFirebase();
        firebase.child("produtos").child(getId()).removeValue();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }
}
