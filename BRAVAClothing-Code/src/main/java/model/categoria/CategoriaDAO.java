package model.categoria;

import model.ConPool;
import model.categoria.Categoria;
import model.prodotto.Prodotto;

import java.sql.*;
import java.util.ArrayList;

public class CategoriaDAO {
    public void addCategoria(Categoria cat){
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO categoria (nome, descrizione, idCategoria, genere) VALUES(?,?,?, ?)");
            ps.setString(1, cat.getNome());
            ps.setString(2, cat.getDescrizione());
            ps.setLong(3,cat.getIdCategoria());
            ps.setString(4, cat.getGenere());
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean doChanges(Categoria cat){
        try(Connection con = ConPool.getConnection()){
            PreparedStatement ps = con.prepareStatement("UPDATE categoria c SET c.nome = (?), c.Descrizione = (?), c.Genere = (?) WHERE c.idCategoria = (?);");
            ps.setString(1, cat.getNome());
            ps.setString(2, cat.getDescrizione());
            ps.setString(3, cat.getGenere());
            ps.setLong(4, cat.getIdCategoria());
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }
            return true;
        } catch(SQLException e){
            return false;
        }
    }

    public ArrayList<Categoria> doRetrieveAll(){
        ArrayList<Categoria> result=new ArrayList<Categoria>();
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM categoria as cat");
            ResultSet rs = ps.executeQuery();
            CategoriaExtractor catExtractor=new CategoriaExtractor();
            while(rs.next()) {
                result.add(catExtractor.extract(rs));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Categoria>  doRetrieveByGenere(String genere){
        ArrayList<Categoria> result=new ArrayList<Categoria>();
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM categoria as cat WHERE Genere=(?)");
            ps.setString(1, genere);
            ResultSet rs = ps.executeQuery();
            CategoriaExtractor catExtractor=new CategoriaExtractor();
            while(rs.next()) {
                result.add(catExtractor.extract(rs));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Categoria doRetrieveById(long idCategoria){
        Categoria c = null;
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM categoria as cat WHERE idCategoria=?");
            ps.setLong(1,idCategoria);
            ResultSet rs = ps.executeQuery();
            CategoriaExtractor catExtractor=new CategoriaExtractor();
            if(rs.next()) {
                c=catExtractor.extract(rs);
            }
            return c;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}

