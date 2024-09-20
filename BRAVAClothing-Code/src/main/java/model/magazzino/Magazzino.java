package model.magazzino;

public class Magazzino {
    public Magazzino(){
        super();
    }

    public long getIdMagazzino() {return idMagazzino;}
    public void setIdMagazzino(long idMagazzino) {this.idMagazzino = idMagazzino;}
    public String getNome() {return nome;}
    public void setNome(String nome) {this.nome = nome;}
    public String getIndirizzo() {return indirizzo;}
    public void setIndirizzo(String indirizzo) {this.indirizzo = indirizzo;}

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Magazzino magazzino = (Magazzino) o;
        return idMagazzino == magazzino.idMagazzino;
    }

    public String toString() {
        return "Model.Magazzino.Magazzino{" +
                "idMagazzino=" + idMagazzino +
                ", nome='" + nome + '\'' +
                ", indirizzo='" + indirizzo + '\'' +
                '}';
    }

    private long idMagazzino;
    private String nome;
    private String indirizzo;
}

