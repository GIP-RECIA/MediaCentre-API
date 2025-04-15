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
import fr.recia.mediacentre.api.model.resource.Ressource;
import fr.recia.mediacentre.api.web.rest.exception.MediacentreWSException;
import fr.recia.mediacentre.api.web.rest.exception.YmlPropertyNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public abstract class MediaCentreServiceAbstractImpl implements MediaCentreService{

  @Autowired
  SoffitHolder soffitHolder;

  @Autowired
  MappingProperties mappingProperties;

  @Override
  public Optional<Ressource> retrieveRessourceById(String ressourceId, List<String> isMemberOf, boolean isBase64, boolean forCurrentEtab) throws YmlPropertyNotFoundException, MediacentreWSException {
    String ressourceIdForFiltering = ressourceId;
    if(forCurrentEtab){
      soffitHolder.setUaiList(soffitHolder.getUaiCurrent());
    }
    if(isBase64){
      String decodedId = new String(Base64.decodeBase64(ressourceId.getBytes()));
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
      if(ressourceId.trim().equalsIgnoreCase(ressource.getIdRessource().trim())){
        return Optional.of(ressource);
      }
    }
    return Optional.empty();
  }
}
