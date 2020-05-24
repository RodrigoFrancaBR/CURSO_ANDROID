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

        aguardandoClickBotaoSalvar();

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
                unidadeInterface.posicao = position;

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
                            listarUnidades();
                            executarToast("remover_sucesso");
                            // adapterListaUnidades.remover(position);
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

                        setUnidadeInterface(unidade);

                        /*Bundle bundleDadosUnidade = new Bundle();
                        bundleDadosUnidade.putLong("id_unidade", unidade.getId());
                        bundleDadosUnidade.putString("nome_unidade", unidade.getNome());
                        bundleDadosUnidade.putString("endereco_unidade", unidade.getEndereco());
                        bundleDadosUnidade.putString("status_unidade", unidade.getStatus().getValor());

                        Intent itentEditarUnidade = new Intent(ActivityUnidade.this, ActivityEditarUnidade.class);
                        itentEditarUnidade.putExtras(bundleDadosUnidade);
                        startActivity(itentEditarUnidade);*/
                        dialog.cancel();
                    }
                });

                janelaDeOpcoes.create().show();
            }
        });
    }

    private void setUnidadeInterface(Unidade unidade) {
        unidadeInterface.editId.setText(unidade.getId().toString());
        unidadeInterface.editNome.setText(unidade.getNome());
        unidadeInterface.editEndereco.setText(unidade.getEndereco());
        unidadeInterface.editStatus.setText(unidade.getStatus().getValor());
    }

    private void aguardandoClickBotaoSalvar() {
        unidadeInterface.btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Unidade unidade = obterUnidadeDaInterface();
                System.out.println(unidade.toString());
                // ConexaoSQLite conexaoSQLite = ConexaoSQLite.getInstancia(ActivityUnidade.this);
                // controller = new UnidadeController(conexaoSQLite);
                try {
                    if (unidade.getId() == null) {
                        long id = controller.salvar(unidade);
                        listarUnidades();
                        // unidade.setId(id);
                        // adapterListaUnidades.addItem(unidade);
                        executarToast("salvar_sucesso");
                    } else {
                        controller.atualizar(unidade);
                        listarUnidades();
                        //adapterListaUnidades.remover(unidadeInterface.posicao);
                        //adapterListaUnidades.addItem(unidade);
                        executarToast("atualizar_sucesso");
                    }

                    limparInterface();
                    // listarUnidades();
                } catch (Exception e) {
                    executarToast(e.getMessage());
                }
            }
        });
    }

    private void limparInterface() {
        unidadeInterface.editId.setText("");
        unidadeInterface.editNome.setText("");
        unidadeInterface.editEndereco.setText("");
        unidadeInterface.editStatus.setText("");
    }

    private Unidade obterUnidadeDaInterface() {
        // String resultado = unidadeInterface.editId.getText().toString();
        Long id = null;
        if (!unidadeInterface.editId.getText().toString().equals(""))
            id = Long.valueOf(unidadeInterface.editId.getText().toString());

        return new Unidade(
                unidadeInterface.editNome.getText().toString(),
                unidadeInterface.editEndereco.getText().toString(),
                Status.ATIVA,
                id
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
            case "atualizar_falha":
                Toast.makeText(this, getString(R.string.atualizar_falha), Toast.LENGTH_LONG).show();
                break;
            case "atualizar_sucesso":
                Toast.makeText(this, getString(R.string.atualizar_sucesso), Toast.LENGTH_LONG).show();
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
        unidadeInterface.editId = findViewById(R.id.edit_id);
        unidadeInterface.editNome = findViewById(R.id.edit_nome);
        unidadeInterface.editEndereco = findViewById(R.id.edit_endereco);
        unidadeInterface.editStatus = findViewById(R.id.edit_status);
        unidadeInterface.btnSalvar = findViewById(R.id.btn_salvar);
        unidadeInterface.listViewUnidades = findViewById(R.id.list_view_unidades);
    }

    private static class UnidadeInterface {
        ListView listViewUnidades;
        EditText editId;
        EditText editNome;
        EditText editEndereco;
        EditText editStatus;
        Button btnSalvar;
        Button btnListar;
        int posicao;
    }
}
