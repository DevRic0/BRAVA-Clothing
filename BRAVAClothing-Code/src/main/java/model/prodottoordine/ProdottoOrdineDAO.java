package model.prodottoordine;

import model.ConPool;
import model.ordine.Ordine;
import model.prodotto.Prodotto;
import model.prodotto.ProdottoExtractor;
import model.prodottoordine.ProdottoOrdine;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class ProdottoOrdineDAO {
    public void addProdottoOrdine(Ordine o, Prodotto p, int q){
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO prodotto_ordine (ord_fk,pro_fk,quantita) VALUES(?,?,?)");
            ps.setLong(1, o.getIdOrdine());
            ps.setLong(2, p.getIdProdotto());
            ps.setInt(3, q);
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void doChanges(Prodotto p,Ordine o,int q){
        try (Connection con = ConPool.getConnection()) {
            Statement st = con.createStatement();
            String query = "update prodotto_ordine set quantita=" + q + " where ord_fk=" + o.getIdOrdine() + " and pro_fk="+p.getIdProdotto()+";";
            st.executeUpdate(query);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<ProdottoOrdine> doRetrieveProdottiWithIdOrdine(long id){
        ArrayList<ProdottoOrdine> result = new ArrayList<>();
        try (Connection con = ConPool.getConnection()) {
            String query = "SELECT * FROM prodotto_ordine as po WHERE po.ord_fk = (?);";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            ProdottoOrdineExtractor poExtractor = new ProdottoOrdineExtractor();
            while( rs.next()) {
                ProdottoOrdine po;
                po = poExtractor.extract(rs);
                result.add(po);
            }
        } catch (SQLException | IOException throwable) {
            throwable.printStackTrace();
        }
        return result;
    }

    public void deleteByIdOrdine(long id){
        try (Connection con = ConPool.getConnection()) {
            String query ="DELETE FROM prodotto_ordine as po WHERE po.ord_fk = (?);";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setLong(1, id);
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("DELETE error.");
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    /*public ArrayList<ProdottoOrdine> doRetrieveAll(){
        ArrayList<ProdottoOrdine> result=new ArrayList<ProdottoOrdine>();
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM prodotto_ordine as po " +
                    "INNER JOIN prodotto as pro ON po.pro_fk = pro.idProdotto " +
                    "INNER JOIN ordine as ord ON po.ord_fk = ord.idOrdine");
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                ProdottoOrdine po = new ProdottoOrdine();

                Ordine o=new Ordine();
                o.setIva(rs.getDouble("ord.iva"));
                o.setDataInserimento(rs.getDate("ord.dataInserimento"));
                o.setIdOrdine(rs.getInt("ord.idOrdine"));

                Prodotto p=new Prodotto();
                p.setNome(rs.getString("pro.nome"));
                p.setPrezzo(rs.getDouble("pro.prezzo"));
                p.setDescrizioneBreve((rs.getString("pro.descrizioneBreve")));
                p.setDescrizioneDettagliata((rs.getString("pro.descrizioneDettagliata")));
                p.setInOfferta(rs.getBoolean("pro.inOfferta"));
                p.setIdProdotto(rs.getLong("pro.idProdotto"));

                int q=rs.getInt("po.quantita");
                po.setOrdine(o);
                po.setProdotto(p);
                po.setQuantita(q);
                result.add(po);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
*/



}
