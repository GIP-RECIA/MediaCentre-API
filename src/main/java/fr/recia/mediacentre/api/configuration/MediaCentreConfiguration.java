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

import fr.recia.mediacentre.api.configuration.bean.CategoriesByProfilesProperties;
import fr.recia.mediacentre.api.configuration.bean.GestionAffectationsProperties;
import fr.recia.mediacentre.api.configuration.bean.MappingProperties;
import fr.recia.mediacentre.api.dao.MediaCentreResource;
import fr.recia.mediacentre.api.dao.impl.MediaCentreResourceJacksonImpl;
import fr.recia.mediacentre.api.interceptor.bean.SoffitHolder;
import fr.recia.mediacentre.api.service.mediacentre.MediaCentreService;
import fr.recia.mediacentre.api.service.mediacentre.impl.MediaCentreServiceImpl;
import fr.recia.mediacentre.api.service.mediacentre.impl.MediaCentreServiceMockImpl;
import fr.recia.mediacentre.api.service.mediacentre.impl.MediaCentreServiceMockWithUAIFilterImpl;
import fr.recia.mediacentre.api.service.utils.UserInfosBuilder;
import org.apache.catalina.User;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.client.RestTemplate;

@Configuration
public class MediaCentreConfiguration {


  private final SoffitHolder soffitHolder;

  private final MediaCentreResource mediaCentreResource;

  private final MappingProperties mappingProperties;

  private final UserInfosBuilder userInfosBuilder;

  private final GestionAffectationsProperties gestionAffectationsProperties;

  private final CategoriesByProfilesProperties categoriesByProfilesProperties;

  public MediaCentreConfiguration(SoffitHolder soffitHolder, MappingProperties mappingProperties, UserInfosBuilder userInfosBuilder, MediaCentreResource mediaCentreResource, GestionAffectationsProperties gestionAffectationsProperties, CategoriesByProfilesProperties categoriesByProfilesProperties){
    this.soffitHolder = soffitHolder;
    this.mediaCentreResource = mediaCentreResource;
    this.mappingProperties = mappingProperties;
    this.userInfosBuilder = userInfosBuilder;
    this.gestionAffectationsProperties = gestionAffectationsProperties;
    this.categoriesByProfilesProperties = categoriesByProfilesProperties;
  }




  @ConditionalOnProperty(name="mock.status", havingValue="1")
  @Bean(name = "mediaCentreService")
  public MediaCentreService localFileFilterImpl() { return new MediaCentreServiceMockWithUAIFilterImpl(soffitHolder, mappingProperties, categoriesByProfilesProperties); }

  @ConditionalOnProperty(name="mock.status", havingValue="2")
  @Bean(name = "mediaCentreService")
  public MediaCentreService localFileImpl() {
    return new MediaCentreServiceMockImpl(soffitHolder, mappingProperties, categoriesByProfilesProperties);
  }

  @ConditionalOnMissingBean(type = "MediaCentreService")
  @Bean(name = "mediaCentreService")
  public MediaCentreService httpGetImpl() {
    return new MediaCentreServiceImpl(soffitHolder, mappingProperties,  userInfosBuilder, mediaCentreResource, gestionAffectationsProperties, categoriesByProfilesProperties);
  }
}
