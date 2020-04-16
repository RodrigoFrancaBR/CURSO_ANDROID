package com.rfranca.trocaractivity;

import android.app.Activity;
import android.content.Intent;
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
}
