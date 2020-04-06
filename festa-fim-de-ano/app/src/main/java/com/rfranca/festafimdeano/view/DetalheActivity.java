package com.rfranca.festafimdeano.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CheckBox;

import com.rfranca.festafimdeano.R;

public class DetalheActivity extends AppCompatActivity {

    private ViewHolder mViewHolder = new ViewHolder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe);
        buscarElementosDaInterface();
    }

    private void buscarElementosDaInterface() {
        this.mViewHolder.checkBoxParticipa = findViewById(R.id.checkbox_voce_vai_participar);
    }

    private static class ViewHolder {
        CheckBox checkBoxParticipa;
    }
}
