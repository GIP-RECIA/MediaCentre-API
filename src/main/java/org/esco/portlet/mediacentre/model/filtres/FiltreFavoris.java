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

/**
 * @author elecaude
 *
 */
public class FiltreFavoris extends Filtre {

	public static final String DEFAULT_ID = "favoris";
	public static final String DEFAULT_NOM_ATTR = "favorite";
	public static final String DEFAULT_REGEX_ATTR = "true";

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

	public FiltreFavoris() {
		super.setId(DEFAULT_ID);
		super.setNomAttribut(DEFAULT_NOM_ATTR);
		super.setRegexpAttribut(DEFAULT_REGEX_ATTR);
		super.setCaseSelectAll(false);
	}
	
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
	 * @see org.esco.portlet.mediacentre.model.filtres.Filtre#setCaseSelectAll(boolean)
	 */
	@Override
	public void setCaseSelectAll(boolean caseSelectAll) {
		throw new IllegalArgumentException("L'attribut CaseSelectAll n'est pas autorisé pour la classe FiltreFavoris");
	}

	/* (non-Javadoc)
	 * @see org.esco.portlet.mediacentre.model.filtres.Filtre#setId(java.lang.String)
	 */
	@Override
	public void setId(String id) {
		throw new IllegalArgumentException("L'attribut Id n'est pas autorisé pour la classe FiltreFavoris");
	}

	/* (non-Javadoc)
	 * @see org.esco.portlet.mediacentre.model.filtres.Filtre#setNomAttribut(java.lang.String)
	 */
	@Override
	public void setNomAttribut(String nomAttribut) {
		throw new IllegalArgumentException("L'attribut NomAttribut n'est pas autorisé pour la classe FiltreFavoris");
	}

	/* (non-Javadoc)
	 * @see org.esco.portlet.mediacentre.model.filtres.Filtre#setRegexpAttribut(java.lang.String)
	 */
	@Override
	public void setRegexpAttribut(String regexpAttribut) {
		throw new IllegalArgumentException("L'attribut RegexpAttribut n'est pas autorisé pour la classe FiltreFavoris");
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Object clone(){
		Filtre filtre = new FiltreFavoris();
		filtre.setLibelle(this.getLibelle());
		filtre.setPopulation(this.getPopulation());
		filtre.setRegexpPopulation(this.getRegexpPopulation());
		filtre.setActif(this.isActif());
		return filtre;
	}
}
