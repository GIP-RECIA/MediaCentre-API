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
package org.esco.portlet.mediacentre.service;

import java.util.List;

import org.esco.portlet.mediacentre.model.filtres.CategorieFiltres;
import org.esco.portlet.mediacentre.model.ressource.Ressource;

public interface IFiltrageService {

    /**
     * @param categoriesFiltres
     * @param ressources
     * @return la liste des ressources correspondant aux filtres actifs
     * @throws Exception
     */
	public List<Ressource> filtrerRessources(List<CategorieFiltres> categoriesFiltres,  List<Ressource> ressources) throws Exception;

}
