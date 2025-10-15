package br.edu.cs.poo.ac.ordem.mediators;

public class DadosEquipamento {
    private String serial;
    private String descricao;
    private boolean ehNovo;
    private double valorEstimado;

    public DadosEquipamento(String serial, String descricao,
                            boolean ehNovo, double valorEstimado) {
        this.serial = serial;
        this.descricao = descricao;
        this.ehNovo = ehNovo;
        this.valorEstimado = valorEstimado;
    }

    public String getSerial() {
        return serial;
    }

    public String getDescricao() {
        return descricao;
    }

    public boolean getEhNovo() {
        return ehNovo;
    }

    public double getValorEstimado() {
        return valorEstimado;
    }
}