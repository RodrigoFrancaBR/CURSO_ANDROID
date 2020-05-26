package br.com.franca.cursoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.com.franca.cursoapp.activities.ActivityTurma;
import br.com.franca.cursoapp.activities.ActivityUnidade;
import br.com.franca.cursoapp.controller.UnidadeController;
import br.com.franca.cursoapp.dbHelper.ConexaoSQLite;
import br.com.franca.cursoapp.domain.Unidade;
import br.com.franca.cursoapp.domain.enun.Status;

public class MainActivity extends AppCompatActivity {
    private Principal bean = new Principal();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Grava uma mensagem no banco de dados
        FirebaseDatabase database = FirebaseDatabase . getInstance ();
        DatabaseReference myRef = database.getReference ( "mensagem" );

        myRef.setValue ( "Olá, mundo!" );


        buscarElementosDaInterface();

        bean.btnCadastrarUnidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ActivityUnidade.class));
            }
        });

        bean.btnCadastrarTurma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ActivityTurma.class));
            }
        });
    }

    private void buscarElementosDaInterface() {
        this.bean.btnCadastrarUnidade = findViewById(R.id.btn_cadastrar_unidade);
        this.bean.btnCadastrarTurma = findViewById(R.id.btn_cadastrar_turma);
    }

    private static class Principal {
        Button btnCadastrarUnidade;
        Button btnCadastrarTurma;
    }
}
