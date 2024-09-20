package model.ordine;

import model.ConPool;
import model.categoria.Categoria;
import model.prodotto.Prodotto;
import model.prodotto.ProdottoExtractor;
import model.prodottoordine.ProdottoOrdine;
import model.cliente.Cliente;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class OrdineDAO {

    public void addOrdine(Ordine p, Cliente c){
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO ordine (iva, dataInserimento, idOrdine, cli_fk) VALUES(?,?,?,?)");
            ps.setDouble(1, p.getIva());
            ps.setDate(2, (Date) p.getDataInserimento());
            ps.setLong(3, p.getIdOrdine());
            ps.setLong(4, c.getIdCliente());
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean doChanges(Ordine p){
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("UPDATE ordine o SET o.iva = (?), o.dataInserimento = (?) WHERE o.idOrdine = (?);");
            ps.setDouble(1, p.getIva());
            ps.setDate(2, (Date) p.getDataInserimento());
            ps.setLong(3, p.getIdOrdine());
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }
            return true;
        } catch(SQLException e){
            return false;
        }
    }

    public ArrayList<Ordine> doRetrieveAll(){
        ArrayList<Ordine> result=new ArrayList<Ordine>();
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM ordine as ord");
            ResultSet rs = ps.executeQuery();
            OrdineExtractor ordExtractor = new OrdineExtractor();
            while(rs.next()) {
                result.add(ordExtractor.extract(rs));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Ordine doRetrieveById(long id){
        Ordine p = new Ordine();
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Ordine as ord WHERE idOrdine=?");
            ps.setLong(1,id);
            ResultSet rs = ps.executeQuery();
            OrdineExtractor ordExtractor = new OrdineExtractor();
            if(rs.next()) {
               p=ordExtractor.extract(rs);
            }
            return p;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public ArrayList<Ordine> doRetrieveByIdCliente(long id){
        ArrayList<Ordine> result = new ArrayList<>();
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Ordine as ord WHERE ord.cli_fk=?");
            ps.setLong(1,id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                OrdineExtractor ordExtractor = new OrdineExtractor();
                result.add(ordExtractor.extract(rs));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /*public Ordine doRetrieveByIdOrdineAndCliente(long idOrdine, long idCliente){
        Ordine p=new Ordine();
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM prodotto_ordine as po " +
                    "INNER JOIN prodotto as pro ON po.pro_fk = pro.idProdotto " +
                    "INNER JOIN ordine as ord ON po.ord_fk = ord.idOrdine where ord.idOrdine=? and ord.cli_fk=?");
            ps.setLong(1, idOrdine);
            ps.setLong(2, idCliente);
            ResultSet rs = ps.executeQuery();
            OrdineExtractor ordExtractor=new OrdineExtractor();
            if(rs.next()) {
                p=ordExtractor.extract(rs);
                ArrayList<Prodotto> prodotti=new ArrayList<>();
                p.setProdotti(prodotti);
                Prodotto a;
                ProdottoExtractor proExtractor=new ProdottoExtractor();
                a=proExtractor.extract(rs);
                Prodotto x=new Prodotto();
                x.setQuantita(rs.getInt("po.quantita"));
                prodotti.add(x);
                while(rs.next()) {
                    Prodotto b;
                    b=proExtractor.extract(rs);
                    Prodotto y = new Prodotto();
                    y.setQuantita(rs.getInt("po.quantita"));
                    prodotti.add(y);
                }
            }
            return p;
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }*/

}



