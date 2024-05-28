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
package fr.recia.mediacentre.mediacentre.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.recia.mediacentre.mediacentre.interceptor.bean.SoffitHolder;
import fr.recia.mediacentre.mediacentre.service.LdapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class LdapServiceImpl implements LdapService {

    @Autowired
    private SoffitHolder soffit;


    // to change
    @Override
    public List<String> getIsMemberOf() throws IOException {
        List<Map<String,String>> isMemberof = null;
        ObjectMapper objectMapper = new ObjectMapper();
        isMemberof = objectMapper.readValue(new File("src/main/resources/static/isMemberOf.json"),List.class);
        List<String> liste = new ArrayList<>();
        int id = 0;
        for(Map<String,String> champ : isMemberof){
            liste.add(id + ": " + champ.get(id++));
        }
        return liste;
    }

}
