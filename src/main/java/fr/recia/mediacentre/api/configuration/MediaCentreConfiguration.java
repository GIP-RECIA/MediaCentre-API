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
package fr.recia.mediacentre.api.configuration;

import fr.recia.mediacentre.api.dao.MediaCentreResource;
import fr.recia.mediacentre.api.dao.impl.MediaCentreResourceJacksonImpl;
import fr.recia.mediacentre.api.service.MediaCentreService;
import fr.recia.mediacentre.api.service.impl.MediaCentreServiceImpl;
import fr.recia.mediacentre.api.service.impl.MediaCentreServiceMockImpl;
import fr.recia.mediacentre.api.service.impl.MediaCentreServiceMockWIthUAIFilterImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class MediaCentreConfiguration {

  @Bean
  public RestTemplate restTemplate() {
      return new RestTemplate();
  }

  @ConditionalOnMissingBean(type = "MediaCentreResource")
  @Bean(name = "mediaCentreResource")
  public MediaCentreResource wsGetResource() { return  new MediaCentreResourceJacksonImpl(); }

  @ConditionalOnProperty(name="mock.status", havingValue="1")
  @Bean(name = "mediaCentreService")
  public MediaCentreService localFileFilterImpl() { return new MediaCentreServiceMockWIthUAIFilterImpl(); }

  @ConditionalOnProperty(name="mock.status", havingValue="2")
  @Bean(name = "mediaCentreService")
  public MediaCentreService localFileImpl() {
    return new MediaCentreServiceMockImpl();
  }

  @ConditionalOnMissingBean(type = "MediaCentreService")
  @Bean(name = "mediaCentreService")
  public MediaCentreService httpGetImpl() {
    return new MediaCentreServiceImpl();
  }
}
