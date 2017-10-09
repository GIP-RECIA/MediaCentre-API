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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.esco.portlet.mediacentre.model.affectation.GestionAffectation;
import org.esco.portlet.mediacentre.model.filtres.CategorieFiltres;
import org.esco.portlet.mediacentre.model.filtres.CategorieFiltresEtablissement;
import org.esco.portlet.mediacentre.model.filtres.CategorieFiltresUtilisateur;
import org.esco.portlet.mediacentre.model.filtres.Filtre;
import org.esco.portlet.mediacentre.model.ressource.Ressource;
import org.esco.portlet.mediacentre.service.IFiltrageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class FiltrageServiceImpl implements IFiltrageService {

	/* 
	 * ===============================================
	 * Propriétés de la classe 
	 * =============================================== 
	 */
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
    @Value("${userInfo.key.etabIds}")
    private String etabCodesInfoKey;
    
    @Value("${userInfo.key.currentEtabId}")
    private String currentEtabCodeInfoKey;
    
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
	 * @see org.esco.portlet.mediacentre.service.IFiltrageService#preparerFiltrage(java.util.Map, java.util.List, java.util.List, java.util.List, java.util.List)
	 */
	public String preparerFiltrage(
			Map<String, List<String>> userInfoMap, 
			List<CategorieFiltres> categoriesFiltres,  
			List<Ressource> ressources, 
			List<CategorieFiltres> categoriesFiltresCandidats, 
			List<Ressource> ressourcesCandidates) throws Exception {
		
    	//------------------------------------------------------------
    	// Calcul des ressources candidates et des filtres a afficher
    	//------------------------------------------------------------
    	Map<Integer, Ressource> mapRessourcesCandidates = new HashMap<Integer, Ressource>();
    	Map<String, Map<String, List<Integer>>> mapCategories = new HashMap<>();
    	
    	if (log.isDebugEnabled()) {
    		log.debug("Ressources avant filtrage");
    		ObjectMapper mapper = new ObjectMapper();
        	log.debug(mapper.writeValueAsString(ressources));
    	}
    	
    	for (CategorieFiltres categorie : categoriesFiltres ) {
    		if ( !categorie.concerneUtilisateur(userInfoMap)) {
    			continue;
    		}
    				
    		if (categorie.estCategorieEtablissement()) {
    			categorie = (CategorieFiltres)categorie.clone();
    			((CategorieFiltresEtablissement)categorie).initialiser(userInfoMap.get(etabCodesInfoKey), userInfoMap.get(currentEtabCodeInfoKey));	
    		} else if (categorie.estCategorieUtilisateur()) {
    			categorie = (CategorieFiltres)categorie.clone();
    			((CategorieFiltresUtilisateur)categorie).initialiser(userInfoMap);	
    		}
    		
    		Map<String, List<Integer>> mapFiltre = new HashMap<String, List<Integer>>();
    		List<Filtre> filtresClone = new ArrayList<Filtre>();
    		
    		// Pour chaque filtre de la catégorie, on vérifie s'il existe des ressources passantes 
    		int nbFiltreSelectAll=0;
    		for (Filtre filtre : categorie.getFiltres()) {
    			if (!filtre.concerneUtilisateur(userInfoMap)) {
    				continue;
    			}
    			
    			List<Integer> listeRessources = new ArrayList<Integer>();
    			if (!filtre.isCaseSelectAll()) {
        			for (Ressource ressource : ressources) {
        				if (filtre.estPassante(ressource)) {
        					listeRessources.add(ressource.getIdInterne());
        					mapRessourcesCandidates.put(ressource.getIdInterne(), ressource);
        				}
        			}
    			} else {
    				nbFiltreSelectAll++;
    			}
    			
    			// S'il existe au moins une ressource passante, on conserve le filtre
    			if (!listeRessources.isEmpty() || filtre.isCaseSelectAll() || "favoris".equalsIgnoreCase(filtre.getId())) {
    				mapFiltre.put(filtre.getId(), listeRessources);
    				
    				filtresClone.add((Filtre)filtre.clone());
    			}
    		}
    		
    		// La catégorie est affichée si elle comporte plusieurs filtres (dont le type est different de "selectAll") 
    		boolean estAffichable = filtresClone.size() > (1 + nbFiltreSelectAll);
    		if (estAffichable) {
    			mapCategories.put(categorie.getId(), mapFiltre);

    			CategorieFiltres categorieClone = (CategorieFiltres)categorie.clone();
    			categoriesFiltresCandidats.add(categorieClone);
    			categorieClone.setFiltres(filtresClone);
    		}
    	}
    	
    	ressourcesCandidates.addAll(mapRessourcesCandidates.values());
    	if (log.isDebugEnabled()) {
    		log.debug("Ressources filtrées");
    		ObjectMapper mapper = new ObjectMapper();
        	log.debug(mapper.writeValueAsString(ressourcesCandidates));
    	}    	
    	
    	// Transformation en JSON du paramétrage
    	ObjectMapper mapper = new ObjectMapper();
    	String json = mapper.writeValueAsString(mapCategories);
    	log.debug("parametres de filtrage : " + json);
    	log.debug("nombre de ressources candidates : " + ressourcesCandidates.size());
    	return json;
	}
	

	/* (non-Javadoc)
	 * @see org.esco.portlet.mediacentre.service.IFiltrageService#filtrerGestionAffectation(java.util.List, java.util.Map)
	 */
	public List<GestionAffectation> filtrerGestionAffectation(List<GestionAffectation> listeGestionAffectation, Map<String, List<String>> userInfo) {
		List<GestionAffectation> gestionAffectationFiltrees = new ArrayList<GestionAffectation>();
		for (GestionAffectation gestionAffectation: listeGestionAffectation) {
			if (gestionAffectation.concerneUtilisateur(userInfo)) {
				gestionAffectationFiltrees.add(gestionAffectation);
			}
		}
		return gestionAffectationFiltrees;
	}
}

