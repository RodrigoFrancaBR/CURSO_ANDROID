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
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_calcular) {

            if (!isValidoValorDigitado(mViewHolder.valorDolarHoje()) && !isValidoValorDigitado(mViewHolder.valorEuroHoje())) {
                Toast.makeText(this, getString(R.string.dolar_ou_euro_obrigatorio), Toast.LENGTH_LONG).show();
            }

            if (!isValidoValorDigitado(mViewHolder.valorReal())) {
                Toast.makeText(this, getString(R.string.campo_obrigatorio) + " Valor em real ", Toast.LENGTH_LONG).show();
            }

            if (isValidoValorDigitado(mViewHolder.valorDolarHoje())) {
                double dolarConvertido = converterDePara(Double.parseDouble(mViewHolder.valorReal()), Double.parseDouble(mViewHolder.valorDolarHoje()));
                mViewHolder.dolarConvertido(String.valueOf(dolarConvertido));
            }

            if (isValidoValorDigitado(mViewHolder.valorEuroHoje())) {
                double euroConvertido = converterDePara(Double.parseDouble(mViewHolder.valorReal()), Double.parseDouble(mViewHolder.valorEuroHoje()));
                mViewHolder.dolarConvertido(String.valueOf(euroConvertido));
            }
        }
    }

    private boolean isValidoValorDigitado(String valorDigitado) {
        return valorDigitado.equals("") ? false : true;
    }

    private double converterDePara(double valorReal, double outroValor) {
        return valorReal / outroValor;
    }

    private boolean isValidoQualValorDoDolarOuEuroHoje() {
        return mViewHolder.valorDolarHoje().equals("") && mViewHolder.valorEuroHoje().equals("") ? false : true;
    }

    private boolean isValidoValorEmReal() {
        return mViewHolder.valorReal().equals("") ? false : true;
    }

    private void buscarElementosDaInterface() {
        this.mViewHolder.valorDolarHoje = findViewById(R.id.edit_valor_dolar_hoje);
        this.mViewHolder.valorDolarHoje = findViewById(R.id.edit_valor_euro_hoje);
        this.mViewHolder.valorReal = findViewById(R.id.edit_valor_real);
        this.mViewHolder.dolarConvertido = findViewById(R.id.text_dolar_convertido);
        this.mViewHolder.euroConvertido = findViewById(R.id.text_euro_convertido);
        this.mViewHolder.buttonCalcular = findViewById(R.id.button_calcular);
    }

    private static class ViewHolder {
        EditText valorDolarHoje;
        EditText valorEuroHoje;
        EditText valorReal;
        TextView dolarConvertido;
        TextView euroConvertido;
        Button buttonCalcular;

        public String valorDolarHoje() {
            return this.valorDolarHoje.getText().toString();
        }

        public void valorDolarHoje(EditText valorDolarHoje) {
            this.valorDolarHoje = valorDolarHoje;
        }

        public String valorEuroHoje() {
            return valorEuroHoje.getText().toString();
        }

        public void valorEuroHoje(EditText valorEuroHoje) {
            this.valorEuroHoje = valorEuroHoje;
        }

        public String valorReal() {
            return valorReal.getText().toString();
        }

        public void valorReal(EditText valorReal) {
            this.valorReal = valorReal;
        }

        public String dolarConvertido() {
            return dolarConvertido.getText().toString();
        }

        public void dolarConvertido(String dolarConvertido) {
            this.dolarConvertido.setText(dolarConvertido);
        }

        public String euroConvertido() {
            return euroConvertido.getText().toString();
        }

        public void euroConvertido(TextView euroConvertido) {
            this.euroConvertido = euroConvertido;
        }

    }

}
