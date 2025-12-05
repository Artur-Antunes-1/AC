package br.edu.cs.poo.ac.ordem.daos;

import br.edu.cs.poo.ac.ordem.entidades.OrdemServico;
import br.edu.cs.poo.ac.utils.Registro;

public class OrdemServicoDAO extends DAOGenerico {

    public OrdemServicoDAO() {
        super();
    }

    public OrdemServico buscar(String numero) {
        return (OrdemServico) super.buscar(numero);
    }

    public boolean incluir(OrdemServico ordem) {
        return super.incluir(ordem);
    }

    public boolean alterar(OrdemServico ordem) {
        return super.alterar(ordem);
    }

    public boolean excluir(String numero) {
        return super.excluir(numero);
    }

    public OrdemServico[] buscarTodos() {
        Registro[] ret = super.buscarTodos();
        OrdemServico[] retorno;
        if (ret != null && ret.length > 0) {
            retorno = new OrdemServico[ret.length];
            for (int i = 0; i < ret.length; i++) {
                retorno[i] = (OrdemServico) ret[i];
            }
        } else {
            retorno = new OrdemServico[0];
        }
        return retorno;
    }
    
    @Override
    public Class<?> getClasseEntidade() {
        return OrdemServico.class;
    }
}
