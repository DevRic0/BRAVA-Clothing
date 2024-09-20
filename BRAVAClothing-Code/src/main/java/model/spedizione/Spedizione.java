package model.spedizione;

import model.ordine.Ordine;

import java.util.Date;

public class Spedizione {

    public Spedizione(){

    }

    public Spedizione(int idSpedizione, Date data, String status, double spese) {
        this.idSpedizione = idSpedizione;
        this.data = data;
        this.status = status;
        this.spese = spese;
    }

    public long getIdSpedizione() {
        return idSpedizione;
    }

    public void setIdSpedizione(Long idSpedizione) {
        this.idSpedizione = idSpedizione;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getSpese() {
        return spese;
    }

    public void setSpese(double spese) {
        this.spese = spese;
    }

    public Ordine getOrdine() {
        return ordine;
    }

    public void setOrdine(Ordine ordine) {
        this.ordine = ordine;
    }

    @Override
    public String toString() {
        return "Spedizione{" +
                "idSpedizione=" + idSpedizione +
                ", data=" + data +
                ", status='" + status + '\'' +
                ", spese=" + spese +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Spedizione that = (Spedizione) o;
        return idSpedizione == that.idSpedizione;
    }

    private long idSpedizione;
    private Date data;
    private String status;
    private double spese;
    private Ordine ordine;

}
