package com.rfranca.caraoucoroa;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Arrays;

public enum CaraOuCoroa {
    CARA(0, "CARA"),
    COROA(1, "COROA"),
    INVALIDO(100, "OPÇÃO INVÁLIDA");

    private int chave;
    private String valor;

    private CaraOuCoroa(int chave, String valor) {
        this.chave = chave;
        this.valor = valor;
    }

    public int getChave() {
        return chave;
    }

    public String getValor() {
        return valor;
    }

    public static String getCaraOuCoroa(int chave) {
        if (chave == 0) {
            return CaraOuCoroa.CARA.getValor();
        } else {
            return CaraOuCoroa.COROA.getValor();
        }
    }

    /*@RequiresApi(api = Build.VERSION_CODES.N)
    public static CaraOuCoroa getCaraOuCoroa(int chave) {
        return Arrays.asList(CaraOuCoroa.values())
                .parallelStream()
                .filter(e -> e.getChave() == chave)
                .findFirst().orElse(CaraOuCoroa.INVALIDO);
    }*/

}