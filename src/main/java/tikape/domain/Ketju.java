package tikape.domain;

import java.sql.Timestamp;
import java.util.ArrayList;

public class Ketju {

    private Integer id_tunnus;
    private String nimi;
    private Integer alue;
    private ArrayList<Viesti> lista;
    private String aikaleima;
    private Integer maara;

    public Ketju(int id_tunnus, String nimi, int alue) {
        this.id_tunnus = id_tunnus;
        this.nimi = nimi;
        this.alue = alue;
        this.lista = new ArrayList<Viesti>();
        this.aikaleima = "ketju on tyhjä";
    }

    public Integer getId() {
        return this.id_tunnus;
    }

    public String getNimi() {
        return this.nimi;
    }
    
    public ArrayList<Viesti> getViestit(){
        return this.lista;
    }
    
    public void lisaaViesti(Viesti viesti){
        this.lista.add(viesti);
    }

    public int getAlue() {
        return this.alue;
    }

    public void setId(Integer id) {
        this.id_tunnus = id;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public void setAlue(Integer alue) {
        this.alue = alue;
    }
    
    public void setAikaleima(String aika) {
        this.aikaleima = aika;
    }
    
    public String getAikaleima() {
        return this.aikaleima;
    }
    
    public void setMaara(Integer maara) {
        this.maara = maara;
    }
    
    public int getMaara() {
        return this.maara;
    }
    

}
