package br.edu.cs.poo.ac.ordem.entidades;

public enum StatusOrdem {
    ABERTA(1, "Aberta"),
    CANCELADA(2, "Cancelada"),
    FECHADA(3, "Fechada");

    private final int codigo;
    private final String descricao;

    private StatusOrdem(int codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }
}

