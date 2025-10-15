package br.edu.cs.poo.ac.ordem.daos;

import br.edu.cs.poo.ac.ordem.entidades.Notebook;
import java.io.Serializable;

public class NotebookDAO extends DAOGenerico {

    public NotebookDAO() {
        super(Notebook.class);
    }

    public Notebook buscar(String idUnico) {
        return (Notebook)cadastroObjetos.buscar(idUnico);
    }

    public boolean incluir(Notebook notebook) {
        String idUnico = notebook.getIdTipo() + notebook.getSerial();
        if (buscar(idUnico) == null) {
            cadastroObjetos.incluir(notebook, idUnico);
            return true;
        } else {
            return false;
        }
    }

    public boolean alterar(Notebook notebook) {
        String idUnico = notebook.getIdTipo() + notebook.getSerial();
        if (buscar(idUnico) != null) {
            cadastroObjetos.alterar(notebook, idUnico);
            return true;
        } else {
            return false;
        }
    }

    public boolean excluir(String idUnico) {
        if (buscar(idUnico) != null) {
            cadastroObjetos.excluir(idUnico);
            return true;
        } else {
            return false;
        }
    }

    public Notebook[] buscarTodos() {
        Serializable[] ret = cadastroObjetos.buscarTodos();
        Notebook[] retorno;
        if (ret != null && ret.length > 0) {
            retorno = new Notebook[ret.length];
            for (int i=0; i<ret.length; i++) {
                retorno[i] = (Notebook)ret[i];
            }
        } else {
            retorno = new Notebook[0];
        }
        return retorno;
    }
}