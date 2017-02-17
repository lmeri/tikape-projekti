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
        lista.add("CREATE TABLE Alue (id_tunnus integer PRIMARY KEY, nimi varchar(50));");
        lista.add("INSERT INTO Alue (nimi) VALUES ('Pelit');");
        lista.add("INSERT INTO Alue (nimi) VALUES ('Elokuvat');");
        lista.add("INSERT INTO Alue (nimi) VALUES ('Kirjat');");
        
        lista.add("CREATE TABLE Ketju (id_tunnus integer PRIMARY KEY, nimi varchar(50) NOT NULL, alue integer NOT NULL, FOREIGN KEY(alue) REFERENCES Alue(id_tunnus));");
        lista.add("INSERT INTO Ketju (nimi, alue) VALUES ('Borderlands 2', 1);");
        lista.add("INSERT INTO Ketju (nimi, alue) VALUES ('Skyrim', 1);");
        lista.add("INSERT INTO Ketju (nimi, alue) VALUES ('Harry Potter', 3);");
        
        lista.add("CREATE TABLE Viesti (id_tunnus integer PRIMARY KEY, viesti varchar(500), aikaleima timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP, kirjoittaja varchar(16) NOT NULL, ketju integer NOT NULL, FOREIGN KEY(ketju) REFERENCES Ketju(id_tunnus));");
        lista.add("INSERT INTO Viesti (viesti, kirjoittaja, ketju) VALUES ('Jeejee', 'boo', 1);");
        lista.add("INSERT INTO Viesti (viesti, kirjoittaja, ketju) VALUES ('kirjoitan hieman', 'lol', 3);");
        lista.add("INSERT INTO Viesti (viesti, kirjoittaja, ketju) VALUES ('woohoo', 'omg', 3);");

        return lista;
    }
}
