package br.edu.cs.poo.ac.excecoes;

import br.edu.cs.poo.ac.ordem.mediators.ResultadoMediator;

public class ExcecaoNegocio extends Exception {
    private static final long serialVersionUID = 1L;
    private ResultadoMediator res;
    
    public ExcecaoNegocio(ResultadoMediator res) {
        super();
        this.res = res;
    }
    
    public ResultadoMediator getRes() {
        return res;
    }
}

