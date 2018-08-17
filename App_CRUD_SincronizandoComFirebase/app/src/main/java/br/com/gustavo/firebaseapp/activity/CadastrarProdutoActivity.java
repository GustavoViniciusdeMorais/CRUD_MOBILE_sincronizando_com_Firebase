package br.com.gustavo.firebaseapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

import br.com.gustavo.firebaseapp.R;
import br.com.gustavo.firebaseapp.config.ConfiguracaoFirebase;
import br.com.gustavo.firebaseapp.model.Produto;

public class CadastrarProdutoActivity extends AppCompatActivity {

    private EditText cpDescricao;
    private EditText cpCor;
    private EditText cpFabricante;
    private Button btnCadProd;
    private Produto produto;
    private DatabaseReference firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_produto);

        cpDescricao = (EditText) findViewById(R.id.cpProdDescricao);
        cpCor = (EditText) findViewById(R.id.cpProdCor);
        cpFabricante = (EditText) findViewById(R.id.cpProdFabricante);
        btnCadProd = (Button) findViewById(R.id.btnCadProd);

        btnCadProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    produto = new Produto();
                    produto.setDescricao(cpDescricao.getText().toString());
                    produto.setCor(cpCor.getText().toString());
                    produto.setFabricante(cpFabricante.getText().toString());

                    firebase = ConfiguracaoFirebase.getFirebase();
                    produto.setId(firebase.child("produtos").push().getKey()); // problem id 123as2
                    produto.salvar();
                    Toast.makeText(CadastrarProdutoActivity.this, "Salvo com sucesso!", Toast.LENGTH_LONG)
                            .show();
                    voltar();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(CadastrarProdutoActivity.this, "Erro ao salvar!", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });

    }

    private void voltar(){
        Intent intent = new Intent(CadastrarProdutoActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
