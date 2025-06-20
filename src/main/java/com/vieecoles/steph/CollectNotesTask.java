package com.vieecoles.steph;

import java.util.ArrayList;
import java.util.List;

import jakarta.inject.Inject;

import com.vieecoles.steph.entities.Constants;
import com.vieecoles.steph.entities.Evaluation;
import com.vieecoles.steph.entities.Notes;
import com.vieecoles.steph.services.NoteService;

public class CollectNotesTask implements Runnable { // ou Callable si vous devez renvoyer une valeur
	@Inject
	NoteService noteService;
        private final List<Notes> noteList;
        private final Evaluation ev;

        public CollectNotesTask(List<Notes> noteList, Evaluation ev) {
            this.noteList = noteList;
            this.ev = ev;
        }

        @Override
        public void run() { // ou Object call() si vous utilisez Callable
            List<Notes> listNotesByEvaluation = new ArrayList<>();
            System.out.println(ev.getMatiereEcole().getLibelle() + " :: pec ->" + ev.getPec().toString());
            if (ev.getPec() != null && ev.getPec() == Constants.PEC_1) {
                listNotesByEvaluation = noteService.getNotesClasseWithPec(ev.getCode(), Constants.PEC_1);
                synchronized (noteList) {
                    noteList.addAll(listNotesByEvaluation); // noteList est partagée par toutes les instances de CollectNotesTask, donc synchronisez l'ajout
                }
            }
        }
    }
