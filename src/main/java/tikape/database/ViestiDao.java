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
import java.util.List;
import tikape.domain.Viesti;


public class ViestiDao implements Dao<Viesti, Integer>{
    
    private Database database;
    
    public ViestiDao(Database database) {
        this.database = database;
    }

    @Override
    public Viesti findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viesti WHERE id_tunnus = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("id");
        String kirjoittaja = rs.getString("kirjoittaja");
        String viesti = rs.getString("viesti");
        Integer ketju = rs.getInt("ketju");
        Timestamp aika= rs.getTimestamp("aikaleima");

        Viesti v = new Viesti(id, kirjoittaja, viesti, ketju, aika);

        rs.close();
        stmt.close();
        connection.close();

        return v;
    }

    @Override
    public List<Viesti> findAll() throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viesti");

        ResultSet rs = stmt.executeQuery();
        List<Viesti> viestit = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String kirjoittaja = rs.getString("kirjoittaja");
            String viesti = rs.getString("viesti");
            Integer ketju = rs.getInt("ketju");
            Timestamp aika= rs.getTimestamp("aikaleima");

            viestit.add(new Viesti(id, kirjoittaja, viesti, ketju, aika));
        }

        rs.close();
        stmt.close();
        connection.close();

        return viestit;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        
    }
    
}
