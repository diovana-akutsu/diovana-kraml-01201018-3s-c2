package com.example.continuada02.dominio;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Golpe {

    private Integer idLutadorBate;

    private Integer idLutadorApanha;

    public Integer getIdLutadorBate() {
        return idLutadorBate;
    }

    public void setIdLutadorBate(Integer idLutadorBate) {
        this.idLutadorBate = idLutadorBate;
    }

    public Integer getIdLutadorApanha() {
        return idLutadorApanha;
    }

    public void setIdLutadorApanha(Integer idLutadorApanha) {
        this.idLutadorApanha = idLutadorApanha;
    }
}
