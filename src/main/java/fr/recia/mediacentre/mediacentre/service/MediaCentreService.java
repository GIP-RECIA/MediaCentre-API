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
package fr.recia.mediacentre.mediacentre.service;

import fr.recia.mediacentre.mediacentre.model.filter.FilterEnum;
import fr.recia.mediacentre.mediacentre.model.resource.Ressource;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;


public interface MediaCentreService {


    /***
     * Function that returns the list of etablishment ids of the user.
     *
     * @return a list of etablishment ids of the user.
     */
	List<String> getUserLinkedEtablissements();


    /***
     * Function that returns the current etablishment id of the user.
     *
     * @return currentEtabId - the current etablishment id of the user.
     */
	String getUserCurrentEtablissement();


    /***
     * Function that returns a list of current user's groups (not used yet).
     *
     * @return groups - The list of current user's groups.
     */
//    List<String> getUserGroups();


    /***
     * Function that returns the current user's favorites.
     *
     * @return favorites - The list of current user's favorites.
     */
    List<Ressource> getUserFavorites();

    /***
     * Function that returns a boolean if the web service was able to
     * add the resource from the current user's favorites.
     *
     * @param idFavorite - the id of favorite resource selected.
     * @return true if the resource selected has been added to the current user's favorite resources,
     * false otherwise.
     */
    boolean addToUserFavorites(@NotNull final String idFavorite);

    /***
     * Function that returns a boolean if the web service was able to
     * remove the resource from the current user's favorites.
     *
     * @param idFavorite - the id of favorite resource selected.
     * @return true if the resource selected has been added to the current user's favorite resources,
     * false otherwise.
     */
    boolean removeToUserFavorites(@NotNull final String idFavorite);

    /***
     * Function that returns the list of current user's resources.
     *
     * @return listeRessources - The list of current user's resources.
     */
    List<Ressource> retrieveListRessource() throws IOException;

    /***
     * Function that check the user's profile and returns a list of FilterEnum corresponding to the yml properties file.
     *
     * @return a List<FilterEnum>
     */
    List<FilterEnum> retrieveFiltersList();
}
