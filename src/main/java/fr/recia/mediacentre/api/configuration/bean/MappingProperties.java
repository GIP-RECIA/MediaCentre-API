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

import fr.recia.mediacentre.api.model.pojo.UserInfoAttribute;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
@Data
@Slf4j
@Setter(AccessLevel.PUBLIC)
@Component
@Validated
@ConfigurationProperties(prefix = "mapping")
public class MappingProperties {

  //  @Value("${mapping.soffit.userinfoAttributes}")
//  @NotNull @NotEmpty
  private List<UserInfoAttribute> userinfoAttributes;

  //  @Value("${mapping.soffit.profileKey}")
//  @NotNull
  private String profileKey;

  //  @Value("${mapping.payload.groups.out}")
//  @NotNull
  private UserInfoAttribute groups;


  @PostConstruct
  private void init() {
    log.info("Loaded properties: {}", this);
  }

  @Override
  public String toString() {

    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Mapping properties: {\n");

    stringBuilder.append("\t\"groups\": ");
    stringBuilder.append(groups.toString());
    stringBuilder.append(",\n");

    stringBuilder.append("\t\"profileKey\": ");
    stringBuilder.append(profileKey);
    stringBuilder.append(",\n");

    stringBuilder.append("\t\"userinfoAttributes\": { \n");
    for(UserInfoAttribute userInfoAttribute : userinfoAttributes){
      stringBuilder.append("\t\t\"");
      stringBuilder.append(userInfoAttribute.toString());
      stringBuilder.append("\",\n");
    }
    stringBuilder.append( "\t}");
    stringBuilder.append( "\n}");

    return stringBuilder.toString();
  }

}


