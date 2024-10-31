package com.vieecoles.dto;

public class NiveauOrderDto {
    String niveau;
    Integer orderNiveau;

    public NiveauOrderDto(String niveau, Integer orderNiveau) {
        this.niveau = niveau;
        this.orderNiveau = orderNiveau;
    }

    public Integer getOrderNiveau() {
        return orderNiveau;
    }

    public void setOrderNiveau(Integer orderNiveau) {
        this.orderNiveau = orderNiveau;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    @Override
    public String toString() {
        return "NiveauDto{" +
                "niveau='" + niveau + '\'' +
                '}';
    }
}
