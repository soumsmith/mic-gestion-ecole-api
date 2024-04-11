package com.vieecoles.services;

import com.vieecoles.dto.BarroResultatDto;

public class BarroService {
    public BarroResultatDto getInfos(){
        BarroResultatDto m = new BarroResultatDto ();
        String vnom ,vprenom,vmatricule ;
        vnom= getNom ();
        vprenom =getPrenNom () ;
        vmatricule = getMatricule () ;
        m.setNom (vnom);
        m.setPrenom (vprenom);
        m.setMatricule (vmatricule);
        return  m;
    }

    String getNom(){
        return  "NOM";
    }

    String getPrenNom(){
        return  "PRENOM";
    }

    String getMatricule(){
        return  "Matricule";
    }
}
