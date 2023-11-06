package com.vieecoles.services.etats;

import com.vieecoles.dto.*;
import com.vieecoles.entities.operations.Inscriptions;
import com.vieecoles.services.eleves.InscriptionService;
import com.vieecoles.services.souscription.SousceecoleService;
import com.vieecoles.steph.entities.Branche;
import com.vieecoles.steph.entities.Classe;
import com.vieecoles.steph.entities.ClasseMatiere;
import com.vieecoles.steph.entities.Notes;
import com.vieecoles.steph.services.NoteService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ProcesVerbalServices {
    @Inject
    EntityManager em;
    @Inject
    NoteService noteService ;

    public List<ProcesVerbalDto> getProcesVerEval(Long idEcole ,String code){
       List<Notes> notesList ;

       notesList = noteService.getNotesClasse(code);
         Double pour8_50 = null, pour_8_50_inf_9_99 = null, pour_nbreNoteSup10 = null, pour_nbreNoteSup12 = null;
         Long present ,nbreInf8_50, nbre8_50_inf_9_99 , nbreNoteSup10,nbrenbreNoteSup12 ;

       int longEvalu ;
        longEvalu = notesList.size() ;
        System.out.println("Longueur notesList "+notesList.size());
        List<ProcesVerbalDto> procesVerbalDtoList = new ArrayList<>(longEvalu);
        for (int k=0; k< longEvalu;k++) {
            ProcesVerbalDto m= new ProcesVerbalDto() ;
            m.setMatiere(notesList.get(k).getEvaluation().getMatiereEcole().getMatiere().getLibelle());
            m.setCodeEval(notesList.get(k).getEvaluation().getId().toString());
            m.setDateEval(notesList.get(k).getEvaluation().getDate());
            m.setNatureEvaluation(notesList.get(k).getEvaluation().getType().getLibelle());
            m.setHeureDebut(notesList.get(k).getEvaluation().getHeure());
            m.setTemps(notesList.get(k).getEvaluation().getDuree());
            m.setPeriode(notesList.get(k).getEvaluation().getPeriode().getLibelle());
            m.setMatricule(notesList.get(k).getClasseEleve().getInscription().getEleve().getMatricule());
            m.setNomPrenoms(notesList.get(k).getClasseEleve().getInscription().getEleve().getNom()+" "+notesList.get(k).getClasseEleve().getInscription().getEleve().getPrenom());
            m.setClasse(notesList.get(k).getClasseEleve().getClasse().getLibelle());
            m.setNoteObtenu(notesList.get(k).getNote());
            m.setNoteSur(notesList.get(k).getEvaluation().getNoteSur());
            m.setAppreciation(notesList.get(k).getAppreciation());
            m.setObservation(notesList.get(k).getCommentaire());
            m.setEffectif(notesList.get(k).getClasseEleve().getClasse().getEffectif().longValue());
            m.setPresent(getPresent(notesList.get(k).getEvaluation().getId()));
            present = m.getPresent() ;

            m.setAbsent(m.getEffectif()-m.getPresent());
            m.setChapitreDeLecon(notesList.get(k).getEvaluation().getChapitreDeLecon());
            m.setMoyenneEvaluation(getMoyenneEva(notesList.get(k).getEvaluation().getId()));
            m.setForteNote(getPlusForteNote(notesList.get(k).getEvaluation().getId()));
            m.setFaibleNote(getPlusFaibleNote(notesList.get(k).getEvaluation().getId()));
            m.setNbreNoteInf_egal_8_50(getNoteInf_8_5(notesList.get(k).getEvaluation().getId(),8.5d));
            nbreInf8_50 = m.getNbreNoteInf_egal_8_50() ;
            m.setNbreNoteSup_8_50_inf_9_99(getNoteInf_8_50_inf_9_99(notesList.get(k).getEvaluation().getId(),8.5d,9.99d));
           nbre8_50_inf_9_99 = m.getNbreNoteSup_8_50_inf_9_99() ;
            m.setNbreNoteSup10(getNoteSupA(notesList.get(k).getEvaluation().getId(),10d));
            nbreNoteSup10 = m.getNbreNoteSup10() ;
            m.setNbreNoteSup12(getNoteSupA(notesList.get(k).getEvaluation().getId(),12d));
            nbrenbreNoteSup12= m.getNbreNoteSup12() ;


            if(present!=0L) {
                pour8_50 = (((double)nbreInf8_50*100d)/(double)present);
                m.setPour8_50(pour8_50);

                pour_8_50_inf_9_99 = (((double)nbre8_50_inf_9_99*100d)/(double)present);
                m.setPour_8_50_inf_9_99(pour_8_50_inf_9_99);

                pour_nbreNoteSup10 = (((double)nbreNoteSup10*100d)/(double)present);
                m.setPour_nbreNoteSup10(pour_nbreNoteSup10);

                pour_nbreNoteSup12 = (((double)nbrenbreNoteSup12*100d)/(double)present);
                m.setPour_nbreNoteSup12(pour_nbreNoteSup12);

            }




            procesVerbalDtoList.add(m);
        }

        return  procesVerbalDtoList ;

    }

    public ProcesVerbalDto getProcesVerEvalByMatricule(Long idEcole ,String code ,String matricule){
        List<Notes> notesList ;

        notesList = noteService.getNotesClasse(code);
        Double pour8_50 = null, pour_8_50_inf_9_99 = null, pour_nbreNoteSup10 = null, pour_nbreNoteSup12 = null;
        Long present ,nbreInf8_50, nbre8_50_inf_9_99 , nbreNoteSup10,nbrenbreNoteSup12 ;

        List<Notes> noteFiltres = notesList.stream()
                .filter(p -> matricule.equals(p.getClasseEleve().getInscription().getEleve().getMatricule()))
                .collect(Collectors.toList());
        int longEvalu ;
        longEvalu = noteFiltres.size() ;
        System.out.println("Longueur notesList "+notesList.size());
        List<ProcesVerbalDto> procesVerbalDtoList = new ArrayList<>(longEvalu);
        ProcesVerbalDto m= new ProcesVerbalDto() ;
        for (int k=0; k< longEvalu;k++) {


            m.setMatricule(notesList.get(k).getClasseEleve().getInscription().getEleve().getMatricule());
            m.setNomPrenoms(notesList.get(k).getClasseEleve().getInscription().getEleve().getNom()+" "+notesList.get(k).getClasseEleve().getInscription().getEleve().getPrenom());
            m.setClasse(notesList.get(k).getClasseEleve().getClasse().getLibelle());
            m.setNoteObtenu(notesList.get(k).getNote());
            m.setNoteSur(notesList.get(k).getEvaluation().getNoteSur());
            m.setAppreciation(notesList.get(k).getAppreciation());
            m.setObservation(notesList.get(k).getCommentaire());
            m.setEffectif(notesList.get(k).getClasseEleve().getClasse().getEffectif().longValue());
            m.setPresent(getPresent(notesList.get(k).getEvaluation().getId()));
            present = m.getPresent() ;

            m.setAbsent(m.getEffectif()-m.getPresent());
            m.setChapitreDeLecon(notesList.get(k).getEvaluation().getChapitreDeLecon());
            m.setMoyenneEvaluation(getMoyenneEva(notesList.get(k).getEvaluation().getId()));
            m.setForteNote(getPlusForteNote(notesList.get(k).getEvaluation().getId()));
            m.setFaibleNote(getPlusFaibleNote(notesList.get(k).getEvaluation().getId()));
            m.setNbreNoteInf_egal_8_50(getNoteInf_8_5(notesList.get(k).getEvaluation().getId(),8.5d));
            nbreInf8_50 = m.getNbreNoteInf_egal_8_50() ;
            m.setNbreNoteSup_8_50_inf_9_99(getNoteInf_8_50_inf_9_99(notesList.get(k).getEvaluation().getId(),8.5d,9.99d));
            nbre8_50_inf_9_99 = m.getNbreNoteSup_8_50_inf_9_99() ;
            m.setNbreNoteSup10(getNoteSupA(notesList.get(k).getEvaluation().getId(),10d));
            nbreNoteSup10 = m.getNbreNoteSup10() ;
            m.setNbreNoteSup12(getNoteSupA(notesList.get(k).getEvaluation().getId(),12d));
            nbrenbreNoteSup12= m.getNbreNoteSup12() ;


            if(present!=0L) {
                pour8_50 = (((double)nbreInf8_50*100d)/(double)present);
                m.setPour8_50(pour8_50);

                pour_8_50_inf_9_99 = (((double)nbre8_50_inf_9_99*100d)/(double)present);
                m.setPour_8_50_inf_9_99(pour_8_50_inf_9_99);

                pour_nbreNoteSup10 = (((double)nbreNoteSup10*100d)/(double)present);
                m.setPour_nbreNoteSup10(pour_nbreNoteSup10);

                pour_nbreNoteSup12 = (((double)nbrenbreNoteSup12*100d)/(double)present);
                m.setPour_nbreNoteSup12(pour_nbreNoteSup12);

            }


        }

        return  m ;

    }
    public  Double getMoyenneEva(Long idEval){
        try {
            Double   moyClasseF = (Double) em.createQuery("select  avg(n.note) from Notes n ,Evaluation e where n.evaluation.id = e.id and e.id=:idEval")
                    .setParameter("idEval",idEval)
                    .getSingleResult();
            return  moyClasseF ;
        } catch (NoResultException e){
            return 0D ;
        }

    }

    public  Double getPlusForteNote(Long idEval){
        try {
            Double   moyClasseF = (Double) em.createQuery("select  max(n.note) from Notes n ,Evaluation e where n.evaluation.id = e.id and e.id=:idEval")
                    .setParameter("idEval",idEval)
                    .getSingleResult();
            return  moyClasseF ;
        } catch (NoResultException e){
            return 0D ;
        }

    }

    public  Double getPlusFaibleNote(Long idEval){
        try {
            Double   moyClasseF = (Double) em.createQuery("select  min(n.note) from Notes n ,Evaluation e where n.evaluation.id = e.id and e.id=:idEval")
                    .setParameter("idEval",idEval)
                    .getSingleResult();
            return  moyClasseF ;
        } catch (NoResultException e){
            return 0D ;
        }

    }

    public  Long getNoteInf_8_5(Long idEval ,Double moy){
        try {
            Long   moyClasseF = (Long) em.createQuery("select  count(n.note) from Notes n ,Evaluation e where n.evaluation.id = e.id and e.id=:idEval" +
                            " and n.note<=: moy")
                    .setParameter("idEval",idEval)
                    .setParameter("moy",moy)
                    .getSingleResult();
            return  moyClasseF ;
        } catch (NoResultException e){
            return 0L ;
        }

    }
    public  Long getPresent(Long idEval){
        try {
            Long   moyClasseF = (Long) em.createQuery("select  count(n.note) from Notes n ,Evaluation e where n.evaluation.id = e.id and e.id=:idEval")
                    .setParameter("idEval",idEval)
                    .getSingleResult();
            return  moyClasseF ;
        } catch (NoResultException e){
            return 0L ;
        }

    }

    public  Long getNoteInf_8_50_inf_9_99(Long idEval ,Double moy1,Double moy2){
        try {
            Long   moyClasseF = (Long) em.createQuery("select  count(n.note) from Notes n ,Evaluation e where n.evaluation.id = e.id and e.id=:idEval" +
                            " and n.note >: moy1  and n.note<=: moy2")
                    .setParameter("idEval",idEval)
                    .setParameter("moy1",moy1)
                    .setParameter("moy2",moy2)
                    .getSingleResult();
            return  moyClasseF ;
        } catch (NoResultException e){
            return 0L ;
        }

    }

    public  Long getNoteSupA(Long idEval ,Double moy){
        try {
            Long   moyClasseF = (Long) em.createQuery("select  count(n.note) from Notes n ,Evaluation e where n.evaluation.id = e.id and e.id=:idEval" +
                            " and n.note>=: moy")
                    .setParameter("idEval",idEval)
                    .setParameter("moy",moy)
                    .getSingleResult();
            return  moyClasseF ;
        } catch (NoResultException e){
            return 0L ;
        }

    }

}
