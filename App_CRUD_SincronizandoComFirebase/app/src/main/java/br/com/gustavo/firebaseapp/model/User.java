package br.com.gustavo.firebaseapp.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import br.com.gustavo.firebaseapp.config.ConfiguracaoFirebase;

/**
 * Created by Cliente on 22/09/2017.
 */

public class User {

    private String id;
    private String nome;
    private String email;
    private String senha;

    public User() {
    }

    public void salvar(){
        DatabaseReference referencia = ConfiguracaoFirebase.getFirebase();
        referencia.child("usuarios").child(getId()).setValue(this);
    }

    @Exclude // exclui o id de ser salvo no database
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Exclude // exclui a senha de ser salva no database
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
