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
package fr.recia.mediacentre.api.web.rest;

import fr.recia.mediacentre.api.model.pojo.GestionAffectationDTO;
import fr.recia.mediacentre.api.model.filter.FilterEnum;
import fr.recia.mediacentre.api.model.pojo.IsMemberOf;
import fr.recia.mediacentre.api.model.resource.Ressource;
import fr.recia.mediacentre.api.service.mediacentre.MediaCentreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(path = "/api/resources")
public class MediaCentreController {

    @Autowired
    private MediaCentreService mediaCentreService;

    @PostMapping
    public ResponseEntity<List<Ressource>> getResources(@RequestBody IsMemberOf isMemberOf) {
        List<Ressource> resourcesList = mediaCentreService.retrieveListRessource(isMemberOf.getIsMemberOf());
        return new ResponseEntity<>(resourcesList, HttpStatus.OK);
    }

    @PostMapping(path = "/gestion")
    public ResponseEntity<List<GestionAffectationDTO>> getGestion(@RequestBody IsMemberOf isMemberOf){
      return ResponseEntity.ok().body(mediaCentreService.getGestionAffectationDTOs(isMemberOf.getIsMemberOf()));
    }

    @GetMapping(path = "/filters")
    public ResponseEntity<List<FilterEnum>> getFilters(){
        List<FilterEnum> filterEnumList = mediaCentreService.retrieveFiltersList();
        return new ResponseEntity<>(filterEnumList, HttpStatus.OK);
    }

    @PostMapping(path = "/{nomRessource}")
    public ResponseEntity<Ressource> getResourceByName(@PathVariable String nomRessource, @RequestBody IsMemberOf isMemberOf,
                                                       @RequestParam(defaultValue = "false") boolean base64,
                                                       @RequestParam(defaultValue = "true") boolean forCurrentEtab){
        Optional<Ressource> ressourceOptional = mediaCentreService.retrieveRessourceByName(nomRessource, isMemberOf.getIsMemberOf(),base64, forCurrentEtab);
        if(ressourceOptional.isEmpty()){
          return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }else {
          return new ResponseEntity<>(ressourceOptional.get(), HttpStatus.OK);
        }
    }
}
