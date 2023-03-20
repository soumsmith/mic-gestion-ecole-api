package com.vieecoles.dto;

public class EmptyDto {
    private  String intro ;

    public EmptyDto() {
    }

    public EmptyDto(String intro) {
        this.intro = intro;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }
}
