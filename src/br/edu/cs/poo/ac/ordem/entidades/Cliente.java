package br.edu.cs.poo.ac.ordem.entidades;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;

public class Cliente implements Serializable {
    private final String cpfCnpj;
    private String nome;
    private Contato contato;
    private final LocalDate dataCadastro;

    public Cliente(String cpfCnpj, String nome, Contato contato, LocalDate dataCadastro){
        this.cpfCnpj = cpfCnpj;
        this.nome = nome;
        this.contato = contato;
        this.dataCadastro = dataCadastro;
    }

    public String getCpfCnpj(){
        return this.cpfCnpj;
    }

    public LocalDate getDataCadastro(){
        return this.dataCadastro;
    }

    public Contato getContato(){
        return this.contato;
    }

    public String getNome(){
        return this.nome;
    }

    public void setContato(Contato contato){
        this.contato = contato;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public int getIdadeCadastro(){
        LocalDate dataAtual = LocalDate.now();
        Period periodo = Period.between(this.dataCadastro, dataAtual);

        return periodo.getYears();
    }


}
