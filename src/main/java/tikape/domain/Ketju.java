package tikape.domain;

import java.util.ArrayList;

public class Ketju {

    private Integer id_tunnus;
    private String nimi;
    private Integer alue;
    private ArrayList<Viesti> lista;

    public Ketju(int id_tunnus, String nimi, int alue) {
        this.id_tunnus = id_tunnus;
        this.nimi = nimi;
        this.alue = alue;
        this.lista = new ArrayList<Viesti>();
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

    public Integer getAlue() {
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

}
