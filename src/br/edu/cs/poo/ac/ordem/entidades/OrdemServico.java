package br.edu.cs.poo.ac.ordem.entidades;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class OrdemServico implements Serializable {

    private Cliente cliente;
    private PrecoBase precobase;
    private Notebook notebook;
    private Desktop desktop;
    private LocalDateTime dataHoraAbertura;
    private int prazoEmDias;
    private double valor;

    public LocalDate getDataEstimadaEntrega(){
        return this.dataHoraAbertura.toLocalDate().plusDays(this.prazoEmDias);
    }

    public String getNumero(){
        String tipoEquipamento;
        if (this.notebook != null) {
            tipoEquipamento = this.notebook.getIdTipo();
        } else {
            tipoEquipamento = this.desktop.getIdTipo();
        }

        String clienteIdLimpo = this.cliente.getCpfCnpj().replaceAll("[^0-9]", "");

        String ano = String.valueOf(this.dataHoraAbertura.getYear());
        String mes = String.format("%02d", this.dataHoraAbertura.getMonthValue());
        String dia = String.format("%02d", this.dataHoraAbertura.getDayOfMonth());
        String hora = String.format("%02d", this.dataHoraAbertura.getHour());
        String minuto = String.format("%02d", this.dataHoraAbertura.getMinute());

        if (clienteIdLimpo.length() == 14) {
            return tipoEquipamento + ano + mes + dia + hora + minuto + clienteIdLimpo;
        } else {
            return mes + ano + dia + hora + minuto + "000" + clienteIdLimpo;
        }
    }
}
