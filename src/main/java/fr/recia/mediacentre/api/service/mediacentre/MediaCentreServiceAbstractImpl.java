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
package fr.recia.mediacentre.api.service.mediacentre;

import fr.recia.mediacentre.api.configuration.bean.MappingProperties;
import fr.recia.mediacentre.api.interceptor.bean.SoffitHolder;
import fr.recia.mediacentre.api.model.pojo.RessourceLight;
import fr.recia.mediacentre.api.model.resource.IdEtablissement;
import fr.recia.mediacentre.api.model.resource.Ressource;
import fr.recia.mediacentre.api.web.rest.exception.MediacentreWSException;
import fr.recia.mediacentre.api.web.rest.exception.YmlPropertyNotFoundException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
public abstract class MediaCentreServiceAbstractImpl implements MediaCentreService{

  @Getter
  SoffitHolder soffitHolder;

  MappingProperties mappingProperties;


  public MediaCentreServiceAbstractImpl(SoffitHolder soffitHolder, MappingProperties mappingProperties){
    this.soffitHolder = soffitHolder;
    this.mappingProperties = mappingProperties;
  }

  @Override
  public List<RessourceLight> retrieveListRessourceFav(List<String> isMemberOf, List<String> favorites) throws YmlPropertyNotFoundException {
    List<Ressource> ressourceList = retrieveListRessource(isMemberOf);
    String currentUAI =  soffitHolder.getUaiCurrent().get(0);

    Predicate<Ressource> isFavorite = new Predicate<Ressource>() {
      @Override
      public boolean test(Ressource ressource) {
        return favorites.contains(ressource.getIdRessource());
      }
    };
    Predicate<Ressource> isCurrentEtab = new Predicate<Ressource>() {
      @Override
      public boolean test(Ressource ressource) {
        if(Objects.isNull(ressource.getIdEtablissement()) || ressource.getIdEtablissement().isEmpty()){
          return true;
        }
        for(IdEtablissement idEtablissement : ressource.getIdEtablissement()){
          if(Objects.equals(idEtablissement.getUAI(), currentUAI)){
            return true;
          }
        }
        return false;
      }
    };
    return ressourceList.stream().filter(isFavorite.and(isCurrentEtab)).map(x -> new RessourceLight(x.getIdRessource(),x.getNomRessource(), x.getTypePresentation())).collect(Collectors.toList());
  }

  @Override
  public Optional<Ressource> retrieveRessourceByName(String nomRessource, List<String> isMemberOf, boolean isBase64, boolean forCurrentEtab) throws YmlPropertyNotFoundException, MediacentreWSException {
    String ressourceIdForFiltering = nomRessource;
    if(forCurrentEtab){
      soffitHolder.setUaiList(soffitHolder.getUaiCurrent());
    }
    if(isBase64){
      String decodedId = new String(Base64.decodeBase64(nomRessource.getBytes()));
      ressourceIdForFiltering = decodedId;
    }
    return getRessourceOfCurrentEtabFromRessourceList(ressourceIdForFiltering, retrieveListRessource(isMemberOf));
  }

  protected Optional<Ressource> getRessourceOfCurrentEtabFromRessourceList(String ressourceId, List<Ressource> ressourceList){
    List<String> currentUaiList = soffitHolder.getUaiCurrent();
    if(Objects.isNull(currentUaiList) || currentUaiList.isEmpty()){
      throw new YmlPropertyNotFoundException("Missing mapping for current etab UAI");
    }
    String currentUai = currentUaiList.get(0);
    if(Objects.isNull(ressourceList)){
      return Optional.empty();
    }
    for(Ressource ressource : ressourceList){
      if(ressourceId.trim().equalsIgnoreCase(ressource.getNomRessource().trim())){
        if( Objects.isNull(ressource.getIdEtablissement()) || ressource.getIdEtablissement().isEmpty()){
          return Optional.of(ressource);
        } else {
          if(ressource.getIdEtablissement().stream().anyMatch(x -> Objects.equals(x.getUAI(), currentUai))){
            return Optional.of(ressource);
          } else {
            return Optional.empty();
          }
        }
      }
    }
    return Optional.empty();
  }
}
