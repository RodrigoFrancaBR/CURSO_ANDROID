package aula09.lembretes.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import aula09.lembretes.db.LembretesContract.Lembretes;

public class LembretesHelper extends SQLiteOpenHelper {

	/**
	 * Construtor base. Apenas uma refer�ncia a um objeto de contexto �
	 * recebido como par�metro. Os demais par�metros s�o obtidos da classe
	 * de contrato LembrestContract.
	 * @param context
	 */
	public LembretesHelper(Context context) {
		/*
		 * O terceiro par�metro refere-se a um modelo de Cursor, que n�o ser�
		 * estudado daqui. Portanto, fornecemos 'null' no lugar desse par�metro.
		 */
		super(context, LembretesContract.NOME_BANCO, null, LembretesContract.VERSAO_BANCO);
	}

	@Override
	public void onCreate(SQLiteDatabase banco) {
		/**
		 * Dentro do m�todo onCreate devemos executar o c�digo de constru��o
		 * da tabela, definido na classe de contrato.
		 */
		banco.execSQL(Lembretes.SQL_CRIAR_TABELA_LEMBRETES);
	}

	@Override
	public void onUpgrade(SQLiteDatabase banco, int antigaVersao, int novaVersao) {
		/**
		 * Caso a estrutura do banco de dados venha a ser atualizada,
		 * este m�todo deve cuidar das mudan�as e c�pias de registros das
		 * tabelas para efetuar uma atualiza��o sem perda de dados.
		 */
	}

}
