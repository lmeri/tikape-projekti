package tikape.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import tikape.domain.Alue;
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

    public ArrayList<Ketju> findAllFrom(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT Ketju.id_tunnus, Ketju.nimi, Ketju.alue, MAX(Viesti.aikaleima) AS aikaleima, COUNT(Viesti.id_tunnus) AS maara FROM Ketju LEFT JOIN Viesti ON Viesti.ketju = Ketju.id_tunnus WHERE alue = ? GROUP BY Ketju.id_tunnus ORDER BY Viesti.aikaleima DESC LIMIT 10");
        stmt.setInt(1, key);
        ResultSet rs = stmt.executeQuery();

        ArrayList<Ketju> ketjut = new ArrayList<>();

        while (rs.next()) {
            Integer id_tunnus = rs.getInt(1);
            String nimi = rs.getString(2);
            Integer alue = rs.getInt(3);
            String aikaleima = rs.getString(4);
            Integer maara = rs.getInt("maara");
            Ketju k = new Ketju(id_tunnus, nimi, alue);
            k.setAikaleima(aikaleima);
            k.setMaara(maara);
            ketjut.add(k);

        }
        connection.close();

        return ketjut;
    }

    public void insertKetju(String nimi, Integer alue) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Ketju (nimi, alue) VALUES (?, ?)");
        stmt.setString(1, nimi);
        stmt.setInt(2, alue);
        stmt.executeUpdate();

        stmt.close();
        connection.close();
    }

    @Override
    public void delete(Integer key) throws SQLException {
        //ei toteuteta
    }

}
