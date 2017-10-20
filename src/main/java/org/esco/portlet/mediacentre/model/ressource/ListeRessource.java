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
package org.esco.portlet.mediacentre.model.ressource;

import java.util.List;

/**
 * @author elecaude
 *
 */
public class ListeRessource extends AbstractJson {
	/* 
	 * ===============================================
	 * Propriétés de la classe 
	 * =============================================== 
	 */
	private List<Ressource> ressources;

	/* 
	 * ===============================================
	 * Constructeurs de la classe 
	 * =============================================== 
	 */
	/**
	 * @param ressources
	 */
	public ListeRessource(List<Ressource> ressources) {
		super();
		this.ressources = ressources;
	}
	
	/* 
	 * ===============================================
	 * Getter / Setter de la classe 
	 * =============================================== 
	 */
	/**
	 * Getter de la propriété ressources
	 * @return la propriété ressources
	 */
	public List<Ressource> getRessources() {
		return ressources;
	}

	/**
	 * Setter de la propriété ressources
	 * @param ressources 
	 */
	public void setRessources(List<Ressource> ressources) {
		this.ressources = ressources;
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
