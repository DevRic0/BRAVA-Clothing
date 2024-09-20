package model.cliente;

import model.ConPool;
import model.ordine.Ordine;
import model.ordine.OrdineExtractor;

import java.sql.*;
import java.util.ArrayList;

public class ClienteDAO {
    public ArrayList<Cliente> doRetrieveAll(){
        ArrayList<Cliente> result=new ArrayList<Cliente>();
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM cliente as cli");
            ResultSet rs = ps.executeQuery();
            ClienteExtractor cliExtractor=new ClienteExtractor();
            while(rs.next()) {
                result.add(cliExtractor.extract(rs));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Cliente doRetrieveClienteByEmailPassword(String email,String pass){
        Cliente p = null;
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM cliente as cli WHERE cli.email=? AND cli.password=SHA1(?)");
            ps.setString(1,email);
            ps.setString(2,pass);
            ResultSet rs = ps.executeQuery();
            ClienteExtractor cliExtractor=new ClienteExtractor();
            if(rs.next())
                p=cliExtractor.extract(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return p;
    }

    public Cliente doRetrieveClienteWithOrdini(long id){
        Cliente p = new Cliente();
        try (Connection con = ConPool.getConnection()) {
            String query = "SELECT * FROM cliente as cli INNER JOIN ordine as ord ON cli.idCliente = ord.cli_fk WHERE cli.idCliente = " + id;
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                ClienteExtractor cliExtractor=new ClienteExtractor();
                p=cliExtractor.extract(rs);
                OrdineExtractor ordExtractor=new OrdineExtractor();
                while (rs.next()) {
                    Ordine o = new Ordine();
                    o=ordExtractor.extract(rs);
                    p.getOrdini().add(o);
                }
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return p;
    }

    public Cliente doRetrieveById(long id){
        Cliente p = null;
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM cliente as cli WHERE idCliente=?");
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            ClienteExtractor cliExtractor=new ClienteExtractor();
            if(rs.next()) {
                p=cliExtractor.extract(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return p;
    }

    public void addCliente(Cliente p){
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO cliente (nome, cognome, email, username, password, indirizzo, isAdmin, telefono, idCliente) VALUES(?,?,?,?,SHA1(?),?,?,?,?)");
            ps.setString(1, p.getNome());
            ps.setString(2, p.getCognome());
            ps.setString(3, p.getEmail());
            ps.setString(4,p.getUsername());
            ps.setString(5,p.getPassword());
            ps.setString(6,p.getIndirizzo());
            ps.setBoolean(7, p.isAdmin());
            ps.setString(8, p.getTelefono());
            ps.setLong(9,p.getIdCliente());
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void doChanges(Cliente c){
        try (Connection con = ConPool.getConnection()) {
            Statement st = con.createStatement();
            String query = "UPDATE Cliente cl SET cl.nome='" + c.getNome() + "', " + "cl.cognome='"+c.getCognome() + "', cl.email='"+c.getEmail() +"'," +
                    "cl.username='"+c.getUsername()+"', cl.indirizzo='"+c.getIndirizzo()+"', cl.isAdmin="+
                    c.isAdmin()+", cl.telefono='"+c.getTelefono()+"' WHERE cl.idCliente=" + c.getIdCliente() + ";";
            st.executeUpdate(query);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void doChangesWithPass(Cliente c){
        try (Connection con = ConPool.getConnection()) {
            Statement st = con.createStatement();
            String query = "UPDATE Cliente cl SET cl.nome='" + c.getNome() + "', " + "cl.cognome='"+c.getCognome() + "', cl.email='"+c.getEmail() +"'," +
                    "cl.username='"+c.getUsername()+"',cl.password=SHA1('"+c.getPassword()+"'), cl.indirizzo='"+c.getIndirizzo()+"', cl.isAdmin="+
                    c.isAdmin()+", cl.telefono='"+c.getTelefono()+"' WHERE cl.idCliente=" + c.getIdCliente() + ";";
            st.executeUpdate(query);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
