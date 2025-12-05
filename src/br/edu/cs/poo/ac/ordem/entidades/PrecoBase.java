package br.edu.cs.poo.ac.ordem.entidades;

public enum PrecoBase {
    MANUTENCAO_NORMAL(1, "Manutenção normal"),
    MANUTENCAO_EMERGENCIAL(2, "Manutenção emergencial"),
    REVISAO(3, "Revisão"),
    LIMPEZA(4, "Limpeza");

    private final int codigo;
    private final String descricao;

    private PrecoBase(int codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static PrecoBase getPrecoBase(int codigo) {
        for (PrecoBase pb : PrecoBase.values()) {
            if (pb.getCodigo() == codigo) {
                return pb;
            }
        }
        return null;
    }
}
