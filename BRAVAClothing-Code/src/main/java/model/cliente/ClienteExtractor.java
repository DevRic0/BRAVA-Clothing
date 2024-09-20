package model.cliente;

import model.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClienteExtractor implements ResultSetExtractor<Cliente> {
    @Override
    public Cliente extract(ResultSet rs) throws SQLException {
        Cliente c = new Cliente();
        c.setNome(rs.getString("cli.nome"));
        c.setCognome(rs.getString("cli.cognome"));
        c.setEmail(rs.getString("cli.email"));
        c.setUsername(rs.getString("cli.username"));
        c.setPassword(rs.getString("cli.password"));
        c.setIndirizzo(rs.getString("cli.indirizzo"));
        c.setAdmin(rs.getBoolean("cli.isAdmin"));
        c.setTelefono(rs.getString("cli.telefono"));
        c.setIdCliente(rs.getLong("cli.idCliente"));
        return c;
    }
}
