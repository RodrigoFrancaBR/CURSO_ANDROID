package aula09.lembretes.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import aula08.lembretes.R;
import aula09.lembretes.Lembrete;
import aula09.lembretes.db.LembretesDatabase;
/**
 *	Esta é a activity usada para exibir os dados de um Lembrete.
 *	A partir dessa tela é possível ir para a activity de edição
 *	de lembrete ou efetuar a exclusão de um lembrete. 
 */
public class ExibirLembrete extends Activity {

	// Identificador do lembrete - Uso na Edição
	private Integer idLembrete;
	// Campos de texto usados para exibir os dados do Lembrete
	private TextView tituloLembrete;
	private TextView descricaoLembrete;
	// Dialogo de espera para esconder o processo lento de acesso ao banco
	private ProgressDialog dialogoEspera;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exibir_lembrete);
		
		// Referência dos componentes de tela
		tituloLembrete = (TextView) findViewById(R.id.strTituloLembrete);
		descricaoLembrete = (TextView) findViewById(R.id.strDescricaoLembrete);
		
		// Coleta de dados do lembrete injetados na Intent
		Intent intent = getIntent();
		idLembrete = intent.getExtras().getInt("id");
		tituloLembrete.setText(intent.getExtras().getString("titulo"));
		descricaoLembrete.setText(intent.getExtras().getString("descricao"));
		
	}
	
	public void editar(View v){
		// O botão Editar leva os dados do Lembrete para serem
		// modificados na activity EditarLembrete
		Intent i = new Intent(ExibirLembrete.this, EditarLembrete.class);
		i.putExtra("id",idLembrete);
		i.putExtra("titulo",tituloLembrete.getText().toString());
		i.putExtra("descricao",descricaoLembrete.getText().toString());
		startActivityForResult(i, ListaDeLembretes.EDITAR_LEMBRETE);
	}
	
	public void excluir(View v){
		// O botão Excluir enviar os dados do Lembrete para
		// efetivar a exclusão na activity ListaDeLembretes
		Intent i = new Intent();
		i.putExtra("acao", ListaDeLembretes.EXCLUIR_LEMBRETE);
		i.putExtra("id", idLembrete);
		i.putExtra("titulo", tituloLembrete.getText().toString());
		setResult(RESULT_OK, i);
		finish();
	}
	
	public void voltar(View v){
		/*
		 * Apenas retorna à activity ListaDeLembretes
		 */
		Intent i = new Intent();
		setResult(RESULT_CANCELED, i);
		finish();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == RESULT_OK){
			/*
			 * Ao receber o retorno da activity EditarLembrete, os dados do
			 * Lembrete são coletados e a operação de atualização é realizada
			 * nessa activity
			 * 
			 */
			Lembrete lembrete = new Lembrete(
					data.getExtras().getInt("id"),
					data.getExtras().getString("titulo"), 
					data.getExtras().getString("descricao"));
			new EditarTask().execute(lembrete);
		}		
	}

	@Override
	public void onBackPressed() {
		/*
		 * Se o botão BACK for pressionado, vamos repetir o
		 * funcionamento do botão cancelar. 
		 */
		voltar(null);
	}
	
	/**
	 *	Tarefa assíncrona especializada na edição de lembretes
	 *  no banco de dados da aplicação
	 */
	private class EditarTask extends AsyncTask<Lembrete, Void, Void>{
		/*
		 * Referência do Lembrete que será editado
		 */
		private Lembrete lembrete;
		
		@Override
		protected void onPreExecute() {
			/*
			 * Exibe o diálogo de progresso enquanto o acesso
			 * ao banco de dados é realizado.
			 */
			dialogoEspera = new ProgressDialog(ExibirLembrete.this);
			dialogoEspera.setTitle("Atualizando Lembrete");
			dialogoEspera.setMessage("Por favor, aguarde");
			dialogoEspera.setCancelable(false);
			dialogoEspera.setIndeterminate(true);
			dialogoEspera.show();
		}
		
		@Override
		protected Void doInBackground(Lembrete... params) {
			// Cópia do lembrete para referência local
			lembrete = params[0];			
			// Dispara a edição do registro do lembrete
			LembretesDatabase.atualizarLembrete(lembrete);
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			
			// Atualizando os campos da tela
			tituloLembrete.setText(lembrete.getTitulo());
			descricaoLembrete.setText(lembrete.getDescricao());
			
			// Fecha o diálogo de espera
			dialogoEspera.dismiss();
			
			// Exibe a mensagem de resultado
			Toast.makeText( ExibirLembrete.this,
					"Lembrete atualizado com sucesso.",
					Toast.LENGTH_SHORT).show();
			
		}
		
	}
}
