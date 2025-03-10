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
package fr.recia.mediacentre.api;

import fr.recia.mediacentre.api.service.utils.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.junit.Assert.assertEquals;


@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles({ "test" })
public class MapUtilsTest {

  private Map<String, List<String>> mapToCopy;

  final String keyA = "keyA";
  final String keyB = "keyB";
  final String keyC = "keyC";

  final String subvalueA1 = "subvalueA1";
  final String subvalueA2 = "subvalueA2";
  final String subvalueA3 = "subvalueA3";

  final String subvalueB1 = "subvalueB1";
  final String subvalueB2 = "subvalueB2";
  final String subvalueB3 = "subvalueB3";

  final String subvalueC1 = "subvalueC1";
  final String subvalueC2 = "subvalueC2";
  final String subvalueC3 = "subvalueC3";

  @Before
  public void initMapToCopy(){
    if(Objects.nonNull(mapToCopy)){
      mapToCopy.clear();
    }else {
      mapToCopy = new HashMap<>();
    }

    List<String> valueA = new ArrayList<>( Arrays.asList(subvalueA1, subvalueA2, subvalueA3));
    List<String> valueB = new ArrayList<>( Arrays.asList(subvalueB1, subvalueB2, subvalueB3));
    List<String> valueC = new ArrayList<>( Arrays.asList(subvalueC1, subvalueC2, subvalueC3));

    mapToCopy.put(keyA, valueA);
    mapToCopy.put(keyB, valueB);
    mapToCopy.put(keyC, valueC);
  }


  @Test
  public void putNotShared_stringListStringDeepCopy_OK() throws Exception {
    Map<String, List<String>> deepCopiedMap = MapUtils.stringListStringDeepCopy(mapToCopy);
    deepCopiedMap.put("KeyD", Arrays.asList("Lorem", "Ipsum"));
    assertEquals(3, mapToCopy.size());
  }

  @Test
  public void removeNotShared_stringListStringDeepCopy_OK() throws Exception {
    Map<String, List<String>> deepCopiedMap = MapUtils.stringListStringDeepCopy(mapToCopy);
    deepCopiedMap.remove(keyA);
    assertEquals(3, mapToCopy.size());
  }


  @Test
  public void valuesNotShared_stringListStringDeepCopy_OK() throws Exception {
    Map<String, List<String>> deepCopiedMap = MapUtils.stringListStringDeepCopy(mapToCopy);
    deepCopiedMap.get(keyA).add("Lorem Ipsum");
    assertEquals(3, mapToCopy.get(keyA).size());
    deepCopiedMap.replace(keyA, Arrays.asList("Lorem", "Ipsum"));
    assertEquals(3, mapToCopy.get(keyA).size());
  }

}
