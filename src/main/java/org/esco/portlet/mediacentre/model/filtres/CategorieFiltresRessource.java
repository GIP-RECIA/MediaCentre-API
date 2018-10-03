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
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.esco.portlet.mediacentre.model.ressource.ListeRessource;
import org.esco.portlet.mediacentre.model.ressource.Ressource;

/**
 * @author elecaude
 *
 */
public class CategorieFiltresRessource extends CategorieFiltresCalcules {

	/* 
	 * ===============================================
	 * Propriétés de la classe 
	 * =============================================== 
	 */
	
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
	 * @see org.esco.portlet.mediacentre.model.filtres.CategorieFiltresCalcules#initialiser(java.util.List, java.util.List)
	 */
	@Override
	public void initialiser(Map<String, List<String>> userInfoMap, List<Ressource> ressources) throws Exception {
		if (ressources == null) {
			return;
		}
		
		ListeRessource listeRessource = new ListeRessource(ressources); 
    	List<String> listeValeurs = new ArrayList<>();
    	for (Ressource rs: listeRessource.getRessources()) {
    		final List<String> values = rs.getValeursAttribut(getNomAttributFiltre());
    		if (!values.isEmpty()) {
    			listeValeurs.addAll(values);
			} else {
				listeValeurs.add(this.getDefaultEmptyValue());
			}
		}

    	List<Filtre> filtres = new ArrayList<Filtre>();
    	Collections.sort(listeValeurs);
    	
		if (StringUtils.isNotBlank(getLibelleTous())) {
			Filtre filtre = new Filtre();
			filtre.setId(getId());
			filtre.setLibelle(getLibelleTous());			
			filtre.setCaseSelectAll(true);
			filtre.setRegexpAttribut(".*");
			filtres.add(filtre);
		}
		
		for (int i=0 ; i<listeValeurs.size() ; i++) {
			String valeur = listeValeurs.get(i);
			
			Filtre filtre = new Filtre();
			filtre.setId(getId() + "_" + i);
			filtre.setLibelle(valeur);
			filtre.setDefaultEmptyValue(this.getDefaultEmptyValue());
    		filtre.setNomAttribut(getNomAttributFiltre());
    		filtre.setRegexpAttribut(Pattern.quote(valeur));
    		filtres.add(filtre);			
		}
				
		setFiltres(filtres);
	}
}
