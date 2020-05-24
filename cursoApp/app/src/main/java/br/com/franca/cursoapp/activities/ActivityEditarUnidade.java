package br.com.franca.cursoapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import br.com.franca.cursoapp.R;
import br.com.franca.cursoapp.domain.Unidade;

public class ActivityEditarUnidade extends AppCompatActivity {

    // classe resp pelo bind da interface
    private UnidadeInterface unidadeInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_unidade);

        buscarElementosDaInterface();

        Bundle bundleDadosUnidade = getIntent().getExtras();

        Unidade unidade = new Unidade(bundleDadosUnidade.getLong("id_unidade"),
                bundleDadosUnidade.getString("nome_unidade"),
                bundleDadosUnidade.getString("endereco_unidade"));

        setUnidadeInterface(unidade);
    }


    public void setUnidadeInterface(Unidade unidade){
        this.unidadeInterface.editNome.setText(unidade.getNome());
        this.unidadeInterface.editEndereco.setText(unidade.getEndereco());
    }

    private void buscarElementosDaInterface() {
        unidadeInterface = new UnidadeInterface();
        unidadeInterface.editNome = findViewById(R.id.edit_nome);
        unidadeInterface.editEndereco = findViewById(R.id.edit_endereco);
        /*unidadeInterface.btnCadastrar = findViewById(R.id.btn_cadastrar);
        unidadeInterface.listViewUnidades = findViewById(R.id.list_view_unidades);*/
    }

    private static class UnidadeInterface {
        ListView listViewUnidades;
        EditText editNome;
        EditText editEndereco;
        Button btnCadastrar;
        Button btnListar;
    }
}
