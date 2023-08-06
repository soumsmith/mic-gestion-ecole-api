package com.vieecoles.services.etats;

import com.vieecoles.dto.BoursierDto;
import com.vieecoles.dto.NiveauDto;
import com.vieecoles.dto.ResultatsElevesAffecteDto;
import com.vieecoles.dto.parametreDto;
import com.vieecoles.entities.operations.ecole;
import com.vieecoles.entities.parametre;
import com.vieecoles.services.eleves.InscriptionService;
import com.vieecoles.services.souscription.SousceecoleService;

import javax.enterprise.context.ApplicationScoped;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class BulletinRapportServices {
    @Inject
    EntityManager em;
    @Inject
    InscriptionService inscriptionService ;
    @Inject
    SousceecoleService sousceecoleService ;
@Transactional
    public List<parametreDto>  getInfos(Long idEcole , String libelleTrimestre ,String libelleAnnee  ,String libelleClasse ) throws IOException {
        int LongTableau;

        List<NiveauDto> classeNiveauDtoList = new ArrayList<>() ;
        TypedQuery<NiveauDto> q = em.createQuery( "SELECT new com.vieecoles.dto.NiveauDto(b.matricule) from Bulletin b  where b.ecoleId =:idEcole and b.libellePeriode=:periode and b.anneeLibelle=:annee " +
                " and b.libelleClasse=:libelleClasse ", NiveauDto.class);
        classeNiveauDtoList = q.setParameter("idEcole", idEcole)
                .setParameter("annee", libelleAnnee)
                .setParameter("periode", libelleTrimestre)
                .setParameter("libelleClasse", libelleClasse)
                .getResultList() ;

        LongTableau= classeNiveauDtoList.size();
        List<parametreDto> mList = new ArrayList<>(LongTableau);
        ecole myEcole= new ecole() ;

        for (int i=0; i< 1;i++) {
            myEcole=sousceecoleService.getInffosEcoleByID(idEcole);
            parametreDto m = new parametreDto() ;

            parametre m1 = new parametre() ;
            m1= parametre.findById(1L);

            m.setFiligramme( m1.getFiligramme());
            m.setIdparametre(1L);
            m.setLibelle(myEcole.getEcoleclibelle());
            mList.add(m) ;

        }

        return  mList ;


    }







}
