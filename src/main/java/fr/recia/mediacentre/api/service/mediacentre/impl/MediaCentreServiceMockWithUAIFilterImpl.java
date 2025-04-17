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
package fr.recia.mediacentre.api.service.mediacentre.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.recia.mediacentre.api.configuration.bean.CategoriesByProfilesProperties;
import fr.recia.mediacentre.api.configuration.bean.MappingProperties;
import fr.recia.mediacentre.api.dao.MediaCentreResource;
import fr.recia.mediacentre.api.dao.impl.MediaCentreResourceJacksonImpl;
import fr.recia.mediacentre.api.interceptor.bean.SoffitHolder;
import fr.recia.mediacentre.api.model.filter.FilterEnum;
import fr.recia.mediacentre.api.model.pojo.GestionAffectationDTO;
import fr.recia.mediacentre.api.model.resource.IdEtablissement;
import fr.recia.mediacentre.api.model.resource.Ressource;
import fr.recia.mediacentre.api.service.mediacentre.MediaCentreServiceAbstractImpl;
import fr.recia.mediacentre.api.web.rest.exception.MediacentreWSException;
import fr.recia.mediacentre.api.web.rest.exception.YmlPropertyNotFoundException;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MediaCentreServiceMockWithUAIFilterImpl extends MediaCentreServiceAbstractImpl {
  @NonNull
  @Value("${mock.mockedDataLocation:}")
  @Setter
  private String urlRessources;

  @NonNull
  @Value("${mock.mockedDTOLocation:}")
  @Setter
  private String urlDTOS;


  private final CategoriesByProfilesProperties categoriesByFilters;

  public MediaCentreServiceMockWithUAIFilterImpl(SoffitHolder soffitHolder, MappingProperties mappingProperties, CategoriesByProfilesProperties categoriesByProfilesProperties)
  {
    super(soffitHolder, mappingProperties);
    this.categoriesByFilters = categoriesByProfilesProperties;
  }

  @Override
  public List<Ressource> retrieveListRessource(List<String> isMemberOf) throws YmlPropertyNotFoundException, MediacentreWSException {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      URL resource = MediaCentreServiceMockImpl.class.getResource(urlRessources);
      assert resource != null;
      File file = Paths.get(resource.toURI()).toFile();
      List<Ressource> ressourceList = objectMapper.readValue(file, new TypeReference<>() {});
      Predicate<Ressource> atLeastOneEtabInUserEtabs = (i) ->{
        if(i.getIdEtablissement().isEmpty()){
          return true;
        }
        for (IdEtablissement idEtablissement: i.getIdEtablissement()){
          if(getSoffitHolder().getUaiList().contains(idEtablissement.getUAI())){
            return true;
          }
        }
        return false;
      };
      return  ressourceList.stream().filter(atLeastOneEtabInUserEtabs).collect(Collectors.toList());
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    } catch (URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public List<FilterEnum> retrieveFiltersList() throws YmlPropertyNotFoundException {
    List<String> userProfile = getSoffitHolder().getProfiles();
    return getFiltersByProfile(userProfile);
  }

  @Override
  public List<GestionAffectationDTO> getGestionAffectationDTOs(List<String> isMemberOf) {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      URL resource = MediaCentreServiceMockImpl.class.getResource(urlDTOS);
      assert resource != null;
      File file = Paths.get(resource.toURI()).toFile();
      return objectMapper.readValue(file, new TypeReference<>() {});
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    } catch (URISyntaxException e) {
      throw new RuntimeException(e);
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
