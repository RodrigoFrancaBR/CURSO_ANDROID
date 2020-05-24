package aula09.lembretes.activity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import aula08.lembretes.R;
import aula09.lembretes.Lembrete;
import aula09.lembretes.db.LembretesDatabase;

/**
 *	Esta é a activity principal da aplicação e é responsável por
 *	exibir a lista atual de lembretes gravados no banco de dados.
 */
public class ListaDeLembretes extends Activity {

	// Campo de texto que indica a quantidade de lembretes na lista
	private TextView msgListaLembretes;
	// Lista de lembretes
	private ListView listaLembretes;

	
	// Adaptador do ListView baseado na classe Lembrete
	private LembreteAdapter adapter;
	
	// Dialogo de espera para esconder o processo lento de acesso ao banco
	private ProgressDialog dialogoEspera;

	public static final int NOVO_LEMBRETE = 0;
	public static final int EXIBIR_LEMBRETE = 1;
	public static final int EDITAR_LEMBRETE = 2;
	public static final int EXCLUIR_LEMBRETE = 3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_de_lembretes);

		// Referência dos componentes de tela
		msgListaLembretes = (TextView) findViewById(R.id.msgListaLembretes);
		listaLembretes = (ListView) findViewById(R.id.listaLembretes);
		
		// Lista de lembretes locais, que será alimentada pelo banco de dados
		adapter = new LembreteAdapter(ListaDeLembretes.this, new ArrayList<Lembrete>());
		listaLembretes.setAdapter(adapter);
		listaLembretes.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View item,
					int posicao, long id) {
				Intent intent = new Intent(ListaDeLembretes.this,
						ExibirLembrete.class);
				// Quando um lembrete da lista for pressionado, seus dados
				// são levados para a activity de exibição de lembrete
				Lembrete l = adapter.obterLembrete(posicao);
				intent.putExtra("id", l.getId());
				intent.putExtra("titulo", l.getTitulo());
				intent.putExtra("descricao", l.getDescricao());
				startActivityForResult(intent, EXIBIR_LEMBRETE);
			}

		});

		// Inicialização do diálogo de espera que será usado em todas as AsyncTasks
		dialogoEspera = new ProgressDialog(ListaDeLembretes.this);
		dialogoEspera.setIcon(null);
		dialogoEspera.setCancelable(false);
		dialogoEspera.setIndeterminate(true);

	}

	@Override
	protected void onStart() {
		super.onStart();
		// Na abertura da activity, inicializar o acesso ao banco de dados
		LembretesDatabase.inicializar(getApplicationContext());
	}

	@Override
	protected void onResume() {
		super.onResume();
		// Ao entrar em primeiro plano, a lista de lembretes
		// deve ser recarregada direto do banco de dados
		new CarregarLembretesTask().execute();
	}

	public void criarNovoLembrete(View v) {
		// Navegar para a activity EditarLembrete para criação de um novo lembrete
		Intent intent = new Intent(ListaDeLembretes.this, EditarLembrete.class);
		startActivityForResult(intent, NOVO_LEMBRETE);
	}

	/**
	 * Atualiza a mensagem sobre a quantidade de elementos na lista
	 */
	public void atualizarMensagemLembretes() {
		if (adapter.isEmpty()) {
			msgListaLembretes.setText(R.string.msg_nenhum_lembrete);
		} else {
			msgListaLembretes.setText("Você possui " + adapter.getCount()
					+ " lembrete(s)");
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if(resultCode == RESULT_OK){
			// Montagem do lembrete de referência, para adição ou exclusão
			/** Se o id não estiver disponível, o valor null é fornecido : linha 143 */
			Lembrete lembrete = new Lembrete(
					data.getExtras().containsKey("id")?data.getExtras().getInt("id"):null, 
					data.getExtras().getString("titulo"), 
					data.getExtras().getString("descricao"));
			
			if (requestCode == NOVO_LEMBRETE) {
				// Operação em segundo plano de adição de novo lembrete
				new InserirTask().execute(lembrete);
			} else if (requestCode == EXIBIR_LEMBRETE) {
				if (data.getExtras().getInt("acao") == EXCLUIR_LEMBRETE) {
					// Operação em segundo plano de exclusão do lembrete
					new ExcluirTask().execute(lembrete);
				}
			}
		}

	}

	/**
	 *	Classe adapatora de itens do tipo Lembrete para ListView
	 */
	private class LembreteAdapter extends ArrayAdapter<Lembrete> {

		// Contexto
		private final Activity context;
		// Lista de elementos
		private final ArrayList<Lembrete> elementos;

		public LembreteAdapter(Activity context, ArrayList<Lembrete> elementos) {
			super(context, R.layout.item_lembretes_layout, elementos);
			this.context = context;
			this.elementos = elementos;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			LayoutInflater inflater = context.getLayoutInflater();
			View rowView = inflater.inflate(R.layout.item_lembretes_layout,
					null, true);
			TextView textView = (TextView) rowView
					.findViewById(R.id.tituloLembreteLista);
			textView.setText(elementos.get(position).getTitulo());

			return rowView;

		}

		@Override
		public void add(Lembrete novoElemento) {
			elementos.add(novoElemento);
			notifyDataSetChanged();
		}

		@Override
		public void addAll(Collection<? extends Lembrete> collection) {
			for (Lembrete lembrete : collection) {
				elementos.add(lembrete);
			}
			notifyDataSetChanged();
		}

		/**
		 * Método auxilar que provê acesso a itens da lista de Lembretes.
		 * @param posicao indice do item dentro da lista
		 * @return referência ao Lembrete na posição indicada
		 */
		public Lembrete obterLembrete(int posicao) {
			if (posicao >= 0 && posicao < elementos.size())
				return elementos.get(posicao);
			return null;
		}

		@Override
		public void remove(Lembrete lembrete) {
			for (int i = 0; i < elementos.size(); i++) {
				if (elementos.get(i).getId() == lembrete.getId()) {
					elementos.remove(i);
					break;
				}
			}
			notifyDataSetChanged();
		}
	}

	/**
	 * Tarefa assíncrona especializada na exclusão de lembrete
	 * do banco de dados da aplicação
	 */
	private class ExcluirTask extends AsyncTask<Lembrete, Void, Void> {

		/*
		 * Referência do Lembrete que será excluído
		 */
		private Lembrete lembrete;

		@Override
		protected void onPreExecute() {
			/*
			 * Exibe o diálogo de progresso enquanto o acesso
			 * ao banco de dados é realizado.
			 */
			dialogoEspera.setTitle("Excluindo Lembrete");
			dialogoEspera.setMessage("Por favor, aguarde");
			dialogoEspera.show();
		}

		@Override
		protected Void doInBackground(Lembrete... params) {
			// Cópia do lembrete para referência local
			lembrete = params[0];
			// Dispara a exclusão do registro do lembrete
			LembretesDatabase.excluirLembrete(lembrete.getId());
			return null;
		}

		@Override
		protected void onPostExecute(Void param) {

			// Atualizando a lista na tela
			adapter.remove(lembrete);
			atualizarMensagemLembretes();

			// Fecha o diálogo de espera
			dialogoEspera.dismiss();

			// Exibe a mensagem de resultado
			Toast.makeText(ListaDeLembretes.this,
					"Lembrete \"" + lembrete.getTitulo() + 
					"\" excluído com sucesso.", Toast.LENGTH_LONG)
					.show();
		}

	}

	/**
	 * Tarefa assíncrona especializada na criação de lembretes
	 * no banco de dados da aplicação.
	 */
	private class InserirTask extends AsyncTask<Lembrete, Void, Void> {

		/*
		 * Referência do Lembrete que será adicionado ao banco
		 */
		private Lembrete novoLembrete;

		@Override
		protected void onPreExecute() {
			/*
			 * Exibe o diálogo de progresso enquanto o acesso
			 * ao banco de dados é realizado.
			 */
			dialogoEspera.setTitle("Salvando Lembrete");
			dialogoEspera.setMessage("Por favor, aguarde");
			dialogoEspera.show();
		}

		@Override
		protected Void doInBackground(Lembrete... params) {
			// Cópia do lembrete para referência local
			novoLembrete = params[0];
			// Dispara a adição do registro do lembrete
			long idNovoLembrete = LembretesDatabase
					.inserirLembrete(novoLembrete);
			novoLembrete.setId((int) idNovoLembrete);

			return null;
		}

		@Override
		protected void onPostExecute(Void param) {

			// Atualizando a lista da tela
			adapter.add(novoLembrete);
			atualizarMensagemLembretes();

			// Fecha o diálogo de espera
			dialogoEspera.dismiss();

			// Exibe a mensagem de resultado
			Toast.makeText(
					ListaDeLembretes.this, "Lembrete \"" + 
					novoLembrete.getTitulo() + "\" adcionado com sucesso.", 
					Toast.LENGTH_LONG).show();
		}

	}

	/**
	 * Tarefa assíncrona especializada no carregamento da lista de lembretes
	 * do banco de dados da aplicação.
	 * 
	 */
	private class CarregarLembretesTask extends AsyncTask<Void, Void, Void> {

		// Lista de lembretes local
		private List<Lembrete> lembretes;

		protected void onPreExecute() {
			/*
			 * Exibe o diálogo de progresso enquanto o acesso
			 * ao banco de dados é realizado.
			 */
			dialogoEspera.setTitle("Carregando Lembretes");
			dialogoEspera.setMessage("Por favor, aguarde");
			dialogoEspera.show();
		}

		@Override
		protected Void doInBackground(Void... param) {
			// Dispara a busca por todos os registro de lembretes
			lembretes = LembretesDatabase.todosLembretes();
			return null;

		}

		@Override
		protected void onPostExecute(Void result) {

			// Atualizando a lista da tela
			adapter.clear();
			adapter.addAll(lembretes);
			atualizarMensagemLembretes();

			// Fecha o diálogo de espera
			dialogoEspera.dismiss();
		}

	}

}
