package br.com.gustavo.firebaseapp.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import br.com.gustavo.firebaseapp.R;
import br.com.gustavo.firebaseapp.config.ConfiguracaoFirebase;
import br.com.gustavo.firebaseapp.model.User;

public class LoginActivity extends AppCompatActivity {

    private TextView cadastrar;
    private EditText cpEmail;
    private EditText cpSenha;
    private Button login;
    private User usuario;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        verificarSeEstaLogado();

        cpEmail = (EditText) findViewById(R.id.cpLoginEmail);
        cpSenha = (EditText) findViewById(R.id.cpSenha);
        login = (Button) findViewById(R.id.btnLogin);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                usuario = new User();
                usuario.setEmail(cpEmail.getText().toString());
                usuario.setSenha(cpSenha.getText().toString());
                validarLogin();

            }
        });

        cadastrar = (TextView) findViewById(R.id.linkCadastrar);
        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, CadastroActivity.class);
                startActivity(intent);
            }
        });

    }

    private void validarLogin() {

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.signInWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Bem vindo !!!", Toast.LENGTH_LONG).show();
                    abrirTelaPrincipal();
                } else {
                    String erroLogin = "";
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthInvalidUserException e) {
                        erroLogin = "Usuário não existe ou fou desativado!";
                    } catch (Exception e) {
                        erroLogin = "Erro ao executar o login!";
                    }
                    Toast.makeText(LoginActivity.this, erroLogin, Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    private void abrirTelaPrincipal(){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void verificarSeEstaLogado(){
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        if( autenticacao.getCurrentUser() != null ){
            abrirTelaPrincipal();
        }
    }

}
