package br.edu.cs.poo.ac.ordem.daos;

import br.edu.cs.poo.ac.ordem.entidades.Notebook;
import br.edu.cs.poo.ac.utils.Registro;

public class NotebookDAO extends DAOGenerico {

    public NotebookDAO() {
        super();
    }

    public Notebook buscar(String idUnico) {
        return (Notebook) super.buscar(idUnico);
    }

    public boolean incluir(Notebook notebook) {
        return super.incluir(notebook);
    }

    public boolean alterar(Notebook notebook) {
        return super.alterar(notebook);
    }

    public boolean excluir(String idUnico) {
        return super.excluir(idUnico);
    }

    public Notebook[] buscarTodos() {
        Registro[] ret = super.buscarTodos();
        Notebook[] retorno;
        if (ret != null && ret.length > 0) {
            retorno = new Notebook[ret.length];
            for (int i = 0; i < ret.length; i++) {
                retorno[i] = (Notebook) ret[i];
            }
        } else {
            retorno = new Notebook[0];
        }
        return retorno;
    }
    
    @Override
    public Class<?> getClasseEntidade() {
        return Notebook.class;
    }
}
