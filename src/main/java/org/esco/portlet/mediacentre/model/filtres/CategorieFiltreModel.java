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

import java.io.Serializable;
import java.util.List;

public class CategorieFiltreModel implements Serializable {

	/* 
	 * ===============================================
	 * Propriétés de la classe 
	 * =============================================== 
	 */
	private static final long serialVersionUID = -979373924543584085L;

	private List<CategorieFiltres> listCategorieFiltre;

	/* 
	 * ===============================================
	 * Constructeurs de la classe 
	 * =============================================== 
	 */
	public CategorieFiltreModel() {
		
	}

	/**
	 * @param listCategorieFiltre
	 */
	public CategorieFiltreModel(List<CategorieFiltres> listCategorieFiltre) {
		this.listCategorieFiltre = listCategorieFiltre;
	}

	/* 
	 * ===============================================
	 * Getter / Setter de la classe 
	 * =============================================== 
	 */
	/**
	 * Getter de la propriété listCategorieFiltre
	 * @return la propriété listCategorieFiltre
	 */
	public List<CategorieFiltres> getListCategorieFiltre() {
		return listCategorieFiltre;
	}

	/**
	 * Setter de la propriété listCategorieFiltre
	 * @param listCategorieFiltre 
	 */
	public void setListCategorieFiltre(List<CategorieFiltres> listCategorieFiltre) {
		this.listCategorieFiltre = listCategorieFiltre;
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
	
}
