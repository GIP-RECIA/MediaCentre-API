/**
 * Copyright © 2017 GIP-RECIA (https://www.recia.fr/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.esco.portlet.mediacentre.model.filtres;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.esco.portlet.mediacentre.model.ressource.Ressource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author elecaude
 *
 */
public class Filtre implements Cloneable{
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
    		log.warn("L'expression regulière du filtre \"" + getId() + "\" n'est pas renseignée pour l'attribut");
    		return false;
    	}
    	
    	List<Object> valeurs = ressource.getValeursAttribut(getNomAttribut());
    	
    	for (Object valeur : valeurs) {
    		if (valeur == null) {
    			continue;
    		}
    		String valeurStr = valeur.toString();
    		if (valeurStr.matches(getRegexpAttribut())) {
    			return true;
    		}
    	}
    	
    	return false;
    }

    /**
     * @param userInfoMap Map contenant le profil de l'utilisateur
     * @return true si le filtre concerne l'utilisateur, false Sinon
     * @throws Exception
     */
    public boolean concerneUtilisateur(Map<String, List<String>> userInfoMap) {
    	String regexp = getRegexpPopulation();
    	if (userInfoMap == null || StringUtils.isBlank(getPopulation()) || StringUtils.isBlank(regexp)) {
    		return true;
    	}
    	
    	List<String> valeurs = userInfoMap.get(getPopulation());
    	if (valeurs == null) {
    		return false;
    	}
    	
    	for (String valeur : valeurs) {
    		if (valeur.matches(regexp)) {
    			return true;
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
	
	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Object clone(){
		Filtre filtre = null;
		try {
			filtre = (Filtre) super.clone();
			filtre.setId(this.getId());
			filtre.setLibelle(this.getLibelle());
			filtre.setNomAttribut(this.getNomAttribut());
			filtre.setRegexpAttribut(this.getRegexpAttribut());
			filtre.setPopulation(this.getPopulation());
			filtre.setRegexpPopulation(this.getRegexpPopulation());
			filtre.setActif(this.isActif());
			filtre.setCaseSelectAll(this.isCaseSelectAll());
			
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return filtre;
	}
}
