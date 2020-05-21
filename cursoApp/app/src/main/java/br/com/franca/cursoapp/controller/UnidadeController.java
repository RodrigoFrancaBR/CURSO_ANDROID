package br.com.franca.cursoapp.controller;


import android.util.Log;

import java.util.List;

import br.com.franca.cursoapp.dao.UnidadeDAO;
import br.com.franca.cursoapp.dbHelper.ConexaoSQLite;
import br.com.franca.cursoapp.domain.Unidade;

public class UnidadeController {
    private final UnidadeDAO dao;

    public UnidadeController(ConexaoSQLite conexaoSQLite) {
        dao = new UnidadeDAO(conexaoSQLite);
    }

    public long salvar(Unidade unidade) throws Exception {
        if (unidade == null)
            throw new Exception("entidade_null");

        if (nomeInvalido(unidade.getNome()))
            throw new Exception("nome_invalido");

        if (enderecoInvalido(unidade.getEndereco()))
            throw new Exception("endereco_invalido");

        return dao.salvar(unidade);

    }

    public int atualizar(Unidade unidade) throws Exception {
        // Unidade unidadeEncontrada = validarID(unidade.getId());
        return dao.atualizar(unidade);
    }

//    private Unidade buscarPorId(Long id) throws Exception {
//        /*if (id == null)
//            throw new Exception("id_null");*/
//        return validarID(id);
//        return dao.buscarPorId(id);
//    }

    public List<Unidade> listar() {
        return dao.listar();
    }

    public void remover(Long id) throws Exception {
        // validarID(id);
        dao.remover(id);
    }


    private boolean nomeInvalido(String nome) {
        return nome == null || nome.trim().equals("") ? true : false;
    }

    private boolean enderecoInvalido(String endereco) {
        return endereco == null || endereco.trim().equals("") ? true : false;
    }

    private Unidade validarID(Long id) throws Exception {
        if (id == null)
            throw new Exception("id_null");

        // Unidade unidadeEncontrada = buscarPorId(id);
        Unidade unidadeEncontrada = dao.buscarPorId(id);

        if (unidadeEncontrada == null)
            throw new Exception("entidade_nao_encontrada");
        return unidadeEncontrada;
    }

    /*public Unidade alterar(Unidade unidade) throws Exception {
        Log.d("Alterando a unidade", unidade.toString());
        return dao.atualizar(unidade) > 0 ? unidade : null;
    }*/
}
