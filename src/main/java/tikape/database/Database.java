package tikape.database;

import java.net.URI;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private String databaseAddress;

    public Database(String databaseAddress) throws ClassNotFoundException {
        this.databaseAddress = databaseAddress;
    }

    

    public void init() {
        List<String> lauseet = null;
        if (this.databaseAddress.contains("postgres")) {
            lauseet = postgreLauseet();
        } else {
            lauseet = sqliteLauseet();
        }

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
    
    public Connection getConnection() throws SQLException {
        if (this.databaseAddress.contains("postgres")) {
            try {
                URI dbUri = new URI(databaseAddress);

                String username = dbUri.getUserInfo().split(":")[0];
                String password = dbUri.getUserInfo().split(":")[1];
                String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();

                return DriverManager.getConnection(dbUrl, username, password);
            } catch (Throwable t) {
                System.out.println("Error: " + t.getMessage());
                t.printStackTrace();
            }
        }

        return DriverManager.getConnection(databaseAddress);
    }
    
    private List<String> postgreLauseet() {
        ArrayList<String> lista = new ArrayList<>();

        // tietokantataulujen luomiseen tarvittavat komennot suoritusjärjestyksessä
        lista.add("DROP TABLE Alue;");
        lista.add("DROP TABLE Ketju;");
        lista.add("DROP TABLE Viesti;");
        // heroku käyttää SERIAL-avainsanaa uuden tunnuksen automaattiseen luomiseen
        lista.add("CREATE TABLE Alue (id_tunnus SERIAL PRIMARY KEY, nimi varchar(50));");
        lista.add("CREATE TABLE Ketju (id_tunnus SERIAL PRIMARY KEY, nimi varchar(50), alue integer NOT NULL, FOREIGN KEY(alue) REFERENCES Alue.id_tunnus);");
        lista.add("CREATE TABLE Viesti (id_tunnus SERIAL PRIMARY KEY, viesti varchar(500), aikaleima timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP, kirjoittaja varchar(16) NOT NULL, ketju integer NOT NULL, FOREIGN KEY(ketju) REFERENCES Ketju(id_tunnus));");
        //lista.add("INSERT INTO Tuote (nimi) VALUES ('postgresql-tuote');");

        return lista;
    }

    private List<String> sqliteLauseet() {
        ArrayList<String> lista = new ArrayList<>();

        // tietokantataulujen luomiseen tarvittavat komennot suoritusjärjestyksessä
        //lista.add("CREATE TABLE Alue (id integer PRIMARY KEY, nimi varchar(50));");

        
        //lista.add("CREATE TABLE Ketju (id integer PRIMARY KEY, nimi varchar(50), alue integer NOT NULL, FOREIGN KEY(alue) REFERENCES Alue.id_tunnus);");

        
        //lista.add("CREATE TABLE Viesti (id_tunnus integer PRIMARY KEY, viesti varchar(500), aikaleima timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP, kirjoittaja varchar(16) NOT NULL, ketju integer NOT NULL, FOREIGN KEY(ketju) REFERENCES Ketju(id_tunnus));");


        return lista;
    }
}
