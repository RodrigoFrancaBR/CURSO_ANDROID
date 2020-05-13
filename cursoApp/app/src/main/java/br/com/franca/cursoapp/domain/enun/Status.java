package br.com.franca.cursoapp.domain.enun;

import java.util.Arrays;

public enum Status {
    DESATIVADA(0, "Desativada"), ATIVA(1, "Ativa"), INVALIDA(100, "Status inválido");

    private int chave;
    private String valor;

    private Status(int chave, String valor) {
        this.chave = chave;
        this.valor = valor;
    }

    public Status obterStatusPelaChave(int chave) {
        switch (chave){
            case 0 :
                return Status.DESATIVADA;
            case 1 :
                return Status.ATIVA;
            default:
                return Status.INVALIDA;
        }
    }

    public int getChave() {
        return chave;
    }

    public String getValor() {
        return valor;
    }



}
