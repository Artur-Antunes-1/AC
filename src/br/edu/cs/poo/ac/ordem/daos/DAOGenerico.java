package br.edu.cs.poo.ac.ordem.daos;

import br.edu.cesarschool.next.oo.persistenciaobjetos.CadastroObjetos;
import br.edu.cs.poo.ac.utils.Registro;

public abstract class DAOGenerico {
    protected CadastroObjetos cadastroObjetos;
    
    public DAOGenerico() {
        cadastroObjetos = new CadastroObjetos(getClasseEntidade());
    }
    
    public Registro buscar(String id) {
        return (Registro) cadastroObjetos.buscar(id);
    }
    
    public boolean incluir(Registro objeto) {
        String id = objeto.getId();
        if (buscar(id) == null) {
            cadastroObjetos.incluir(objeto, id);
            return true;
        }
        return false;
    }
    
    public boolean alterar(Registro objeto) {
        String id = objeto.getId();
        if (buscar(id) != null) {
            cadastroObjetos.alterar(objeto, id);
            return true;
        }
        return false;
    }
    
    public boolean excluir(String id) {
        if (buscar(id) != null) {
            cadastroObjetos.excluir(id);
            return true;
        }
        return false;
    }
    
    public Registro[] buscarTodos() {
        Object[] ret = cadastroObjetos.buscarTodos();
        if (ret != null && ret.length > 0) {
            Registro[] registros = new Registro[ret.length];
            for (int i = 0; i < ret.length; i++) {
                registros[i] = (Registro) ret[i];
            }
            return registros;
        }
        return new Registro[0];
    }

    public abstract Class<?> getClasseEntidade();
}
