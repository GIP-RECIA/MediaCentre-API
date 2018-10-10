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
package org.esco.portlet.mediacentre.model.affectation;

import java.util.List;
import java.util.Map;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.apache.commons.lang.StringUtils;

@Data
@NoArgsConstructor
public class GestionAffectation {

	@NonNull
	private String id;
	@NonNull
	private String nom;
	@NonNull
	private String description;
	@NonNull
	private String lien;
	@NonNull
	private String population;
	@NonNull
	private String regexpPopulation;

	/**
     * @param userInfoMap Map contenant le profil de l'utilisateur
     * @return true si le filtre concerne l'utilisateur, false Sinon
     */
    public boolean concerneUtilisateur(Map<String, List<String>> userInfoMap) {
    	String regexp = getRegexpPopulation();
    	if (userInfoMap == null || StringUtils.isBlank(getPopulation()) || StringUtils.isBlank(regexp)) {
    		return true;
    	}
    	
    	List<String> valeurs = userInfoMap.get(getPopulation());
    	if (valeurs == null) {
    		return false;
    	}
    	
    	for (String valeur : valeurs) {
    		if (valeur.matches(regexp)) {
    			return true;
    		}
    	}
    	
    	return false;
    } 	
}
