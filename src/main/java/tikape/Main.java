package tikape;

import java.util.HashMap;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.database.AlueDao;
import tikape.database.Database;
import tikape.database.KetjuDao;
import tikape.database.ViestiDao;

public class Main {

    public static void main(String[] args) throws Exception {
        Database database = new Database("jdbc:sqlite:database.db");
        database.init();

        AlueDao alueDao = new AlueDao(database);
        KetjuDao ketjuDao = new KetjuDao(database);
        ViestiDao viestiDao = new ViestiDao(database);

        get("/", (req, res) -> {
            res.redirect("/alueet");
            return "ok";
        });

        // "Etusivu", Keskustelufoorumin alkunäkymä, näyttää kaikki alueet.
        get("/alueet", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("alueet", alueDao.findAll());
            return new ModelAndView(map, "alueet");
        }, new ThymeleafTemplateEngine());

        // Näkymä kun on valittu alue. Listaa kymmenen viimeisintä ketjua.
        get("/alueet/:alue", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("ketjut", ketjuDao.findAllFrom(Integer.parseInt(req.params("alue"))));
            return new ModelAndView(map, "alue");
        }, new ThymeleafTemplateEngine());

        // Näkymä kun on valittu Ketju. Listaa ketjun viestit.
        get("alueet/:alue/:ketju", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("viestit", viestiDao.getAllFromKetju(Integer.parseInt(req.params("ketju"))));

            return new ModelAndView(map, "viesti");
        }, new ThymeleafTemplateEngine());

        post("/alueet", (req, res) -> {
            String nimi = req.queryParams("nimi");
            alueDao.insertAlue(nimi);
            return "ok";
        });

        post("/alue/:alue", (req, res) -> {
            String nimi = req.queryParams("nimi");
            ketjuDao.insertKetju(nimi, Integer.parseInt(req.params("alue")));
            return "ok";
        });

        post("/:alue/:ketju", (req, res) -> {
            String nimi = req.queryParams("kirjoittaja");
            String viesti = req.queryParams("viesti");
            viestiDao.insertViesti(nimi, viesti, Integer.parseInt(req.params("ketju")));
            return "ok";
        });
    }
}
