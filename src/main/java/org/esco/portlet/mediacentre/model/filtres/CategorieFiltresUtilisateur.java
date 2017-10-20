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
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.esco.portlet.mediacentre.model.ressource.Ressource;

/**
 * @author elecaude
 *
 */
public class CategorieFiltresUtilisateur extends CategorieFiltresCalcules {

	/* 
	 * ===============================================
	 * Propriétés de la classe 
	 * =============================================== 
	 */
	private String AttributUtilisateur;
	private String AttributUtilisateurDefaut;
	
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
	 * Getter de la propriété attributUtilisateur
	 * @return la propriété attributUtilisateur
	 */
	public String getAttributUtilisateur() {
		return AttributUtilisateur;
	}

	/**
	 * Setter de la propriété attributUtilisateur
	 * @param attributUtilisateur 
	 */
	public void setAttributUtilisateur(String attributUtilisateur) {
		AttributUtilisateur = attributUtilisateur;
	}

	/**
	 * Getter de la propriété attributUtilisateurDefaut
	 * @return la propriété attributUtilisateurDefaut
	 */
	public String getAttributUtilisateurDefaut() {
		return AttributUtilisateurDefaut;
	}

	/**
	 * Setter de la propriété attributUtilisateurDefaut
	 * @param attributUtilisateurDefaut 
	 */
	public void setAttributUtilisateurDefaut(String attributUtilisateurDefaut) {
		AttributUtilisateurDefaut = attributUtilisateurDefaut;
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
	 * @see org.esco.portlet.mediacentre.model.filtres.CategorieFiltres#clone()
	 */
	@Override
	public Object clone() {
		CategorieFiltresUtilisateur categorie = (CategorieFiltresUtilisateur)super.clone();
		categorie.setAttributUtilisateurDefaut(getAttributUtilisateurDefaut());
		return categorie;
	}
		

	/* (non-Javadoc)
	 * @see org.esco.portlet.mediacentre.model.filtres.CategorieFiltresCalcules#initialiser(java.util.Map, java.util.List)
	 */
	@Override
	public void initialiser(Map<String, List<String>> userInfoMap, List<Ressource> ressources) {
		List<String> attributs = userInfoMap.get(getAttributUtilisateur());
		if (attributs == null) {
			return;
		}
		List<String> valeurs = new ArrayList<String>(attributs);
		List<String> valeursDefaut = userInfoMap.get(getAttributUtilisateurDefaut());
		
		if (valeurs.isEmpty()) {
			return;
		}

		Collections.sort(valeurs);
		String valeurDefaut = valeursDefaut != null && !valeursDefaut.isEmpty() ? valeursDefaut.get(0) : null;
		
		List<Filtre> filtres = new ArrayList<Filtre>();
		
		if (isValeursMultiples() &&  StringUtils.isNotBlank(getLibelleTous())) {
			Filtre filtre = new Filtre();
    		filtre.setId(getId());
    		filtre.setLibelle(getLibelleTous());
    		filtre.setActif(false);
    		filtre.setCaseSelectAll(true);
    		filtres.add(filtre);
		}		
		
		for (String valeur : valeurs) {
			Filtre filtre = new Filtre();
			filtre.setActif(false);
			filtre.setId(valeur);
			filtre.setLibelle(valeur);
			filtre.setRegexpAttribut(valeur);
			filtre.setNomAttribut(getNomAttributFiltre());
			filtres.add(filtre);
			if (valeur.equals(valeurDefaut)) {
				filtre.setActif(true);
			}
		}
		setFiltres(filtres);
	}	
	
}
