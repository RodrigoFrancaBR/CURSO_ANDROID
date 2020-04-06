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
        mViewHolder.buttonCalcular.setOnClickListener(this);
        mViewHolder.buttonLimpar.setOnClickListener(this);
        limparResultado();
    }

    private void limparResultado() {
        mViewHolder.dolarConvertido.setText("0.00");
        mViewHolder.euroConvertido.setText("0.00");
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_calcular) {

            if (!isValidoValorDigitado(mViewHolder.valorDolarHoje.getText().toString()) && !isValidoValorDigitado(mViewHolder.valorEuroHoje.getText().toString())) {
                Toast.makeText(this, getString(R.string.dolar_ou_euro_obrigatorio), Toast.LENGTH_LONG).show();
            }

            if (!isValidoValorDigitado(mViewHolder.valorReal.getText().toString())) {
                Toast.makeText(this, getString(R.string.campo_obrigatorio) + " Valor em real ", Toast.LENGTH_LONG).show();
            }

            if (isValidoValorDigitado(mViewHolder.valorDolarHoje.getText().toString())) {
                double dolarConvertido = converterDePara(Double.parseDouble(mViewHolder.valorReal.getText().toString()), Double.parseDouble(mViewHolder.valorDolarHoje.getText().toString()));
                mViewHolder.dolarConvertido.setText(String.valueOf(dolarConvertido));
            }

            if (isValidoValorDigitado(mViewHolder.valorEuroHoje.getText().toString())) {
                double euroConvertido = converterDePara(Double.parseDouble(mViewHolder.valorReal.getText().toString()), Double.parseDouble(mViewHolder.valorEuroHoje.getText().toString()));
                mViewHolder.euroConvertido.setText((String.valueOf(euroConvertido)));
            }
        } else if (v.getId() == R.id.button_limpar) {
            limparCriterioDeConversao();
            limparResultado();
        }
    }

    private void limparCriterioDeConversao() {
        mViewHolder.valorDolarHoje.setText("");
        mViewHolder.valorEuroHoje.setText("");
        mViewHolder.valorReal.setText("");
    }

    private boolean isValidoValorDigitado(String valorDigitado) {
        return valorDigitado.equals("") ? false : true;
    }

    private double converterDePara(double valorReal, double outroValor) {
        return valorReal / outroValor;
    }

    private boolean isValidoQualValorDoDolarOuEuroHoje() {
        return mViewHolder.valorDolarHoje.getText().toString().equals("") && mViewHolder.valorEuroHoje.getText().toString().equals("") ? false : true;
    }

    private boolean isValidoValorEmReal() {
        return mViewHolder.valorReal.getText().toString().equals("") ? false : true;
    }

    private void buscarElementosDaInterface() {
        this.mViewHolder.valorDolarHoje = findViewById(R.id.edit_valor_dolar_hoje);
        this.mViewHolder.valorEuroHoje = findViewById(R.id.edit_valor_euro_hoje);
        this.mViewHolder.valorReal = findViewById(R.id.edit_valor_real);
        this.mViewHolder.dolarConvertido = findViewById(R.id.text_dolar_convertido);
        this.mViewHolder.euroConvertido = findViewById(R.id.text_euro_convertido);
        this.mViewHolder.buttonCalcular = findViewById(R.id.button_calcular);
        this.mViewHolder.buttonLimpar = findViewById(R.id.button_limpar);
    }

    private static class ViewHolder {

        EditText valorDolarHoje;
        EditText valorEuroHoje;
        EditText valorReal;
        TextView dolarConvertido;
        TextView euroConvertido;
        Button buttonCalcular;
        Button buttonLimpar;
    }

}
