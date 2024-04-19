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

import fr.recia.mediacentre.mediacentre.model.filter.Filtre;
import fr.recia.mediacentre.mediacentre.model.resource.ListeRessource;
import fr.recia.mediacentre.mediacentre.model.resource.Ressource;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.annotation.Resource;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @author elecaude
 *
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Resource
public class CategorieFiltresRessource extends CategorieFiltresCalculees {

	@Override
	public void initialiser(Map<String, List<String>> userInfoMap, List<Ressource> ressources) throws Exception {
		if (ressources == null) {
			return;
		}

		ListeRessource listeRessource = new ListeRessource(ressources);
    	Set<String> listeValeurs = new HashSet<String>();
    	for (Ressource rs: listeRessource.getRessources()) {
    		final List<String> values = rs.getValeursAttribut(getNomAttributFiltre());
    		if (!values.isEmpty()) {
    			listeValeurs.addAll(values);
			} else if (this.getDefaultEmptyValue() != null){
				listeValeurs.add(this.getDefaultEmptyValue());
			}
		}

    	List<Filtre> filtres = new ArrayList<Filtre>();

		if (!this.getLibelleTous().isBlank()) {
			Filtre filtre = new Filtre();
			filtre.setId(getId());
			filtre.setLibelle(getLibelleTous());
			filtre.setDefaultEmptyValue(this.getDefaultEmptyValue());
			filtre.setNomAttribut(getNomAttributFiltre());
			filtre.setCaseSelectAll(true);
			filtre.setPopulation(getPopulation());
			filtre.setRegexpPopulation(this.getRegexpPopulation());
			filtre.setRegexpAttribut(".*");
			filtres.add(filtre);
		}

		int i = 0;
		for (String valeur: listeValeurs) {
			Filtre filtre = new Filtre();
			filtre.setId(getId() + "_" + i);
			filtre.setLibelle(valeur);
			filtre.setDefaultEmptyValue(this.getDefaultEmptyValue());
    		filtre.setNomAttribut(getNomAttributFiltre());
			filtre.setPopulation(getPopulation());
			filtre.setRegexpPopulation(this.getRegexpPopulation());
    		filtre.setRegexpAttribut(Pattern.quote(valeur));
    		filtres.add(filtre);
    		i++;
		}

		setFiltres(filtres);
	}
}
