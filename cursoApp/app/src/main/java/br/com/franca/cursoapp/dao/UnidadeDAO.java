package br.com.franca.cursoapp.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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

    public List<Unidade> findAll() {
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
            Log.d("UnidadeDAO", "List<Unidade> findAll()");
            return null;
        } finally {
            if (db != null) {
                db.close();
            }
        }

        return listaDeUnidades;
    }

    public Unidade findById(Long id) {
        Unidade unidade = null;
        SQLiteDatabase db = null;
        Cursor cursor;
        String query = "SELECT * FROM TB_UNIDADE WHERE ID=?";

        try {
            db = conexaoSQLite.getReadableDatabase();
            cursor = db.rawQuery(query, new String[]{id.toString()});
            if (cursor.moveToFirst()) {
                unidade = new Unidade(cursor.getString(1),
                        cursor.getString(2),
                        Status.obterStatusPeloValor(cursor.getString(3)),
                        cursor.getLong(0));
            }

        } catch (Exception e) {
            Log.d("UnidadeDAO", "findById(Long id)");
            return null;
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return unidade;
    }

    public long save(Unidade unidade) throws Exception {
        SQLiteDatabase db = conexaoSQLite.getWritableDatabase();

        try {
            ContentValues v = new ContentValues();
            v.put("id", unidade.getId());
            v.put("nome", unidade.getNome());
            v.put("endereco", unidade.getEndereco());
            v.put("status", unidade.getStatus().getValor());

            return db.insert("TB_UNIDADE", null, v);

        } catch (Exception e) {
            Log.d("UnidadeDAO", "long salvar(Unidade unidade)");
            throw new Exception("salvar_falha");
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }


    public int update(Unidade unidade) throws Exception {
        SQLiteDatabase db = null;
        try {
            db = conexaoSQLite.getWritableDatabase();
            return db.update("TB_UNIDADE",
                    updateContentValues(unidade),
                    "id=?",
                    new String[]{String.valueOf(unidade.getId())});
        } catch (Exception e) {
            Log.d("UnidadeDAO", "int update(Unidade unidadeEncontrada)");
            throw new Exception("atualizar_falha");
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    private ContentValues updateContentValues(Unidade unidade) {
        ContentValues v = new ContentValues();
        v.put("nome", unidade.getNome());
        v.put("endereco", unidade.getEndereco());
        v.put("status", unidade.getStatus().getValor());
        return v;
    }

    public void delete(Long id) throws Exception {
        SQLiteDatabase db = null;
        try {
            db = conexaoSQLite.getWritableDatabase();
            db.delete("TB_UNIDADE", "id = ?", new String[]{String.valueOf(id)});
        } catch (Exception e) {
            Log.d("UnidadeDAO", "void delete(Long id)");
            throw new Exception("remover_falha");
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }
}
