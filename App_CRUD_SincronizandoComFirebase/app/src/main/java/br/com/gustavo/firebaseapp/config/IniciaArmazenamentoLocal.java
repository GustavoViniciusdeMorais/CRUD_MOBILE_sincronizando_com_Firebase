package br.com.gustavo.firebaseapp.config;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Cliente on 25/09/2017.
 */

public class IniciaArmazenamentoLocal extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
