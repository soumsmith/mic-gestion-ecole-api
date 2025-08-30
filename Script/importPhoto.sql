USE
ecoleviedbv2;
CREATE VIEW photos_adamsy AS SELECT
                                 `ev`.`eleveid` AS `eleveid`,
                                 `e`.`matricule` AS `matricule`,
                                 `e`.`nom` AS `nom`,
                                 `e`.`prenoms` AS `prenoms`,
                                 `m`.`chemin` AS `chemin`
                             FROM
                                 (
                                     `eleves` `e`
                                         JOIN(`eleve` `ev`
                                         JOIN `medias` `m`)
                                     )
                             WHERE
                                 (
                                     (`e`.`photo` = `m`.`uid`) AND(
                                         `e`.`matricule` = `ev`.`eleve_matricule`
                                         )
                                     )


UPDATE eleve
    JOIN photos_adamsy ON eleve.eleve_matricule = photos_adamsy.matricule
    SET eleve.cheminphoto = photos_adamsy.chemin;


UPDATE inscriptions
    JOIN eleve ON eleve.eleveid = inscriptions.eleve_eleveid
    SET inscriptions.cheminphoto = eleve.cheminphoto;
SET foreign_key_checks = 0;
DROP TABLE `eleves`, `medias`;


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
select count(ih.id) from
    inscriptions_has_classe ih
where i.inscriptionsid= ih.inscriptions_inscriptionsid and

    i.annee_scolaire_annee_scolaireid =226 and
    i.ecole_ecoleid=103
  and  ih.id  not in ( select ih2.id from inscriptions i2 ,
                                          inscriptions_has_classe ih2 ,
                                          note_eleve n2
                       where i2.inscriptionsid= ih2.inscriptions_inscriptionsid and
                           n2.inscription_has_eleve_id=ih2.id  and
                           i2.annee_scolaire_annee_scolaireid =226 and
                           i2.ecole_ecoleid=103)
