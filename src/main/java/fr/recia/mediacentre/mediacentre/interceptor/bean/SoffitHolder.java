/**
 * Copyright © ${project.inceptionYear} GIP-RECIA (https://www.recia.fr/)
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
package fr.recia.mediacentre.mediacentre.interceptor.bean;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class SoffitHolder {

  // sub
  //uid d'une entité dans LDAP
  private String sub;

  // toutes les infos utilisateur necessaires
  private Map<String, List<String>> userInfos;

  public List<String> getUserInfo(@NotNull final String attributeName){
    if(userInfos.containsKey(attributeName)){
      return userInfos.get(attributeName);
    }
    return new ArrayList<>();
  }
}