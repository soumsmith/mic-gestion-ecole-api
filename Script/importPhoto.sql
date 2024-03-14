UPDATE eleve
    JOIN photos_adamsy ON eleve.eleve_matricule = photos_adamsy.matricule
    SET eleve.cheminphoto = photos_adamsy.chemin;


UPDATE inscriptions
    JOIN eleve ON eleve.eleveid = inscriptions.eleve_eleveid
    SET inscriptions.cheminphoto = eleve.cheminphoto;
