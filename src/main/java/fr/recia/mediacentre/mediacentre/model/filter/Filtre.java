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
package fr.recia.mediacentre.mediacentre.model.filter;

import fr.recia.mediacentre.mediacentre.model.FilterUserRight;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * @author elecaude
 *
 */
@Data
@NoArgsConstructor
@Resource
public class Filtre implements FilterUserRight, Cloneable{

	@NonNull
	private String id;
	@NonNull
	private String libelle;
	@NonNull
	private String defaultEmptyValue;
	@NonNull
	private String nomAttribut;
	@NonNull
	private String regexpAttribut;
	@NonNull
	private String population;
	@NonNull
	private String regexpPopulation;

	private boolean actif = true;

	private boolean caseSelectAll = false;

	private boolean addEmptyFilteredValues = false;

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public Object clone(){
		Filtre filtre = null;
		try {
			filtre = (Filtre) super.clone();
			filtre.setId(this.getId());
			filtre.setLibelle(this.getLibelle());
			filtre.setDefaultEmptyValue(this.getDefaultEmptyValue());
			filtre.setNomAttribut(this.getNomAttribut());
			filtre.setRegexpAttribut(this.getRegexpAttribut());
			filtre.setPopulation(this.getPopulation());
			filtre.setRegexpPopulation(this.getRegexpPopulation());
			filtre.setActif(this.isActif());
			filtre.setCaseSelectAll(this.isCaseSelectAll());
			filtre.setAddEmptyFilteredValues(this.addEmptyFilteredValues);

		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return filtre;
	}
}

