package com.vieecoles.dto;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

public class connexionDto extends PanacheEntityBase{

	private String email ;
	private String login ;
	private  String motdePasse ;
	private  Long  profilid ;
	private  Long  ecoleid ;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMotdePasse() {
		return motdePasse;
	}

	public void setMotdePasse(String motdePasse) {
		this.motdePasse = motdePasse;
	}

	public Long getProfilid() {
		return profilid;
	}

	public void setProfilid(Long profilid) {
		this.profilid = profilid;
	}

	public Long getEcoleid() {
		return ecoleid;
	}

	public void setEcoleid(Long ecoleid) {
		this.ecoleid = ecoleid;
	}
}
