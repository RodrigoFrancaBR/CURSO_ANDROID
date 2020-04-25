package com.dellead.cadastrodeusuario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void fazerCadastro(View view) {
        exibirToast(view);
    }

    private void exibirToast(View view) {
        Context context = view.getContext();
        CharSequence text = "Usuário Cadastrado com Sucesso!";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

}
