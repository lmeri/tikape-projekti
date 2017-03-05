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
    private Integer maara;
    private String aikaleima;
   

    public Alue(Integer i, String n) {
        this.id = i;
        this.nimi = n;
        
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
