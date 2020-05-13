package aula09.lembretes.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import aula09.lembretes.Lembrete;

/**
 * Esta classe é responsável por executar as operações relativas à tabela
 * Lembretes no banco de dados da aplicação.
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
	 * Encerra a sessão com o banco de dados para o caso da 
	 * aplicação entrar em segundo plano
	 */
	public static void encerrarSessao() {
		if (banco != null)
			banco.close();
	}

	/**
	 * Insere um novo lembrete no banco de dados
	 * @param lembrete Lembrete que será inserido no banco de dados
	 * @return id do novo lembrete inserido no banco de dados
	 */
	public static long inserirLembrete(Lembrete lembrete) {
		// Acesso à gravação necessário
		banco = lembretesHelper.getWritableDatabase();

		// Aqui criamos um mapa chave-valor contendo os nomes das colunas
		// da tabela Lembretes e os respecitvos valores para serem atribuídos
		// a elas na criação do novo registro.
		ContentValues dadosLembrete = new ContentValues();
		dadosLembrete.put(LembretesContract.Lembretes.COLUNA_TITULO,
				lembrete.getTitulo());
		dadosLembrete.put(LembretesContract.Lembretes.COLUNA_DESCRICAO,
				lembrete.getDescricao());

		// Esta é a instrução de inserção do registro no banco. O primeiro
		// atributo consiste no nome da tabela e o terceiro é o mapa que contém
		// os valores para cada coluna. O segundo parâmetro corresponde ao nome
		// de uma coluna que possa receber o valor "NULL", caso essa inserção
		// seja
		// de um registro em branco.
		long idNovoLembrete = banco.insert(
				LembretesContract.Lembretes.NOME_TABELA,
				LembretesContract.Lembretes.COLUNA_DESCRICAO, dadosLembrete);

		// Devolver o id que o banco gerou para o novo registro
		return idNovoLembrete;
	}

	/**
	 * Atualiza os dados de um lembrete que já existe no banco de dados
	 * @param lembrete Lembrete que será atualizado no banco de dados
	 * @return verdadeiro se a atualização for executada com sucesso, falso caso contrário.
	 */
	public static boolean atualizarLembrete(Lembrete lembrete) {

		// Acesso à gravação necessário
		banco = lembretesHelper.getWritableDatabase();

		// New value for one column
		ContentValues dadosLembrete = new ContentValues();
		dadosLembrete.put(LembretesContract.Lembretes.COLUNA_TITULO,
				lembrete.getTitulo());
		dadosLembrete.put(LembretesContract.Lembretes.COLUNA_DESCRICAO,
				lembrete.getDescricao());

		// Condição de atualização: ID do registro ser igual
		// ao ID do lembrete que está sendo modificado.
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
	 * @param id identificador do lembrete a ser exlcuído
	 */
	public static void excluirLembrete(int id) {
		// Acesso à gravação necessário
		banco = lembretesHelper.getWritableDatabase();

		// Condição de seleção do(s) registro(s) que será(ão) excluídos:
		// ID do registro ser igual ao ID de referência.
		String condicao = LembretesContract.Lembretes._ID + " = ?";
		// Valor de referência para a condição acima
		String[] valoresCondicao = { String.valueOf(id) };
		// Instrução de exclusão de registro. O método delete recebe como
		// parâmetro o nome da tabela alvo, seguido da lista de condições
		// e os valores que devem ser injetados na lista.
		banco.delete(LembretesContract.Lembretes.NOME_TABELA, condicao,
				valoresCondicao);
	}

	/**
	 * Carrega a lista completa de todos os lembretes do banco de dados
	 * @return lista contendo os lembretes registrados no banco de dados
	 */
	public static List<Lembrete> todosLembretes() {
		// Acesso à leitura necessário
		banco = lembretesHelper.getReadableDatabase();

		// Lista de colunas que devem ser trazidas na consulta
		String[] colunas = { LembretesContract.Lembretes._ID,
				LembretesContract.Lembretes.COLUNA_TITULO,
				LembretesContract.Lembretes.COLUNA_DESCRICAO};

		// Critério para ordenacão do resultado
		String criterioOrdenacao = LembretesContract.Lembretes._ID + " DESC";

		/*
		 *  Os parâmetros do método query são:
		 *  - nome da tabela (String)
		 *  - lista de colunas que devem ser trazidas no resultado (String[])
		 *  - condição de seleção da consulta (String)
		 *  - valores para serem injetados na condição de seleção (String[])
		 *  - campos para agrupamento (String)
		 *  - estartégia de agrupamento (String)
		 *  - colunas para ordenação (String)
		 */
		Cursor c = banco.query(LembretesContract.Lembretes.NOME_TABELA,
				colunas, null , null, null, null, criterioOrdenacao );

		List<Lembrete> lembretes = new ArrayList<Lembrete>();

		// Verificando a exitência de resultados
		if (c != null && c.getCount() > 0) {
			// Ir para o primeiro resultado
			c.moveToFirst();

			// Indices das colunas (apenas para organizar o código)
			int indiceId = c.getColumnIndex(
					LembretesContract.Lembretes._ID);
			int indiceTitulo = c.getColumnIndex(
					LembretesContract.Lembretes.COLUNA_TITULO);
			int indiceDescricao = c.getColumnIndex(
					LembretesContract.Lembretes.COLUNA_DESCRICAO);

			// Laço para recuperar cada registro do resultado
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