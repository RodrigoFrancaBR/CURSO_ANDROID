package br.com.franca.cursoapp.dao;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import br.com.franca.cursoapp.dbHelper.ConexaoSQLite;
import br.com.franca.cursoapp.domain.Unidade;

public class UnidadeDAO {
    private final ConexaoSQLite conexaoSQLite;

    public UnidadeDAO(ConexaoSQLite conexaoSQLite) {
        this.conexaoSQLite = conexaoSQLite;
    }

    public long salvar(Unidade unidade) {
        SQLiteDatabase db = conexaoSQLite.getWritableDatabase();

        try {
            ContentValues v = new ContentValues();
            v.put("id", unidade.getId());
            v.put("nome", unidade.getNome());
            v.put("endereco", unidade.getEndereco());
            v.put("status", unidade.getStatus().getValor());
            return db.insert("TB_UNIDADE", null, v);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
