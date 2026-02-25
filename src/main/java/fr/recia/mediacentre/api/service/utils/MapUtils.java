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

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class MapUtils {

  public static Map<String, List<String>> stringListStringDeepCopy(Map<String, List<String>> mapToCopy){
    HashMap<String, List<String>> deepCopiedMap = new HashMap<>();

    log.info("map to copy keys {}", String.join(";", mapToCopy.keySet()));
    for(Map.Entry<String, List<String>> entry : mapToCopy.entrySet()){
      String key = entry.getKey();
      List<String> values = new ArrayList<>();

      if(Objects.nonNull(entry.getValue())){
        values.addAll(entry.getValue());
      }
      deepCopiedMap.put(key, values);
    }
    return deepCopiedMap;
  }
}
