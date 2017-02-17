package tikape.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.domain.Ketju;

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

    @Override
    public void delete(Integer key) throws SQLException {
        //ei toteuteta
    }

}
