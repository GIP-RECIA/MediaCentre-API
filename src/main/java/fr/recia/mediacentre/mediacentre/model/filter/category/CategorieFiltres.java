/**
 * Copyright © ${project.inceptionYear} GIP-RECIA (https://www.recia.fr/)
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
package fr.recia.mediacentre.mediacentre.model.filter.category;

import fr.recia.mediacentre.mediacentre.model.FilterUserRight;
import fr.recia.mediacentre.mediacentre.model.filter.Filtre;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author elecaude
 *
 */
@Data
@NoArgsConstructor
@Resource
public class CategorieFiltres implements FilterUserRight, Cloneable {

	@NonNull
	private String id;

	@NonNull
	private String libelle;

	private boolean valeursMultiples;

	@NonNull
	private String defaultEmptyValue;

	private boolean categorieExpended = false;

	private List<Filtre> filtres = new ArrayList<>();

	private String population;

	private String regexpPopulation;

	@Override
	public Object clone(){
		CategorieFiltres categorieFiltres = null;
		try {
			categorieFiltres = (CategorieFiltres) super.clone();
			categorieFiltres.setId(this.getId());
			categorieFiltres.setLibelle(this.getLibelle());
			categorieFiltres.setValeursMultiples(this.isValeursMultiples());
			categorieFiltres.setDefaultEmptyValue(this.getDefaultEmptyValue());
			categorieFiltres.setCategorieExpended(this.isCategorieExpended());
			categorieFiltres.setPopulation(getPopulation());
			categorieFiltres.setRegexpPopulation(getRegexpPopulation());
			List<Filtre> listFiltre = new ArrayList<Filtre>();
			categorieFiltres.setFiltres(listFiltre);

		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return categorieFiltres;
	}

	/**
	 * Indique si la categorie correspond à une categorie "calculee"
	 * @return false
	 */
	public boolean estCategorieCalculee() {
		return false;
	}
}

