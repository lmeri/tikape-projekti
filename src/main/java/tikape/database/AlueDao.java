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
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Alue");

        ResultSet rs = stmt.executeQuery();
        List<Alue> alueet = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id_tunnus");
            String nimi = rs.getString("nimi");

            alueet.add(new Alue(id, nimi));
        }

        rs.close();
        stmt.close();
        connection.close();

        return alueet;
    }
    
    public List<Alue> alueet() throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT Alue.id_tunnus, Alue.nimi, COUNT(Viesti.id_tunnus) AS viestit, max(Viesti.aikaleima) AS viimeisin FROM Viesti JOIN Ketju ON Viesti.ketju = Ketju.id_tunnus JOIN Alue ON Ketju.alue = Alue.id_tunnus GROUP BY Alue.nimi ORDER BY Alue.id_tunnus ASC");
        
        List<Alue> alueet = new ArrayList<>();
        
        
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Integer id = rs.getInt(1);
            String nimi = rs.getString(2);

            alueet.add(new Alue(id, nimi));
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
    
    public void insertAlue(String nimi) throws SQLException {
        Connection connection = database.getConnection();
        
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Alue (nimi) VALUES (?)");

        stmt.setString(1, nimi);
        stmt.executeUpdate();
        
        stmt.close();
        connection.close();

    }

}
