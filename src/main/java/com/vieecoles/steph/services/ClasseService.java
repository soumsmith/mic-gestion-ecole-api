package com.vieecoles.steph.services;

import com.vieecoles.steph.entities.Classe;
import com.vieecoles.steph.entities.Ecole;
import com.vieecoles.steph.entities.LangueVivante;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.net.URI;
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

	public List<Classe> getListClasseAllFields() {
		try {
			//logger.info("........ in list <<<<>>>>>");
			return populateNullFieldClasse(Classe.listAll());
		}catch(Exception e) {
			e.printStackTrace();
			return null ;
		}
	}

	public Classe findById(long id) {
		//logger.info(String.format("find by id :: %s", id));
		return Classe.findById(id);
	}

	public List<Classe> findByBranche(long id) {
		//logger.info(String.format("find by Branche id :: %s", id));
		return Classe.find("branche.id = ?1",id).list();
	}
	@Transactional
	public Response save(Classe classe) {
//	logger.info("persist classe ...");
		System.out.println(classe);
		Ecole ecole;
		// A supprimer lorsque le credential contenant l ecole sera disponible
		// Par defaut pour tout enregistrement on set l ecole id à 1
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
		if(cl != null) {
			cl.setAnnee(classe.getAnnee());
			cl.setBranche(classe.getBranche());
			cl.setLangueVivante(classe.getLangueVivante());
			cl.setCode(classe.getCode());
			cl.setLibelle(classe.getLibelle());
			cl.setEffectif(classe.getEffectif());
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
