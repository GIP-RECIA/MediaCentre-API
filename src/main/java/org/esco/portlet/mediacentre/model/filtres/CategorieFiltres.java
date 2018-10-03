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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * @author elecaude
 *
 */
public class CategorieFiltres implements Cloneable {
	/* 
	 * ===============================================
	 * Propriétés de la classe 
	 * =============================================== 
	 */

	private String id;
	
	private String libelle;

	private boolean valeursMultiples;

	private String defaultEmptyValue;
	
	private boolean categorieExpended = false;

	private List<Filtre> filtres = new ArrayList<>();

	private String population;
	
	private String regexpPopulation;	
	
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
	 * Getter de la propriété valeursMultiples
	 * @return la propriété valeursMultiples
	 */
	public boolean isValeursMultiples() {
		return valeursMultiples;
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
	 * @param id identifiant
	 */
	public void setId(String id) {
		this.id = id;
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
	 * @param population population
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

	/**
	 * Getter de la propriété libelle
	 * @return la propriété libelle
	 */
	public String getLibelle() {
		return libelle;
	}

	/**
	 * Setter de la propriété libelle
	 * @param libelle libelle
	 */
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	/**
	 * Setter de la propriété valeursMultiples
	 * @param valeursMultiples valeursMultiples
	 */
	public void setValeursMultiples(boolean valeursMultiples) {
		this.valeursMultiples = valeursMultiples;
	}

	/**
	 * Getter de la propriété defaultEmptyValue
	 * @return defaultEmptyValue defaultEmptyValue
	 */
	public String getDefaultEmptyValue() {
		return defaultEmptyValue;
	}

	/**
	 * Setter de la propriété defaultEmptyValue
	 * @param defaultEmptyValue defaultEmptyValue
	 */
	public void setDefaultEmptyValue(String defaultEmptyValue) {
		this.defaultEmptyValue = defaultEmptyValue;
	}

	/**
	 * @return the categorieExpended
	 */
	public boolean isCategorieExpended() {
		return categorieExpended;
	}

	/**
	 * @param categorieExpended the categorieExpended to set
	 */
	public void setCategorieExpended(boolean categorieExpended) {
		this.categorieExpended = categorieExpended;
	}

	/**
	 * Getter de la propriété filtres
	 * @return la propriété filtres
	 */
	public List<Filtre> getFiltres() {
		return filtres;
	}
	
	/**
	 * Setter de la propriété filtres
	 * @param filtres filtres
	 */
	public void setFiltres(List<Filtre> filtres) {
		this.filtres = filtres;
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
		return "CategorieFiltres [id=" + id + ", libelle=" + libelle + ", valeursMultiples=" + valeursMultiples
				+ ", defaultEmptyValue=" + defaultEmptyValue + ", categorieExpended=" + categorieExpended
				+ ", filtres=" + filtres + ", population=" + population + ", regexpPopulation=" + regexpPopulation + "]";
	}
	
	@Override
	public Object clone(){
		CategorieFiltres categorieFiltres = null;
		try {
			categorieFiltres = (CategorieFiltres) super.clone();
			categorieFiltres.setId(this.getId());
			categorieFiltres.setLibelle(this.getLibelle());
			categorieFiltres.setValeursMultiples(this.isValeursMultiples());
			categorieFiltres.setDefaultEmptyValue(this.getDefaultEmptyValue());
			categorieFiltres.setCategorieExpended(this.isCategorieExpended());
			categorieFiltres.setPopulation(getPopulation());
			categorieFiltres.setRegexpPopulation(getRegexpPopulation());
			List<Filtre> listFiltre = new ArrayList<Filtre>();
			categorieFiltres.setFiltres(listFiltre);
			
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return categorieFiltres;
	}

	/**
	 * Indique si la categorie correspond à une categorie "calculee"
	 * @return false
	 */
	public boolean estCategorieCalculee() {
		return false;
	}

    /**
     * @param userInfoMap Map contenant le profil de l'utilisateur
     * @return true si le filtre concerne l'utilisateur, false Sinon
     */
    public boolean concerneUtilisateur(Map<String, List<String>> userInfoMap) {
    	String regexp = getRegexpPopulation();
    	if (userInfoMap == null) {
    		return false;
		}
    	if (StringUtils.isBlank(getPopulation()) || StringUtils.isBlank(regexp)) {
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
