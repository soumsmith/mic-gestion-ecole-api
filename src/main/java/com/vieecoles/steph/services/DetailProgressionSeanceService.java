package com.vieecoles.steph.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

import com.vieecoles.steph.entities.DetailProgressionSeance;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@ApplicationScoped
public class DetailProgressionSeanceService implements PanacheRepositoryBase<DetailProgressionSeance, String> {

	public List<DetailProgressionSeance> getListByProgressionSeance(String id) {
		List<DetailProgressionSeance> list = null;
		try {
			list = DetailProgressionSeance.find("progressionSeance.id = ?1", id).list();
		} catch (RuntimeException e) {
			list = new ArrayList<DetailProgressionSeance>();
		}
		return list;
	}
	@Transactional
	public void save(DetailProgressionSeance d) {
		UUID uuid = UUID.randomUUID();
		d.setId(uuid.toString());
		d.setDateCreation(new Date());
		d.setDateCessation(new Date());
		d.persist();
	}
	@Transactional
	public void update(DetailProgressionSeance d) {
		DetailProgressionSeance ds = DetailProgressionSeance.findById(d.getId());
		if (ds != null) {
			ds.setDetailProgression(d.getDetailProgression());
			ds.setProgressionSeance(d.getProgressionSeance());
			ds.setDateCessation(new Date());
		} else {
			System.out.println("Aucun DetailProgressionSeance trouvé avec id = " + d.getId());
		}
	}
	@Transactional
	public void delete(String id) {
		DetailProgressionSeance.deleteById(id);
	}

	@Transactional
	public void deleteMany(List<String> ids) {
		DetailProgressionSeance.delete("id in (?1)", ids);
		System.out.println(String.format("%s Detail progression seance supprimé(s)", ids.size()));
	}
}
