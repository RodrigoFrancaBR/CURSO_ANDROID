package com.rfranca.trocaractivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

public class Principal extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
    }

    public void trocarActivity(View v) {
        Intent intent = new Intent(this, SegundaTela.class);
        startActivity(intent);
        //finish();
    }

    public void abrirLink(View v) {
        Intent intentLink = new Intent();
        // Definindo ação: VIEW (visualização)
        intentLink.setAction(Intent.ACTION_VIEW);
        // Definindo categoria: DAFEAULT (Padrão)
        intentLink.addCategory(Intent.CATEGORY_DEFAULT);
        // Definindo o endereço do link a ser aberto
        intentLink.setData(Uri.parse("http://www.google.com"));
        startActivity(intentLink);
    }
}
