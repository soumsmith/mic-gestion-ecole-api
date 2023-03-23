package com.vieecoles.services.etats;

import com.vieecoles.dto.BoursierDto;
import com.vieecoles.dto.MatriculeClasseDto;
import com.vieecoles.dto.NiveauDto;
import com.vieecoles.projection.BulletinSelectDto;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class BulletinClasseServices {
    @Inject
    EntityManager em;

    public List<BulletinSelectDto>  bulletinParClasse(Long idEcole,String classe, String libelleAnnee,String libelleTrimetre){
        int LongTableau;

        List<MatriculeClasseDto> classeNiveauDtoList = new ArrayList<>() ;
        TypedQuery<MatriculeClasseDto> q = em.createQuery( "SELECT new com.vieecoles.dto.MatriculeClasseDto(b.matricule,b.libelleClasse) from  Bulletin b , DetailBulletin d where b.id= d.bulletin.id and  b.ecoleId =:idEcole and b.libelleClasse=:classe group by b.matricule,b.libelleClasse "
                , MatriculeClasseDto.class);
        classeNiveauDtoList = q.setParameter("idEcole", idEcole)
                .setParameter("classe", classe)
                .getResultList() ;
System.out.println("classeNiveauDtoList "+classeNiveauDtoList.toString());


        LongTableau= classeNiveauDtoList.size();
        List<BulletinSelectDto> resultatsListElevesDto = new ArrayList<>();


        System.out.println("Longueur "+LongTableau);
        for (int i=0; i< LongTableau;i++) {
              List<BulletinSelectDto> resultatsListEleves = new ArrayList<>();
             System.out.println("idEcole "+idEcole);
            System.out.println("Matricule "+classeNiveauDtoList.get(i).getMatricule());
            System.out.println("libelleTrimetre "+libelleTrimetre);
            System.out.println("libelleAnnee "+libelleAnnee);
            System.out.println("classe "+classe);
            resultatsListEleves = getInfosBulletin(idEcole,classeNiveauDtoList.get(i).getMatricule(),libelleAnnee,libelleTrimetre) ;

            System.out.println("resultatsListEleves "+resultatsListEleves.toString());
            if(resultatsListEleves.size()>0)
            resultatsListElevesDto.addAll(resultatsListEleves);
        }

        return  resultatsListElevesDto ;
    }


@Transactional
public List<BulletinSelectDto>  getInfosBulletin(Long idEcole , String matricule, String libelleAnnee,String libelleTrimetre  ){
    List<BulletinSelectDto> detailsBull =new ArrayList<>();
    TypedQuery<BulletinSelectDto> q = em.createQuery( "SELECT new com.vieecoles.projection.BulletinSelectDto(b.ecoleId,b.nomEcole,b.statutEcole,b.urlLogo,b.adresseEcole,b.telEcole,b.anneeLibelle, b.libellePeriode,b.matricule,b.nom, b.prenoms, b.sexe,b.dateNaissance,b.lieuNaissance,b.nationalite,b.redoublant,b.boursier,b.affecte,b.libelleClasse,b.effectif,b.totalCoef,b.totalMoyCoef,b.nomPrenomProfPrincipal,b.heuresAbsJustifiees,b.heuresAbsNonJustifiees,b.moyGeneral,b.moyMax,b.moyMin,b.moyAvg,b.moyAn,b.rangAn,b.appreciation,b.dateCreation,b.codeQr,b.statut,d.matiereLibelle,d.moyenne,d.rang,d.coef ,d.moyCoef,d.appreciation,d.categorie,d.num_ordre,b.rang,d.nom_prenom_professeur,d.categorieMatiere) from DetailBulletin  d join d.bulletin b where b.matricule=:matricule " +
            "and b.ecoleId=:idEcole and b.anneeLibelle=:libelleAnnee and b.libellePeriode=: libelleTrimetre  order by d.num_ordre ASC  ", BulletinSelectDto.class);
    detailsBull = q.setParameter("matricule", matricule)
            .setParameter("libelleAnnee", libelleAnnee)
            .setParameter("libelleTrimetre", libelleTrimetre)
            .setParameter("idEcole", idEcole)
            .getResultList() ;
    return detailsBull ;
}





}
