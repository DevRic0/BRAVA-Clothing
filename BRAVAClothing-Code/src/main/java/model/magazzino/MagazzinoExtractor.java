package model.magazzino;

import model.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MagazzinoExtractor implements ResultSetExtractor<Magazzino> {

    @Override
    public Magazzino extract(ResultSet rs) throws SQLException {
        Magazzino m = new Magazzino();
        m.setNome(rs.getString("mag.nome"));
        m.setIndirizzo(rs.getString("mag.indirizzo"));
        m.setIdMagazzino(rs.getLong("mag.idMagazzino"));
        return m;
    }
}
