package aula09.lembretes.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import aula09.lembretes.db.LembretesContract.Lembretes;

public class LembretesHelper extends SQLiteOpenHelper {

	/**
	 * Construtor base. Apenas uma referência a um objeto de contexto é
	 * recebido como parâmetro. Os demais parâmetros são obtidos da classe
	 * de contrato LembrestContract.
	 * @param context
	 */
	public LembretesHelper(Context context) {
		/*
		 * O terceiro parâmetro refere-se a um modelo de Cursor, que não será
		 * estudado daqui. Portanto, fornecemos 'null' no lugar desse parâmetro.
		 */
		super(context, LembretesContract.NOME_BANCO, null, LembretesContract.VERSAO_BANCO);
	}

	@Override
	public void onCreate(SQLiteDatabase banco) {
		/**
		 * Dentro do método onCreate devemos executar o código de construção
		 * da tabela, definido na classe de contrato.
		 */
		banco.execSQL(Lembretes.SQL_CRIAR_TABELA_LEMBRETES);
	}

	@Override
	public void onUpgrade(SQLiteDatabase banco, int antigaVersao, int novaVersao) {
		/**
		 * Caso a estrutura do banco de dados venha a ser atualizada,
		 * este método deve cuidar das mudanças e cópias de registros das
		 * tabelas para efetuar uma atualização sem perda de dados.
		 */
	}

}
