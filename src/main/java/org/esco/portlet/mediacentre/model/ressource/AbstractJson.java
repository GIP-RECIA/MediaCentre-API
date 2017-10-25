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

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlTransient;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author elecaude
 *
 */
@XmlTransient
public abstract class AbstractJson {
	/* 
	 * ===============================================
	 * Propriete de la classe 
	 * =============================================== 
	 */

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

	/* 
	 * ===============================================
	 * Methodes privees de la classe 
	 * =============================================== 
	 */
	/**
	 * @param attribut
	 * @return le nom du getter d'un attribut
	 */
	private String methodeGetter(String attribut) {
		return "get" + StringUtils.capitalize(attribut);
	}

	/* 
	 * ===============================================
	 * Methodes publiques de la classe 
	 * =============================================== 
	 */
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {

    	ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			return super.toString();
		}
	}

	/**
	 * Methode de recuperation des valeurs d'un attribut d'un objet en utilisant une notation "pointee" pour retrouver l'attribut
	 * @param chemin json object path
	 * @return la liste des valeurs de l'attribut
	 * @throws Exception Exceptions de parcours sur l'objet
	 */
	public List<String> getValeursAttribut(String chemin) throws Exception {
		Class<? extends Object> objectClass = this.getClass();	
		String nomAttribut = null;
		Set<String> valeurs = new HashSet<String>();		
		
		int posPoint = chemin.indexOf('.');
		boolean estAttributFinal = (posPoint<0);
		if (!estAttributFinal) {
			nomAttribut = chemin.substring(0, posPoint);
		} else {
			nomAttribut = chemin;
		}
		Method getter = objectClass.getMethod(methodeGetter(nomAttribut), (Class[]) null);
		Object valeurAttribut = getter.invoke(this, (Object[]) null);
		
		
		if (estAttributFinal) {
			valeurs.add(valeurAttribut.toString());
			return new ArrayList<String>(valeurs);
		} 
		
		if (valeurAttribut instanceof List<?>) {
			for (Object obj : (List<?>)valeurAttribut) {
				if (obj instanceof AbstractJson) {
					valeurs.addAll(((AbstractJson)obj).getValeursAttribut(chemin.substring(posPoint+1)));
				}
			}
		} else {
			if (valeurAttribut instanceof AbstractJson) {
				valeurs.addAll(((AbstractJson)valeurAttribut).getValeursAttribut(chemin.substring(posPoint+1)));
			}
		}
		
		return new ArrayList<String>(valeurs);
	}	
	
}
