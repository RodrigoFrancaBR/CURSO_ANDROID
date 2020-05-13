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
 *	Esta � a activity usada para exibir os dados de um Lembrete.
 *	A partir dessa tela � poss�vel ir para a activity de edi��o
 *	de lembrete ou efetuar a exclus�o de um lembrete. 
 */
public class ExibirLembrete extends Activity {

	// Identificador do lembrete - Uso na Edi��o
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
		
		// Refer�ncia dos componentes de tela
		tituloLembrete = (TextView) findViewById(R.id.strTituloLembrete);
		descricaoLembrete = (TextView) findViewById(R.id.strDescricaoLembrete);
		
		// Coleta de dados do lembrete injetados na Intent
		Intent intent = getIntent();
		idLembrete = intent.getExtras().getInt("id");
		tituloLembrete.setText(intent.getExtras().getString("titulo"));
		descricaoLembrete.setText(intent.getExtras().getString("descricao"));
		
	}
	
	public void editar(View v){
		// O bot�o Editar leva os dados do Lembrete para serem
		// modificados na activity EditarLembrete
		Intent i = new Intent(ExibirLembrete.this, EditarLembrete.class);
		i.putExtra("id",idLembrete);
		i.putExtra("titulo",tituloLembrete.getText().toString());
		i.putExtra("descricao",descricaoLembrete.getText().toString());
		startActivityForResult(i, ListaDeLembretes.EDITAR_LEMBRETE);
	}
	
	public void excluir(View v){
		// O bot�o Excluir enviar os dados do Lembrete para
		// efetivar a exclus�o na activity ListaDeLembretes
		Intent i = new Intent();
		i.putExtra("acao", ListaDeLembretes.EXCLUIR_LEMBRETE);
		i.putExtra("id", idLembrete);
		i.putExtra("titulo", tituloLembrete.getText().toString());
		setResult(RESULT_OK, i);
		finish();
	}
	
	public void voltar(View v){
		/*
		 * Apenas retorna � activity ListaDeLembretes
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
			 * Lembrete s�o coletados e a opera��o de atualiza��o � realizada
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
		 * Se o bot�o BACK for pressionado, vamos repetir o
		 * funcionamento do bot�o cancelar. 
		 */
		voltar(null);
	}
	
	/**
	 *	Tarefa ass�ncrona especializada na edi��o de lembretes
	 *  no banco de dados da aplica��o
	 */
	private class EditarTask extends AsyncTask<Lembrete, Void, Void>{
		/*
		 * Refer�ncia do Lembrete que ser� editado
		 */
		private Lembrete lembrete;
		
		@Override
		protected void onPreExecute() {
			/*
			 * Exibe o di�logo de progresso enquanto o acesso
			 * ao banco de dados � realizado.
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
			// C�pia do lembrete para refer�ncia local
			lembrete = params[0];			
			// Dispara a edi��o do registro do lembrete
			LembretesDatabase.atualizarLembrete(lembrete);
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			
			// Atualizando os campos da tela
			tituloLembrete.setText(lembrete.getTitulo());
			descricaoLembrete.setText(lembrete.getDescricao());
			
			// Fecha o di�logo de espera
			dialogoEspera.dismiss();
			
			// Exibe a mensagem de resultado
			Toast.makeText( ExibirLembrete.this,
					"Lembrete atualizado com sucesso.",
					Toast.LENGTH_SHORT).show();
			
		}
		
	}
}
