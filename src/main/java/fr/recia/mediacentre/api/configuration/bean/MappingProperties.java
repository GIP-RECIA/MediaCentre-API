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

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiPredicate;

@Data
@Slf4j
@Setter(AccessLevel.PUBLIC)
@Component
@Validated
@ConfigurationProperties(prefix = "mapping")
public class MappingProperties {


  @Autowired
  private ApplicationContext ctx;

  @NotNull
  private String uaiList;
  @NotNull
  private String uaiCurrent;
  @NotNull
  private String garId;
  @NotNull
  private String profiles;
  @NotNull
  private String groups;
  @NotNull
  private List<String> otherUserInfoAttributes;


  @PostConstruct
  private void init() {


    List<String> tempListForAdditionalValidation = new ArrayList<>(otherUserInfoAttributes);
    tempListForAdditionalValidation.add(uaiList);
    tempListForAdditionalValidation.add(uaiCurrent);
    tempListForAdditionalValidation.add(garId);
    tempListForAdditionalValidation.add(profiles);
    tempListForAdditionalValidation.add(groups);

    BiPredicate<String, String> stringBiPredicate = new BiPredicate<String, String>() {
      @Override
      public boolean test(String s, String s2) {
        return Objects.equals(s, s2);
      }
    };

    for (int i = 0; i < tempListForAdditionalValidation.size(); i++) {

      String key = tempListForAdditionalValidation.get(i);
      long occurrencesKey = tempListForAdditionalValidation.stream().filter(x -> Objects.equals(x, key)).count();
      Assert.isTrue( occurrencesKey == 1, String.format("Key %s is present more than one time.", key));
    }
    log.info("Loaded properties: {}", this);
  }


  @Override
  public String toString() {

    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Mapping properties: {\n");

    stringBuilder.append("\t\"groups\": ");
    stringBuilder.append(groups.toString());
    stringBuilder.append(",\n");

    stringBuilder.append("\t\"profiles\": ");
    stringBuilder.append(profiles.toString());
    stringBuilder.append(",\n");

    stringBuilder.append("\t\"gar id\": ");
    stringBuilder.append(garId.toString());
    stringBuilder.append(",\n");

    stringBuilder.append("\t\"uai current\": ");
    stringBuilder.append(uaiCurrent.toString());
    stringBuilder.append(",\n");

    stringBuilder.append("\t\"uai list\": ");
    stringBuilder.append(uaiList.toString());
    stringBuilder.append(",\n");

    stringBuilder.append("\t\"otherAttributes\": { \n");
    for(String userInfoAttribute : otherUserInfoAttributes){
      stringBuilder.append("\t\t\"");
      stringBuilder.append(userInfoAttribute);
      stringBuilder.append("\",\n");
    }
    stringBuilder.append( "\t}");
    stringBuilder.append( "\n}");

    return stringBuilder.toString();
  }
}


