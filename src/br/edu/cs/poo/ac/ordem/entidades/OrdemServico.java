package br.edu.cs.poo.ac.ordem.entidades;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import br.edu.cs.poo.ac.utils.Registro;
import static br.edu.cs.poo.ac.utils.ValidadorCPFCNPJ.isCPF;

public class OrdemServico implements Registro {
    private static final long serialVersionUID = 1L;
    private Cliente cliente;
    private PrecoBase precoBase;
    private Equipamento equipamento;
    private LocalDateTime dataHoraAbertura;
    private int prazoEmDias;
    private double valor;
    private StatusOrdem status;
    private String vendedor;
    private String motivoCancelamento;
    private LocalDateTime dataHoraCancelamento;
    private FechamentoOrdemServico dadosFechamento;

    public OrdemServico(Cliente cliente, PrecoBase precoBase, Equipamento equipamento,
            LocalDateTime dataHoraAbertura, int prazoEmDias, double valor) {
        super();
        this.cliente = cliente;
        this.precoBase = precoBase;
        this.equipamento = equipamento;
        this.dataHoraAbertura = dataHoraAbertura;
        this.prazoEmDias = prazoEmDias;
        this.valor = valor;
    }

    public LocalDate getDataEstimadaEntrega() {
        return dataHoraAbertura.plusDays(prazoEmDias).toLocalDate();
    }

    public String getNumero() {
        String tipoEq = equipamento.getIdTipo();
        String parteCpfCnpj = "";
        if (isCPF(cliente.getCpfCnpj())) {
            parteCpfCnpj = "000";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        String dhaString = dataHoraAbertura.format(formatter);
        return tipoEq + dhaString + parteCpfCnpj + cliente.getCpfCnpj();
    }

    @Override
    public String getId() {
        return getNumero();
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public PrecoBase getPrecoBase() {
        return precoBase;
    }

    public void setPrecoBase(PrecoBase precoBase) {
        this.precoBase = precoBase;
    }

    public Equipamento getEquipamento() {
        return equipamento;
    }

    public void setEquipamento(Equipamento equipamento) {
        this.equipamento = equipamento;
    }

    public LocalDateTime getDataHoraAbertura() {
        return dataHoraAbertura;
    }

    public void setDataHoraAbertura(LocalDateTime dataHoraAbertura) {
        this.dataHoraAbertura = dataHoraAbertura;
    }

    public int getPrazoEmDias() {
        return prazoEmDias;
    }

    public void setPrazoEmDias(int prazoEmDias) {
        this.prazoEmDias = prazoEmDias;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public StatusOrdem getStatus() {
        return status;
    }

    public void setStatus(StatusOrdem status) {
        this.status = status;
    }

    public String getVendedor() {
        return vendedor;
    }

    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }

    public String getMotivoCancelamento() {
        return motivoCancelamento;
    }

    public void setMotivoCancelamento(String motivoCancelamento) {
        this.motivoCancelamento = motivoCancelamento;
    }

    public LocalDateTime getDataHoraCancelamento() {
        return dataHoraCancelamento;
    }

    public void setDataHoraCancelamento(LocalDateTime dataHoraCancelamento) {
        this.dataHoraCancelamento = dataHoraCancelamento;
    }

    public FechamentoOrdemServico getDadosFechamento() {
        return dadosFechamento;
    }

    public void setDadosFechamento(FechamentoOrdemServico dadosFechamento) {
        this.dadosFechamento = dadosFechamento;
    }
}
