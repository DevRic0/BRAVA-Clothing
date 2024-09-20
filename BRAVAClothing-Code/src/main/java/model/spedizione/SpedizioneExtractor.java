package model.spedizione;

import model.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SpedizioneExtractor implements ResultSetExtractor<Spedizione> {
    @Override
    public Spedizione extract(ResultSet rs) throws SQLException {
        Spedizione p = new Spedizione();
        p.setData(rs.getDate("spe.data"));
        p.setStatus(rs.getString("spe.status"));
        p.setSpese(rs.getDouble("spe.spese"));
        p.setIdSpedizione(rs.getLong("spe.idSpedizione"));
        return p;
    }
}
