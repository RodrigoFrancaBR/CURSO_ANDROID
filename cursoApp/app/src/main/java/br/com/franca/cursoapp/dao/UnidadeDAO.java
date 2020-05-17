package br.com.franca.cursoapp.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;
import java.util.List;

import br.com.franca.cursoapp.dbHelper.ConexaoSQLite;
import br.com.franca.cursoapp.domain.Unidade;
import br.com.franca.cursoapp.domain.enun.Status;

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
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return 0;
    }

    public List<Unidade> listar() {
        List<Unidade> listaDeUnidades = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor;
        String query = "SELECT * FROM TB_UNIDADE";

        try {
            db = conexaoSQLite.getReadableDatabase();
            cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    listaDeUnidades.add(
                            new Unidade(cursor.getString(1),
                                    cursor.getString(2),
                                    Status.obterStatusPeloValor(cursor.getString(3)),
                                    cursor.getLong(0)));

                    }
                while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d("ERRO LISTAR UNIDADES", "ERRO NO RETORNO DAS UNIDADES");
            return null;
        } finally {
            if (db != null) {
                db.close();
            }
        }

        return listaDeUnidades;
    }
}
