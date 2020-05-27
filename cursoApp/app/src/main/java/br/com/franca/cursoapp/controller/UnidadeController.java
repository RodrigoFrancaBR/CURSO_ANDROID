package br.com.franca.cursoapp.controller;

import java.util.List;

import br.com.franca.cursoapp.dao.UnidadeDAO;
import br.com.franca.cursoapp.dbHelper.ConexaoSQLite;
import br.com.franca.cursoapp.domain.Unidade;

public class UnidadeController {
    private final UnidadeDAO dao;

    public UnidadeController(ConexaoSQLite conexaoSQLite) {
        dao = new UnidadeDAO(conexaoSQLite);
    }

    public List<Unidade> findAll() {
        return dao.findAll();
    }

    public Unidade findById(Long id) throws Exception {

        if (id == null)
            throw new Exception("id_null");

        return dao.findById(id);
    }

    public long save(Unidade unidade) throws Exception {
        if (unidade == null)
            throw new Exception("entidade_null");

        if (nomeInvalido(unidade.getNome()))
            throw new Exception("nome_invalido");

        if (enderecoInvalido(unidade.getEndereco()))
            throw new Exception("endereco_invalido");

        return dao.save(unidade);

    }

    public int update(Unidade unidade) throws Exception {

        if (unidade == null)
            throw new Exception("entidade_null");

        Unidade unidadeEncontrada = null;

        unidadeEncontrada = findById(unidade.getId());

        if (unidadeEncontrada == null)
            throw new Exception("entidade_nao_encontrada");

        return dao.update(unidade);
    }

    public void delete(Long id) throws Exception {
        Unidade unidadeEncontrada = null;

        unidadeEncontrada = findById(id);

        if (unidadeEncontrada == null)
            throw new Exception("entidade_nao_encontrada");
        dao.delete(id);
    }

    private boolean nomeInvalido(String nome) {
        return nome == null || nome.trim().equals("") ? true : false;
    }

    private boolean enderecoInvalido(String endereco) {
        return endereco == null || endereco.trim().equals("") ? true : false;
    }

}
