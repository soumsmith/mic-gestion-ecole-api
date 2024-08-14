UPDATE eleve
    JOIN photos_adamsy ON eleve.eleve_matricule = photos_adamsy.matricule
    SET eleve.cheminphoto = photos_adamsy.chemin;


UPDATE inscriptions
    JOIN eleve ON eleve.eleveid = inscriptions.eleve_eleveid
    SET inscriptions.cheminphoto = eleve.cheminphoto;


-----------------------------------------------------------
bulletins primaire
SELECT ev.noteSur , ne.note_elevenote ,ne.appreciation , ne.date_creation ,
       te.type_evaluation_libelle , an.annee_scolaire_libelle ,an.annee_scolaireid,
       an.niveau_enseignement_id ,pe.periodeid ,pe.periodelibelle ,
       eh.alias_matiere_libelle ,eh.num_ordre_affichage, el.elevenom ,el.eleveprenom ,el.eleve_matricule ,
       cl.classelibelle , cl.classeid
FROM
    evaluation ev , note_eleve ne , type_evaluation te ,
    annee_scolaire an , periode pe , ecole_has_matiere eh ,
    inscriptions ins , inscriptions_has_classe ic , classe cl ,
    eleve el
WHERE
    ev.type_evaluation_type_evaluationid = te.type_evaluationid and
    ev.classe_classeid = cl.classeid and
    ev.matiere_matiereid = eh.id and
    ev.periode_periodeid = pe.periodeid and
    ev.annee_id = an.annee_scolaireid  and
    ne.evaluation_evaluationid = ev.evaluationid and
    ic.inscriptions_inscriptionsid = ins.inscriptionsid and
    ne.inscription_has_eleve_id = ic.id and
    ic.classe_classeid =cl.classeid and
    ins.eleve_eleveid= el.eleveid and el.eleve_matricule ='TST21909693E' and pe.periodeid =5


;

