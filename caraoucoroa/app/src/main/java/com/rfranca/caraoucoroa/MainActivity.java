package com.rfranca.caraoucoroa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        EditText texto = (EditText) findViewById(R.id.edit_text);
        texto.setText("");
    }

    public void onclick(View view) {
        Intent intent = new Intent(this, ResultadoActivity.class);
        EditText texto = (EditText) findViewById(R.id.edit_text);
        String msg = texto.getText().toString();
        intent.putExtra("msg", msg);
        startActivity(intent);
    }


}
