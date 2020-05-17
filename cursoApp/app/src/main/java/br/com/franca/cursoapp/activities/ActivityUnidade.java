package br.com.franca.cursoapp.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import br.com.franca.cursoapp.R;
import br.com.franca.cursoapp.adapters.AdapterListaUnidades;
import br.com.franca.cursoapp.controller.UnidadeController;
import br.com.franca.cursoapp.dbHelper.ConexaoSQLite;
import br.com.franca.cursoapp.domain.Unidade;
import br.com.franca.cursoapp.domain.enun.Status;

public class ActivityUnidade extends AppCompatActivity {
    private UnidadeBean bean = new UnidadeBean();
    private UnidadeController controller;
    private Unidade unidade;
    private List<Unidade> listaDeUnidades = new ArrayList<>();
    private AdapterListaUnidades adapterListaUnidades;

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
                controller = new UnidadeController(conexaoSQLite);
                try {
                    // long resultado = 0;
                    // resultado = controller.salvar(unidade);
                    // System.out.println(resultado);
                    if (controller.salvar(unidade) > 0)
                        // unidade.setId(resultado);
                        // listaDeUnidades.add(unidade);
                        listarUnidades();
                } catch (Exception e) {
                    executarToast(e.getMessage());
                }
            }
        });

        bean.listViewUnidades.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Unidade unidade = (Unidade) adapterListaUnidades.getItem(position);

                // Toast.makeText(ActivityUnidade.this, "Unidade: " + unidade.getNome(), Toast.LENGTH_SHORT).show();

                AlertDialog.Builder janelaDeOpcoes = new AlertDialog.Builder(ActivityUnidade.this);
                janelaDeOpcoes.setTitle("Opções:");
                janelaDeOpcoes.setMessage("Escolha uma das opções: ");

                janelaDeOpcoes.setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

                janelaDeOpcoes.setNegativeButton("Remover", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

                janelaDeOpcoes.setPositiveButton("Editar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

                janelaDeOpcoes.create().show();
            }
        });

       /* bean.btnListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // startActivity(new Intent(ActivityUnidade.this, ActivityListarUnidades.class));

                criarUnidade(listaDeUnidades);
                AdapterListaUnidades adapterListaUnidades = new AdapterListaUnidades(ActivityUnidade.this, listaDeUnidades);
                bean.listViewUnidades.setAdapter(adapterListaUnidades);
            }
        });*/

//        ConexaoSQLite conexaoSQLite = ConexaoSQLite.getInstancia(this);
//        Unidade unidade = new Unidade(9l, "Cascadura", "Rua Quintão 153", Status.ATIVA);
//        UnidadeController controller = new UnidadeController(conexaoSQLite);
//        controller.salvar(unidade);

    }

    private void criarUnidade(List<Unidade> listaDeUnidades) {
        for (Long i = 0l; i < 10; i++) {
            String nome = "CASCADURA_" + i;
            listaDeUnidades.add(new Unidade(nome + i, "QUINTÃO 153", Status.ATIVA, i));
        }
    }

    private void executarToast(String erro) {
        switch (erro) {
            case "entidade_null":
                Toast.makeText(this, getString(R.string.entidade_null), Toast.LENGTH_LONG).show();
                break;
            case "nome_invalido":
                Toast.makeText(this, getString(R.string.nome_invalido), Toast.LENGTH_LONG).show();
                break;
            case "endereco_invalido":
                Toast.makeText(this, getString(R.string.endereco_invalido), Toast.LENGTH_LONG).show();
                break;
        }

    }

    private void listarUnidades() {
        listaDeUnidades = controller.listar();
        adapterListaUnidades = new AdapterListaUnidades(ActivityUnidade.this, listaDeUnidades);
        bean.listViewUnidades.setAdapter(adapterListaUnidades);
   /*     bean.listViewUnidades.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Unidade unidade = (Unidade) adapterListaUnidades.getItem(position);

                Toast.makeText(ActivityUnidade.this, "Unidade: " + unidade.getNome(), Toast.LENGTH_SHORT).show();

            }
        });*/
    }

    private Unidade obterUnidadeDoFormulario() {
        return unidade = new Unidade(
                bean.editNome.getText().toString(),
                bean.editEndereco.getText().toString(),
                Status.ATIVA,
                null
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
        // this.bean.btnListar = findViewById(R.id.btn_listar);
        this.bean.listViewUnidades = findViewById(R.id.list_view_unidades);
    }

    private static class UnidadeBean {
        ListView listViewUnidades;
        EditText editNome;
        EditText editEndereco;
        Button btnCadastrar;
        Button btnListar;
    }
}
