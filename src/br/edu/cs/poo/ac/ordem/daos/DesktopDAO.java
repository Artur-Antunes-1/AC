package br.edu.cs.poo.ac.ordem.daos;

import br.edu.cs.poo.ac.ordem.entidades.Desktop;
import java.io.Serializable;

public class DesktopDAO extends DAOGenerico {

    public DesktopDAO() {
        super(Desktop.class);
    }

    public Desktop buscar(String idUnico) {
        return (Desktop)cadastroObjetos.buscar(idUnico);
    }

    public boolean incluir(Desktop desktop) {
        String idUnico = desktop.getIdTipo() + desktop.getSerial();
        if (buscar(idUnico) == null) {
            cadastroObjetos.incluir(desktop, idUnico);
            return true;
        } else {
            return false;
        }
    }

    public boolean alterar(Desktop desktop) {
        String idUnico = desktop.getIdTipo() + desktop.getSerial();
        if (buscar(idUnico) != null) {
            cadastroObjetos.alterar(desktop, idUnico);
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

    public Desktop[] buscarTodos() {
        Serializable[] ret = cadastroObjetos.buscarTodos();
        Desktop[] retorno;
        if (ret != null && ret.length > 0) {
            retorno = new Desktop[ret.length];
            for (int i=0; i<ret.length; i++) {
                retorno[i] = (Desktop)ret[i];
            }
        } else {
            retorno = new Desktop[0];
        }
        return retorno;
    }
}