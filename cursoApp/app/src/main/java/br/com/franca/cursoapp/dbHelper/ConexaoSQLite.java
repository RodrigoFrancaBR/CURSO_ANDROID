package br.com.franca.cursoapp.dbHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ConexaoSQLite extends SQLiteOpenHelper {

    private static ConexaoSQLite INSTANCIA_CONEXAO;
    private static final int VERSAO_DB = 1;
    private static final String NOME_DB = "db_curso";

    public ConexaoSQLite(Context context) {
        super(context, NOME_DB, null, VERSAO_DB);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlCreateTableUnidade = " CREATE TABLE IF NOT EXISTS TB_UNIDADE" +
                "( " +
                    " id INTEGER, " +
                    " nome TEXT, " +
                    " endereco TEXT, " +
                    " status TEXT , " +
                    " PRIMARY KEY (`id`) " +
                ")";
        db.execSQL(sqlCreateTableUnidade);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public static ConexaoSQLite getInstancia(Context context) {
        if (INSTANCIA_CONEXAO == null) {
            INSTANCIA_CONEXAO = new ConexaoSQLite(context);
        }
        return INSTANCIA_CONEXAO;
    }
}
