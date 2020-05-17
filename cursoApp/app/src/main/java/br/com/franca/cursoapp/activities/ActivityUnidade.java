package br.com.franca.cursoapp.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import br.com.franca.cursoapp.R;
import br.com.franca.cursoapp.adapters.AdapterListaUnidades;
import br.com.franca.cursoapp.controller.UnidadeController;
import br.com.franca.cursoapp.dbHelper.ConexaoSQLite;
import br.com.franca.cursoapp.domain.Unidade;
import br.com.franca.cursoapp.domain.enun.Status;

public class ActivityUnidade extends AppCompatActivity {
    // classe resp pelo bind da interface
    private UnidadeInterface unidadeInterface;

    // private Unidade unidade;
    private UnidadeController controller;
    private AdapterListaUnidades adapterListaUnidades;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unidade);

        buscarElementosDaInterface();

        // Abre conexao com o banco;
        ConexaoSQLite conexaoSQLite = ConexaoSQLite.getInstancia(ActivityUnidade.this);

        controller = new UnidadeController(conexaoSQLite);

        listarUnidades();

        aguardandoClickBotaoCadastrar();

        aguardandoSelecionarItemDaLista();

       /* bean.btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Unidade unidade = obterUnidadeDoFormulario();
                ConexaoSQLite conexaoSQLite = ConexaoSQLite.getInstancia(ActivityUnidade.this);
                // controller = new UnidadeController(conexaoSQLite);
                try {
                    controller.salvar(unidade);
                    executarToast("salvar_sucesso");
                    listarUnidades();
                } catch (Exception e) {
                    executarToast(e.getMessage());
                }
            }
        });*/


        // selecionar um item na lista de unidades
       /* unidadeInterface.listViewUnidades.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                final Unidade unidade = (Unidade) adapterListaUnidades.getItem(position);

                AlertDialog.Builder janelaDeOpcoes = new AlertDialog.Builder(ActivityUnidade.this);
                janelaDeOpcoes.setTitle("Opções:");
                janelaDeOpcoes.setMessage("O que fazer com a Entidade: " + unidade.getNome());

                janelaDeOpcoes.setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

                janelaDeOpcoes.setNegativeButton("Remover", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        try {
                            controller.remover(unidade.getId());
                            executarToast("remover_sucesso");
                            adapterListaUnidades.remover(position);
                        } catch (Exception e) {
                            executarToast(e.getMessage());
                        } finally {
                            dialog.cancel();
                        }
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
        });*/

    }

    private void aguardandoSelecionarItemDaLista() {
        unidadeInterface.listViewUnidades.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                final Unidade unidade = (Unidade) adapterListaUnidades.getItem(position);

                AlertDialog.Builder janelaDeOpcoes = new AlertDialog.Builder(ActivityUnidade.this);
                janelaDeOpcoes.setTitle("Opções:");
                janelaDeOpcoes.setMessage("O que fazer com a Entidade: " + unidade.getNome());

                janelaDeOpcoes.setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

                janelaDeOpcoes.setNegativeButton("Remover", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        try {
                            controller.remover(unidade.getId());
                            executarToast("remover_sucesso");
                            adapterListaUnidades.remover(position);
                        } catch (Exception e) {
                            executarToast(e.getMessage());
                        } finally {
                            dialog.cancel();
                        }
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
    }

    private void aguardandoClickBotaoCadastrar() {
        unidadeInterface.btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Unidade unidade = obterUnidadeDaInterface();
                // ConexaoSQLite conexaoSQLite = ConexaoSQLite.getInstancia(ActivityUnidade.this);
                // controller = new UnidadeController(conexaoSQLite);
                try {
                    Long id = controller.salvar(unidade);
                    executarToast("salvar_sucesso");
                    unidade.setId(id);
                    adapterListaUnidades.addItem(unidade);
                    limparInterface();
                    // listarUnidades();
                } catch (Exception e) {
                    executarToast(e.getMessage());
                }
            }
        });
    }

    private void limparInterface() {
        unidadeInterface.editNome.setText("");
        unidadeInterface.editEndereco.setText("");
    }

    private Unidade obterUnidadeDaInterface() {
        return new Unidade(
                unidadeInterface.editNome.getText().toString(),
                unidadeInterface.editEndereco.getText().toString(),
                Status.ATIVA,
                null
        );
    }

    private void listarUnidades() {
        List<Unidade> listaDeUnidades = controller.listar();
        adapterListaUnidades = new AdapterListaUnidades(ActivityUnidade.this, listaDeUnidades);
        unidadeInterface.listViewUnidades.setAdapter(adapterListaUnidades);
    }

    private void executarToast(String msg) {
        switch (msg) {
            case "entidade_null":
                Toast.makeText(this, getString(R.string.entidade_null), Toast.LENGTH_LONG).show();
                break;
            case "nome_invalido":
                Toast.makeText(this, getString(R.string.nome_invalido), Toast.LENGTH_LONG).show();
                break;
            case "endereco_invalido":
                Toast.makeText(this, getString(R.string.endereco_invalido), Toast.LENGTH_LONG).show();
                break;
            case "id_null":
                Toast.makeText(this, getString(R.string.id_null), Toast.LENGTH_LONG).show();
                break;
            case "salvar_falha":
                Toast.makeText(this, getString(R.string.salvar_falha), Toast.LENGTH_LONG).show();
                break;
            case "salvar_sucesso":
                Toast.makeText(this, getString(R.string.salvar_sucesso), Toast.LENGTH_LONG).show();
                break;
            case "remover_falha":
                Toast.makeText(this, getString(R.string.remover_falha), Toast.LENGTH_LONG).show();
                break;
            case "remover_sucesso":
                Toast.makeText(this, getString(R.string.remover_sucesso), Toast.LENGTH_LONG).show();
                break;
        }

    }

    private void buscarElementosDaInterface() {
        unidadeInterface = new UnidadeInterface();
        unidadeInterface.editNome = findViewById(R.id.edit_nome);
        unidadeInterface.editEndereco = findViewById(R.id.edit_endereco);
        unidadeInterface.btnCadastrar = findViewById(R.id.btn_cadastrar);
        unidadeInterface.listViewUnidades = findViewById(R.id.list_view_unidades);
    }

    private static class UnidadeInterface {
        ListView listViewUnidades;
        EditText editNome;
        EditText editEndereco;
        Button btnCadastrar;
        Button btnListar;
    }
}
