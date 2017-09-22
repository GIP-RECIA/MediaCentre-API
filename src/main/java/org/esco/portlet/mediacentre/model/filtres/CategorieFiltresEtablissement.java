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

import org.apache.commons.lang.StringUtils;

/**
 * @author elecaude
 *
 */
public class CategorieFiltresEtablissement extends CategorieFiltres {

	/* 
	 * ===============================================
	 * Propriétés de la classe 
	 * =============================================== 
	 */
	private String libelleTous;
	private String nomAttributFiltre;
	
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
	 * Getter de la propriété libelleTous
	 * @return la propriété libelleTous
	 */
	public String getLibelleTous() {
		return libelleTous;
	}

	/**
	 * Setter de la propriété libelleTous
	 * @param libelleTous 
	 */
	public void setLibelleTous(String libelleTous) {
		this.libelleTous = libelleTous;
	}

	/**
	 * Getter de la propriété nomAttributFiltre
	 * @return la propriété nomAttributFiltre
	 */
	public String getNomAttributFiltre() {
		return nomAttributFiltre;
	}

	/**
	 * Setter de la propriété nomAttributFiltre
	 * @param nomAttributFiltre 
	 */
	public void setNomAttributFiltre(String nomAttributFiltre) {
		this.nomAttributFiltre = nomAttributFiltre;
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
	 * @see org.esco.portlet.mediacentre.model.filtres.CategorieFiltres#isValeursMultiples()
	 */
	@Override
	public boolean isValeursMultiples() {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.esco.portlet.mediacentre.model.filtres.CategorieFiltres#estCategorieEtablissement()
	 */
	@Override
	public boolean estCategorieEtablissement() {
		return true;
	}


	/**
	 * Initialise la liste des filtres de la catégorie avec les établissements de l'utilisateur
	 * @param etablissements
	 * @param etablissementsCourants
	 */
	public void initialiser(List<String> etablissements, List<String> etablissementsCourants) {
		if (etablissements == null) {
			return;
		}
		String etablissementCourant = "";
		if (etablissementsCourants != null && !etablissementsCourants.isEmpty()) {
			etablissementCourant = etablissementsCourants.get(0);
		}
		Collections.sort(etablissements);
		
		List<Filtre> filtres = new ArrayList<Filtre>();
		
		if (StringUtils.isNotBlank(getLibelleTous())) {
			Filtre filtre = new Filtre();
    		filtre.setId(getId());
    		filtre.setLibelle(getLibelleTous());
    		filtre.setActif(false);
    		filtre.setCaseSelectAll(true);
    		filtres.add(filtre);
		}
		
		for (String etablissement : etablissements) {
	    		Filtre filtre = new Filtre();
	    		filtre.setId(etablissement);
	    		filtre.setLibelle(etablissement);
	    		filtre.setActif(etablissement.equalsIgnoreCase(etablissementCourant));
	    		filtre.setNomAttribut(getNomAttributFiltre());
	    		filtre.setRegexpAttribut(etablissement);
	    		filtres.add(filtre);
	    }
		setFiltres(filtres);
	}
}
