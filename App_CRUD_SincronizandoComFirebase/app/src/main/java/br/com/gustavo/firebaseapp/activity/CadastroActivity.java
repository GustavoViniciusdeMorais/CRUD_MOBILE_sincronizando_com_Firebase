package br.com.gustavo.firebaseapp.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

import br.com.gustavo.firebaseapp.R;
import br.com.gustavo.firebaseapp.config.ConfiguracaoFirebase;
import br.com.gustavo.firebaseapp.model.User;

public class CadastroActivity extends AppCompatActivity {

    private EditText nome;
    private EditText email;
    private EditText senha;
    private Button btnCad;
    private User usuario;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        nome = (EditText) findViewById(R.id.cpCadNome);
        email = (EditText) findViewById(R.id.cpCadEmail);
        senha = (EditText) findViewById(R.id.cpCadSenha);
        btnCad = (Button) findViewById(R.id.btnCadCadastrar);

        btnCad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                usuario = new User();
                usuario.setNome(nome.getText().toString());
                usuario.setEmail(email.getText().toString());
                usuario.setSenha(senha.getText().toString());
                cadastrarUsuario();

            }
        });

    }

    private void cadastrarUsuario(){
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(CadastroActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if( task.isSuccessful() ){
                    FirebaseUser usuarioFirebase = task.getResult().getUser();
                    usuario.setId(usuarioFirebase.getUid());
                    usuario.salvar();
                    Toast.makeText(CadastroActivity.this, "Sucesso ao cadastrar!", Toast.LENGTH_LONG).show();
                    autenticacao.signOut();
                    nome.setText("");
                    email.setText("");
                    senha.setText("");
                    finish();
                }else{

                    String erroExcecao = "";

                    try{
                        throw task.getException();
                    }catch (FirebaseAuthWeakPasswordException e){
                        erroExcecao = "Digite uma senha com 8 caracteres!";
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        erroExcecao = "Digite um e-mail válido!";
                    }catch (FirebaseAuthUserCollisionException e){
                        erroExcecao = "Este usuário já existe, por favor cadastre outro!";
                    }catch (Exception e){
                        erroExcecao = "Erro ao efetuar cadastro! A senha deve conter 8 caracteres";
                        e.printStackTrace();
                    }

                    Toast.makeText(CadastroActivity.this, erroExcecao, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
