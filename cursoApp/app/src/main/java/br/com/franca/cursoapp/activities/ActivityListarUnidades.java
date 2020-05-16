package br.com.franca.cursoapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.com.franca.cursoapp.R;
import br.com.franca.cursoapp.adapters.AdapterListaUnidades;
import br.com.franca.cursoapp.domain.Unidade;
import br.com.franca.cursoapp.domain.enun.Status;

public class ActivityListarUnidades extends AppCompatActivity {

    private ListView listViewUnidades;
    private List<Unidade> listaDeUnidades;
    private AdapterListaUnidades adapterListaUnidades;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_unidades);

        listaDeUnidades = new ArrayList<>();
        criarUnidade();
        listViewUnidades = findViewById(R.id.list_view_unidades);
        adapterListaUnidades = new AdapterListaUnidades(ActivityListarUnidades.this, listaDeUnidades);
        listViewUnidades.setAdapter(adapterListaUnidades);
    }

    private void criarUnidade() {
        for (Long i = 0l; i < 10; i++) {
            String nome = "CASCADURA_" + i;
            listaDeUnidades.add(new Unidade(nome + i, "QUINTÃO 153", Status.ATIVA, i));
        }
    }
}
