package model.magazzino;

import model.ConPool;
import model.magazzino.Magazzino;

import java.sql.*;
import java.util.ArrayList;

public class MagazzinoDAO {

    public void addMagazzino(Magazzino m){
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO magazzino (idMagazzino, nome, indirizzo) VALUES(?,?,?)");
            ps.setString(1, m.getNome());
            ps.setString(2, m.getIndirizzo());
            ps.setLong(3, m.getIdMagazzino());
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void doChanges(Magazzino m){
        try (Connection con = ConPool.getConnection()) {
            Statement st = con.createStatement();
            String query = "update magazzino set nome='" + m.getNome() + "', indirizzo=" + m.getIndirizzo() + " " +
                    "where idMagazzino=" + m.getIdMagazzino() + ";";
            st.executeUpdate(query);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    public Magazzino doRetrieveById(long idMagazzino){
        Magazzino m = null;
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM magazzino as mag WHERE idMagazzino=?");
            ps.setLong(1,idMagazzino);
            ResultSet rs = ps.executeQuery();
            MagazzinoExtractor magExtractor=new MagazzinoExtractor();
            if(rs.next()) {
                m=magExtractor.extract(rs);
            }
            return m;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
