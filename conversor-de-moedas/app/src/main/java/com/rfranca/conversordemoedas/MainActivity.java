package com.rfranca.conversordemoedas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ViewHolder mViewHolder = new ViewHolder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buscarElementosDaInterface();
        this.mViewHolder.buttonCalcular.setOnClickListener(this);
        // onclick();
        // exibirHelloWorld();
    }

//    public void onclick(View view) {
//    }

//    private void onclick() {
//        this.mViewHolder.buttonCalcular.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//    }

    @Override
    public void onClick(View v) {
        this.mViewHolder.editValue.setText(getString(R.string.clicou)+ mViewHolder.index + " vez.");
        Toast.makeText(this, getString(R.string.clicou) + mViewHolder.index + " vez.", Toast.LENGTH_LONG).show();
        mViewHolder.index +=1;
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
        int index = 0;
        EditText editValue;
        TextView textEuro;
        TextView textDolar;
        Button buttonCalcular;
    }
}
