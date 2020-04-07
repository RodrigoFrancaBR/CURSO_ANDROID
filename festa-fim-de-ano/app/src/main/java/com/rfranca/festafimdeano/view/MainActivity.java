package com.rfranca.festafimdeano.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import com.rfranca.festafimdeano.R;
import com.rfranca.festafimdeano.constantes.FimDeAnoConst;
import com.rfranca.festafimdeano.data.SecurityPreferences;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewHolder mViewHolder = new ViewHolder();
    SecurityPreferences mSecurityPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buscarElementosDaInterface();
        mViewHolder.textDataAtual.setText(obterDataAtualNoFormato("dd/MM/yyyy"));
        mViewHolder.textDiasRestantes.setText(String.valueOf(obterDiasFaltando() + " " + getString(R.string.dias)));
        mSecurityPreferences = new SecurityPreferences(this);
        this.mViewHolder.buttonOpcao.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        estaConfirmado();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_opcao) {
            Intent intent = new Intent(this, DetalheActivity.class);
            String estaConfirmado = mSecurityPreferences.getString(FimDeAnoConst.PRESENCA_CONFIRMADA);
            intent.putExtra(FimDeAnoConst.PRESENCA_CONFIRMADA,estaConfirmado);
            startActivity(intent);
        }
    }

    private void estaConfirmado() {

        String estaConfirmado = mSecurityPreferences.getString(FimDeAnoConst.PRESENCA_CONFIRMADA);
        if (estaConfirmado.equals("")) {
            mViewHolder.buttonOpcao.setText(getString(R.string.nao_confirmado));
        } else if (estaConfirmado.equals(FimDeAnoConst.SIM)) {
            mViewHolder.buttonOpcao.setText(getString(R.string.sim));
        } else {
            mViewHolder.buttonOpcao.setText(getString(R.string.nao));
        }
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

    private static class ViewHolder {
        TextView textDataAtual;
        TextView textDiasRestantes;
        Button buttonOpcao;
    }


}
