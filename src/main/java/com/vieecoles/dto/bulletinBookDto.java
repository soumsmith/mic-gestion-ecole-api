package com.vieecoles.dto;

import com.vieecoles.projection.BulletinSelectDto;

import java.util.List;

public class bulletinBookDto {
    private List<BulletinSelectDto> bulletinSelectDto ;

    public List<BulletinSelectDto> getBulletinSelectDto() {
        return bulletinSelectDto;
    }

    public void setBulletinSelectDto(List<BulletinSelectDto> bulletinSelectDto) {
        this.bulletinSelectDto = bulletinSelectDto;
    }

    @Override
    public String toString() {
        return "bulletinBookDto{" +
                "bulletinSelectDto=" + bulletinSelectDto +
                '}';
    }
}
