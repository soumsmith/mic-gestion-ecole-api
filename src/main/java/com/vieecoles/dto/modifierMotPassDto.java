package com.vieecoles.dto;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

public class modifierMotPassDto extends PanacheEntityBase{

	private  String login ;
	private  String motdePasse ;
	private String confirmMotPass ;

	public modifierMotPassDto() {
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getMotdePasse() {
		return motdePasse;
	}

	public void setMotdePasse(String motdePasse) {
		this.motdePasse = motdePasse;
	}

	public String getConfirmMotPass() {
		return confirmMotPass;
	}

	public void setConfirmMotPass(String confirmMotPass) {
		this.confirmMotPass = confirmMotPass;
	}
}
