package model.categoria;

public class Categoria {
    public Categoria(){
        super();
    }
    public Categoria(String nome,String descrizione,long idCategoria, String genere){
        this.nome=nome;
        this.descrizione=descrizione;
        this.idCategoria=idCategoria;
        this.genere = genere;
    }
    public long getIdCategoria() {return idCategoria;}
    public void setIdCategoria(long idCategoria) {this.idCategoria = idCategoria;}
    public String getNome() {return nome;}
    public void setNome(String nome) {this.nome = nome;}
    public String getDescrizione() {return descrizione;}
    public void setDescrizione(String descrizione) {this.descrizione = descrizione;}

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Categoria categoria = (Categoria) o;
        return idCategoria == categoria.idCategoria;
    }

    public String toString() {
        return "Model.Categoria.Categoria{" +
                "idCategoria=" + idCategoria +
                ", nome='" + nome + '\'' +
                ", descrizione='" + descrizione + '\'' +
                '}';
    }

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }

    private long idCategoria;
    private String nome;
    private String descrizione;
    private String genere;

}
