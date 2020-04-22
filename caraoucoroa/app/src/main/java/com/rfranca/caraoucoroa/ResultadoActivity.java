package com.rfranca.caraoucoroa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Random;

public class ResultadoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);
        receberMensagemDoJogador();
    }

    private void receberMensagemDoJogador() {
        String caraOuCoroa = null;
        Intent intent = getIntent();

        String msgDoUsuario = intent.getStringExtra("msg");

        if (mensagemValida(msgDoUsuario)) {
            caraOuCoroa = executarJogo(msgDoUsuario);
        } else {
            caraOuCoroa = "VALOR INVÁLIDO INFORMADO PELO USUÁRIO ";
        }

        TextView textView = (TextView) findViewById(R.id.msg);

        textView.setText(String.valueOf(caraOuCoroa));
    }

    private boolean mensagemValida(String msg) {
        boolean resultado = false;
        if (msg.equals("") || msg == null) {
            resultado = false;
        } else if (
                msg.trim().toUpperCase().equals(CaraOuCoroa.CARA.getValor())
                        || CaraOuCoroa.COROA.getValor().equals(msg.trim().toUpperCase())) {
            resultado = true;
        }
        return resultado;
    }

    private String executarJogo(String msgDoUsuario) {
        String caraOuCoroa = null;
        int resultado = new Random().nextInt(2);
        caraOuCoroa = CaraOuCoroa.getCaraOuCoroa(resultado);
        if (msgDoUsuario.equals(caraOuCoroa)) {
            return "VITORIA!";
        } else {
            return "DERROTA";
        }
    }
}
