/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.domain;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author mkatri
 */
public class Alue {

    private Integer id;
    private String nimi;
    private ArrayList<Ketju> lista;
    private int lkm;
    private Timestamp aika;
   

    public Alue(Integer i, String n, int lkm, Timestamp aika) {
        this.id = i;
        this.nimi = n;
        this.aika = aika;
        this.lkm = lkm;
        this.lista = new ArrayList();
        
    }
    
    public Alue(Integer i, String n) {
        this.id = i;
        this.nimi = n;
        this.lista = new ArrayList();
        
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public Integer getId() {
        return id;
    }

    public String getNimi() {
        return nimi;
    }
    
//    public Ketju getKetju() {
//        
//    }
    

}
