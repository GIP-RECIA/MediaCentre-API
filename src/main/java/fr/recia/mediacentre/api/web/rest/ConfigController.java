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

import com.fasterxml.jackson.core.JsonProcessingException;
import fr.recia.mediacentre.api.model.pojo.Config;
import fr.recia.mediacentre.api.model.pojo.ConfigElement;
import fr.recia.mediacentre.api.model.pojo.Uais;
import fr.recia.mediacentre.api.service.config.ConfigService;
import fr.recia.mediacentre.api.service.config.impl.ConfigServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping(path = "api/config")
public class ConfigController {

  @Autowired
  private ConfigService configService;

  @PostMapping(consumes = "application/json")
  public ResponseEntity<Config> getConfig(@RequestBody(required = false) Uais uais) throws JsonProcessingException {
    if(Objects.isNull(uais)){
      uais = new Uais();
    }

    Config config = new Config();

    List<ConfigElement> groupList = configService.getGroups();
    if(Objects.nonNull(groupList) && !groupList.isEmpty()){
      config.getConfigListMap().put("groups", groupList);
    }

    List<ConfigElement> etabNameList = configService.getEtabsNames(uais.getUais());
    if(Objects.nonNull(etabNameList) && !etabNameList.isEmpty()){
      config.getConfigListMap().put("etabsNames", etabNameList);
    }
    if(config.getConfigListMap().isEmpty()){
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }else{
      return new ResponseEntity<>(config, HttpStatus.OK);
    }
  }
}
