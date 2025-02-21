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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Data
@Slf4j
@Setter(AccessLevel.PUBLIC)
@Component
@Validated
@ConfigurationProperties(prefix = "mapping")
public class MappingProperties {


  @Autowired
  private ApplicationContext ctx;


  @NotNull @NotEmpty
  private List<UserInfoAttribute> userinfoAttributes;


  private String profileKey;
  private String currentEtabUaiKey;

  @NotNull
  private UserInfoAttribute groups;


  @PostConstruct
  private void init() {

    if(this.groups.getOut().trim().isEmpty()){
      cancelServerStart("Parameter {out} of {groups} in MappingProperties is empty, startup canceled.");
    }

    if(this.profileKey.trim().isEmpty()){
      cancelServerStart("Parameter {profileKey} in MappingProperties is empty, startup canceled.");
    }
    if(this.currentEtabUaiKey.trim().isEmpty()){
      cancelServerStart("Parameter {currentEtabUaiKey} in MappingProperties is empty, startup canceled.");
    }

    Set<String> inKeys = new HashSet<>();
    Set<String> outKeys = new HashSet<>();

    for(UserInfoAttribute userInfoAttribute: this.userinfoAttributes){
      if(Objects.isNull(userInfoAttribute.getIn()) || userInfoAttribute.getIn().trim().isEmpty()){
        cancelServerStart("Parameter {in} of a member of {userinfoAttributes} in MappingProperties is empty, startup canceled.");
      }
      if(Objects.isNull(userInfoAttribute.getOut()) || userInfoAttribute.getIn().trim().isEmpty()){
        cancelServerStart("Parameter {out} of a member of {userinfoAttributes} in MappingProperties is empty, startup canceled.");
      }

      boolean addedIn = inKeys.add(userInfoAttribute.getIn());
      boolean addedOut = outKeys.add(userInfoAttribute.getOut());

      if(!addedIn){
        cancelServerStart(String.format("the {in} key {%s} of a member of {userinfoAttribues} in MappingProperties is present multiple time, startup canceled", userInfoAttribute.getIn()));
      }
      if(!addedOut){
        cancelServerStart(String.format("the {out} key {%s} of a member of {userinfoAttribues} in MappingProperties is present multiple time, startup canceled", userInfoAttribute.getOut()));
      }
    }

    if(!outKeys.contains(this.profileKey)){
      cancelServerStart(String.format("Parameter {profileKey} : {%s} in MappingProperties does not match any out key of {groups}, startup canceled.", this.profileKey));
    }
    if(!outKeys.contains(this.currentEtabUaiKey)){
      cancelServerStart(String.format("Parameter {currentEtabUaiKey} : {%s} in MappingProperties does not match any out key of {groups}, startup canceled.", this.currentEtabUaiKey));
    }
    if(this.profileKey.equals(this.currentEtabUaiKey)){
      cancelServerStart(String.format("Parameter {profileKey} : {%s} and {currentEtabUaiKey} : {%s} in MappingProperties are the same, startup canceled.", this.profileKey, this.currentEtabUaiKey));
    }

    log.info("Loaded properties: {}", this);
  }

  private void cancelServerStart(String exMessage){
    log.error(exMessage);
    ((ConfigurableApplicationContext) ctx).close();
    System.exit(1);
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


