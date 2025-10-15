package br.edu.cs.poo.ac.utils;

public class ElementoListaString {
    private String conteudo;
    private ElementoListaString proximo;
    public ElementoListaString(String conteudo, ElementoListaString proximo) {
        this.conteudo = conteudo;
        this.proximo = proximo;
    }
    public String getConteudo() {
        return conteudo;
    }
    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }
    public ElementoListaString getProximo() {
        return proximo;
    }
    public void setProximo(ElementoListaString proximo) {
        this.proximo = proximo;
    }
}