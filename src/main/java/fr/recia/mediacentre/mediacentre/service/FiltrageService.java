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

import fr.recia.mediacentre.mediacentre.model.FilterUserRight;
import fr.recia.mediacentre.mediacentre.model.allocation.GestionAffectation;
import fr.recia.mediacentre.mediacentre.model.filter.category.CategorieFiltres;
import fr.recia.mediacentre.mediacentre.model.resource.Ressource;

import java.util.List;
import java.util.Map;

public interface FiltrageService {

  /**
   * Cette fonction prépare le filtrage en répertoriant les ressources correspondant aux filtres et
   * en répertoriant les filtres permettant d'afficher des ressources
   *
   * @param categoriesFiltres l'ensembles des filtres issus du paramétrage
   * @param ressources l'ensemble des ressources remontées par le GAR
   * @param categoriesFiltresCandidats (en sortie) liste des filtres permettant d'afficher au moins
   *     une ressource
   * @param ressourcesCandidates (en sortie) liste des ressources pouvant être affichées par au
   *     moins un filtre
   * @return les ressources candidates par Filtre au format JSON à passer à la JSP
   * @throws Exception Exception
   */
  String preparerFiltrage(
      List<CategorieFiltres> categoriesFiltres,
      List<Ressource> ressources,
      List<CategorieFiltres> categoriesFiltresCandidats,
      List<Ressource> ressourcesCandidates)
      throws Exception;

  /**
   * Permet de tester sur un utilisateur a les droits d'accès à la gestion d'affectation d'une des
   * ressources.
   *
   * @param listeGestionAffectation Liste des Gestionnaires d'affectation
   * @param userInfo Map contenant le profil de l'utilisateur
   * @return la liste des objets GestionAffectation qui sont autorisés pour l'utilisateur
   */
  List<GestionAffectation> filtrerGestionAffectation(
      List<GestionAffectation> listeGestionAffectation, Map<String, List<String>> userInfo);

  /**
   * Teste si l'objet concerne l'utilisateur
   *
   * @param filter Filtre à tester
   * @param userInfoMap Map contenant le profil de l'utilisateur
   * @return true si le filtre concerne l'utilisateur, false Sinon
   */
  boolean concerneUtilisateur(final FilterUserRight filter, final Map<String, List<String>> userInfoMap);
}



