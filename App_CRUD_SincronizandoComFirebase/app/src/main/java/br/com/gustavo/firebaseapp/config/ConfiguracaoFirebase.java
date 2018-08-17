package br.com.gustavo.firebaseapp.config;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Cliente on 22/09/2017.
 */

public final class ConfiguracaoFirebase {

    private static DatabaseReference referenciaFairebase;
    private static FirebaseAuth autenticacao;

    public static DatabaseReference getFirebase() {
        if (referenciaFairebase == null) {
            referenciaFairebase = FirebaseDatabase.getInstance().getReference();
        }
        return referenciaFairebase;
    }

    public static FirebaseAuth getFirebaseAutenticacao(){
        if(autenticacao == null){
            autenticacao = FirebaseAuth.getInstance();
        }
        return autenticacao;
    }

}
