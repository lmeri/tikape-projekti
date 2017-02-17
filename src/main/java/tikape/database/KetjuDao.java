package tikape.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import tikape.domain.Ketju;
import tikape.domain.Viesti;

public class KetjuDao implements Dao<Ketju, Integer> {

    private Database database;

    public KetjuDao(Database database) {
        this.database = database;
    }

    @Override
    public Ketju findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Ketju WHERE id_tunnus = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id_tunnus = rs.getInt("id_tunnus");
        String nimi = rs.getString("nimi");
        Integer alue = rs.getInt("alue");

        Ketju ketju = new Ketju(id_tunnus, nimi, alue);

        rs.close();
        stmt.close();
        connection.close();

        return ketju;
    }

    @Override
    public List<Ketju> findAll() throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Ketju");

        ResultSet rs = stmt.executeQuery();
        List<Ketju> ketjut = new ArrayList<>();
        while (rs.next()) {
            Integer id_tunnus = rs.getInt("id_tunnus");
            String nimi = rs.getString("nimi");
            Integer alue = rs.getInt("alue");

            ketjut.add(new Ketju(id_tunnus, nimi, alue));
        }

        rs.close();
        stmt.close();
        connection.close();

        return ketjut;
    }

    public List<Ketju> getLastTen() throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Ketju WHERE alue = ? ORDER BY id_tunnus DESC LIMIT 10");

        ResultSet rs = stmt.executeQuery();
        List<Ketju> ketjut = new ArrayList<>();
        while (rs.next()) {
            Integer id_tunnus = rs.getInt("id_tunnus");
            String nimi = rs.getString("nimi");
            Integer alue = rs.getInt("alue");

            ketjut.add(new Ketju(id_tunnus, nimi, alue));
        }

        rs.close();
        stmt.close();
        connection.close();

        return ketjut;
    }

    public void insertKetju(String nimi, Integer alue) throws SQLException {
        Connection connection = database.getConnection();
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("INSERT INTO Ketju (nimi, alue) VALUES ('" + nimi + "', " + alue + ")");
        stmt.close();
        connection.close();
    }

    @Override
    public void delete(Integer key) throws SQLException {
        //ei toteuteta
    }

}
