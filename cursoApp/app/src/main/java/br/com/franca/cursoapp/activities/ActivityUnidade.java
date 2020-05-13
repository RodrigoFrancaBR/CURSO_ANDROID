package br.com.franca.cursoapp.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import br.com.franca.cursoapp.R;
import br.com.franca.cursoapp.controller.UnidadeController;
import br.com.franca.cursoapp.dbHelper.ConexaoSQLite;
import br.com.franca.cursoapp.domain.Unidade;
import br.com.franca.cursoapp.domain.enun.Status;

public class ActivityUnidade extends AppCompatActivity {
    private UnidadeBean bean = new UnidadeBean();
    private Unidade unidade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unidade);

        buscarElementosDaInterface();
        bean.btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Unidade unidade = obterUnidadeDoFormulario();
                ConexaoSQLite conexaoSQLite = ConexaoSQLite.getInstancia(ActivityUnidade.this);
                UnidadeController controller = new UnidadeController(conexaoSQLite);
                try {
                    System.out.println(controller.salvar(unidade));
                } catch (Exception e) {
                    executarToast(e.getMessage());
                }
            }
        });

//        ConexaoSQLite conexaoSQLite = ConexaoSQLite.getInstancia(this);
//        Unidade unidade = new Unidade(9l, "Cascadura", "Rua Quintão 153", Status.ATIVA);
//        UnidadeController controller = new UnidadeController(conexaoSQLite);
//        controller.salvar(unidade);

    }

    private void executarToast(String erro) {
        switch (erro) {
            case "entidade_null":
                Toast.makeText(this, getString(R.string.entidade_null) , Toast.LENGTH_LONG).show();
                break;
            case "nome_invalido":
                Toast.makeText(this, getString(R.string.nome_invalido) , Toast.LENGTH_LONG).show();
                break;
            case "endereco_invalido":
                Toast.makeText(this, getString(R.string.endereco_invalido) , Toast.LENGTH_LONG).show();
                break;
        }

    }

    private Unidade obterUnidadeDoFormulario() {
        return unidade = new Unidade(
                bean.editNome.getText().toString(),
                bean.editEndereco.getText().toString(),
                Status.ATIVA
        );

//        if (!bean.editNome.getText().toString().isEmpty())
//            unidade.setNome(bean.editNome.getText().toString());
//
//        if (!bean.editEndereco.getText().toString().isEmpty())
//            unidade.setEndereco(bean.editEndereco.getText().toString());
    }

    private void buscarElementosDaInterface() {
        this.bean.editNome = findViewById(R.id.edit_nome);
        this.bean.editEndereco = findViewById(R.id.edit_endereco);
        this.bean.btnCadastrar = findViewById(R.id.btn_cadastrar);
    }

    private static class UnidadeBean {
        EditText editNome;
        EditText editEndereco;
        Button btnCadastrar;
    }
}
