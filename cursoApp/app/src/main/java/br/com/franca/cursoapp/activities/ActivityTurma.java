package br.com.franca.cursoapp.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import br.com.franca.cursoapp.R;
import br.com.franca.cursoapp.domain.Turma;

public class ActivityTurma extends AppCompatActivity {
    private TurmaBean bean = new TurmaBean();
    private Turma turma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turma);
    }

    private Turma obterUnidadeDoFormulario() {
        turma = new Turma();

        if (!bean.editNome.getText().toString().isEmpty())
            turma.setNome(bean.editNome.getText().toString());

        return turma;
    }

    private void buscarElementosDaInterface() {
        this.bean.editNome = findViewById(R.id.edit_nome);
        this.bean.btnCadastrar = findViewById(R.id.btn_cadastrar);
    }

    private static class TurmaBean {
        EditText editNome;
        Button btnCadastrar;
    }
}
