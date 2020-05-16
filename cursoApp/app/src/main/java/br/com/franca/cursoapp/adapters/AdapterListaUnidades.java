package br.com.franca.cursoapp.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.franca.cursoapp.R;
import br.com.franca.cursoapp.domain.Unidade;

public class AdapterListaUnidades extends BaseAdapter {

    private LayoutUnidadeBean bean = new LayoutUnidadeBean();
    private Context context;
    private List<Unidade> listaDeUnidades;

    public AdapterListaUnidades(Context context, List<Unidade> listaDeUnidades) {
        this.context = context;
        this.listaDeUnidades = listaDeUnidades;
    }

    @Override
    public int getCount() {
        return listaDeUnidades.size();
    }

    @Override
    public Object getItem(int position) {
        return listaDeUnidades.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void remover(int position) {
        listaDeUnidades.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        View v = View.inflate(this.context, R.layout.layout_unidade, null);

        buscarElementosDaInterface(v);
        bean.textId.setText(Long.toString(listaDeUnidades.get(position).getId()));
        // bean.textId.setText(Long.toString(listaDeUnidades.get(position).getId()));
        bean.textNome.setText(listaDeUnidades.get(position).getNome());
        bean.textEndereco.setText(listaDeUnidades.get(position).getEndereco());
        bean.textStatus.setText(listaDeUnidades.get(position).getStatus().getValor());

        return v;
    }

    private void buscarElementosDaInterface(View v) {
        this.bean.textId = v.findViewById(R.id.text_id);
        this.bean.textNome = v.findViewById(R.id.text_nome);
        this.bean.textEndereco = v.findViewById(R.id.text_endereco);
        this.bean.textStatus = v.findViewById(R.id.text_status);

    }

    private static class LayoutUnidadeBean {
        TextView textId;
        TextView textNome;
        TextView textEndereco;
        TextView textStatus;

    }

}