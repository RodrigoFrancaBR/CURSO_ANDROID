package aula09.lembretes.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import aula08.lembretes.R;

/**
 *	Esta é a actvitivy usada para criar um novo Lembrete ou
 *	editar um lembrete existente.
 */
public class EditarLembrete extends Activity {

	// Identificador do lembrete - Uso na Edição
	private Integer idLembrete;
	// Campos de texto usados para capturar os dados do Lembrete
	private EditText tituloLembrete;
	private EditText descricaoLembrete;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editar_lembrete);

		// Referência dos componentes de tela
		tituloLembrete = (EditText) findViewById(R.id.tituloLembrete);
		descricaoLembrete = (EditText) findViewById(R.id.descricaoLembrete);

		// Coleta de dados do lembrete injetados na Intent
		Intent intent = getIntent();
		if (intent != null && intent.getExtras() != null
				&& intent.getExtras().size() > 0) {
			idLembrete = intent.getExtras().getInt("id");
			tituloLembrete.setText(intent.getExtras().getString("titulo"));
			descricaoLembrete.setText(intent.getExtras().getString("descricao"));
		}

	}

	public void cancelar(View v) {
		/*
		 * Apenas retorna à activity de Lista de Lembretes
		 */
		Intent i = new Intent();
		setResult(RESULT_CANCELED, i);
		finish();
	}

	public void salvar(View v) {
		/*
		 * Montar Lembrete para ser salva no banco de dados
		 * na activity anterior (ListaDeLmebretes ou ExibirLembrete)
		 */
		Intent i = new Intent();
		if (idLembrete != null) {
			i.putExtra("id", idLembrete);
		}
		i.putExtra("titulo", tituloLembrete.getText().toString());
		i.putExtra("descricao", descricaoLembrete.getText().toString());
		setResult(RESULT_OK, i);
		finish();
	}

	@Override
	public void onBackPressed() {
		/*
		 * Se o botão BACK for pressionado, vamos repetir o
		 * funcionamento do botão cancelar. 
		 */
		cancelar(null);
	}

}
