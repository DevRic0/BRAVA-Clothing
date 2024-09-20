package model.prodottoordine;

import model.ResultSetExtractor;
import model.ordine.Ordine;
import model.prodotto.Prodotto;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProdottoOrdineExtractor implements ResultSetExtractor<ProdottoOrdine> {
    @Override
    public ProdottoOrdine extract(ResultSet rs) throws SQLException, IOException {
        ProdottoOrdine po = new ProdottoOrdine();
        Prodotto p=new Prodotto();
        p.setIdProdotto(rs.getLong("po.pro_fk"));
        Ordine o=new Ordine();
        o.setIdOrdine(rs.getLong("po.ord_fk"));
        po.setQuantita(rs.getInt("po.quantita"));
        po.setProdotto(p);
        po.setOrdine(o);
        return po;
    }
}

