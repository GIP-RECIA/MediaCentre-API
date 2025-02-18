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
package fr.recia.mediacentre.api.service.impl;

import fr.recia.mediacentre.api.configuration.bean.CategoriesByProfilesProperties;
import fr.recia.mediacentre.api.configuration.bean.GestionAffectationsProperties;
import fr.recia.mediacentre.api.dao.impl.MediaCentreResourceJacksonImpl;
import fr.recia.mediacentre.api.model.pojo.GestionAffectation;
import fr.recia.mediacentre.api.model.pojo.GestionAffectationDTO;
import fr.recia.mediacentre.api.service.utils.UserInfosBuilder;
import fr.recia.mediacentre.api.web.rest.exception.MediacentreWSException;
import fr.recia.mediacentre.api.web.rest.exception.YmlPropertyNotFoundException;
import fr.recia.mediacentre.api.interceptor.bean.SoffitHolder;
import fr.recia.mediacentre.api.model.filter.FilterEnum;
import fr.recia.mediacentre.api.model.resource.Ressource;
import fr.recia.mediacentre.api.service.MediaCentreService;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Created by jgribonvald on 06/06/17.
 */
@Slf4j
@Service
@NoArgsConstructor
public class MediaCentreServiceImpl implements MediaCentreService {

  @NonNull
  @Value("${url.ressources.mediacentre}")
  @Setter
  private String urlRessources;

  @Autowired
  private UserInfosBuilder userInfosBuilder;

  @Autowired
  private MediaCentreResourceJacksonImpl mediaCentreResource;

  @Autowired
  private SoffitHolder soffit;

  @Autowired
  private GestionAffectationsProperties gestionAffectations;

  @Autowired
  private CategoriesByProfilesProperties categoriesByFilters;

  @Override
  public List<Ressource> retrieveListRessource(List<String> isMemberOf) throws YmlPropertyNotFoundException, MediacentreWSException {

    if (log.isDebugEnabled()) {
      log.debug("Preference mediacentre url is {}", urlRessources);
    }

    if (Objects.isNull(urlRessources) || urlRessources.trim().isEmpty()) {
      throw new YmlPropertyNotFoundException("Property url.ressources is empty");
    }
    Map<String, List<String>> userInfos = userInfosBuilder.getUserInfos(soffit, isMemberOf);

    List<Ressource> listRessources = mediaCentreResource.retrieveListRessource(urlRessources, userInfos);
    return listRessources;
  }

  @Override
  public List<FilterEnum> retrieveFiltersList() throws YmlPropertyNotFoundException {
    List<String> userProfiles = soffit.getProfiles();
    return getFiltersByProfile(userProfiles);
  }

  @Override
  public List<GestionAffectationDTO> getGestionAffectationDTOs(List<String> isMemberOf) {
    return convertGestionAffectationListToDTOS(getFilteredGestionAffectations(isMemberOf));
  }

  private List<GestionAffectationDTO> convertGestionAffectationListToDTOS(List<GestionAffectation> gestionAffectations){
    List<GestionAffectationDTO> gestionAffectationDTOS = new ArrayList<>();
    for(GestionAffectation gestionAffectation : gestionAffectations){
      gestionAffectationDTOS.add( GestionAffectationDTO.fromGestionAffectation(gestionAffectation));
    }
    return gestionAffectationDTOS;
  }

  public List<GestionAffectation> getFilteredGestionAffectations(List<String> isMemberOf) {
    List<GestionAffectation> gestionAffectationsFiltered = new ArrayList<>();
    for(GestionAffectation gestionAffectation: gestionAffectations.getObjects()){
      if(concerneUtilisateur(gestionAffectation, isMemberOf)){
        gestionAffectationsFiltered.add(gestionAffectation);
      }
    }
    return gestionAffectationsFiltered;
  }


  public boolean concerneUtilisateur(GestionAffectation gestionAffectation, List<String> isMemberOf) {
    String regexp = gestionAffectation.getRegexp();
    if (isMemberOf == null) {
      return false;
    }
    if (!StringUtils.hasText(regexp)) {
      return true;
    }

    for (String value : isMemberOf) {
      if (value.matches(regexp)) {
        return true;
      }
    }
    return false;
  }

  private List<FilterEnum> getFiltersByProfile(List<String> profiles) throws YmlPropertyNotFoundException {
    List<CategoriesByProfilesProperties.ProfilesMap> profilesMapList = categoriesByFilters.getCategoriesByProfiles();
    Set<FilterEnum> filterEnumSet = new HashSet<>();
    if (profilesMapList.isEmpty()) {
      throw new YmlPropertyNotFoundException("ProfilesMap list of filters.categoriesByProfiles is empty");
    }
    for (CategoriesByProfilesProperties.ProfilesMap item : profilesMapList) {
      for(String profile : profiles){
        if (item.getProfiles().contains(profile)) {
          filterEnumSet.addAll(item.getFilters());
        }
      }
    }
    return new ArrayList<>(filterEnumSet);
  }

}
