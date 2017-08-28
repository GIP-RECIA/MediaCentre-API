package org.esco.portlet.mediacentre.model.filtres;

import java.util.List;

import org.esco.portlet.mediacentre.model.ressource.Ressource;
import org.jvnet.jaxb2_commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author elecaude
 *
 */
public class Filtre {
	/* 
	 * ===============================================
	 * Propriétés de la classe 
	 * =============================================== 
	 */

	private String id;
	
	private String libelle;
	
	private String nomAttribut;
	
	private String regexpAttribut;
	
	private String population;
	
	private String regexpPopulation;
	
	private boolean actif = true;
	
	private boolean caseSelectAll = false;
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	/* 
	 * ===============================================
	 * Constructeurs de la classe 
	 * =============================================== 
	 */

	/* 
	 * ===============================================
	 * Getter / Setter de la classe 
	 * =============================================== 
	 */

	/**
	 * Getter de la propriété actif
	 * @return la propriété actif
	 */
	public boolean isActif() {
		return actif;
	}

	/**
	 * Getter de la propriété libelle
	 * @return la propriété libelle
	 */
	public String getLibelle() {
		return libelle;
	}

	/**
	 * Setter de la propriété libelle
	 * @param libelle 
	 */
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	/**
	 * Setter de la propriété actif
	 * @param actif 
	 */
	public void setActif(boolean actif) {
		this.actif = actif;
	}

	/**
	 * @return the caseSelectAll
	 */
	public boolean isCaseSelectAll() {
		return caseSelectAll;
	}

	/**
	 * @param caseSelectAll the caseSelectAll to set
	 */
	public void setCaseSelectAll(boolean caseSelectAll) {
		this.caseSelectAll = caseSelectAll;
	}

	/**
	 * Getter de la propriété id
	 * @return la propriété id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Setter de la propriété id
	 * @param id 
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Getter de la propriété nomAttribut
	 * @return la propriété nomAttribut
	 */
	public String getNomAttribut() {
		return nomAttribut;
	}

	/**
	 * Setter de la propriété nomAttribut
	 * @param nomAttribut 
	 */
	public void setNomAttribut(String nomAttribut) {
		this.nomAttribut = nomAttribut;
	}

	/**
	 * Getter de la propriété regexpAttribut
	 * @return la propriété regexpAttribut
	 */
	public String getRegexpAttribut() {
		return regexpAttribut;
	}

	/**
	 * Setter de la propriété regexpAttribut
	 * @param regexpAttribut 
	 */
	public void setRegexpAttribut(String regexpAttribut) {
		this.regexpAttribut = regexpAttribut;
	}

	/**
	 * Getter de la propriété population
	 * @return la propriété population
	 */
	public String getPopulation() {
		return population;
	}

	/**
	 * Setter de la propriété population
	 * @param population 
	 */
	public void setPopulation(String population) {
		this.population = population;
	}

	/**
	 * Getter de la propriété regexpPopulation
	 * @return la propriété regexpPopulation
	 */
	public String getRegexpPopulation() {
		return regexpPopulation;
	}

	/**
	 * Setter de la propriété regexpPopulation
	 * @param regexpPopulation 
	 */
	public void setRegexpPopulation(String regexpPopulation) {
		this.regexpPopulation = regexpPopulation;
	}


	/* 
	 * ===============================================
	 * Méthodes privées de la classe 
	 * =============================================== 
	 */

	/* 
	 * ===============================================
	 * Méthodes publiques de la classe 
	 * =============================================== 
	 */
    /** 
     * @param ressource
     * @return true si la ressource est passante pour le filtre, false sinon
     * @throws Exception
     */
    public boolean estPassante(Ressource ressource) throws Exception {
    	if (StringUtils.isEmpty(getRegexpAttribut())) {
    		log.error("L'expression regulière du filtre " + getId() + " n'est pas renseignée pour l'attribut");
    		return false;
    	}
    	
    	List<Object> valeurs = ressource.getValeursAttribut(getNomAttribut());
    	
    	for (Object valeur : valeurs) {
    		if (valeur == null) {
    			continue;
    		}
    		if(!caseSelectAll && actif){
	    		String valeurStr = valeur.toString();
	    		if (valeurStr.matches(getRegexpAttribut())) {
	    			return true;
	    		}
    		}
    	}
    	
    	return false;
    }
    
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Filtre [id=" + id + ", libelle=" + libelle + ", nomAttribut=" + nomAttribut + ", regexpAttribut="
				+ regexpAttribut + ", population=" + population + ", regexpPopulation=" + regexpPopulation + ", actif="
				+ actif + "]";
	}
	
}
