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

import java.util.List;
import java.util.Map;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;
import org.esco.portlet.mediacentre.model.ressource.Ressource;

/**
 * @author elecaude
 *
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public abstract class CategorieFiltresCalcules extends CategorieFiltres {

	@NonNull
	private String libelleTous;
	@NonNull
	private String nomAttributFiltre;

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
	 * @param userInfoMap Map contenant le profil de l'utilisateur
	 * @param ressources Liste des ressources.
	 * @throws Exception Exception
	 */
	public abstract void initialiser(Map<String, List<String>> userInfoMap, List<Ressource> ressources) throws Exception;
}
