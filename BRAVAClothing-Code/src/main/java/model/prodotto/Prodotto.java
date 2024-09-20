package model.prodotto;

import model.categoria.Categoria;
import model.magazzino.Magazzino;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Locale;

public class Prodotto {

    public Prodotto(){ }
    public Prodotto(long idProdotto, String nome, double prezzo, boolean isInOfferta, String descrizioneBreve, String descrizioneDettagliata,int quantita,Magazzino mag, Categoria cat) {
        this.idProdotto = idProdotto;
        this.nome = nome;
        this.prezzo = prezzo;
        this.isInOfferta = isInOfferta;
        this.descrizioneBreve = descrizioneBreve;
        this.descrizioneDettagliata = descrizioneDettagliata;
        this.quantita=quantita;
        this.magazzino=mag;
        this.categoria=cat;
    }

    public Prodotto(String img,long idProdotto, String nome, double prezzo, boolean isInOfferta, String descrizioneBreve, String descrizioneDettagliata,int quantita,Magazzino mag, Categoria cat) throws IOException, SQLException {
        this.idProdotto = idProdotto;
        this.nome = nome;
        this.prezzo = prezzo;
        this.isInOfferta = isInOfferta;
        this.descrizioneBreve = descrizioneBreve;
        this.descrizioneDettagliata = descrizioneDettagliata;
        this.quantita=quantita;
        this.magazzino=mag;
        this.categoria=cat;
        this.base64Image=img;
        /*ProdottoDAO prodottoDAO = new ProdottoDAO();
        Blob blob = prodottoDAO.stringToBlob(img);
        if (blob!=null) {
            InputStream inputStream = blob.getBinaryStream();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int bytesRead = -1;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            byte[] imageBytes = outputStream.toByteArray();
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);
            this.setBase64Image(base64Image);
            inputStream.close();
            outputStream.close();
        }*/

    }

    public long getIdProdotto() {
        return idProdotto;
    }
    public void setIdProdotto(long idProdotto) {
        this.idProdotto = idProdotto;
    }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public double getPrezzo() { return prezzo; }
    public void setPrezzo(double prezzo) { this.prezzo = prezzo; }
    public boolean isInOfferta() { return isInOfferta; }
    public void setInOfferta(boolean inOfferta) { isInOfferta = inOfferta; }
    public String getDescrizioneBreve() { return descrizioneBreve; }
    public void setDescrizioneBreve(String descrizioneBreve) { this.descrizioneBreve = descrizioneBreve; }
    public String getDescrizioneDettagliata() { return descrizioneDettagliata; }
    public void setDescrizioneDettagliata(String descrizioneDettagliata) { this.descrizioneDettagliata = descrizioneDettagliata; }
    public int getQuantita() { return quantita; }
    public void setQuantita(int quantita) { this.quantita = quantita; }
    public Magazzino getMagazzino() { return magazzino; }
    public void setMagazzino(Magazzino magazzino) { this.magazzino = magazzino; }
    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }
    public String getBase64Image() {
        return base64Image;
    }

    public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
    }

    @Override
    public String toString() {
        return "Prodotto{" +
                "idProdotto=" + idProdotto +
                ", nome='" + nome + '\'' +
                ", prezzo=" + prezzo +
                ", isInOfferta=" + isInOfferta +
                ", descrizioneBreve='" + descrizioneBreve + '\'' +
                ", descrizioneDettagliata='" + descrizioneDettagliata + '\'' +
                ", categoria=" + categoria +
                ", quantita=" + quantita +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Prodotto prodotto = (Prodotto) o;
        return idProdotto == prodotto.idProdotto;
    }

    private long idProdotto;
    private String nome;
    private double prezzo;
    private boolean isInOfferta;
    private String descrizioneBreve;
    private String descrizioneDettagliata;
    private Magazzino magazzino;
    private Categoria categoria;
    private int quantita;
    private String base64Image;

}
