package br.com.franca.cursoapp.controller;

import android.widget.Toast;


import java.util.List;

import br.com.franca.cursoapp.MainActivity;
import br.com.franca.cursoapp.activities.ActivityUnidade;
import br.com.franca.cursoapp.dao.UnidadeDAO;
import br.com.franca.cursoapp.dbHelper.ConexaoSQLite;
import br.com.franca.cursoapp.domain.Unidade;

public class UnidadeController {
    private final UnidadeDAO dao;

    public UnidadeController(ConexaoSQLite conexaoSQLite) {
        dao = new UnidadeDAO(conexaoSQLite);
    }

    public void salvar(Unidade unidade) throws Exception {
        if (unidade == null)
            throw new Exception("entidade_null");

        if (nomeInvalido(unidade.getNome()))
            throw new Exception("nome_invalido");

        if (enderecoInvalido(unidade.getEndereco()))
            throw new Exception("endereco_invalido");

        dao.salvar(unidade);
    }

    public List<Unidade> listar() {
        return dao.listar();
    }

    public void remover(Long id) throws Exception {
        if (id == null)
            throw new Exception("id_null");
        dao.remover(id);
    }


    private boolean nomeInvalido(String nome) {
        return nome == null || nome.trim().equals("") ? true : false;
    }

    private boolean enderecoInvalido(String endereco) {
        return endereco == null || endereco.trim().equals("") ? true : false;
    }


}
