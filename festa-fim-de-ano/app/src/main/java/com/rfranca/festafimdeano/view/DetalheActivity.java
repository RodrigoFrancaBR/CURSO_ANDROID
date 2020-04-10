package com.rfranca.festafimdeano.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.rfranca.festafimdeano.R;
import com.rfranca.festafimdeano.constantes.FimDeAnoConst;
import com.rfranca.festafimdeano.data.SecurityPreferences;

public class DetalheActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewHolder mViewHolder = new ViewHolder();
    SecurityPreferences mSecurityPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe);
        buscarElementosDaInterface();
        mSecurityPreferences = new SecurityPreferences(this);
        carregarDadosDaActivity();
        mViewHolder.checkBoxParticipa.setOnClickListener(this);
    }

    private void buscarElementosDaInterface() {
        this.mViewHolder.checkBoxParticipa = findViewById(R.id.checkbox_voce_vai_participar);
    }

    private void carregarDadosDaActivity() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String estaConfirmada = extras.getString(FimDeAnoConst.PRESENCA_CONFIRMADA);
            if (estaConfirmada != null && estaConfirmada.equals(FimDeAnoConst.SIM)) {
                mViewHolder.checkBoxParticipa.setChecked(true);
            } else {
                mViewHolder.checkBoxParticipa.setChecked(false);
            }
        }

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.checkbox_voce_vai_participar) {

            if (mViewHolder.checkBoxParticipa.isChecked()) {
                mSecurityPreferences.setString(FimDeAnoConst.PRESENCA_CONFIRMADA, FimDeAnoConst.SIM);
            } else {
                mSecurityPreferences.setString(FimDeAnoConst.PRESENCA_CONFIRMADA, FimDeAnoConst.NAO);
            }
        }
    }

    private static class ViewHolder {
        CheckBox checkBoxParticipa;
    }
}
