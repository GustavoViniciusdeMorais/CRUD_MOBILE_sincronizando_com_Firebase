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

public class GerenciarProdutoActivity extends AppCompatActivity {

    public static Produto produto = new Produto();
    private EditText cpDescricao;
    private EditText cpFabricante;
    private EditText cpCor;
    private Button btnSalvar;
    private Button btnExcluir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerenciar_produto);

        cpDescricao = (EditText) findViewById(R.id.cpGeDescricao);
        cpCor = (EditText) findViewById(R.id.cpGeCor);
        cpFabricante = (EditText) findViewById(R.id.cpGeFabricante);

        cpDescricao.setText(produto.getDescricao());
        cpCor.setText(produto.getCor());
        cpFabricante.setText(produto.getFabricante());

        btnSalvar = (Button) findViewById(R.id.btnGeSalvar);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alterarDados();
            }
        });

        btnExcluir = (Button) findViewById(R.id.btnGeExcluir);
        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    produto.deletar();

                    Intent intent = new Intent(GerenciarProdutoActivity.this, MainActivity.class);
                    startActivity(intent);

                    Toast.makeText(getApplicationContext(), "Dados excluidos com sucesso!", Toast.LENGTH_LONG).show();

                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), "Erro ao excluir!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });

    }

    private void alterarDados(){
        try {
            cpDescricao = (EditText) findViewById(R.id.cpGeDescricao);
            cpCor = (EditText) findViewById(R.id.cpGeCor);
            cpFabricante = (EditText) findViewById(R.id.cpGeFabricante);

            Produto p = new Produto();
            p.setId(produto.getId());
            p.setDescricao(cpDescricao.getText().toString());
            p.setCor(cpCor.getText().toString());
            p.setFabricante(cpFabricante.getText().toString());

            p.salvar();
            Toast.makeText(getApplicationContext(), "Dados salvos com sucesso!", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(GerenciarProdutoActivity.this, MainActivity.class);
            startActivity(intent);

        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Erro ao salvar!", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }
}
