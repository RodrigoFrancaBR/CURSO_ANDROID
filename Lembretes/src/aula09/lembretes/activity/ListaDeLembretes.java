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
 *	Esta � a activity principal da aplica��o e � respons�vel por
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

		// Refer�ncia dos componentes de tela
		msgListaLembretes = (TextView) findViewById(R.id.msgListaLembretes);
		listaLembretes = (ListView) findViewById(R.id.listaLembretes);
		
		// Lista de lembretes locais, que ser� alimentada pelo banco de dados
		adapter = new LembreteAdapter(ListaDeLembretes.this, new ArrayList<Lembrete>());
		listaLembretes.setAdapter(adapter);
		listaLembretes.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View item,
					int posicao, long id) {
				Intent intent = new Intent(ListaDeLembretes.this,
						ExibirLembrete.class);
				// Quando um lembrete da lista for pressionado, seus dados
				// s�o levados para a activity de exibi��o de lembrete
				Lembrete l = adapter.obterLembrete(posicao);
				intent.putExtra("id", l.getId());
				intent.putExtra("titulo", l.getTitulo());
				intent.putExtra("descricao", l.getDescricao());
				startActivityForResult(intent, EXIBIR_LEMBRETE);
			}

		});

		// Inicializa��o do di�logo de espera que ser� usado em todas as AsyncTasks
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
		// Navegar para a activity EditarLembrete para cria��o de um novo lembrete
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
			msgListaLembretes.setText("Voc� possui " + adapter.getCount()
					+ " lembrete(s)");
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if(resultCode == RESULT_OK){
			// Montagem do lembrete de refer�ncia, para adi��o ou exclus�o
			/** Se o id n�o estiver dispon�vel, o valor null � fornecido : linha 143 */
			Lembrete lembrete = new Lembrete(
					data.getExtras().containsKey("id")?data.getExtras().getInt("id"):null, 
					data.getExtras().getString("titulo"), 
					data.getExtras().getString("descricao"));
			
			if (requestCode == NOVO_LEMBRETE) {
				// Opera��o em segundo plano de adi��o de novo lembrete
				new InserirTask().execute(lembrete);
			} else if (requestCode == EXIBIR_LEMBRETE) {
				if (data.getExtras().getInt("acao") == EXCLUIR_LEMBRETE) {
					// Opera��o em segundo plano de exclus�o do lembrete
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
		 * M�todo auxilar que prov� acesso a itens da lista de Lembretes.
		 * @param posicao indice do item dentro da lista
		 * @return refer�ncia ao Lembrete na posi��o indicada
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
	 * Tarefa ass�ncrona especializada na exclus�o de lembrete
	 * do banco de dados da aplica��o
	 */
	private class ExcluirTask extends AsyncTask<Lembrete, Void, Void> {

		/*
		 * Refer�ncia do Lembrete que ser� exclu�do
		 */
		private Lembrete lembrete;

		@Override
		protected void onPreExecute() {
			/*
			 * Exibe o di�logo de progresso enquanto o acesso
			 * ao banco de dados � realizado.
			 */
			dialogoEspera.setTitle("Excluindo Lembrete");
			dialogoEspera.setMessage("Por favor, aguarde");
			dialogoEspera.show();
		}

		@Override
		protected Void doInBackground(Lembrete... params) {
			// C�pia do lembrete para refer�ncia local
			lembrete = params[0];
			// Dispara a exclus�o do registro do lembrete
			LembretesDatabase.excluirLembrete(lembrete.getId());
			return null;
		}

		@Override
		protected void onPostExecute(Void param) {

			// Atualizando a lista na tela
			adapter.remove(lembrete);
			atualizarMensagemLembretes();

			// Fecha o di�logo de espera
			dialogoEspera.dismiss();

			// Exibe a mensagem de resultado
			Toast.makeText(ListaDeLembretes.this,
					"Lembrete \"" + lembrete.getTitulo() + 
					"\" exclu�do com sucesso.", Toast.LENGTH_LONG)
					.show();
		}

	}

	/**
	 * Tarefa ass�ncrona especializada na cria��o de lembretes
	 * no banco de dados da aplica��o.
	 */
	private class InserirTask extends AsyncTask<Lembrete, Void, Void> {

		/*
		 * Refer�ncia do Lembrete que ser� adicionado ao banco
		 */
		private Lembrete novoLembrete;

		@Override
		protected void onPreExecute() {
			/*
			 * Exibe o di�logo de progresso enquanto o acesso
			 * ao banco de dados � realizado.
			 */
			dialogoEspera.setTitle("Salvando Lembrete");
			dialogoEspera.setMessage("Por favor, aguarde");
			dialogoEspera.show();
		}

		@Override
		protected Void doInBackground(Lembrete... params) {
			// C�pia do lembrete para refer�ncia local
			novoLembrete = params[0];
			// Dispara a adi��o do registro do lembrete
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

			// Fecha o di�logo de espera
			dialogoEspera.dismiss();

			// Exibe a mensagem de resultado
			Toast.makeText(
					ListaDeLembretes.this, "Lembrete \"" + 
					novoLembrete.getTitulo() + "\" adcionado com sucesso.", 
					Toast.LENGTH_LONG).show();
		}

	}

	/**
	 * Tarefa ass�ncrona especializada no carregamento da lista de lembretes
	 * do banco de dados da aplica��o.
	 * 
	 */
	private class CarregarLembretesTask extends AsyncTask<Void, Void, Void> {

		// Lista de lembretes local
		private List<Lembrete> lembretes;

		protected void onPreExecute() {
			/*
			 * Exibe o di�logo de progresso enquanto o acesso
			 * ao banco de dados � realizado.
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

			// Fecha o di�logo de espera
			dialogoEspera.dismiss();
		}

	}

}
