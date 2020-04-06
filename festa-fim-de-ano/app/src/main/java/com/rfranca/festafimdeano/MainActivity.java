package com.rfranca.festafimdeano;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewHolder mViewHolder = new ViewHolder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buscarElementosDaInterface();
        this.mViewHolder.buttonOpcao.setOnClickListener(this);
    }


    private void buscarElementosDaInterface() {
        this.mViewHolder.textData = findViewById(R.id.text_data);
        this.mViewHolder.textLabelDiasRestantes = findViewById(R.id.text_label_dias_restantes);
        this.mViewHolder.buttonOpcao = findViewById(R.id.button_opcao);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_opcao) {
            Intent intent = new Intent(this, DetalheActivity.class);
            startActivity(intent);
        }
    }

    private static class ViewHolder {
        TextView textData;
        TextView textLabelDiasRestantes;
        Button buttonOpcao;
    }


}
