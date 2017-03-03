package tikape;

import java.util.ArrayList;
import java.util.HashMap;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.database.AlueDao;
import tikape.database.Database;
import tikape.database.KetjuDao;
import tikape.database.ViestiDao;
import tikape.domain.Alue;
import tikape.domain.Ketju;

public class Main {

    public static void main(String[] args) throws Exception {
        if (System.getenv("PORT") != null) {
            port(Integer.valueOf(System.getenv("PORT")));
        }
        
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
        get("alueet/:alue/:viesti", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("viestit", viestiDao.getAllFromKetju(Integer.parseInt(req.params("viesti"))));
            return new ModelAndView(map, "viesti");
        }, new ThymeleafTemplateEngine());

        // Metodi uuden alueen lisäämiseen.
        post("/alueet", (req, res) -> {
            String nimi = req.queryParams("nimi");
            alueDao.insertAlue(nimi);
            res.redirect("/alueet");
            return "ok";
        });

        // Metodi uuden ketjun lisäämiseen.
        post("/alueet/:alue", (req, res) -> {
            String nimi = req.queryParams("nimi");
            ketjuDao.insertKetju(nimi, Integer.parseInt(req.params("alue")));
            res.redirect("/alueet/" + req.params(":alue"));
            return "ok";
        });

        // Metodi uuden viestin lisäämiseen.
        post("alueet/:alue/:viesti", (req, res) -> {
            String nimi = req.queryParams("kirjoittaja");
            String viesti = req.queryParams("viesti");
            viestiDao.insertViesti(nimi, viesti, Integer.parseInt(req.params("viesti")));
            res.redirect("/alueet/" + req.params(":alue") + "/" + req.params(":viesti"));
            return "ok";
        });
    }
}
