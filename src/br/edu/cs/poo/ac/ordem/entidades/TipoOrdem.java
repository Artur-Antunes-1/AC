package br.edu.cs.poo.ac.ordem.entidades;

public enum TipoOrdem {

    MANUTENCAO(1, "Manutenção"),
    CONFIGURACAO(2, "Configuração"),
    UPGRADE(3, "Upgrade");

    // Atributos
    private final int codigo;
    private final String nome;

    // Construtor
    private TipoOrdem(int codigo, String nome){
        this.codigo = codigo;
        this.nome = nome;
    }

    // get codigo
    public int getCodigo(){
        return this.codigo;
    }

    //get nome
    public String getNome(){
        return this.nome;
    }


    public static TipoOrdem getTipoOrdem(int codigo){
        for (TipoOrdem tipo : TipoOrdem.values()){
            if(tipo.getCodigo() == codigo){
                return tipo;
            }
        }
        throw new IllegalArgumentException("Código de ordem inválido: " + codigo);
    }
}

