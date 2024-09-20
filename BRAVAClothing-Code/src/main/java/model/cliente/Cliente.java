package model.cliente;

import model.ordine.Ordine;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Cliente {
    public Cliente(){ super(); }

    public Cliente(String cognome, String nome, String email, String username, String password, String indirizzo, String telefono, long idCliente, boolean isAdmin) {
        this.cognome = cognome;
        this.nome = nome;
        this.email = email;
        this.username = username;
        this.password = password;
        this.indirizzo = indirizzo;
        this.telefono = telefono;
        this.idCliente = idCliente;
        this.isAdmin = isAdmin;
        this.ordini=new ArrayList<>();
    }

    public String getCognome() { return cognome; }
    public void setCognome(String cognome) { this.cognome = cognome; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.reset();
            digest.update(password.getBytes(StandardCharsets.UTF_8));
            this.password = String.format("%040x", new BigInteger(1, digest.digest()));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    public String getIndirizzo() { return indirizzo; }
    public void setIndirizzo(String indirizzo) { this.indirizzo = indirizzo; }
    public long getIdCliente() { return idCliente; }
    public void setIdCliente(long idCliente) { this.idCliente = idCliente; }
    public boolean isAdmin() { return isAdmin; }
    public void setAdmin(boolean admin) { isAdmin = admin; }
    public ArrayList<Ordine> getOrdini() { return ordini; }
    public void setOrdini(ArrayList<Ordine> order) { this.ordini = ordini; }
    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return idCliente == cliente.idCliente;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "cognome='" + cognome + '\'' +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", indirizzo='" + indirizzo + '\'' +
                ", telefono='" + telefono + '\'' +
                ", idCliente=" + idCliente +
                ", isAdmin=" + isAdmin +
                '}';
    }

    private String cognome;
    private String nome;
    private String email;
    private String username;
    private String password;
    private String indirizzo;
    private String telefono;
    private long idCliente;
    private boolean isAdmin;
    ArrayList<Ordine> ordini;
}
