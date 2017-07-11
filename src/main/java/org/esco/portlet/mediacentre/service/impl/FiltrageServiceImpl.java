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
package org.esco.portlet.mediacentre.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.esco.portlet.mediacentre.model.filtres.CategorieFiltres;
import org.esco.portlet.mediacentre.model.filtres.Filtre;
import org.esco.portlet.mediacentre.model.ressource.Ressource;
import org.esco.portlet.mediacentre.service.IFiltrageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class FiltrageServiceImpl implements IFiltrageService {

	/* 
	 * ===============================================
	 * Propriétés de la classe 
	 * =============================================== 
	 */
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
	 * @see org.esco.portlet.mediacentre.service.IFiltrageService#filtrerRessources(java.util.List, java.util.List)
	 */
	@Override
	public List<Ressource> filtrerRessources(List<CategorieFiltres> categoriesFiltres, List<Ressource> ressources) throws Exception {
    	List<Ressource> ressourcesFiltrees = new ArrayList<Ressource>();
    	
    	for (Ressource ressource : ressources) {
    		boolean categoriePassant = true;
    		
    		// Verifie si la ressource correspond à toutes les catégories
	    	for (CategorieFiltres categorie : categoriesFiltres ) {
	    		
	    		// Verifie si la ressource correspond à au moins un filtre de la categorie
	    		boolean filtrePassant = false;
	    		for (Filtre filtre : categorie.getFiltres()) {
	    			if (filtre.estPassante(ressource)) {
	    				filtrePassant = true;
	    				break;
	    			}
	    		}
	    		categoriePassant = categoriePassant && filtrePassant;
	    		if (!categoriePassant) {
	    			break;
	    		}
	    	}
	    	if (categoriePassant) {
	    		ressourcesFiltrees.add(ressource);
	    	}
    	}
    	
    	return ressourcesFiltrees;
	}
	
}
