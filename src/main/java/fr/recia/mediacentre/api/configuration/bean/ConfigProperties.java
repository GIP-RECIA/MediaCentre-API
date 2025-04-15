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
package fr.recia.mediacentre.api.configuration.bean;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

import org.springframework.validation.annotation.Validated;

@Slf4j
@ConfigurationProperties(prefix = "config")
@Component
@Data
@Validated
public class ConfigProperties {

  @NotNull @NotEmpty
  private List<String> groups;

  @NotNull @NotEmpty
  private String paramUserEtabsURl;

  @NotNull @NotEmpty
  private String paramUserEtabsDisplayNameKey;

  @PostConstruct
  private void init() throws JsonProcessingException {
    log.info("Loaded properties: {}", this);
  }

  @Override
  public String toString(){
      String joinedGroups = String.join("\", \"", groups);
    return "\"Config properties\": {" +
      "\n\t\"groups\": \"" + joinedGroups + "\"" +
      "\n\t\"paramUserEtabsURl\": \"" + paramUserEtabsURl + "\"" +
      "\n}";
  }
}
