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
	
	private boolean categorieExpended = false;

	private List<Filtre> filtres;
	
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
	 * @param id 
	 */
	public void setId(String id) {
		this.id = id;
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
	 * @param libelle 
	 */
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	/**
	 * Setter de la propriété valeursMultiples
	 * @param valeursMultiples 
	 */
	public void setValeursMultiples(boolean valeursMultiples) {
		this.valeursMultiples = valeursMultiples;
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
	 * Getter de la propriété filtre
	 * @return la propriété filtre
	 */
	public List<Filtre> getFiltres() {
		return filtres;
	}
	
	/**
	 * Setter de la propriété filtre
	 * @param filtre 
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
				+ ", categorieExpended=" + categorieExpended + ", filtres=" + filtres + "]";
	}
	
	@Override
	public Object clone(){
		CategorieFiltres categorieFiltres = null;
		try {
			categorieFiltres = (CategorieFiltres) super.clone();
			categorieFiltres.setId(this.getId());
			categorieFiltres.setLibelle(this.getLibelle());
			categorieFiltres.setValeursMultiples(this.isValeursMultiples());
			categorieFiltres.setCategorieExpended(this.isCategorieExpended());
			List<Filtre> listFiltre = new ArrayList<Filtre>();
			categorieFiltres.setFiltres(listFiltre);
			
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return categorieFiltres;
	}
	
	/**
	 * Indique si la categorie correspond à une categorie "etablissement"
	 * @return false
	 */
	public boolean estCategorieEtablissement() {
		return false;
	}
	
}
