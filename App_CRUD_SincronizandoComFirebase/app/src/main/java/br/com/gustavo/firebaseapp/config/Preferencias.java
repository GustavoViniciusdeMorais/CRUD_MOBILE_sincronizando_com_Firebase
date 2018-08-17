package br.com.gustavo.firebaseapp.config;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Cliente on 24/09/2017.
 */

public class Preferencias {

    private Context contexto;
    private SharedPreferences preferences;
    private String NOME_ARQUIVO = "appPreferencias";
    private int MODE = 0;
    private SharedPreferences.Editor editor;

    public Preferencias(Context contextoParametro){

        contexto = contextoParametro;
        preferences = contexto.getSharedPreferences(NOME_ARQUIVO, MODE);
        editor = preferences.edit();

    }

    public void salvarUsuarioPreferencias(){
        
    }

}
