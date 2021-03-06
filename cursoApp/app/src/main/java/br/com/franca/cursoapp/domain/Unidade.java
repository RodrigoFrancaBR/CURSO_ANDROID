package br.com.franca.cursoapp.domain;

import br.com.franca.cursoapp.domain.enun.Status;

public class Unidade {

    private Long id;

    private String nome;

    private String endereco;

    private Status status;

    public Unidade() {
    }

    public Unidade(String nome, String endereco, Status status, Long id) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.status = status;
    }

    public Unidade(long id_unidade, String nome_unidade, String endereco_unidade) {
        this.id = id_unidade;
        this.nome = nome_unidade;
        endereco = endereco_unidade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Unidade other = (Unidade) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Unidade{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", endereco='" + endereco + '\'' +
                ", status=" + status +
                '}';
    }
}
