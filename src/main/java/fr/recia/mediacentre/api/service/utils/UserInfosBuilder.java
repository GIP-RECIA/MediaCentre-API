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
package fr.recia.mediacentre.api.service.utils;

import fr.recia.mediacentre.api.configuration.bean.MappingProperties;
import fr.recia.mediacentre.api.interceptor.bean.SoffitHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserInfosBuilder {



  @Autowired
  MappingProperties mappingProperties;

  public  Map<String, List<String>> getUserInfos(SoffitHolder soffitHolder, List<String> isMemberOf){

    //deep copy of the map

    Map<String, List<String>> mapToCopy = soffitHolder.getUserInfosWithoutIsMemberOf();

    Map<String, List<String>> deepCopiedMap = new HashMap<>();

    for(Map.Entry<String, List<String>> entry : mapToCopy.entrySet()){
      String key = String.valueOf( entry.getKey());
      List<String> values = new ArrayList<>();
      for(String value : entry.getValue()){
        values.add(String.valueOf(value));
      }
      deepCopiedMap.put(key, values);
    }
    deepCopiedMap.put(mappingProperties.getGroups().getOut(), isMemberOf);
    return deepCopiedMap;
  }

}
