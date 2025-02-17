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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.recia.mediacentre.api.configuration.bean.CategoriesByProfilesProperties;
import fr.recia.mediacentre.api.dao.impl.MediaCentreResourceJacksonImpl;
import fr.recia.mediacentre.api.interceptor.bean.SoffitHolder;
import fr.recia.mediacentre.api.model.filter.FilterEnum;
import fr.recia.mediacentre.api.model.pojo.GestionAffectationDTO;
import fr.recia.mediacentre.api.model.resource.Ressource;
import fr.recia.mediacentre.api.service.MediaCentreService;
import fr.recia.mediacentre.api.web.rest.exception.MediacentreWSException;
import fr.recia.mediacentre.api.web.rest.exception.YmlPropertyNotFoundException;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@NoArgsConstructor
public class MediaCentreServiceJsonImpl implements MediaCentreService {

  @NonNull
  @Value("${service.mockedDataLocation}")
  @Setter
  private String urlRessources;

  @NonNull
  @Value("${service.mockedDTOLocation}")
  @Setter
  private String urlDTOS;

  @Autowired
  private MediaCentreResourceJacksonImpl mediaCentreResource;

  @Autowired
  private SoffitHolder soffit;

  @Autowired
  private CategoriesByProfilesProperties categoriesByFilters;

  @Override
  public List<Ressource> retrieveListRessource(List<String> isMemberOf) throws YmlPropertyNotFoundException, MediacentreWSException {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      File file = new File(urlRessources);
      List<Ressource> ressourceList = objectMapper.readValue(file, new TypeReference<List<Ressource>>(){});
      return ressourceList;
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  @Override
  public List<FilterEnum> retrieveFiltersList() throws YmlPropertyNotFoundException {
    List<String> userProfile = soffit.getProfiles();
    return getFiltersByProfile(userProfile);
  }

  @Override
  public List<GestionAffectationDTO> getGestionAffectationDTOs(List<String> isMemberOf) {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      File file = new File(urlDTOS);
      return objectMapper.readValue(file, new TypeReference<List<GestionAffectationDTO>>(){});
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
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
