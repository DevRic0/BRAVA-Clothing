package model.categoria;

import model.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoriaExtractor implements ResultSetExtractor<Categoria> {

    @Override
    public Categoria extract(ResultSet rs) throws SQLException {
        Categoria c = new Categoria();
        c.setNome(rs.getString("cat.nome"));
        c.setDescrizione(rs.getString("cat.descrizione"));
        c.setIdCategoria(rs.getLong("cat.idCategoria"));
        c.setGenere(rs.getString("cat.genere"));
        return c;
    }
}
