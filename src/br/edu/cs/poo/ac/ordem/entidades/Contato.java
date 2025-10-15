package br.edu.cs.poo.ac.ordem.entidades;

import java.io.Serializable;

public class Contato implements Serializable {

    private String email;
    private String celular;
    private boolean ehZap;

    public Contato(String email, String celular, boolean ehZap){
        this.email = email;
        this.celular = celular;
        this.ehZap = ehZap;
    }

    public String getEmail(){
        return this.email;
    }

    public String getContato() {
        return this.celular;
    }

    public boolean getEhZap() {
        return ehZap;
    }

}
