package com.vieecoles.services.etats;

import com.vieecoles.dto.BulletinSpiderDto;
import com.vieecoles.dto.MoyenParProfDto;
import com.vieecoles.dto.NiveauDto;
import com.vieecoles.dto.NoteDto;
import com.vieecoles.entities.InfosPersoBulletins;
import com.vieecoles.entities.operations.Inscriptions;
import com.vieecoles.entities.operations.ecole;
import com.vieecoles.entities.parametre;
import com.vieecoles.services.eleves.InscriptionService;
import com.vieecoles.services.souscription.SousceecoleService;
import com.vieecoles.steph.dto.MoyenneEleveDto;
import com.vieecoles.steph.entities.*;
import com.vieecoles.steph.services.NoteService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.*;

@ApplicationScoped
public class MoyenneParProfServices {
    @Inject
    EntityManager em;
    @Inject
    NoteService noteService ;
@Transactional
    public List<MoyenParProfDto>  moyenneParProf(String classe ,String matiere ,String annee ,String periode){
        int LongTableau;



    List<MoyenneEleveDto> noteMoyenne = new ArrayList<>() ;

    noteMoyenne=  noteService.moyennesAndMatiereAndNotesHandle(classe,matiere, annee, periode);

        LongTableau= noteMoyenne.size();
        System.out.println ("Longueur Tabl>>> "+LongTableau);


        List<MoyenParProfDto> mlist = new ArrayList<>();
        for (int i=0; i< LongTableau;i++) {
            MoyenParProfDto m = new MoyenParProfDto() ;
            List<Notes> mesNotes = new ArrayList<>() ;
            List<NoteDto> noteDtos = new ArrayList<>() ;
            m.setMatricule(noteMoyenne.get(i).getEleve().getMatricule());
            m.setNomPrenom(noteMoyenne.get(i).getEleve().getNom()+" "+ noteMoyenne.get(i).getEleve().getPrenom());
            m.setSexe(noteMoyenne.get(i).getEleve().getSexe());
            Map.Entry<EcoleHasMatiere, List< Notes >> firstEntry = null;
            for (Map.Entry<EcoleHasMatiere, List< Notes >> entry :noteMoyenne.get(i).getNotesMatiereMap().entrySet()){
                firstEntry = entry;
                break;
            }
            if(firstEntry!=null){
                m.setMoyenne(firstEntry.getKey().getMoyenne().toString());
                m.setRang(firstEntry.getKey().getRang().equals("1")? "1er(e)":firstEntry.getKey().getRang()+"e");
                mesNotes = firstEntry.getValue() ;
            }
            for (int k =0; k < mesNotes.size() ;k++) {
                NoteDto n = new NoteDto();
                int num = k+1 ;
                n.setNoteEval(mesNotes.get(k).getNote().toString()+"/"+mesNotes.get(k).getEvaluation().getNoteSur());
                n.setInfosEval("N°"+num+" "+mesNotes.get(k).getEvaluation().getType().getLibelle()+" "+mesNotes.get(k).getEvaluation().getDate());
                n.setMatricule(noteMoyenne.get(i).getEleve().getMatricule());
                noteDtos.add(n);
            }
            m.setNoteDtoList(noteDtos);
            mlist.add(m);
        }
    Collections.sort(mlist) ;
        return  mlist ;
    }



}
