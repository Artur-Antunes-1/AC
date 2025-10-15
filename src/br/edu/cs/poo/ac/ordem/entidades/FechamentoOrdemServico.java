package br.edu.cs.poo.ac.ordem.entidades;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class FechamentoOrdemServico implements Serializable {

    private String numeroOrdemServico;
    private LocalDate dataFechamento;
    private boolean pago;
    private String relatorioFinal;

}
