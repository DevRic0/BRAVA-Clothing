package model.prodotto;

import model.ResultSetExtractor;
import model.categoria.Categoria;
import model.magazzino.Magazzino;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

public class ProdottoExtractor implements ResultSetExtractor<Prodotto> {
    @Override
    public Prodotto extract(ResultSet rs) throws SQLException, IOException {
        Prodotto p = new Prodotto();
        p.setNome(rs.getString("pro.nome"));
        p.setPrezzo(rs.getDouble("pro.prezzo"));
        p.setDescrizioneBreve((rs.getString("pro.descrizioneBreve")));
        p.setDescrizioneDettagliata((rs.getString("pro.descrizioneDettagliata")));
        p.setInOfferta(rs.getBoolean("pro.inOfferta"));
        p.setIdProdotto(rs.getLong("pro.idProdotto"));
        p.setQuantita(rs.getInt("pro.quantita"));
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
        int idcat=rs.getInt("pro.cat_fk");
        int idmag=rs.getInt("pro.mag_fk");
        Categoria c=new Categoria();
        c.setIdCategoria(idcat);
        p.setCategoria(c);
        Magazzino m=new Magazzino();
        m.setIdMagazzino(idmag);
        p.setMagazzino(m);
        return p;
    }
}
