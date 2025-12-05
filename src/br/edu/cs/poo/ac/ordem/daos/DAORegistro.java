package br.edu.cs.poo.ac.ordem.daos;

import java.util.ArrayList;
import java.util.List;

import br.edu.cesarschool.next.oo.persistenciaobjetos.CadastroObjetos;
import br.edu.cs.poo.ac.excecoes.ExcecaoObjetoJaExistente;
import br.edu.cs.poo.ac.excecoes.ExcecaoObjetoNaoExistente;
import br.edu.cs.poo.ac.utils.Registro;

public class DAORegistro<T extends Registro> {
    private CadastroObjetos cadastroObjetos;
    private Class<T> classe;
    
    public DAORegistro(Class<T> classe) {
        this.classe = classe;
        this.cadastroObjetos = new CadastroObjetos(classe);
    }
    
    public void incluir(T objeto) throws ExcecaoObjetoJaExistente {
        String id = objeto.getId();
        if (buscar(id) != null) {
            throw new ExcecaoObjetoJaExistente(classe.getSimpleName() + " já existente");
        }
        cadastroObjetos.incluir(objeto, id);
    }
    
    public void alterar(T objeto) throws ExcecaoObjetoNaoExistente {
        String id = objeto.getId();
        if (buscar(id) == null) {
            throw new ExcecaoObjetoNaoExistente(classe.getSimpleName() + " não existente");
        }
        cadastroObjetos.alterar(objeto, id);
    }
    
    public void excluir(String id) throws ExcecaoObjetoNaoExistente {
        if (buscar(id) == null) {
            throw new ExcecaoObjetoNaoExistente(classe.getSimpleName() + " não existente");
        }
        cadastroObjetos.excluir(id);
    }
    
    public T buscar(String id) {
        return (T) cadastroObjetos.buscar(id);
    }
    
    public List<T> buscarTodos() {
        Object[] ret = cadastroObjetos.buscarTodos();
        List<T> lista = new ArrayList<>();
        if (ret != null && ret.length > 0) {
            for (Object obj : ret) {
                lista.add((T) obj);
            }
        }
        return lista;
    }
}

