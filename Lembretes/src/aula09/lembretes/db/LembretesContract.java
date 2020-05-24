package aula09.lembretes.db;

import android.provider.BaseColumns;

public class LembretesContract {
	
	// Informações Gerais do banco
	public static final String NOME_BANCO = "lembretes.db";
	public static final int VERSAO_BANCO = 1;
	
	// Informações da tabela 'lembretes'
	public static abstract class Lembretes implements BaseColumns{
		
		// Colunas da tabela 'lembretes'
		public static final String NOME_TABELA = "lembretes";
		public static final String COLUNA_TITULO = "titulo";
		public static final String COLUNA_DESCRICAO = "descricao";
		
		// Query de criação da tabela
		public static final String SQL_CRIAR_TABELA_LEMBRETES = 
				"CREATE TABLE " + NOME_TABELA + "(" +
				Lembretes._ID + " INTEGER PRIMARY KEY, " +
				Lembretes.COLUNA_TITULO + " TEXT, " +
				Lembretes.COLUNA_DESCRICAO + " TEXT " + ")";
		
	}

}
