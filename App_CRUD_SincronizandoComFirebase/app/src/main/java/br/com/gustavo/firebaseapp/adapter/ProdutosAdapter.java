package br.com.gustavo.firebaseapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.gustavo.firebaseapp.R;
import br.com.gustavo.firebaseapp.model.Produto;

/**
 * Created by Cliente on 25/09/2017.
 */

public class ProdutosAdapter extends ArrayAdapter<Produto> {

    private ArrayList<Produto> produtos;
    private Context context;

    public ProdutosAdapter(Context c, ArrayList<Produto> objects) {
        super(c, 0, objects);
        this.produtos = objects;
        this.context = c;

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = null;

        // Verificar se tem itens para listar
        if ( produtos != null ){

            // iniciar objeto para montar a view
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            // Monta view a partir do XML
            view = inflater.inflate(R.layout.lista_produtos, parent, false);

            // recuperar elemento para exibição
            TextView descricao = (TextView) view.findViewById(R.id.tv_descricao);
            TextView fabricante = (TextView) view.findViewById(R.id.tv_fabricante);

            Produto produto = produtos.get(position);
            descricao.setText(produto.getDescricao());
            fabricante.setText(produto.getFabricante());
        }

        return view;
    }
}
