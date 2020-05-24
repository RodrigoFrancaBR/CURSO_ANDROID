package aula09.lembretes.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import aula09.lembretes.Lembrete;

/**
 * Esta classe � respons�vel por executar as opera��es relativas � tabela
 * Lembretes no banco de dados da aplica��o.
 * 
 */
public class LembretesDatabase {

	private static LembretesHelper lembretesHelper;
	private static SQLiteDatabase banco;

	/**
	 * Inicializa os objetos para acesso ao banco de dados.
	 * @param contexto Contexto fornecido pela activity atual
	 */
	public static void inicializar(Context contexto) {
		if (lembretesHelper == null) {
			lembretesHelper = new LembretesHelper(contexto);
		}
	}

	/**
	 * Encerra a sess�o com o banco de dados para o caso da 
	 * aplica��o entrar em segundo plano
	 */
	public static void encerrarSessao() {
		if (banco != null)
			banco.close();
	}

	/**
	 * Insere um novo lembrete no banco de dados
	 * @param lembrete Lembrete que ser� inserido no banco de dados
	 * @return id do novo lembrete inserido no banco de dados
	 */
	public static long inserirLembrete(Lembrete lembrete) {
		// Acesso � grava��o necess�rio
		banco = lembretesHelper.getWritableDatabase();

		// Aqui criamos um mapa chave-valor contendo os nomes das colunas
		// da tabela Lembretes e os respecitvos valores para serem atribu�dos
		// a elas na cria��o do novo registro.
		ContentValues dadosLembrete = new ContentValues();
		dadosLembrete.put(LembretesContract.Lembretes.COLUNA_TITULO,
				lembrete.getTitulo());
		dadosLembrete.put(LembretesContract.Lembretes.COLUNA_DESCRICAO,
				lembrete.getDescricao());

		// Esta � a instru��o de inser��o do registro no banco. O primeiro
		// atributo consiste no nome da tabela e o terceiro � o mapa que cont�m
		// os valores para cada coluna. O segundo par�metro corresponde ao nome
		// de uma coluna que possa receber o valor "NULL", caso essa inser��o
		// seja
		// de um registro em branco.
		long idNovoLembrete = banco.insert(
				LembretesContract.Lembretes.NOME_TABELA,
				LembretesContract.Lembretes.COLUNA_DESCRICAO, dadosLembrete);

		// Devolver o id que o banco gerou para o novo registro
		return idNovoLembrete;
	}

	/**
	 * Atualiza os dados de um lembrete que j� existe no banco de dados
	 * @param lembrete Lembrete que ser� atualizado no banco de dados
	 * @return verdadeiro se a atualiza��o for executada com sucesso, falso caso contr�rio.
	 */
	public static boolean atualizarLembrete(Lembrete lembrete) {

		// Acesso � grava��o necess�rio
		banco = lembretesHelper.getWritableDatabase();

		// New value for one column
		ContentValues dadosLembrete = new ContentValues();
		dadosLembrete.put(LembretesContract.Lembretes.COLUNA_TITULO,
				lembrete.getTitulo());
		dadosLembrete.put(LembretesContract.Lembretes.COLUNA_DESCRICAO,
				lembrete.getDescricao());

		// Condi��o de atualiza��o: ID do registro ser igual
		// ao ID do lembrete que est� sendo modificado.
		String condicao = LembretesContract.Lembretes._ID + " = ?";
		String[] valoresCondicao = { String.valueOf(lembrete.getId()) };

		int registrosAtualizados = banco.update(
				LembretesContract.Lembretes.NOME_TABELA, dadosLembrete,
				condicao, valoresCondicao);

		// Se um registro foi atualizado, devolver true.
		return registrosAtualizados == 1;
	}

	/**
	 * Exclui um lembrete do banco de dados
	 * @param id identificador do lembrete a ser exlcu�do
	 */
	public static void excluirLembrete(int id) {
		// Acesso � grava��o necess�rio
		banco = lembretesHelper.getWritableDatabase();

		// Condi��o de sele��o do(s) registro(s) que ser�(�o) exclu�dos:
		// ID do registro ser igual ao ID de refer�ncia.
		String condicao = LembretesContract.Lembretes._ID + " = ?";
		// Valor de refer�ncia para a condi��o acima
		String[] valoresCondicao = { String.valueOf(id) };
		// Instru��o de exclus�o de registro. O m�todo delete recebe como
		// par�metro o nome da tabela alvo, seguido da lista de condi��es
		// e os valores que devem ser injetados na lista.
		banco.delete(LembretesContract.Lembretes.NOME_TABELA, condicao,
				valoresCondicao);
	}

	/**
	 * Carrega a lista completa de todos os lembretes do banco de dados
	 * @return lista contendo os lembretes registrados no banco de dados
	 */
	public static List<Lembrete> todosLembretes() {
		// Acesso � leitura necess�rio
		banco = lembretesHelper.getReadableDatabase();

		// Lista de colunas que devem ser trazidas na consulta
		String[] colunas = { LembretesContract.Lembretes._ID,
				LembretesContract.Lembretes.COLUNA_TITULO,
				LembretesContract.Lembretes.COLUNA_DESCRICAO};

		// Crit�rio para ordenac�o do resultado
		String criterioOrdenacao = LembretesContract.Lembretes._ID + " DESC";

		/*
		 *  Os par�metros do m�todo query s�o:
		 *  - nome da tabela (String)
		 *  - lista de colunas que devem ser trazidas no resultado (String[])
		 *  - condi��o de sele��o da consulta (String)
		 *  - valores para serem injetados na condi��o de sele��o (String[])
		 *  - campos para agrupamento (String)
		 *  - estart�gia de agrupamento (String)
		 *  - colunas para ordena��o (String)
		 */
		Cursor c = banco.query(LembretesContract.Lembretes.NOME_TABELA,
				colunas, null , null, null, null, criterioOrdenacao );

		List<Lembrete> lembretes = new ArrayList<Lembrete>();

		// Verificando a exit�ncia de resultados
		if (c != null && c.getCount() > 0) {
			// Ir para o primeiro resultado
			c.moveToFirst();

			// Indices das colunas (apenas para organizar o c�digo)
			int indiceId = c.getColumnIndex(
					LembretesContract.Lembretes._ID);
			int indiceTitulo = c.getColumnIndex(
					LembretesContract.Lembretes.COLUNA_TITULO);
			int indiceDescricao = c.getColumnIndex(
					LembretesContract.Lembretes.COLUNA_DESCRICAO);

			// La�o para recuperar cada registro do resultado
			do {
				Lembrete l = new Lembrete(
						c.getInt(indiceId),
						c.getString(indiceTitulo), 
						c.getString(indiceDescricao));
				lembretes.add(l);
			} while (c.moveToNext());
		}
		
		return lembretes;
	}
}