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

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.apache.commons.lang.StringUtils;
import org.esco.portlet.mediacentre.model.ressource.Ressource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author elecaude
 *
 */
@Data
@NoArgsConstructor
public class Filtre implements Cloneable{

	@NonNull
	private String id;
	@NonNull
	private String libelle;
	@NonNull
	private String defaultEmptyValue;
	@NonNull
	private String nomAttribut;
	@NonNull
	private String regexpAttribut;
	@NonNull
	private String population;
	@NonNull
	private String regexpPopulation;
	
	private boolean actif = true;
	
	private boolean caseSelectAll = false;
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());

    /** 
     * @param ressource ressource
     * @return true si la ressource est passante pour le filtre, false sinon
     * @throws Exception Exception
     */
    public boolean estPassante(Ressource ressource) throws Exception {
    	if (StringUtils.isEmpty(getRegexpAttribut())) {
    		log.warn("L'expression regulière du filtre \"" + getId() + "\" n'est pas renseignée pour l'attribut");
    		return false;
    	}
    	
    	List<String> valeurs = ressource.getValeursAttribut(getNomAttribut());
    	if (valeurs.isEmpty()) {
    		valeurs.add(this.getDefaultEmptyValue());
		}
    	
    	for (Object valeur : valeurs) {
    		if (valeur == null) {
    			continue;
    		}
    		String valeurStr = valeur.toString();
    		if (valeurStr.matches(getRegexpAttribut())) {
				log.debug("return estPassante {} on filter {}", true, this);
    			return true;
    		}
    	}
    	log.debug("return estPassante {} on filter {}", false, this);
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
			filtre.setDefaultEmptyValue(this.getDefaultEmptyValue());
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
