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

import org.esco.portlet.mediacentre.model.ressource.Ressource;

/**
 * @author elecaude
 *
 */
public abstract class CategorieFiltresCalcules extends CategorieFiltres {

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
	 * @see org.esco.portlet.mediacentre.model.filtres.CategorieFiltres#estCategorieCalculee()
	 */
	@Override
	public boolean estCategorieCalculee() {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.esco.portlet.mediacentre.model.filtres.CategorieFiltres#clone()
	 */
	@Override
	public Object clone() {
		CategorieFiltresCalcules categorie = (CategorieFiltresCalcules)super.clone();
		categorie.setLibelleTous(getLibelleTous());
		categorie.setNomAttributFiltre(getNomAttributFiltre());
		return categorie;
	}

	/**
	 * Initialise la liste des filtres de la catégorie 
	 * @param userInfoMap
	 * @param ressources
	 */
	public void initialiser(Map<String, List<String>> userInfoMap, List<Ressource> ressources) throws Exception {
		setFiltres(new ArrayList<Filtre>());
	}
}
