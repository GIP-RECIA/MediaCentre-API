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
package fr.recia.mediacentre.api.interceptor.bean;

import fr.recia.mediacentre.api.configuration.bean.MappingProperties;
import fr.recia.mediacentre.api.service.utils.MapUtils;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class SoffitHolder {


  @Autowired
  MappingProperties mappingProperties;

  private String sub;

  private List<String> profiles;
  private List<String> uaiCurrent;
  private List<String> uaiList;
  private List<String> garId;


  private Map<String, List<String>> otherUserInfoAttributes = new HashMap<>();

  public Map<String, List<String>> getUserInfosWithoutIsMemberOf(){
    Map<String, List<String>> deepCopiedMap = MapUtils.stringListStringDeepCopy(otherUserInfoAttributes);
    deepCopiedMap.put(mappingProperties.getProfiles(), profiles);
    deepCopiedMap.put(mappingProperties.getUaiCurrent(), uaiCurrent);
    deepCopiedMap.put(mappingProperties.getUaiList(), uaiList);
    deepCopiedMap.put(mappingProperties.getGarId(), garId);
    return deepCopiedMap;
  }

}
