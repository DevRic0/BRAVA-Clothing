package model.prodottoordine;

import model.ordine.Ordine;
import model.prodotto.Prodotto;

public class ProdottoOrdine {
    public ProdottoOrdine() { }
    public ProdottoOrdine(Prodotto p, Ordine o,int q){
        this.prodotto=p;
        this.ordine=o;
        this.quantita=q;
    }

    public Prodotto getProdotto() { return prodotto; }
    public void setProdotto(Prodotto prodotto) { this.prodotto = prodotto; }
    public Ordine getOrdine() { return ordine; }
    public void setOrdine(Ordine ordine) { this.ordine = ordine; }
    public int getQuantita() { return quantita; }
    public void setQuantita(int quantita) { this.quantita = quantita; }

    private Prodotto prodotto;
    private Ordine ordine;
    private int quantita;

}
