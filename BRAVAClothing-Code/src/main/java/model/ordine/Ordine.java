package model.ordine;

import model.prodotto.Prodotto;
import model.prodottoordine.ProdottoOrdine;
import model.cliente.Cliente;

import java.sql.Date;
import java.util.ArrayList;

public class Ordine {
    public Ordine() {
        this.prodotti=new ArrayList<>();
    }
    public Ordine(double iva,Date data,long id,Cliente c) {
        this.iva=iva;
        this.dataInserimento=data;
        this.idOrdine=id;
        this.cliente=c;
        this.prodotti=new ArrayList<>();
    }
    public long getIdOrdine() { return idOrdine; }
    public void setIdOrdine(long idOrdine) { this.idOrdine = idOrdine; }
    public Double getIva() { return iva; }
    public void setIva(Double iva) { this.iva = iva; }
    public Date getDataInserimento() { return dataInserimento; }
    public void setDataInserimento(Date dataInserimento) { this.dataInserimento = dataInserimento; }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    public ArrayList<ProdottoOrdine> getProdotti() {
        return prodotti;
    }

    public void setProdotti(ArrayList<ProdottoOrdine> prodotti) {
        this.prodotti = prodotti;
    }
    public String toString() {
        return "Ordine{" +
                "idOrdine=" + idOrdine +
                ", IVA=" + iva +
                ", dataInserimento=" + dataInserimento + "prodotti"+prodotti+
                '}';
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ordine ordine = (Ordine) o;
        return idOrdine == ordine.idOrdine;
    }

    private long idOrdine;
    private Double iva;
    private Date dataInserimento;
    private Cliente cliente;
    private ArrayList<ProdottoOrdine> prodotti;


}
