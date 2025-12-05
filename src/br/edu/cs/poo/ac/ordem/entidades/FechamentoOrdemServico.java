package br.edu.cs.poo.ac.ordem.entidades;

import java.time.LocalDate;
import br.edu.cs.poo.ac.utils.Registro;

public class FechamentoOrdemServico implements Registro {
    private static final long serialVersionUID = 1L;
    private String numeroOrdemServico;
    private LocalDate dataFechamento;
    private boolean pago;
    private String relatorioFinal;

    public FechamentoOrdemServico(String numeroOrdemServico, LocalDate dataFechamento, 
            boolean pago, String relatorioFinal) {
        super();
        this.numeroOrdemServico = numeroOrdemServico;
        this.dataFechamento = dataFechamento;
        this.pago = pago;
        this.relatorioFinal = relatorioFinal;
    }

    @Override
    public String getId() {
        return numeroOrdemServico;
    }

    public String getNumeroOrdemServico() {
        return numeroOrdemServico;
    }

    public void setNumeroOrdemServico(String numeroOrdemServico) {
        this.numeroOrdemServico = numeroOrdemServico;
    }

    public LocalDate getDataFechamento() {
        return dataFechamento;
    }

    public void setDataFechamento(LocalDate dataFechamento) {
        this.dataFechamento = dataFechamento;
    }

    public boolean isPago() {
        return pago;
    }

    public void setPago(boolean pago) {
        this.pago = pago;
    }

    public String getRelatorioFinal() {
        return relatorioFinal;
    }

    public void setRelatorioFinal(String relatorioFinal) {
        this.relatorioFinal = relatorioFinal;
    }
}
