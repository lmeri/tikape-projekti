package tikape.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private String databaseAddress;

    public Database(String databaseAddress) throws ClassNotFoundException {
        this.databaseAddress = databaseAddress;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(databaseAddress);
    }

    public void init() {
        List<String> lauseet = sqliteLauseet();

        // "try with resources" sulkee resurssin automaattisesti lopuksi
        try (Connection conn = getConnection()) {
            Statement st = conn.createStatement();

            // suoritetaan komennot
            for (String lause : lauseet) {
                System.out.println("Running command >> " + lause);
                st.executeUpdate(lause);
            }

        } catch (Throwable t) {
            // jos tietokantataulu on jo olemassa, ei komentoja suoriteta
            System.out.println("Error >> " + t.getMessage());
        }
    }

    private List<String> sqliteLauseet() {
        ArrayList<String> lista = new ArrayList<>();

        // tietokantataulujen luomiseen tarvittavat komennot suoritusjärjestyksessä
        lista.add("CREATE TABLE Alue (id integer PRIMARY KEY, nimi varchar(50));");

        
        lista.add("CREATE TABLE Ketju (id integer PRIMARY KEY, nimi varchar(50), alue integer NOT NULL, FOREIGN KEY(alue) REFERENCES Alue.id_tunnus);");

        
        lista.add("CREATE TABLE Viesti (id_tunnus integer PRIMARY KEY, viesti varchar(500), aikaleima timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP, kirjoittaja varchar(16) NOT NULL, ketju integer NOT NULL, FOREIGN KEY(ketju) REFERENCES Ketju(id_tunnus));");


        return lista;
    }
}
