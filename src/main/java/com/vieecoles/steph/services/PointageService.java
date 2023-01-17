package com.vieecoles.steph.services;

import com.vieecoles.steph.entities.Pointage;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class PointageService implements PanacheRepositoryBase<Pointage, Long> {

	public List<Pointage> getList() {
		try {
			System.out.println("PointageService.getList()");
			return Pointage.listAll();
		}catch(Exception e) {
			e.printStackTrace();
			return new ArrayList<Pointage>() ;
		}
	}

	public Pointage findById(long id) {
		System.out.println(String.format("find by id :: %s", id));
		return Pointage.findById(id);
	}
}
