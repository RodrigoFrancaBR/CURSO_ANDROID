package com.rfranca.festafimdeano.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import com.rfranca.festafimdeano.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewHolder mViewHolder = new ViewHolder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buscarElementosDaInterface();
        mViewHolder.textDataAtual.setText(obterDataAtualNoFormato("dd/MM/yyyy"));
        mViewHolder.textDiasRestantes.setText(String.valueOf(obterDiasFaltando()));
        this.mViewHolder.buttonOpcao.setOnClickListener(this);
    }

    private int obterDiasFaltando() {
        // dia máximo do ano - dia atual
        return Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_YEAR) - Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
    }

    private String obterDataAtualNoFormato(String formato) {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formato);
        return simpleDateFormat.format(date);
    }


    private void buscarElementosDaInterface() {
        this.mViewHolder.textDataAtual = findViewById(R.id.text_data_atual);
        this.mViewHolder.textDiasRestantes = findViewById(R.id.dias_restantes);
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
        TextView textDataAtual;
        TextView textDiasRestantes;
        Button buttonOpcao;
    }


}
