package com.vieecoles.steph.services;

import com.vieecoles.steph.entities.Classe;
import com.vieecoles.steph.entities.Ecole;
import com.vieecoles.steph.entities.LangueVivante;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Sort;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.Date;
import java.util.List;


@ApplicationScoped
public class ClasseService implements PanacheRepositoryBase<Classe,Integer> {
//Logger logger = Logger.getLogger(ClasseService.class.getName());
	public List<Classe> getListClasse() {
		try {
		//	logger.info("........ in list <<<<>>>>>");
			return Classe.listAll();
		}catch(Exception e) {
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Classe> getListClasseByEcole(Long ecoleId) {
		try {
		//	logger.info("........ in list <<<<>>>>>");
			return Classe.find("ecole.id =?1 and visible = 1", Sort.by("libelle").descending(), ecoleId).list();
		}catch(Exception e) {
			e.printStackTrace();
			return null ;
		}
	}
	/**
	 * Cette méthode permet de lister les classes de manière ordonnée.
	 * 
	 * @param ecoleId
	 * @return La liste des classes
	 */
	public List<Classe> getListSortedByClasseByEcole(Long ecoleId) {
		try {
		//	logger.info("........ in list <<<<>>>>>");
			return Classe.find(" ecole.id =?1 and visible = 1 "
					+ " ORDER BY SUBSTRING(libelle, 1, 1) desc, SUBSTRING(libelle, -1, 1) asc ", ecoleId).list();
		}catch(Exception e) {
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Classe> getListAllClasseByEcole(Long ecoleId) {
		try {
		//	logger.info("........ in list <<<<>>>>>");
			return Classe.find("ecole.id =?1 ", Sort.by("libelle").descending(), ecoleId).list();
		}catch(Exception e) {
			e.printStackTrace();
			return null ;
		}
	}
	
	public long countClassesByEcole(Long ecoleId) {
		try {
		//	logger.info("........ in list <<<<>>>>>");
			return Classe.find("ecole.id =?1", ecoleId).count();		
		}catch(Exception e) {
			e.printStackTrace();
			return 0 ;
		}
	}

	public List<Classe> getListClasseAllFields() {
		try {
			//logger.info("........ in list <<<<>>>>>");
			return populateNullFieldClasse(Classe.listAll());
		}catch(Exception e) {
			e.printStackTrace();
			return null ;
		}
	}
	/**
	 * Cette méthode renvoie la liste des classes visible dans une école
	 *
	 * @param ecoleId identifiant de l'école
	 * @return Liste des classes visibles
	 */
	public List<Classe> getListClasseAllFields(Long ecoleId) {
		try {
			//logger.info("........ in list <<<<>>>>>");
			return populateNullFieldClasse(getListClasseByEcole(ecoleId));
		}catch(Exception e) {
			e.printStackTrace();
			return null ;
		}
	}
	/**
	 * Cette méthode renvoie toutes les classes d'une école
	 *
	 * @param ecoleId identifiant de l'école
	 * 
	 * @return liste des classes
	 */
	public List<Classe> getListAllClasseAllFields(Long ecoleId) {
		try {
			//logger.info("........ in list <<<<>>>>>");
			return populateNullFieldClasse(getListAllClasseByEcole(ecoleId));
		}catch(Exception e) {
			e.printStackTrace();
			return null ;
		}
	}

	public Classe findById(long id) {
		System.out.println(String.format("find by id :: %s", id));
		return Classe.findById(id);
	}
	
	public Classe findByCode(String code) {
		//logger.info(String.format("find by id :: %s", id));
		return Classe.find("code =?1",code).singleResult();
	}

	public List<Classe> findByBranche(long id, Long ecoleId) {
		//logger.info(String.format("find by Branche id :: %s", id));
		return Classe.find("branche.id = ?1 and ecole.id=?2",id, ecoleId).list();
	}
	
	public List<Classe> findVisibleByBranche(long id, Long ecoleId) {
		//logger.info(String.format("find by Branche id :: %s", id));
		return Classe.find("branche.id = ?1 and ecole.id=?2 and visible = 1",id, ecoleId).list();
	}
	
	@Transactional
	public Response save(Classe classe) {
//	logger.info("persist classe ...");
		System.out.println(classe);
		Ecole ecole;
		// A supprimer lorsque le credential contenant l ecole sera disponible
		// Par defaut pour tout enregistrement on set l ecole id à 1
		classe.setDateCreation(new Date());
		classe.setDateUpdate(new Date());
		if(classe.getEcole()== null) {
			ecole = new Ecole();
			ecole.setId(1);
			classe.setEcole(ecole);
		}
		if(classe.getLangueVivante() != null && classe.getLangueVivante().getId() == 0) {
			classe.setLangueVivante(null);
			System.out.println("langue vivante initialisée à null");
		}
		classe.persist();
		return Response.created(URI.create("/classe/" + classe.getId())).build();
	}

	@Transactional
	public Classe update(Classe classe) {
	//	logger.info("updating classe ...");
		Classe cl = Classe.findById(classe.getId());
		classe.setDateUpdate(new Date());
		if(cl != null) {
//			cl.setAnnee(classe.getAnnee());
			cl.setBranche(classe.getBranche());
			cl.setLangueVivante(classe.getLangueVivante());
			cl.setCode(classe.getCode());
			cl.setLibelle(classe.getLibelle());
			cl.setEffectif(classe.getEffectif());
			cl.setVisible(classe.getVisible());
		}
//		logger.info(new Gson().toJson(cl));
		return cl;
	}


	public Classe updateAndDisplay(Classe classe) {
		Classe cl ;
		if(update(classe)!=null) {
			cl = findById(classe.getId());
//			logger.info("classe apres le get");;
//			logger.info(new Gson().toJson(cl));
			return cl;
		}

		return null;


	}

	@Transactional
	public void delete(String id) {

			//logger.info("delete classe id "+id);
			Classe classe = findById(Long.parseLong(id));
			classe.delete();

	}

	public List<Classe> populateNullFieldClasse(List<Classe> classes){
		for(Classe classe : classes) {
			if(classe.getLangueVivante()==null) {
				classe.setLangueVivante(new LangueVivante());
//				classe.getLangueVivante().setId(3);
//				classe.getLangueVivante().setCode("");
//				classe.getLangueVivante().setLibelle("");
			}
//			logger.info(new Gson().toJson(classe));
		}
		return classes;
	}

}
