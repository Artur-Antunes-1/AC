package br.edu.cs.poo.ac.ordem.daos;

import br.edu.cs.poo.ac.ordem.entidades.Desktop;
import br.edu.cs.poo.ac.utils.Registro;

public class DesktopDAO extends DAOGenerico {

    public DesktopDAO() {
        super();
    }

    public Desktop buscar(String idUnico) {
        return (Desktop) super.buscar(idUnico);
    }

    public boolean incluir(Desktop desktop) {
        return super.incluir(desktop);
    }

    public boolean alterar(Desktop desktop) {
        return super.alterar(desktop);
    }

    public boolean excluir(String idUnico) {
        return super.excluir(idUnico);
    }

    public Desktop[] buscarTodos() {
        Registro[] ret = super.buscarTodos();
        Desktop[] retorno;
        if (ret != null && ret.length > 0) {
            retorno = new Desktop[ret.length];
            for (int i = 0; i < ret.length; i++) {
                retorno[i] = (Desktop) ret[i];
            }
        } else {
            retorno = new Desktop[0];
        }
        return retorno;
    }
    
    @Override
    public Class<?> getClasseEntidade() {
        return Desktop.class;
    }
}
