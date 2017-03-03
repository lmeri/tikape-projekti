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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import tikape.domain.Ketju;
import tikape.domain.Viesti;



public class ViestiDao implements Dao<Viesti, Integer>{
    
    private Database database;
    //private Dao<Ketju, Integer> ketjuDao;
    
    public ViestiDao(Database database) {
        this.database = database;
        //this.ketjuDao = ketjuDao;
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
        String aika= rs.getString("aikaleima");

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
            String aika= rs.getString("aikaleima");

            viestit.add(new Viesti(id, kirjoittaja, viesti, ketju, aika));
        }

        rs.close();
        stmt.close();
        connection.close();

        return viestit;
    }
    
    public List<Viesti> getAllFromKetju(Integer ketjuId) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viesti WHERE ketju = ?");
        stmt.setInt(1, ketjuId);
        ResultSet rs = stmt.executeQuery();
        List<Viesti> viestit = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id_tunnus");
            String kirjoittaja = rs.getString("kirjoittaja");
            String viesti = rs.getString("viesti");
            Integer ketju = rs.getInt("ketju");
            String aikaleima = rs.getString("aikaleima");

            viestit.add(new Viesti(id, kirjoittaja, viesti, ketju, aikaleima));
        }

        rs.close();
        stmt.close();
        connection.close();

        return viestit;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        
    }
    
    public void insertViesti(String kirjoittaja, String viesti, Integer id) throws SQLException {
        Connection connection = database.getConnection();
        
        Timestamp a = new Timestamp(System.currentTimeMillis());
        String aika = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(a);
        //String aika = a.toString();
        
        
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Viesti (viesti, aikaleima, kirjoittaja, ketju) VALUES (?, ?, ?, ?)");

        stmt.setString(1, viesti);
        stmt.setString(2, aika);
        stmt.setString(3, kirjoittaja);
        stmt.setInt(4, id);
        stmt.executeUpdate();
        
        stmt.close();
        connection.close();

    }
    
}
