/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import tikape.domain.Alue;
import tikape.domain.Ketju;

/**
 *
 * @author mkatri
 */
public class AlueDao implements Dao<Alue, Integer> {

    public Database database;

    public AlueDao(Database a) {
        this.database = a;
    }

    @Override
    public Alue findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Alue WHERE id_tunnus = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("id_tunnus");
        String nimi = rs.getString("nimi");

        Alue o = new Alue(id, nimi);

        rs.close();
        stmt.close();
        connection.close();

        return o;
    }

    @Override
    public List<Alue> findAll() throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT Alue.id_tunnus, Alue.nimi, MAX(Viesti.aikaleima) AS aikaleima, COUNT(Viesti.id_tunnus) AS maara FROM Alue LEFT JOIN Ketju ON Ketju.alue = Alue.id_tunnus LEFT JOIN Viesti ON Viesti.ketju = Ketju.id_tunnus GROUP BY Alue.id_tunnus ORDER BY Alue.nimi ASC");
        ResultSet rs = stmt.executeQuery();
        List<Alue> alueet = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt(1);
            String nimi = rs.getString(2);
            String aikaleima = rs.getString("aikaleima");
            Integer maara = rs.getInt("maara");
            Alue k = new Alue(id, nimi);
            k.setAikaleima(aikaleima);
            k.setMaara(maara);
            alueet.add(k);
        }

        rs.close();
        stmt.close();
        connection.close();

        return alueet;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        //nope
    }

    public void insertAlue(String key) throws SQLException {
        Connection connection = database.getConnection();

        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Alue (nimi) VALUES (?)");
        stmt.setString(1, key);
        stmt.executeUpdate();

        stmt.close();
        connection.close();

    }

}
