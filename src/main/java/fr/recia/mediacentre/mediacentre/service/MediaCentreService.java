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
package fr.recia.mediacentre.mediacentre.service;

import fr.recia.mediacentre.mediacentre.model.resource.Ressource;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface MediaCentreService {

	List<String> getUserLinkedEtablissements();

	List<String> getUserCurrentEtablissement();

    List<String> getUserGroups();

    List<String> getUserFavorites();

    boolean addToUserFavorites(@NotNull final String idFavorite);

    boolean removeToUserFavorites(@NotNull final String idFavorite);

    List<Ressource> retrieveListRessource();
}
