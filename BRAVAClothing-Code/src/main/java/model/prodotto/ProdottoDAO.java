package model.prodotto;

import model.ConPool;
import model.categoria.Categoria;
import model.magazzino.Magazzino;
import model.prodotto.Prodotto;

import javax.sql.rowset.serial.SerialBlob;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.LinkedList;
public class ProdottoDAO {
    public Prodotto doRetrieveById(long id) {
        Prodotto p;
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("SELECT * FROM prodotto as pro WHERE idProdotto=?");
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            ProdottoExtractor proExtractor = new ProdottoExtractor();
            if (rs.next()) {
                p = proExtractor.extract(rs);
                return p;
            }
            return null;
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Prodotto> doRetrieveAll(){
        ArrayList<Prodotto> prodotti = new ArrayList<>();
        try(Connection con = ConPool.getConnection()){
            PreparedStatement s = con.prepareStatement("SELECT * FROM prodotto as pro");
            ResultSet rs = s.executeQuery();
            ProdottoExtractor proExtractor = new ProdottoExtractor() ;
            while(rs.next()){
                prodotti.add(proExtractor.extract(rs));
            }
            return prodotti;
        } catch(SQLException | IOException e){
            throw new RuntimeException(e);
        }
    }

    public boolean doChangesWithImg(Prodotto prodotto){
        String address="C:\\ProgramData\\MySQL\\MySQL Server 8.0\\Uploads\\";
        try(Connection con = ConPool.getConnection()){
            address=address+prodotto.getBase64Image();
            PreparedStatement ps = con.prepareStatement("UPDATE prodotto p SET p.nome = (?), p.prezzo = (?),p.mag_fk = (?), p.cat_fk = (?), p.inofferta = (?)," +
                    " p.descrizioneBreve = (?), p.descrizioneDettagliata = (?), p.quantita= (?), image= LOAD_FILE(?) WHERE p.IdProdotto = (?);");
            ps.setString(1, prodotto.getNome());
            ps.setDouble(2, prodotto.getPrezzo());
            ps.setLong(3,prodotto.getMagazzino().getIdMagazzino());
            ps.setLong(4,prodotto.getCategoria().getIdCategoria());
            ps.setBoolean(5, prodotto.isInOfferta());
            ps.setString(6, prodotto.getDescrizioneBreve());
            ps.setString(7, prodotto.getDescrizioneDettagliata());
            ps.setLong(8,prodotto.getQuantita());
            ps.setString(9,address);
            ps.setLong(10, prodotto.getIdProdotto());
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }
            return true;
        } catch(SQLException e){
            return false;
        }
    }
    public boolean doChanges(Prodotto prodotto){
        try(Connection con = ConPool.getConnection()){
            PreparedStatement ps = con.prepareStatement("UPDATE prodotto p SET p.nome = (?), p.prezzo = (?),p.mag_fk = (?), p.cat_fk = (?), p.inofferta = (?)," +
                    " p.descrizioneBreve = (?), p.descrizioneDettagliata = (?), p.quantita= (?) WHERE p.IdProdotto = (?);");
            ps.setString(1, prodotto.getNome());
            ps.setDouble(2, prodotto.getPrezzo());
            ps.setLong(3,prodotto.getMagazzino().getIdMagazzino());
            ps.setLong(4,prodotto.getCategoria().getIdCategoria());
            ps.setBoolean(5, prodotto.isInOfferta());
            ps.setString(6, prodotto.getDescrizioneBreve());
            ps.setString(7, prodotto.getDescrizioneDettagliata());
            ps.setLong(8,prodotto.getQuantita());
            ps.setLong(9,prodotto.getIdProdotto());
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }
            return true;
        } catch(SQLException e){
            return false;
        }
    }

    public void addProdotto(Prodotto prodotto, Categoria categoria, Magazzino magazzino) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO prodotto (nome, prezzo, descrizioneBreve, descrizioneDettagliata, inOfferta, idProdotto,mag_fk,cat_fk,quantita) VALUES(?,?,?,?,?,?,?,?,?)");
            ps.setString(1, prodotto.getNome());
            ps.setDouble(2, prodotto.getPrezzo());
            ps.setString(3, prodotto.getDescrizioneBreve());
            ps.setString(4, prodotto.getDescrizioneDettagliata());
            ps.setBoolean(5, prodotto.isInOfferta());
            ps.setLong(6, prodotto.getIdProdotto());
            ps.setLong(7,magazzino.getIdMagazzino());
            ps.setLong(8,categoria.getIdCategoria());
            ps.setLong(9,prodotto.getQuantita());
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean addProdottoImg(Prodotto prodotto, Categoria categoria, Magazzino magazzino) {
        String address="C:\\ProgramData\\MySQL\\MySQL Server 8.0\\Uploads\\";
        try (Connection con = ConPool.getConnection()) {
            address=address+prodotto.getBase64Image();
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO prodotto (nome, prezzo, descrizioneBreve, descrizioneDettagliata, inOfferta, idProdotto,image,mag_fk,cat_fk,quantita) VALUES(?,?,?,?,?,?,LOAD_FILE(?),?,?,?)");
            ps.setString(1, prodotto.getNome());
            ps.setDouble(2, prodotto.getPrezzo());
            ps.setString(3, prodotto.getDescrizioneBreve());
            ps.setString(4, prodotto.getDescrizioneDettagliata());
            ps.setBoolean(5, prodotto.isInOfferta());
            ps.setLong(6, prodotto.getIdProdotto());
            ps.setString(7,address);
            ps.setLong(8,magazzino.getIdMagazzino());
            ps.setLong(9,categoria.getIdCategoria());
            ps.setLong(10,prodotto.getQuantita());
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public ArrayList<Prodotto> doRetrieveProdottiWithCategoria(long id){
        ArrayList<Prodotto> prodotti = new ArrayList<>();
        try (Connection con = ConPool.getConnection()) {
            String query = "SELECT * FROM prodotto as pro INNER JOIN categoria ON pro.cat_fk = categoria.idCategoria WHERE categoria.idCategoria = " + id;
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            ProdottoExtractor proExtractor = new ProdottoExtractor();
            while( rs.next()) {
                Prodotto p;
                p = proExtractor.extract(rs);
                prodotti.add(p);
            }
        } catch (SQLException | IOException throwable) {
            throwable.printStackTrace();
        }
        return prodotti;
    }

    public ArrayList<Prodotto> doRetrieveProdottiinOfferta(){
        ArrayList<Prodotto> prodotti = new ArrayList<>();
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM prodotto as pro WHERE pro.inOfferta = (?)");
            ps.setBoolean(1, true);
            ResultSet rs = ps.executeQuery();
            ProdottoExtractor proExtractor = new ProdottoExtractor();
            while( rs.next()) {
                Prodotto p;
                p = proExtractor.extract(rs);
                prodotti.add(p);
            }
        } catch (SQLException | IOException throwable) {
            throwable.printStackTrace();
        }
        return prodotti;
    }

    public void deleteById(long id){
        try (Connection con = ConPool.getConnection()) {
            String query ="DELETE FROM prodotto as pro WHERE pro.IdProdotto = (?);";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setLong(1, id);
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("DELETE error.");
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    /*public Blob stringToBlob(String img) throws SQLException {
        byte[] buff = img.getBytes();
        Blob blob = new SerialBlob(buff);
        return blob;
    }

    public void retrieveEncode64Image(Prodotto p){
        try(Connection con = ConPool.getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT image FROM prodotto as pro WHERE pro.idProdotto=?");
            ps.setLong(1, p.getIdProdotto());
            ResultSet rs = ps.executeQuery();
        Blob blob = rs.getBlob("pro.image");
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
            p.setBase64Image(base64Image);
            inputStream.close();
            outputStream.close();
        }
    } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        }

    }*/
}
