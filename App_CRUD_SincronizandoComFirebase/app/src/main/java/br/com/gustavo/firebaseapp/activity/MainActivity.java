package br.com.gustavo.firebaseapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import br.com.gustavo.firebaseapp.R;
import br.com.gustavo.firebaseapp.adapter.ProdutosAdapter;
import br.com.gustavo.firebaseapp.config.ConfiguracaoFirebase;
import br.com.gustavo.firebaseapp.model.Produto;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth autenticacao;
    private Toolbar toolbar;
    private ArrayList<Produto> produtos;
    private ArrayAdapter adapter;
    private ListView lista;
    private DatabaseReference databaseReference;
    private ValueEventListener valueEventListener;

    @Override
    protected void onStart() {
        super.onStart();
        // carrega a lista na tela quando a tela e carregada na aplicação
        databaseReference.addValueEventListener(valueEventListener);
        Log.i("valueEventListener","onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        databaseReference.removeEventListener( valueEventListener );
        Log.i("valueEventListener","onStop");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();

        lista = (ListView) findViewById(R.id.listaDeProdutos);

        toolbar = (Toolbar) findViewById(R.id.toolbarDaTela);
        toolbar.setTitle("CRUD Firebase");
        setSupportActionBar(toolbar);

        listarProdutos();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId() ){
            case R.id.itemSair:
                deslogarUsuario();
                return true;
            case R.id.itemAdicionar:
                abrirTelaCadastro();
                return true;
            case R.id.itemDesenvolvedor:
                abrirTelaDetalhes();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void deslogarUsuario(){
        autenticacao.signOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void abrirTelaCadastro(){

        Intent intent = new Intent(MainActivity.this, CadastrarProdutoActivity.class);
        startActivity(intent);

    }

    private void abrirTelaDetalhes(){
        Intent intent = new Intent(MainActivity.this, DetalhesActivity.class);
        startActivity(intent);
    }

    private void listarProdutos(){

        // inicia a lista
        produtos = new ArrayList<>();

        /*adapter = new ArrayAdapter(
                getApplicationContext(),
                R.layout.lista_produtos,
                produtos
        );*/
        adapter = new ProdutosAdapter(getApplicationContext(), produtos);

        lista.setAdapter(adapter);

        databaseReference = ConfiguracaoFirebase.getFirebase()
            .child("produtos");

        // inicia a lista
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // Limpar lista
                produtos.clear(); // para que não replique dados na lista

                // listar produtos
                for(DataSnapshot dados: dataSnapshot.getChildren()){

                    Produto produto = dados.getValue(Produto.class);
                    produtos.add(produto);
                }

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int codPosicao = position;
                Produto produto = (Produto) lista.getItemAtPosition(codPosicao);
                /*Toast.makeText(getApplicationContext(), produto.getDescricao()
                        +" " +produto.getId() + " " + produto.getFabricante()+" " +produto.getCor(), Toast.LENGTH_LONG).show();
                */

                Intent intent = new Intent(MainActivity.this, GerenciarProdutoActivity.class);
                GerenciarProdutoActivity.produto = produto;
                startActivity(intent);

            }
        });

    }

}
