package com.rfranca.conversordemoedas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private ViewHolder mViewHolder = new ViewHolder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buscarElementosDaInterface();
        exibirHelloWorld();
    }

    private void buscarElementosDaInterface() {
        this.mViewHolder.editValue = findViewById(R.id.edit_valor);
        this.mViewHolder.textDolar = findViewById(R.id.text_dolar);
        this.mViewHolder.textEuro = findViewById(R.id.text_euro);
        this.mViewHolder.buttonCalcular = findViewById(R.id.button_calcular);
    }


    private void exibirHelloWorld() {
        this.mViewHolder.editValue.setText("Hello World");
    }

    private static class ViewHolder {
        EditText editValue;
        TextView textEuro;
        TextView textDolar;
        Button buttonCalcular;
    }
}
