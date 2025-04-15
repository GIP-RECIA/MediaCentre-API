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
package fr.recia.mediacentre.api.service.config;

import fr.recia.mediacentre.api.model.pojo.Config;
import fr.recia.mediacentre.api.model.pojo.ConfigElement;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ConfigService {

  public List<ConfigElement> getEtabsNames(List<String> uais);

  public List<ConfigElement> getGroups();

}
