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
import fr.recia.mediacentre.api.model.pojo.GestionAffectation;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@ConfigurationProperties(prefix = "gestionaffectations")
@Component
@Data
@Validated
public class GestionAffectationsProperties {
  @NotNull
  @NotEmpty
  private List<GestionAffectation> objects;

  @PostConstruct
  private void init() throws JsonProcessingException {
    log.info("Loaded properties: {}", this);
  }

  @Override
  public String toString(){

    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("\"Gestion Affectations properties: {\n");
    for(GestionAffectation gestionAffectation : objects){
      stringBuilder.append("\t{\n");
      stringBuilder.append(gestionAffectation.toString());
      stringBuilder.append("\t},\n");
    }

    stringBuilder.append("}");
    return stringBuilder.toString();

  }
}
