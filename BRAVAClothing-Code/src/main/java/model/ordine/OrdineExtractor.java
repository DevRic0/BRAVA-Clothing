package model.ordine;

import model.ResultSetExtractor;
import model.cliente.Cliente;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrdineExtractor implements ResultSetExtractor<Ordine> {
    @Override
    public Ordine extract(ResultSet rs) throws SQLException {
        Ordine p = new Ordine();
        p.setIva(rs.getDouble("ord.iva"));
        p.setDataInserimento(rs.getDate("ord.dataInserimento"));
        p.setIdOrdine(rs.getInt("ord.idOrdine"));
        int idCli=rs.getInt("ord.cli_fk");
        Cliente o=new Cliente();
        o.setIdCliente(idCli);
        p.setCliente(o);
        return p;
    }
}
