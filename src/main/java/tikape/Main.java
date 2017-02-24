package tikape;

import java.util.HashMap;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.database.AlueDao;
import tikape.database.Database;
import tikape.database.KetjuDao;
import tikape.database.OpiskelijaDao;
import tikape.database.ViestiDao;

public class Main {

    public static void main(String[] args) throws Exception {
        Database database = new Database("jdbc:sqlite:database.db");
        database.init();

        AlueDao alueDao = new AlueDao(database);
        KetjuDao ketjuDao = new KetjuDao(database);
        ViestiDao viestiDao = new ViestiDao(database);

        // "Etusivu", Keskustelufoorumin alkunäkymä, näyttää kaikki alueet.
        get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("alueet", alueDao.findAll());
            return new ModelAndView(map, "alueet");
        }, new ThymeleafTemplateEngine());

        // Näkymä kun on valittu alue. Listaa kymmenen viimeisintä ketjua.
        get("/alueet/:alue", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("ketjut", ketjuDao.getLastTen(Integer.parseInt(req.params("alue"))));
            return new ModelAndView(map, "alue");
        }, new ThymeleafTemplateEngine());

        // Näkymä kun on valittu Ketju. Listaa ketjun viestit.
        get("/:alue/:ketju", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("viestit", viestiDao.getAllFromKetju(Integer.parseInt(req.params("ketju"))));

            return new ModelAndView(map, "ketju");
        }, new ThymeleafTemplateEngine());
    }
}
