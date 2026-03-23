package com.vieecoles.services.etats.PvConseilsClasse;

import com.vieecoles.dto.MajorParClasseNiveauDto;
import com.vieecoles.dto.ProcesVerbalListeClasseDto;
import java.util.ArrayList;
import java.util.List;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

@ApplicationScoped
public class MajorClassePvServices {
    @Inject
    EntityManager em;

    public List<ProcesVerbalListeClasseDto> MajorParNiveauClasse(Long idEcole , String libelleAnnee , String libelleTrimestre,String classe){


        Long  effeG,effeF,classF,classG,nonclassF,nonclassG,nbreMoySup10F,nbreMoySup10G,nbreMoyInf999F,nbreMoyInf999G,nbreMoyInf85G,nbreMoyInf85F;
        Double pourMoySup10F ,pourMoySup10G,pourMoyInf999F,pourMoyInf999G,pourMoyInf85G,pourMoyInf85F,moyClasseF,moyClasseG;
       Integer effectifClasse ;
        List<ProcesVerbalListeClasseDto> resultatsListElevesDto = new ArrayList<>();
        List<MajorParClasseNiveauDto> majorExeco = new ArrayList<>();

            Double moyMajor = getMajorDto(idEcole, classe, libelleAnnee, libelleTrimestre);
            majorExeco = getListMajorParClasseNiveau(idEcole, classe, moyMajor, libelleAnnee, libelleTrimestre);

            if (majorExeco != null && majorExeco.size() > 1) {
                for (int k = 0; k < majorExeco.size(); k++) {
                    MajorParClasseNiveauDto major = majorExeco.get(k);
                    ProcesVerbalListeClasseDto dto = new ProcesVerbalListeClasseDto();
                    dto.setMoyenne(major.getMoyGeneral());
                    dto.setMatricule(major.getMatricule());
                    dto.setDateNaissance(major.getAnneeNaiss());
                    dto.setNom(major.getNom());
                    dto.setPrenoms(major.getPrenom());
                    dto.setClasse(classe);
                    dto.setRang(1);
                    resultatsListElevesDto.add(dto);
                }
            } else if (majorExeco != null && !majorExeco.isEmpty()) {
                MajorParClasseNiveauDto major = majorExeco.get(0);
                ProcesVerbalListeClasseDto dto = new ProcesVerbalListeClasseDto();
                dto.setMoyenne(major.getMoyGeneral());
                dto.setMatricule(major.getMatricule());
                dto.setDateNaissance(major.getAnneeNaiss());
                dto.setNom(major.getNom());
                dto.setPrenoms(major.getPrenom());
                dto.setClasse(classe);
                dto.setRang(1);
                resultatsListElevesDto.add(dto);
            }




        return  resultatsListElevesDto ;
    }


    public List<MajorParClasseNiveauDto>  getListMajorParClasseNiveau(Long idEcole ,String classe,Double moy ,String libelleAnnee , String libelleTrimestre ){
        List<MajorParClasseNiveauDto> classeNiveauDtoList = new ArrayList<>();
        try {
            TypedQuery  q= em.createQuery("select new com.vieecoles.dto.MajorParClasseNiveauDto(o.niveau,o.libelleClasse,o.matricule,o.nom,o.prenoms,SUBSTRING(o.dateNaissance,1,4) ,o.sexe,o.appreciation,o.redoublant,o.moyGeneral,o.lv2,o.ordreNiveau) from Bulletin o where  o.ecoleId =:idEcole and   o.libelleClasse=:libelleClasse and o.moyGeneral=:moy and o.libellePeriode=:periode and o.anneeLibelle=:annee ", MajorParClasseNiveauDto.class);
            classeNiveauDtoList = q.setParameter("idEcole",idEcole)
                    .setParameter("libelleClasse",classe)
                    .setParameter("moy",moy)
                    .setParameter("annee", libelleAnnee)
                    .setParameter("periode", libelleTrimestre)
                    .getResultList() ;
            return classeNiveauDtoList ;
        } catch (NoResultException e){
            return new ArrayList<>();
        }

    }

    public Double getMajorDto(Long idEcole , String classe ,String libelleAnnee , String libelleTrimestre){
        Double classeNiveauDtoList ;
        try {
            TypedQuery<Double> q= (TypedQuery<Double>) em.createQuery("select MAX(o.moyGeneral) from Bulletin o where  o.ecoleId =:idEcole  and o.libelleClasse=:libelleClasse and o.libellePeriode=:periode and o.anneeLibelle=:annee");
            classeNiveauDtoList = q.setParameter("idEcole",idEcole)
                    .setParameter("libelleClasse",classe)
                    .setParameter("annee", libelleAnnee)
                    .setParameter("periode", libelleTrimestre)
                    .getSingleResult() ;
            return classeNiveauDtoList ;
        } catch (NoResultException e){
            return 0D ;
        }

    }



}
