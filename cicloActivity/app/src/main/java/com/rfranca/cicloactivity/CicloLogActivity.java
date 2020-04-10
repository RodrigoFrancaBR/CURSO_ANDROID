package com.rfranca.cicloactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class CicloLogActivity extends AppCompatActivity {

    private static final String Ciclo = "Ciclo";

    /**
     * Chamado quando a Activity é criado
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ciclo_log);
        Log.i(Ciclo, "Metodo onCreate chamado!");
    }

    /**
     * Chamado quando a Activity está prestes a se tornar visível.
     */
    @Override
    protected void onStart() {
        super.onStart();
        Log.i(Ciclo, "Metodo onStart chamado!");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(Ciclo, "Metodo onRestart chamado!");
    }

    /**
     * Chamado quando a Activity já se tornou visível.
     */
    @Override
    protected void onResume() {
        super.onResume();
        Log.i(Ciclo, "Metodo onResume chamado!");
    }

    /**
     * Chamado quando outra  Activity está tomando o foco.
     */
    @Override
    protected void onPause() {
        super.onPause();
        Log.i(Ciclo, "Metodo onPause chamado!");
    }

    /**
     * Chamado quando a Activity não está mais visível.
     */
    @Override
    protected void onStop() {
        super.onStop();
        Log.i(Ciclo, "Metodo onStop chamado!");
    }

    /**
     * Chamado quando a Activity está prestes a ser destruído.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(Ciclo, "Metodo onDestroy chamado!");
    }
}
