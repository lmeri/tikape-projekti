/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.domain;

import java.util.ArrayList;

/**
 *
 * @author mkatri
 */
public class Alue {

    private Integer id;
    private String nimi;
   

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
    

}