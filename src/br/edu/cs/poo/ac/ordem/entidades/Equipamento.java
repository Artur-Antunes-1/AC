package br.edu.cs.poo.ac.ordem.entidades;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import br.edu.cs.poo.ac.utils.Registro;

@Getter
@Setter
@AllArgsConstructor
public abstract class Equipamento implements Registro {

    private String serial;
    private String descricao;
    private boolean ehNovo;
    private double valorEstimado;
    
    // Construtor sem argumentos para permitir inicialização nas subclasses
    public Equipamento() {
    }
    
    public abstract String getIdTipo();
    
    @Override
    public String getId() {
        return getIdTipo() + serial;
    }
    
    // Getters e Setters (gerados pelo Lombok, mas explicitados para garantir compilação)
    public String getSerial() {
        return serial;
    }
    
    public void setSerial(String serial) {
        this.serial = serial;
    }
    
    public String getDescricao() {
        return descricao;
    }
    
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    public boolean isEhNovo() {
        return ehNovo;
    }
    
    public void setEhNovo(boolean ehNovo) {
        this.ehNovo = ehNovo;
    }
    
    public double getValorEstimado() {
        return valorEstimado;
    }
    
    public void setValorEstimado(double valorEstimado) {
        this.valorEstimado = valorEstimado;
    }
}
