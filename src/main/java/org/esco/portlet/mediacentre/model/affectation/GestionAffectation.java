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
package org.esco.portlet.mediacentre.model.affectation;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GestionAffectation {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	/* 
	 * ===============================================
	 * Propriétés de la classe 
	 * =============================================== 
	 */
	private String id;
	
	private String nom;
	
	private String description;
	
	private String lien;
	
	private String population;
	
	private String regexpPopulation;
	
	/* 
	 * ===============================================
	 * Constructeurs de la classe 
	 * =============================================== 
	 */
	public GestionAffectation() {

	}
	
	/* 
	 * ===============================================
	 * Getter / Setter de la classe 
	 * =============================================== 
	 */
    /**
	 * Getter de la propriété id
	 * @return la propriété id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Setter de la propriété id
	 * @param id identifiant.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Getter de la propriété nom
	 * @return la propriété nom
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * Setter de la propriété nom
	 * @param nom nom.
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * Getter de la propriété description
	 * @return la propriété description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Setter de la propriété description
	 * @param description description.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Getter de la propriété lien
	 * @return la propriété lien
	 */
	public String getLien() {
		return lien;
	}

	/**
	 * Setter de la propriété lien
	 * @param lien lien.
	 */
	public void setLien(String lien) {
		this.lien = lien;
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
	 * @param population population.
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
	 * @param regexpPopulation regexpPopulation
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
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "GestionAffectation [log=" + log + ", id=" + id + ", nom=" + nom + ", description=" + description
				+ ", lien=" + lien + ", population=" + population + ", regexpPopulation=" + regexpPopulation + "]";
	}

	/**
     * @param userInfoMap Map contenant le profil de l'utilisateur
     * @return true si le filtre concerne l'utilisateur, false Sinon
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
}
