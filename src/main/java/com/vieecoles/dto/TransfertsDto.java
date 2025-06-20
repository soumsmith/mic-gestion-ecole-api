package com.vieecoles.dto;

import lombok.Data;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.List;


public class TransfertsDto {

	private String nom;
	private String prenoms;
	private String classe;
	private String redoublant;
	private String dateNaissance;
	private String nredoublant;
	private String decision;
	private String etablissementOrigine;
	private String niveau;
	private String matricule;


	public TransfertsDto(

						 String nom,
						 String prenoms,
						 String classe,
						 String redoublant,
						 String dateNaissance,
						 String nredoublant,
						 String decision,
						 String etablissementOrigine,
						 String niveau,
						 String matricule
						  ) {

		this.nom = nom;
		this.prenoms = prenoms;
		this.classe = classe;
		this.redoublant = redoublant;
		this.dateNaissance = dateNaissance;
		this.nredoublant = nredoublant;
		this.decision = decision;
		this.etablissementOrigine = etablissementOrigine;
		this.niveau = niveau;
		this.matricule=matricule ;

	}

    public TransfertsDto(List<TransfertsDto> transferts) {
    }

    public String getMatricule() {
		return matricule;
	}

	public void setMatricule(String matricule) {
		this.matricule = matricule;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenoms() {
		return prenoms;
	}

	public void setPrenoms(String prenoms) {
		this.prenoms = prenoms;
	}

	public String getClasse() {
		return classe;
	}

	public void setClasse(String classe) {
		this.classe = classe;
	}

	public String getRedoublant() {
		return redoublant;
	}

	public void setRedoublant(String redoublant) {
		this.redoublant = redoublant;
	}

	public String getDateNaissance() {
		return dateNaissance;
	}

	public void setDateNaissance(String dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	public String getNredoublant() {
		return nredoublant;
	}

	public void setNredoublant(String nredoublant) {
		this.nredoublant = nredoublant;
	}

	public String getDecision() {
		return decision;
	}

	public void setDecision(String decision) {
		this.decision = decision;
	}

	public String getEtablissementOrigine() {
		return etablissementOrigine;
	}

	public void setEtablissementOrigine(String etablissementOrigine) {
		this.etablissementOrigine = etablissementOrigine;
	}

	public String getNiveau() {
		return niveau;
	}

	public void setNiveau(String niveau) {
		this.niveau = niveau;
	}
}
