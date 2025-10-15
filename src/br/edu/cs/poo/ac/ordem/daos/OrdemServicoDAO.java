package br.edu.cs.poo.ac.ordem.daos;

import br.edu.cs.poo.ac.ordem.entidades.OrdemServico;
import java.io.Serializable;

public class OrdemServicoDAO extends DAOGenerico {

    public OrdemServicoDAO() {
        super(OrdemServico.class);
    }

    public OrdemServico buscar(String numero) {
        return (OrdemServico)cadastroObjetos.buscar(numero);
    }

    public boolean incluir(OrdemServico ordem) {
        String numero = ordem.getNumero();
        if (buscar(numero) == null) {
            cadastroObjetos.incluir(ordem, numero);
            return true;
        } else {
            return false;
        }
    }

    public boolean alterar(OrdemServico ordem) {
        String numero = ordem.getNumero();
        if (buscar(numero) != null) {
            cadastroObjetos.alterar(ordem, numero);
            return true;
        } else {
            return false;
        }
    }

    public boolean excluir(String numero) {
        if (buscar(numero) != null) {
            cadastroObjetos.excluir(numero);
            return true;
        } else {
            return false;
        }
    }

    public OrdemServico[] buscarTodos() {
        Serializable[] ret = cadastroObjetos.buscarTodos();
        OrdemServico[] retorno;
        if (ret != null && ret.length > 0) {
            retorno = new OrdemServico[ret.length];
            for (int i=0; i<ret.length; i++) {
                retorno[i] = (OrdemServico)ret[i];
            }
        } else {
            retorno = new OrdemServico[0];
        }
        return retorno;
    }
}