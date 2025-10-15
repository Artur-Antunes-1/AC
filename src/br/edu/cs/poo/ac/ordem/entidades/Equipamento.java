package br.edu.cs.poo.ac.ordem.entidades;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class Equipamento implements Serializable {

    private String serial;
    private String descricao;
    private boolean ehNovo;
    private double valorEstimado;
}
