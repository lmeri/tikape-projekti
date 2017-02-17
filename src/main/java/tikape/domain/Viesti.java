/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.domain;

import java.sql.Timestamp;


public class Viesti {
    private int id_tunnus;
    private String kirjoittaja;
    private String viesti;
    private int ketju;
    private Timestamp aikaleima;
    
    public Viesti(int id_tunnus, String kirjoittaja, String viesti, int ketju, Timestamp aikaleima) {
        this.id_tunnus = id_tunnus;
        this.kirjoittaja = kirjoittaja;
        this.viesti = viesti;
        this.ketju = ketju;
        this.aikaleima = aikaleima;   
    }
    
    public Integer getId () {
        return this.id_tunnus;
    }
    
    public void setId (Integer id) {
        this.id_tunnus = id;
    }
    
    public String getKirjoittaja () {
        return this.kirjoittaja;
    }
    
    public void setKirjoittaja (String kirjuri) {
        this.kirjoittaja = kirjuri;
    }
    
    public String getViesti () {
        return this.viesti;
    }
    
    public void setViesti (String viesti) {
        this.viesti = viesti;
    }
    public Integer getKetju () {
        return this.ketju;
    }
    public void setKetju (Integer ketju) {
        this.ketju = ketju;
    }
    public Timestamp getAikaleima () {
        return this.aikaleima;
    }
    
    public void setAikaleima (Timestamp aika) {
        this.aikaleima = aika;
    }
    
    
}
